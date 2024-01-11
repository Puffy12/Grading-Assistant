package edu.csus.csc131.ga.controller;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 8119310450716704068L;

	public BadRequestException(String msg) {
		super(msg);
	}
    
}