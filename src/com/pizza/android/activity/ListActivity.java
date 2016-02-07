package com.pizza.android.activity;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.eclinic.android.R;
import com.pizza.android.adapter.MenuListAdapter;
import com.pizza.android.model.PizzaDetail;
import com.pizza.android.shop.Basket;
import com.pizza.android.shop.MenuModel;

public class ListActivity extends Activity {

	private ListView listView;
	private MenuListAdapter adapter;
	private boolean basketMode;

	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_grid);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ArrayList<PizzaDetail> list = (ArrayList<PizzaDetail>) getIntent().getExtras()
				.getSerializable(getString(R.string.list));
		try {
			basketMode = getIntent().getExtras().getBoolean("basketMode");
		} catch (Exception e) {
			basketMode = false;
		}
		updateAdapter(list);
		initializeGrid();
	}

	private void updateAdapter(Collection<PizzaDetail> list) {
		adapter = new MenuListAdapter(this, list, basketMode);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.details_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (basketMode) {
			switch (item.getItemId()) {
			case R.id.order: {
				goToPanel(new ArrayList<PizzaDetail>(Basket.getInstance().getList()), basketMode);
				return true;
			}
			case R.id.deleteAll: {
				Basket.getInstance().clearList();
				goToPanel(new ArrayList<PizzaDetail>(MenuModel.getList()), false);
				return true;
			}
			default: {
				return super.onOptionsItemSelected(item);
			}
			}
		}
		return false;
	}

	private void goToPanel(ArrayList<PizzaDetail> listAdapter, boolean basketMode) {
		Intent intent = null;
		intent = new Intent(ListActivity.this, OrderActivity.class);
		intent.putExtra(getString(R.string.list), listAdapter);
		startActivity(intent);
	}

	private void initializeGrid() {
		listView = (ListView) findViewById(R.id.list_grid);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showDetail((PizzaDetail) parent.getItemAtPosition(position));

			}
		});
	}

	private void showDetail(PizzaDetail model) {
		Intent i = new Intent(this, ListDetailsActivity.class);
		i.putExtra(getString(R.string.detail), model);
		i.putExtra("basketMode", basketMode);
		startActivity(i);
	}
}
