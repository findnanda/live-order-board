package com.liveorder.board;

import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.liveorder.board.model.CreateOrderRequest;
import com.liveorder.board.repository.InMemoryOrderStore;
import com.liveorder.board.repository.OrderRepository;
import com.liveorder.board.service.LiveOrderService;
import com.liveorder.board.service.LiveOrderServiceImpl;
import com.liveorder.board.validation.OrderValidationServiceImpl;

//  A Very quick intergation test written just to check if it's working as expected. this can be written properly with various scenarios
public class LiveOrderBoardIntegrationTest {
	private static final Log LOGGER = LogFactory.getLog(LiveOrderBoardIntegrationTest.class);
	private LiveOrderService los;

	@Before
	public void setUp() {
		OrderRepository orderRepository = new OrderRepository(new InMemoryOrderStore());
		los = new LiveOrderServiceImpl(orderRepository, new OrderValidationServiceImpl());
	}

	@Test
	public void testLiveSellAndBuyOrdersSuccess() {
		createSellOrders(los);
		createBuyOrders(los);
		LOGGER.info("**************** Display live order ***************");
		los.getLiveOrderSummary().entrySet().stream().forEach(es -> {
			es.getValue().stream()
					.forEach(v -> LOGGER.info(v.getOrderType() + ":" + v.getQuantity() + " for " + v.getPrice()));
		});
		LOGGER.info("***************************************************");
		assertEquals(4, los.getLiveOrderSummary().get("BUY").size());
		assertEquals("2.25,4.00,3.25,8.20", los.getLiveOrderSummary().get("BUY").stream().map(o -> o.getQuantity())
				.collect(Collectors.joining(",")));
		assertEquals(4, los.getLiveOrderSummary().get("SELL").size());

	}

	private static void createBuyOrders(LiveOrderService los) {
		los.registerOrder(createOrder("BUY", "£250.00", "1.50 kg", "user1"));
		los.registerOrder(createOrder("BUY", "£250.00", "2.50 kg", "user3"));
		los.registerOrder(createOrder("BUY", "£100.05", "3.25 kg", "user2"));
		los.registerOrder(createOrder("BUY", "£50.10", "3.20 kg", "user3"));
		los.registerOrder(createOrder("BUY", "£50.10", "5 kg", "user1"));
		los.registerOrder(createOrder("BUY", "£300.00", "2.25 kg", "user1"));
	}

	private static void createSellOrders(LiveOrderService los) {
		los.registerOrder(createOrder("SELL", "£200.00", "12.50 kg", "user1"));
		los.registerOrder(createOrder("SELL", "£200.00", "12.50 kg", "user3"));
		los.registerOrder(createOrder("SELL", "£100.05", "10.25 kg", "user2"));
		los.registerOrder(createOrder("SELL", "£50.10", "10.20 kg", "user3"));
		los.registerOrder(createOrder("SELL", "£50.10", "5 kg", "user1"));
		los.registerOrder(createOrder("SELL", "£300.00", "2.50 kg", "user1"));
	}

	private static CreateOrderRequest createOrder(String type, String price, String qty, String userId) {
		return new CreateOrderRequest(userId, qty, type, price);
	}
}
