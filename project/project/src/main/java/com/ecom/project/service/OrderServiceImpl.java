package com.ecom.project.service;

import com.ecom.project.payload.OrderDTO;

public class OrderServiceImpl implements OrderService {

    @Override
    public OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod,
                               String pgName, String pgPaymentId, String pgStatus,
                               String pgResponseMessage) {
        return null;
    }
}
