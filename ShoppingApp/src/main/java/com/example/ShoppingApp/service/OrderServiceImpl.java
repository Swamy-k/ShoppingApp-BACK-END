package com.example.ShoppingApp.service;

import com.example.ShoppingApp.constants.ProductCategory;
import com.example.ShoppingApp.dto.OrderDTO;
import com.example.ShoppingApp.dto.PlaceOrderDTO;
import com.example.ShoppingApp.entity.*;
import com.example.ShoppingApp.exception.AddressNotFoundException;
import com.example.ShoppingApp.exception.CardDetailsNotFoundException;
import com.example.ShoppingApp.exception.EmptyCartException;
import com.example.ShoppingApp.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO getOrderStatus(Long customerId) {

        //getting customer using customerID
        Customer customer = customerService.getCustomerById(customerId);

        OrderDTO order = new OrderDTO();

        //getting the cart list of that customer
        List<Item> cartList = customer.getCart().getItems();

        if (cartList.isEmpty())
            throw new EmptyCartException("No Products are found in the cart to order");

        order.setOrderedItems(cartList);

        for (Item element : cartList) {

            ProductCategory category = element.getProduct().getCategory();

            Product product = element.getProduct();

            //calculating gst for each categories
            if (category == ProductCategory.ELECTRONICS) {
                Double itemGST = element.getProduct().getPrice() * 0.18;
                if (order.getGst() != null) {
                    order.setGst(order.getGst() + itemGST);
                } else {
                    order.setGst(itemGST);
                }
            } else if (category == ProductCategory.CLOTHES) {
                Double itemGST = element.getProduct().getPrice() * 0.12;
                if (order.getGst() != null) {
                    order.setGst(order.getGst() + itemGST);
                } else {
                    order.setGst(itemGST);
                }
            } else if (category == ProductCategory.KITCHEN) {
                Double itemGST = element.getProduct().getPrice() * 0.18;
                if (order.getGst() != null) {
                    order.setGst(order.getGst() + itemGST);
                } else {
                    order.setGst(itemGST);
                }
            }
        }

        //cost of all items excluding gst, delivery
        double total = customer.getCart().getCartTotal();
        order.setTotalAmount(total);

        //setting delivery cost
        order.setDeliveryCharge(40.0);

        //final cost paid by customer
        order.setTotalAmount(total + order.getGst() + order.getDeliveryCharge());
        return order;

    }


    @Override
    public Order createOrder(long customerId, String lastFourDigitsOfCardUsed, long addressId) {

        //Calling the order status method to get the details of the order cost
        OrderDTO orderDetails = this.getOrderStatus(customerId);

        List<Item> itemsOrdered = cartService.sendToOrder(customerId);

        //Creating the order using the cart of the user and the orderDetails
        Order order = new Order();

        //Creation of shipment
        Shipment shipment = new Shipment();
        shipment.setShippedTo(addressService.getAddressById(addressId).toString());

        shipment.setShippedFrom(itemsOrdered.get(0).getProduct().getSeller().getAddresses().get(0).toString());
        shipment.setExpectedDate(LocalDate.now().plusDays(7));

        //Creation of order
        order.setCardUsedForPayment("XXXXXXXX".concat(lastFourDigitsOfCardUsed));
        order.setDeliveryCharge(orderDetails.getDeliveryCharge());
        if (orderDetails.getGst() != null) {
            order.setGst(orderDetails.getGst());
        }
        order.setItemsCost(orderDetails.getItemsCost());
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setOrderedItems(itemsOrdered);
        order.setShipment(shipment);

        //Send the order to the customer to be added to the order list
        customerService.addCustomerOrder(customerId, order);
        return order;
    }

    //This method persists the order (Called internally only, part of order placement flow)
    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);

    }

    //Calling this method will trigger the order creation

    @Override
    public Order placeOrder(long customerId, PlaceOrderDTO paymentInfo) {

        int addressId = paymentInfo.getAddressId();

        //Checking if the addressId exists
        if (!addressService.checkAddressId(customerId)) {
            throw new AddressNotFoundException("No Address exists for this ID.");
        }

        //Checking if the card is available
        Card cardUsedForPayment = customerService.getCustomerById(customerId).getCardDetails();
        if (cardUsedForPayment == null) {
            throw new CardDetailsNotFoundException("Please add a card for payment before proceeding.");
        }

        return this.createOrder(customerId, cardUsedForPayment.getCardNumber().substring(cardUsedForPayment.getCardNumber().length() - 4), addressId);

    }

}
