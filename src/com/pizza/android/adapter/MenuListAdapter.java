package com.pizza.android.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eclinic.android.R;
import com.pizza.android.domain.PizzaDetail;

public class MenuListAdapter extends BaseAdapter {

	private List<PizzaDetail> list = new ArrayList<>();
	private Context context;
	private String prevActivity;

	public MenuListAdapter(Context context, Collection<PizzaDetail> list, String prevActivity) {
		this.context = context;
		this.list.clear();
		this.list.addAll(list);
		this.prevActivity = prevActivity;
	}

	public void setRecipes(Collection<PizzaDetail> recipes) {
		list.clear();
		list.addAll(recipes);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public PizzaDetail getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View recipeView;

		if (convertView == null) {
			recipeView = LayoutInflater.from(context).inflate(R.layout.main_row, parent, false);
		} else {
			recipeView = convertView;
		}

		PizzaDetail adapterView = getItem(position);

		bindToView(adapterView, recipeView, position);

		return recipeView;
	}

	private void bindToView(PizzaDetail i, View view, int position) {
		ImageView pizzaImage = (ImageView) view.findViewById(R.id.pizza_image);
//		pizzaImage.set//set image 
		TextView pizzaName = (TextView) view.findViewById(R.id.pizza_name);
		pizzaName.setText(i.getName());
		TextView pizzaPrice = (TextView) view.findViewById(R.id.pizza_price);
		pizzaPrice.setText(String.valueOf(i.getPrice()));
	}

}