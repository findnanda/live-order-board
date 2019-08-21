package com.liveorder.board.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.liveorder.board.exception.InvalidOrder;
import com.liveorder.board.model.CreateOrderRequest;
import com.liveorder.board.model.Order;
import com.liveorder.board.model.OrderSummary;
import com.liveorder.board.model.OrderType;
import com.liveorder.board.repository.OrderRepository;
import com.liveorder.board.util.OrderFunctionsUtil;
import com.liveorder.board.validation.OrderValidationService;

@Service
public class LiveOrderServiceImpl implements LiveOrderService {

	private static final Log LOGGER = LogFactory.getLog(LiveOrderServiceImpl.class);

	private final OrderRepository orderRepository;
	private OrderValidationService orderValidationService;

	public LiveOrderServiceImpl(final OrderRepository orderRepository,
			final OrderValidationService orderValidationService) {
		this.orderRepository = orderRepository;
		this.orderValidationService = orderValidationService;
	}

	public int registerOrder(final CreateOrderRequest createOrderRequest) throws InvalidOrder {
		orderValidationService.validateOrder(createOrderRequest);
		int orderId = orderRepository.createOrder(OrderFunctionsUtil.XFORM_TO_ORDER.apply(createOrderRequest));
		LOGGER.info("Order created, orderId=" + orderId);
		return orderId;
	}

	public boolean cancelOrder(int orderId) {
		return orderId != 0 ? orderRepository.cancelOrder(orderId) : false;

	}

	public ImmutableMap<String, List<OrderSummary>> getLiveOrderSummary() {
		LOGGER.info("Fetching live orders");
		final Map<OrderType, Map<String, BigDecimal>> orders = orderRepository.findAllOrders().stream().collect(
				Collectors.groupingBy(Order::getOrderType, Collectors.groupingBy(o -> o.getPrice().toPlainString(),
						Collectors.reducing(BigDecimal.ZERO, Order::getOrderQuantity, BigDecimal::add))));
		
		return ImmutableMap.<String, List<OrderSummary>>builder()
				.put(OrderType.BUY.name(),
						OrderFunctionsUtil.AGGREGATE_BUY_ORDERS_BY_PRICE.apply(orders.get(OrderType.BUY)))
				.put(OrderType.SELL.name(),
						OrderFunctionsUtil.AGGREGATE_SELL_ORDERS_BY_PRICE.apply(orders.get(OrderType.SELL)))
				.build();
	}
}
