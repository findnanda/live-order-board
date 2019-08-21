package com.liveorder.board.model;

public class CreateOrderRequest {

	private final String userId;
	private final String quantity;
	private final String orderType;
	private final String price;

	public CreateOrderRequest(String userId, String quantity, String orderType, String price) {
		this.userId = userId;
		this.quantity = quantity;
		this.orderType = orderType;
		this.price = price;
	}

	public String getUserId() {
		return userId;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getOrderType() {
		return orderType;
	}

	public String getPrice() {
		return price;
	}
}
