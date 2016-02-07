package com.pizza.android.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.eclinic.android.R;
import com.pizza.android.model.Contact;

public class ContactActivity extends Activity {

	private TextView name;
	private TextView city;
	private TextView street;
	private TextView phone;
	private TextView hours;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initField();
		ArrayList<Contact> contact = (ArrayList<Contact>) getIntent().getExtras().getSerializable(
				getString(R.string.list));
		bindContactToView(contact.get(0));
	}

	private void bindContactToView(Contact contact) {
		name.setText(contact.getName());
		city.setText(contact.getCity());
		street.setText(contact.getStreet());
		phone.setText(contact.getPhone());
		hours.setText(contact.getHours());

	}

	private void initField() {
		name = (TextView) findViewById(R.id.name);
		city = (TextView) findViewById(R.id.city);
		street = (TextView) findViewById(R.id.street);
		phone = (TextView) findViewById(R.id.phone);
		hours = (TextView) findViewById(R.id.hours);
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
}
