package com.example.ShoppingApp.service;

import com.example.ShoppingApp.constants.ProductCategory;
import com.example.ShoppingApp.constants.ProductStatus;
import com.example.ShoppingApp.dto.ProductDTO;
import com.example.ShoppingApp.entity.Product;
import com.example.ShoppingApp.entity.Seller;
import com.example.ShoppingApp.exception.ProductNotFoundException;
import com.example.ShoppingApp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Seller seller, Product product) {
        product.setSeller(seller);
        return productRepository.save(product);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProductStatusToOutOfStock(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            product.get().setProductStatus(ProductStatus.OUT_OF_STOCK);
            return product.get();
        } else {
            throw new ProductNotFoundException("No product found for this given id");
        }

    }

    @Override
    public Product updateProductStatusToUnAvailable(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            product.get().setProductStatus(ProductStatus.UNAVAILABLE);
            return product.get();
        } else {
            throw new ProductNotFoundException("No Product found for this given id");
        }
    }

    @Override
    public Product updateProductStatusToAvailable(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            product.get().setProductStatus(ProductStatus.AVAILABLE);
            return product.get();
        } else {
            throw new ProductNotFoundException("No Product found for this given id");
        }

    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Product reduceQuantity(Long id, int quantityToReduce) {
        Optional<Product> checkProduct = productRepository.findById(id);
        Product updatedProduct = null;


        if (checkProduct.isPresent()) {
            updatedProduct = checkProduct.get();
            updatedProduct.setQuantity(updatedProduct.getQuantity() - quantityToReduce);

            if (updatedProduct.getQuantity() == 0) {
                updatedProduct.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }

            productRepository.save(updatedProduct);
        }

        return updatedProduct;
    }


    @Override
    public String updateProduct(Long productId, ProductDTO productDTO) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ProductNotFoundException("Product not found for the given id");
        } else {
            if (productDTO.getProductName() != null) {
                product.get().setProductName(productDTO.getProductName());
            }
            if (productDTO.getProductDescription() != null) {
                product.get().setProductDescription(productDTO.getProductDescription());
            }
            if (productDTO.getPrice() != null) {
                product.get().setPrice(productDTO.getPrice());
            }

            if (productDTO.getQuantity() != null) {
                product.get().setQuantity(productDTO.getQuantity());
            }

            if (productDTO.getCategory() != null) {
                product.get().setCategory(productDTO.getCategory());
            }


            productRepository.save(product.get());
            return "product with ID : " + productId + " updated.";

        }


    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> allAvailableProducts = productRepository.findByProductStatus(ProductStatus.AVAILABLE);
        List<Product> allOutOfStockProducts = productRepository.findByProductStatus(ProductStatus.OUT_OF_STOCK);
        allAvailableProducts.addAll(allOutOfStockProducts);
        if (allAvailableProducts.isEmpty()) {
            throw new ProductNotFoundException("no product found for this given id");
        }

        return allAvailableProducts;
    }

    @Override
    public List<Product> getProductsByCategory(ProductCategory category) {
        List<Product> productsCategory = productRepository.findByCategory(category);
        if (productsCategory.isEmpty()) {
            throw new ProductNotFoundException("No product found in this category");
        } else {
            return productsCategory;
        }
    }

    @Override
    public String updateProductStatus(Long productId) {

        Optional<Product> checkProduct = productRepository.findById(productId);

        if (checkProduct.isPresent()) {
            checkProduct.get().setQuantity(0);
            checkProduct.get().setProductStatus(ProductStatus.UNAVAILABLE);
            productRepository.save(checkProduct.get());

            return "product with ID : " + productId + " product status updated.";
        } else {
            throw new ProductNotFoundException("No product found in the given id");
        }

    }
}
