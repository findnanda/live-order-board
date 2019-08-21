package com.liveorder.board.model;

import java.math.BigDecimal;

public class Order {
	private final String userId;
	private final BigDecimal orderQuantity;
	private final OrderType orderType;
	private final BigDecimal price;
	private int orderId;
	
	public Order(String userId, final BigDecimal orderQuantity, final OrderType orderType, final BigDecimal price) {
		this.userId = userId;
		this.orderQuantity = orderQuantity;
		this.orderType = orderType;
		this.price = price;
	}

	public String getUserId() {
		return userId;
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
