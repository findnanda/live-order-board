package com.liveorder.board.model;

public class OrderSummary {
	private final String orderType;
	private final String quantity;
	private final String price;

	public OrderSummary(String orderType, String quantity, String price) {
		this.orderType = orderType;
		this.quantity = quantity;
		this.price = price;
	}

	public String getOrderType() {
		return orderType;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getPrice() {
		return price;
	}
}
