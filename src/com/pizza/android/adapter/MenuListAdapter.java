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
import com.pizza.android.helper.MenuImageHelper;
import com.pizza.android.model.PizzaDetail;

public class MenuListAdapter extends BaseAdapter {

	private List<PizzaDetail> list = new ArrayList<>();
	private Context context;
	private boolean basketMode;

	public MenuListAdapter(Context context, Collection<PizzaDetail> list, boolean basketMode) {
		this.context = context;
		this.list.clear();
		this.list.addAll(list);
		this.basketMode = basketMode;
	}

	public void setPizzaDetails(Collection<PizzaDetail> pd) {
		list.clear();
		list.addAll(pd);
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
		View view;

		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.main_row, parent, false);
		} else {
			view = convertView;
		}

		PizzaDetail adapterView = getItem(position);

		bindToView(adapterView, view, position);

		return view;
	}

	private void bindToView(PizzaDetail i, View view, int position) {
		ImageView pizzaImage = (ImageView) view.findViewById(R.id.pizza_image);
		MenuImageHelper.setPizzaImage(i, pizzaImage);
		TextView pizzaName = (TextView) view.findViewById(R.id.pizza_name);
		pizzaName.setText(i.getName());
		TextView pizzaPrice = (TextView) view.findViewById(R.id.pizza_price);
		pizzaPrice.setText(String.valueOf(i.getPrice()));
	}
}