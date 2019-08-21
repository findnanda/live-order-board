package com.liveorder.board.validation;

import com.liveorder.board.model.CreateOrderRequest;

public interface OrderValidationService {

	void validateOrder(CreateOrderRequest createOrderRequest);

}
