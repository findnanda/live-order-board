package com.liveorder.board.exception;

public class InvalidOrder extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidOrder(String message) {
		super(message);
	}
}
