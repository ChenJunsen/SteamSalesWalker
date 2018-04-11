package com.cjs.mycomputer.db;

import com.cjs.mycomputer.db.annotation.Column;
import com.cjs.mycomputer.db.annotation.Id;
import com.cjs.mycomputer.db.annotation.Table;

@Table(tableName="Fruit")
public class Fruit {
	@Id
	private int productId;
	
	@Column
	private String name;
	
	@Column
	private float price;
	
	@Column
	private String type;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Fruit [productId=" + productId + ", name=" + name + ", price=" + price + ", type=" + type + "]";
	}
	
	
	
}
