package com.pizza.android.shop;

import java.util.List;

import com.pizza.android.model.PizzaDetail;

public class MenuModel {
	
	private static List<PizzaDetail> list;

	public static List<PizzaDetail> getList() {
		return list;
	}

	public static void setList(List<PizzaDetail> list) {
		MenuModel.list = list;
	}

}
