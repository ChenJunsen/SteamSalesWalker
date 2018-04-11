package com.cjs.mycomputer.entity;

import java.io.Serializable;

public abstract class Animal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int age;
	private float weight;
	private float height;
	private float price;
	
	public Animal() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getPrice() {
		return calculatePrice(price);
	}

	@Override
	public String toString() {
		return "Animal [name=" + name + ", age=" + age + ", weight=" + weight + ", height=" + height + ", price="
				+ price + "]";
	}
	
	protected abstract void move();
	protected abstract float calculatePrice(float price);
	
}
