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
import com.pizza.android.domain.PizzaDetail;

public class ListActivity extends Activity {

	private ListView listView;
	private MenuListAdapter adapter;
	private String prevActivity;

	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		prevActivity = "";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_grid);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ArrayList<PizzaDetail> list = (ArrayList<PizzaDetail>) getIntent().getExtras()
				.getSerializable(getString(R.string.list));
		try {
			prevActivity = getIntent().getExtras().getString("activity");
		} catch (Exception e) {
			prevActivity = "";
		}
		updateAdapter(list);
		initializeGrid();
	}

	private void updateAdapter(Collection<PizzaDetail> list) {
		adapter = new MenuListAdapter(this, list,prevActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		startActivity(i);
	}
}
