package com.liveorder.board.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.liveorder.board.model.Order;

//Mimic the database table, note this class is not threadsafe is terms of add and removing items from the list 
@Component
public class InMemoryOrderStore {
	private final AtomicInteger idGenerator;
	private final List<Order> orders;

	public InMemoryOrderStore() {
		orders = new LinkedList<>();
		idGenerator = new AtomicInteger(1);
	}

	public List<Order> loadOrders() {
		return new ArrayList<>(this.orders);
	}

	public int addOrder(final Order order) {
		order.setOrderId(idGenerator.getAndIncrement());
		orders.add(order);
		return order.getOrderId();
	}

	public boolean deleteOrder(int orderId) {
		return orders.removeIf(o -> o.getOrderId() == orderId);
	}
}
