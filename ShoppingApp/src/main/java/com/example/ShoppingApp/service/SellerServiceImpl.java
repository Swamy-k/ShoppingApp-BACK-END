package com.example.ShoppingApp.service;

import com.example.ShoppingApp.constants.ProductStatus;
import com.example.ShoppingApp.dto.ProductDTO;
import com.example.ShoppingApp.dto.UserDTO;
import com.example.ShoppingApp.entity.Address;
import com.example.ShoppingApp.entity.Login;
import com.example.ShoppingApp.entity.Product;
import com.example.ShoppingApp.entity.Seller;
import com.example.ShoppingApp.exception.AddressNotFoundException;
import com.example.ShoppingApp.exception.SellerAlreadyExistException;
import com.example.ShoppingApp.exception.SellerNotFoundException;
import com.example.ShoppingApp.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private AddressService addressService;

    @Override
    public Seller addSeller(Seller seller) {

        Optional<Seller> checkSeller = sellerRepository.findByUserName(seller.getUserName());

        if (checkSeller.isEmpty()) {
            return sellerRepository.save(seller);
        } else {
            throw new SellerAlreadyExistException("Seller Already Exist, try different username & password");
        }
    }

    @Override
    public String removeSeller(UserDTO userInfo) {

        Optional<Seller> seller = sellerRepository.findByUserName(userInfo.getUserName());

        //checing the username & password
        if (seller.isPresent() && seller.get().getUserPassword().equals(userInfo.getUserPassword())) {
            sellerRepository.delete(seller.get());
        } else {
            throw new SellerNotFoundException("username/password is wrong. Please provide the correct details to perform this operation");
        }

        return "Successfully deleted " + userInfo.getUserName() + "'s Account from the database";
    }

    @Override
    public List<Seller> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();

        if (sellers.isEmpty()) {
            throw new SellerNotFoundException("No seller added");
        }
        return sellers;
    }


    @Override
    public Seller updateSeller(UserDTO sellerInfo, Long sellerId) {

        Optional<Seller> opt = sellerRepository.findById(sellerId);

        if (opt.isPresent()) {

            Seller seller = opt.get();
            if (sellerInfo.getEmail() != null) {
                seller.setEmail(sellerInfo.getEmail());
            }

            if (sellerInfo.getFirstName() != null) {
                seller.setFirstName(sellerInfo.getFirstName());
            }

            if (sellerInfo.getLastName() != null) {
                seller.setLastName(sellerInfo.getLastName());
            }

            if (sellerInfo.getMobileNumber() != null) {
                seller.setMobileNumber(sellerInfo.getMobileNumber());
            }

            if (sellerInfo.getUserName() != null) {
                seller.setUserName(sellerInfo.getUserName());
            }

            if (sellerInfo.getUserPassword() != null) {
                seller.setUserPassword(sellerInfo.getUserPassword());
            }

            return sellerRepository.save(seller);

        } else {
            throw new SellerNotFoundException("No seller exists with the given id!");
        }
    }


    @Override
    public Seller addProducts(Long sellerId, Product product) {

        //We only accept product if it has quantity > 1, so changing the status to AVAILLABLE
        product.setProductStatus(ProductStatus.AVAILABLE);

        Optional<Seller> checkSeller = sellerRepository.findById(sellerId);
        Seller updatedSeller = new Seller();
        if (checkSeller.isPresent()) {
            updatedSeller = checkSeller.get();
        }

        //we will need to let customer to add address only if he provides address
        if (updatedSeller.getAddresses().isEmpty())
            throw new AddressNotFoundException("Add the address first to add the products");

        //adds product in seller list
        updatedSeller.getProducts().add(product);

        //provides bi-directional relationship
        productService.addProduct(updatedSeller, product);

        if (checkSeller.isPresent()) {
            sellerRepository.save(updatedSeller);
        } else {
            throw new SellerNotFoundException("seller not found");
        }

        return updatedSeller;
    }

    @Override
    public Seller findByUsernameAndPassword(String username, String password) {

        Optional<Seller> seller = sellerRepository.findByUserNameAndUserPassword(username, password);

        if (seller.isPresent()) {
            return seller.get();
        } else {
            throw new SellerNotFoundException("No such seller. Please check the provided details.");
        }
    }


    @Override
    public Seller persistSeller(Long sellerId, Login login) {
        Optional<Seller> temp = sellerRepository.findById(sellerId);
        Seller seller = temp.get();
        seller.setLogin(login);
        return sellerRepository.save(seller);

    }


    @Override
    public Seller addSellerAddress(Long sellerId, Address address) {

        Optional<Seller> getSeller = sellerRepository.findById(sellerId);

        if (getSeller.isPresent()) {
            //setting the referece of seller in his address
            address.setUser(getSeller.get());

            //saving the address with seller reference
            Address savedAddress = addressService.addAddress(address);

            //adding the address in seller address to get bi-directional relationship
            getSeller.get().getAddresses().add(address);

            return sellerRepository.save(getSeller.get());
        } else {
            throw new SellerAlreadyExistException("Seller with the given username already exists.");
        }
    }


    @Override
    public Seller updateProductStatus(Long sellerId, Long productId) {
        Optional<Seller> seller = sellerRepository.findById(sellerId);

        boolean flag = false;

        if (seller.isPresent()) {

            //if we find the product with given Id we are making the product status UNAVAILLABLE
            for (int i = 0; i < seller.get().getProducts().size(); i++) {
                if (Objects.equals(seller.get().getProducts().get(i).getProductId(), productId))
                    seller.get().getProducts().get(i).setProductStatus(ProductStatus.UNAVAILABLE);
            }
            //To persist the seller in database
            Seller saveSeller = sellerRepository.save(seller.get());

            productService.updateProductStatus(productId);

            return saveSeller;
        } else {
            throw new SellerNotFoundException("No such seller. Please check the provided details.");
        }
    }


    @Override
    public Seller updateProducts(Long sellerId, Long productId, ProductDTO product) {
        Optional<Seller> seller = sellerRepository.findById(sellerId);

        boolean flag = false;

        if (seller.isPresent()) {

            for (int i = 0; i < seller.get().getProducts().size(); i++) {
                if (Objects.equals(seller.get().getProducts().get(i).getProductId(), productId)) {

                    if (product.getProductName() != null) {
                        seller.get().getProducts().get(i).setProductName(product.getProductName());
                    }

                    if (product.getProductDescription() != null) {
                        seller.get().getProducts().get(i).setProductDescription(product.getProductDescription());
                    }

                    if (product.getPrice() != null) {
                        seller.get().getProducts().get(i).setPrice(product.getPrice());
                    }

                    if (product.getQuantity() != null) {
                        seller.get().getProducts().get(i).setQuantity(product.getQuantity());
                    }

                    if (product.getCategory() != null) {
                        seller.get().getProducts().get(i).setCategory(product.getCategory());
                    }
                }
            }

            Seller saveSeller = sellerRepository.save(seller.get());

            productService.updateProduct(productId, product);

            return saveSeller;
        } else {
            throw new SellerNotFoundException("No such seller. Please check the provided details.");
        }

    }

    @Override
    public Seller removeSellerAddress(Long sellerId, Long addressId) {
       
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        boolean flag = false;
        if (seller.isPresent()) {
            //check if the seller address is same as provided address Id
            for (int i = 0; i < seller.get().getAddresses().size(); i++) {
                if (seller.get().getAddresses().get(i).getAddressId() == addressId) {
                    seller.get().getAddresses().remove(i);
                    flag = true;
                }
            }
            //if the address Id is not present throw exception
            if (!flag) {
                throw new AddressNotFoundException("Address Not Found");
            }

            Seller savedSeller = sellerRepository.save(seller.get());
            addressService.deleteAddress(addressId);
            return savedSeller;
        } else {
            throw new SellerNotFoundException("No such seller. Please check the provided details.");
        }
    }

    @Override
    public List<Product> getAllProductsBySeller(Long sellerId) {

        Optional<Seller> seller = sellerRepository.findById(sellerId);

        if (seller.isPresent()) {

            return seller.get().getProducts();
        } else {
            throw new SellerNotFoundException("No such seller. Please check the provided details.");
        }
    }

    @Override
    public Seller getSellerById(Long sellerId) {
        Optional<Seller> opt = sellerRepository.findById(sellerId);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new SellerNotFoundException("No such customer. Please check the provided details.");
        }
    }
}
