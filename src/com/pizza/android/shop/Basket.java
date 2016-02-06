package com.pizza.android.shop;

import java.util.ArrayList;
import java.util.List;

import com.pizza.android.domain.PizzaDetail;

public class Basket {

	private static Basket instance;

	private List<PizzaDetail> list;

	private Basket() {
		list = new ArrayList<>();
	}

	public static Basket getInstance() {
		if (instance == null)
			instance = new Basket();
		return instance;
	}

	public void clearList() {
		list.clear();
	}

	public List<PizzaDetail> getList() {
		return list;
	}
}
