package com.pizza.android.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eclinic.android.R;
import com.pizza.android.helper.Alert;
import com.pizza.android.model.PizzaDetail;

public class OrderActivity extends Activity {

	private Context ctx;
	private TextView summary;
	private EditText name;
	private EditText phone;
	private EditText info;
	private ImageView process;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ctx = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initField();
		ArrayList<PizzaDetail> list = (ArrayList<PizzaDetail>) getIntent().getExtras()
				.getSerializable(getString(R.string.list));

		setSummaryPrice(list);
		processListener();
	}

	private void processListener() {
		process.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String na = name.getText().toString();
				String ph = phone.getText().toString();
				if (na.isEmpty() || ph.isEmpty()) {
					Alert.createToast(ctx, getString(R.string.notEmptyField));
					return;
				}
				Intent intent = new Intent(OrderActivity.this, SummaryActivity.class);
				intent.putExtra(getString(R.string.name_), na);
				intent.putExtra(getString(R.string.phone_), ph);
				intent.putExtra(getString(R.string.info_), info.getText().toString());
				intent.putExtra(getString(R.string.summary_), summary.getText().toString());
				startActivity(intent);
			}
		});

	}

	private void setSummaryPrice(ArrayList<PizzaDetail> list) {
		float price = 0;
		for (PizzaDetail p : list) {
			price += p.getPrice();
		}
		summary.setText(String.valueOf(price) + R.string._pln);
	}

	private void initField() {
		process = (ImageView) findViewById(R.id.process);
		summary = (TextView) findViewById(R.id.summary);
		name = (EditText) findViewById(R.id.name);
		phone = (EditText) findViewById(R.id.phone);
		info = (EditText) findViewById(R.id.info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}
}
