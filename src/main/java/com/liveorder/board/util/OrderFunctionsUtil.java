package com.liveorder.board.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.liveorder.board.model.CreateOrderRequest;
import com.liveorder.board.model.Order;
import com.liveorder.board.model.OrderSummary;
import com.liveorder.board.model.OrderType;

public class OrderFunctionsUtil {
	private static final Function<String, BigDecimal> XFORM_TO_BG_PRICE = s -> new BigDecimal(s.substring(1));
	
	private static final Function<String, BigDecimal> XFORM_TO_BG_QTY = s -> new BigDecimal(s.split(" ")[0]);
	
	private static final Comparator<BigDecimal> SORT_ASC = (bg1, bg2) -> bg1.compareTo(bg2);
	
	public static final Function<CreateOrderRequest, Order> XFORM_TO_ORDER = or -> new Order(or.getUserId(),
			XFORM_TO_BG_QTY.apply(or.getQuantity()), OrderType.valueOf(or.getOrderType()),
			XFORM_TO_BG_PRICE.apply(or.getPrice()));

	public static final Function<Map<String, BigDecimal>, List<OrderSummary>> AGGREGATE_BUY_ORDERS_BY_PRICE = bo -> bo != null ? parallelStream(
			bo.entrySet()).sorted((es1, es2) -> SORT_ASC.reversed().compare(new BigDecimal(es1.getKey()), new BigDecimal(es2.getKey())))
					.map(es -> new OrderSummary(OrderType.BUY.name(), es.getValue().toPlainString(), es.getKey()))
					.collect(Collectors.toList()): new ArrayList<>();

	public static final Function<Map<String, BigDecimal>, List<OrderSummary>> AGGREGATE_SELL_ORDERS_BY_PRICE = bo -> bo != null ?
			bo.entrySet().parallelStream().sorted((es1, es2) -> SORT_ASC.compare(new BigDecimal(es1.getKey()), new BigDecimal(es2.getKey())))
			.map(es -> new OrderSummary(OrderType.SELL.name(), es.getValue().toPlainString(), es.getKey()))
			.collect(Collectors.toList()): new ArrayList<>();

	public static <T> Stream<T> parallelStream(Collection<T> c) {
		return c != null && !c.isEmpty() ? c.parallelStream() : Stream.empty();
	}
}
