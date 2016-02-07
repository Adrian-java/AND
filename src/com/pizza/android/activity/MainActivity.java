package com.pizza.android.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.eclinic.android.R;
import com.pizza.android.model.Contact;
import com.pizza.android.model.PizzaDetail;
import com.pizza.android.rest.client.ContactRestClient;
import com.pizza.android.rest.client.ContactRestClient.OnContactDownloadedListener;
import com.pizza.android.rest.client.PizzaRestClient;
import com.pizza.android.rest.client.PizzaRestClient.OnPizzaDownloadedListener;
import com.pizza.android.shop.MenuModel;

public class MainActivity extends Activity {

	private ImageButton menuButton;
	private ImageButton contactButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		menuButton = (ImageButton) findViewById(R.id.menuButton);
		contactButton = (ImageButton) findViewById(R.id.contactButton);

		goToMenuActivityListener();
		goToContactActivityListener();
	}

	private void goToMenuActivityListener() {

		menuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getAllPizzas();
			}
		});
	}

	protected void getAllPizzas() {
		final PizzaRestClient sp = new PizzaRestClient();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sp.fetchPizza(new OnPizzaDownloadedListener() {

						@Override
						public void onPizzasDownloaded() {
							Intent intent = new Intent(MainActivity.this, ListActivity.class);
							intent.putExtra(getString(R.string.list),
									new ArrayList<PizzaDetail>(sp.getAllPizzas()));
							MenuModel.setList(sp.getAllPizzas());
							goToPanel(intent, ListActivity.class);
						}
					});
				} catch (Exception e) {
				}
			}
		}).start();

	}

	protected void getContact() {
		final ContactRestClient sp = new ContactRestClient();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sp.fetchPizza(new OnContactDownloadedListener() {

						@Override
						public void onContactDownloaded() {
							Intent intent = new Intent(MainActivity.this, ContactActivity.class);
							ArrayList<Contact> a = new ArrayList<Contact>();
							a.add(sp.getContact());
							intent.putExtra(getString(R.string.list), a);
							goToPanel(intent, ContactActivity.class);
						}
					});
				} catch (Exception e) {
				}
			}
		}).start();

	}

	private void goToPanel(Intent intent, Class<?> class1) {
		startActivity(intent);
	}

	private void goToContactActivityListener() {
		contactButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getContact();
			}
		});
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
