package com.liveorder.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liveorder.board.model.Order;

@Repository
public class OrderRepository {
	private final InMemoryOrderStore inMemoryOrderStore;

	public OrderRepository(InMemoryOrderStore inMemoryOrderStore) {
		this.inMemoryOrderStore = inMemoryOrderStore;
	}
	
	public int createOrder(final Order order) {
		return inMemoryOrderStore.addOrder(order);
	}
	
	public boolean cancelOrder(int orderId) {
		return inMemoryOrderStore.deleteOrder(orderId);
	}
	
	public List<Order> findAllOrders(){
		return inMemoryOrderStore.loadOrders();
	}
}
