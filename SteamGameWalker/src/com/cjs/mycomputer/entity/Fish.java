package com.cjs.mycomputer.entity;

public class Fish extends Animal{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5891155436546327829L;

	@Override
	protected void move() {
		System.out.println("Fish moving!");
	}

	@Override
	protected float calculatePrice(float price) {
		return (float) (price*3-0.8);
	}
	
}
