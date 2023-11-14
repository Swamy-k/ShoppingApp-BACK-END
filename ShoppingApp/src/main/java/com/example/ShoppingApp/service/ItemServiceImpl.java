package com.example.ShoppingApp.service;

import com.example.ShoppingApp.constants.ProductStatus;
import com.example.ShoppingApp.entity.Item;
import com.example.ShoppingApp.entity.Product;
import com.example.ShoppingApp.exception.ProductNotFoundException;
import com.example.ShoppingApp.repository.ItemRepository;
import com.example.ShoppingApp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item addItem(Item item) {

        Optional<Product> product = productRepository.findById(item.getProduct().getProductId());

        if (product.isPresent()) {
            Product productCheck = product.get();
            if (productCheck.getQuantity() >= item.getRequiredQuantity() && productCheck.getProductStatus() == ProductStatus.AVAILABLE) {
                item.setItemPrice(productCheck.getPrice() * item.getRequiredQuantity());
                item.setProduct(productCheck);
                return itemRepository.save(item);

            } else {
                throw new ProductNotFoundException("Product quantity is not enough");
            }
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public String removeItem(Item item) {
        Optional<Item> itemToRemove = itemRepository.findById(item.getItemId());

        if (itemToRemove.isPresent()) {
            itemRepository.deleteById(item.getItemId());
        } else {
            throw new ProductNotFoundException("Product does not exist in your cart");
        }
        return "Product with " + item.getItemId() + " is deleted from your cart";
    }

    @Override
    public Item addItem(Product product, int quantity) {
        Item item = new Item();
        item.setItemPrice(product.getPrice() * quantity);
        item.setProduct(product);
        item.setRequiredQuantity(quantity);

        return addItem(item);
    }
}
