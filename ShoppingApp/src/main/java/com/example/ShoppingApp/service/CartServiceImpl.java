package com.example.ShoppingApp.service;

import com.example.ShoppingApp.constants.ProductStatus;
import com.example.ShoppingApp.entity.Cart;
import com.example.ShoppingApp.entity.Customer;
import com.example.ShoppingApp.entity.Item;
import com.example.ShoppingApp.exception.ProductAlreadyFoundException;
import com.example.ShoppingApp.exception.ProductNotAvailableException;
import com.example.ShoppingApp.exception.ProductQuantityNotEnoughException;
import com.example.ShoppingApp.repository.CartRepository;
import com.example.ShoppingApp.repository.CustomerRepository;
import com.example.ShoppingApp.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductService productService;


    @Override
    public Cart saveCart(Customer customer, Item item) {
        Long cartId = (customer.getCart()).getCartId();
        List<Item> itemsList = getAllItem(customer.getCart());
        List<Item> listOfItems = getAllItem(customer.getCart());

        for (Item itemCheck : listOfItems) {
            if (itemCheck.getProduct().getProductId() == item.getProduct().getProductId()) {
                throw new ProductAlreadyFoundException("Product already present in cart, please try to update quantity");
            }
        }

        Cart cart = cartRepository.findByCartId(cartId);
        cart.getItems().add(item);

        cart.setCartTotal((cart.getCartTotal() == null) ? 0 + (double) item.getItemPrice() : cart.getCartTotal().doubleValue() + (double) item.getItemPrice());
        return cartRepository.save(cart);
    }

    @Override
    public List<Item> getAllItem(Cart cart) {
        Optional<Cart> items = cartRepository.findById(cart.getCartId());
        return items.get().getItems();
    }

    /*
     * Removes the Item from the user cart, but it keeps it in DB
     * Returns the list of items deleted from the cart
     */
    @Override
    public List<Item> sendToOrder(long customerId) {

        Long cartId = customerRepository.findByUserId(customerId).getCart().getCartId();

        Cart cart = cartRepository.findByCartId(cartId);

        List<Item> ordereredItems = new ArrayList<>(cart.getItems());

        for (Item item : ordereredItems) {
            if (item.getProduct().getQuantity() < item.getRequiredQuantity()) {
                throw new ProductQuantityNotEnoughException("Only " + item.getProduct().getQuantity() + " units of " + item.getProduct().getProductName() + " are available. Required quantity is: " + item.getRequiredQuantity() + ". Cannot Proceed with purchase.");
            }

            if (item.getProduct().getProductStatus() != ProductStatus.AVAILABLE) {
                throw new ProductNotAvailableException("Product " + item.getProduct().getProductName() + " is not available any more.");
            }
        }

        //If the quantity if enough, we reduce the quantity
        for (Item item : ordereredItems) {
            productService.reduceQuantity(item.getProduct().getProductId(), item.getRequiredQuantity());
        }
        cart.getItems().clear();
        cart.setCartTotal(0.0);
        cartRepository.save(cart);

        return ordereredItems;
    }


}
