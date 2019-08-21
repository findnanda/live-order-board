package com.liveorder.board.service;

import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.liveorder.board.exception.InvalidOrder;
import com.liveorder.board.model.CreateOrderRequest;
import com.liveorder.board.model.OrderSummary;

public interface LiveOrderService {
	
	int registerOrder(CreateOrderRequest createOrderReques) throws InvalidOrder;
	
	boolean cancelOrder(int orderId);
	
	ImmutableMap<String, List<OrderSummary>> getLiveOrderSummary();
}
