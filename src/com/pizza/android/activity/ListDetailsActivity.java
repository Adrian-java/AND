package com.pizza.android.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eclinic.android.R;
import com.pizza.android.domain.PizzaDetail;
import com.pizza.android.helper.Alert;
import com.pizza.android.helper.SaveRecipeOnDiskDelegate;
import com.pizza.android.shop.Basket;

public class ListDetailsActivity extends Activity {

	private TextView name;
	private TextView ingrediets;
	private ImageView image;
	private ImageView addToBasketButton;
	private ImageView goToBasketButton;
	private EditText quantityEditText;
	private PizzaDetail pizzaDetail;
	private Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ctx = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_details);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ingrediets = (TextView) findViewById(R.id.ingried);
		image = (ImageView) findViewById(R.id.image);
		name = (TextView) findViewById(R.id.name);
		quantityEditText = (EditText) findViewById(R.id.quantity);
		addToBasketButton = (ImageView) findViewById(R.id.addToBasket);
		goToBasketButton = (ImageView) findViewById(R.id.goToBasket);
		Intent i = getIntent();
		pizzaDetail = (PizzaDetail) i.getExtras().getSerializable(getString(R.string.detail));
		show(pizzaDetail);
		addToBasketListener();
		goToBasket();
	}

	private void goToBasket() {
		goToBasketButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToPanel(new ArrayList<PizzaDetail>(Basket.getInstance().getList()));
			}
		});

	}

	private void addToBasketListener() {
		addToBasketButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pizzaDetail.setQuantity(Integer.valueOf(quantityEditText.getText().toString()));
				Basket.getInstance().getList().add(pizzaDetail);
				Alert.createToast(ctx, "Added to basket");

			}
		});

	}

	private void goToPanel(ArrayList<PizzaDetail> listAdapter) {
		Intent intent = null;
		intent = new Intent(ListDetailsActivity.this, ListActivity.class);
		intent.putExtra(getString(R.string.list), listAdapter);
		intent.putExtra("activity", ListDetailsActivity.class.getName());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.details_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save_to_disc: {
			// saveRecipeToDisk(iModel);
			return true;
		}
		default: {
			return super.onOptionsItemSelected(item);
		}
		}
	}

	private void show(PizzaDetail pizzaDetail) {
		ingrediets.setText(pizzaDetail.getIngredients());
		name.setText(pizzaDetail.getName());
		image.setImageResource(R.drawable.abc_btn_check_to_on_mtrl_015);
	}

	private void savePizzaToDisk(PizzaDetail recipe) {
		SaveRecipeOnDiskDelegate saveRecipeOnDiskDelegate = new SaveRecipeOnDiskDelegate();
		saveRecipeOnDiskDelegate.saveRecipe(this, recipe);
	}
}
