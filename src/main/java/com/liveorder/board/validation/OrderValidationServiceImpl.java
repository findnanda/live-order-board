package com.liveorder.board.validation;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.liveorder.board.exception.InvalidOrder;
import com.liveorder.board.model.CreateOrderRequest;
import com.liveorder.board.model.OrderType;

@Component
public class OrderValidationServiceImpl implements OrderValidationService {

	private static final String PRICE_REGEX = "^[£][\\d]+[.]{0,1}[\\d]+$";
	private static final String QTY_REGEX = "^[\\d]+([.]{0,1}[\\d]{0,})[\\s]\\bkg\\b$";
	private static final List<String> ORDER_TYPES = Arrays.asList(OrderType.BUY.name(), OrderType.SELL.name());

	@Override
	public void validateOrder(final CreateOrderRequest createOrderRequest) {
		if (createOrderRequest == null) {
			throw new InvalidOrder("Invalid order, order values missing.");
		}
		validatePrice(createOrderRequest.getPrice());
		validateQuantity(createOrderRequest.getQuantity());
		validateOrderType(createOrderRequest.getOrderType());
	}

	private void validatePrice(String price) {
		if (price.isEmpty() || !price.isEmpty() && !Pattern.matches(PRICE_REGEX, price)) {
			throw new InvalidOrder("Invalid price");
		}
	}

	private void validateQuantity(String quantity) {
		if (quantity.isEmpty() || !quantity.isEmpty() && !Pattern.matches(QTY_REGEX, quantity)) {
			throw new InvalidOrder("Invalid quantity");
		}
	}

	private void validateOrderType(String type) {
		if (!ORDER_TYPES.contains(type)) {
			throw new InvalidOrder("Invalid order type");
		}
	}
}
