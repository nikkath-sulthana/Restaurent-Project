package com.nikkath.service;

import com.nikkath.dto.ApiResponse;
import com.nikkath.exception.BadRequestException;
import com.nikkath.exception.ResourceNotFoundException;
import com.nikkath.model.*;
import com.nikkath.dto.OrderResponseDTO;
import com.nikkath.dto.OrderItemResponseDTO;
import com.nikkath.repository.CartRepository;
import com.nikkath.repository.DiningSessionRepository;
import com.nikkath.repository.OrderRepository;

 import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private  DiningSessionRepository diningSessionRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public OrderResponseDTO checkout(Long diningSessionId){
        DiningSession diningSession = diningSessionRepository.findById(diningSessionId)
                .orElseThrow(()-> new ResourceNotFoundException
                        ("Dining session not found with the Id: "+ diningSessionId));

        Cart cart = cartRepository.findByDiningSession(diningSession)
                .orElseThrow(()-> new ResourceNotFoundException
                        ("Cart not found for the dining session: " + diningSessionId));

                if(cart.getItems().isEmpty()){
                    throw new BadRequestException("Cart is empty!");
                }

                Order order = new Order();
                order.setDiningSession(diningSession);
                order.setOrderTime(LocalDateTime.now());
                order.setStatus(OrderStatus.PLACED);

        List<OrderItem> orderItems = new ArrayList<>();

        double totalAmount = 0;
       for(CartItem cartItem: cart.getItems()) {

           OrderItem orderItem = new OrderItem();
           orderItem.setOrder(order);
           orderItem.setMenuItem(cartItem.getMenuItem());
           orderItem.setUnitPrice(cartItem.getUnitPrice());
           orderItem.setQuantity(cartItem.getQuantity());

           double totalPrice = cartItem.getQuantity()*cartItem.getUnitPrice();

           orderItem.setTotalPrice(totalPrice);

           totalAmount +=totalPrice;

           orderItems.add(orderItem);
       }

       order.setItems(orderItems);
       order.setTotalAmount(totalAmount);

       Order savedOrder = orderRepository.save(order);

       cart.getItems().clear();
       cart.setTotalAmount(0);

       cartRepository.save(cart);

       return mapToOrderResponse(savedOrder);
    }


 private OrderResponseDTO mapToOrderResponse(Order order){

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setOrderId(order.getId());
        dto.setDiningSessionId(order.getDiningSession().getId());
        dto.setOrderTime(order.getOrderTime());
        dto.setStatus(order.getStatus());

        List<OrderItemResponseDTO> itemDTOList = new ArrayList<>();

     for (OrderItem orderItem : order.getItems()) {

         OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();

         itemDTO.setMenuItemId(orderItem.getMenuItem().getId());
         itemDTO.setMenuItemName(orderItem.getMenuItem().getName());
         itemDTO.setQuantity(orderItem.getQuantity());
         itemDTO.setUnitPrice(orderItem.getUnitPrice());
         itemDTO.setTotalPrice(orderItem.getTotalPrice());

         itemDTOList.add(itemDTO);
     }

     dto.setItems(itemDTOList);
     dto.setTotalAmount(order.getTotalAmount());


        return dto;
 }


 public OrderResponseDTO getOrderById(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return mapToOrderResponse(order);
    }


    public List<OrderResponseDTO> getAllOrders(){

         List <Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {
            responseList.add(mapToOrderResponse(order));
        }

        return responseList;
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));

        order.setStatus(status);

        Order updatedOrder = orderRepository.save(order);

        return mapToOrderResponse(updatedOrder);
    }

    public List<OrderResponseDTO> getOrdersByStatus(OrderStatus status) {

        List<Order> orders = orderRepository.findByStatus(status);

        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {
            responseList.add(mapToOrderResponse(order));
        }

        return responseList;
    }

    @Transactional
    public ApiResponse cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.PLACED) {
            throw new BadRequestException(
                    "Only placed orders can be cancelled.");
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        return new ApiResponse("Order cancelled successfully.");
    }

}


