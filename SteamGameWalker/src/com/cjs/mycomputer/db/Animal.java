package com.cjs.mycomputer.db;

import com.cjs.mycomputer.db.annotation.Column;
import com.cjs.mycomputer.db.annotation.Table;

@Table
public class Animal {
	@Column
	private String name;
	@Column
	private float weight;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

}
