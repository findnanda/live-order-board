package com.liveorder.board;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.liveorder.board.repository.InMemoryOrderStore;
import com.liveorder.board.repository.OrderRepository;
import com.liveorder.board.service.LiveOrderService;
import com.liveorder.board.service.LiveOrderServiceImpl;
import com.liveorder.board.validation.OrderValidationServiceImpl;

/**
 * Added for Spring bean dependency injection although I am not using it as I am not sure if I can use such external api for demonstration.
 *
 */
@Component
public class LiveOrderBoardFactory {
	private static final Log LOGGER = LogFactory.getLog(LiveOrderBoardFactory.class);
	private LiveOrderService liveOrderService;

	public LiveOrderBoardFactory(LiveOrderService LiveOrderService){
		this.liveOrderService = liveOrderService;
	}
	
	public LiveOrderService createLiveOrderService() {
		OrderRepository orderRepository = new OrderRepository(new InMemoryOrderStore()); 
		return new LiveOrderServiceImpl(orderRepository, new OrderValidationServiceImpl());
	}
}
