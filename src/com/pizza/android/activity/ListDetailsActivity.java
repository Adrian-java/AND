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
import com.pizza.android.helper.MenuImageHelper;
import com.pizza.android.helper.SaveRecipeOnDiskDelegate;
import com.pizza.android.model.PizzaDetail;
import com.pizza.android.shop.Basket;
import com.pizza.android.shop.MenuModel;

public class ListDetailsActivity extends Activity {

	private TextView name;
	private TextView ingrediets;
	private ImageView image;
	private ImageView addOrRemoveToBasketButton;
	private ImageView goToBasketButton;
	private ImageView goToMenu;
	private EditText quantityEditText;
	private PizzaDetail pizzaDetail;
	private Context ctx;
	private boolean basketMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ctx = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_details);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Intent i = getIntent();
		pizzaDetail = (PizzaDetail) i.getExtras().getSerializable(getString(R.string.detail));
		initField();
		basketMode = i.getExtras().getBoolean(getString(R.string.basketmode));
		if (!basketMode) {
			addOrRemoveToBasketButton.setImageResource(R.drawable.basket);
		} else {
			addOrRemoveToBasketButton.setImageResource(R.drawable.delete);
		}
		show(pizzaDetail);
		addOrRemoveToBasketListener();
		goToBasket();
		goToMenu();
	}

	private void initField() {
		ingrediets = (TextView) findViewById(R.id.ingried);
		image = (ImageView) findViewById(R.id.imagePizza);
		name = (TextView) findViewById(R.id.name);
		quantityEditText = (EditText) findViewById(R.id.quantity);
		if (quantityEditText.getText().toString().isEmpty()) {
			quantityEditText.setText(R.string._1);
		}
		addOrRemoveToBasketButton = (ImageView) findViewById(R.id.addToBasket);
		goToBasketButton = (ImageView) findViewById(R.id.goToBasket);
		goToMenu = (ImageView) findViewById(R.id.goToMenu);
		goToBasketButton.setImageResource(R.drawable.gotobasket);
	}

	private void goToMenu() {
		goToMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToPanel(new ArrayList<PizzaDetail>(MenuModel.getList()), false);
			}
		});

	}

	private void goToBasket() {
		goToBasketButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToPanel(new ArrayList<PizzaDetail>(Basket.getInstance().getList()), true);
			}
		});

	}

	private void addOrRemoveToBasketListener() {
		addOrRemoveToBasketButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!basketMode) {
					pizzaDetail.setQuantity(Integer.valueOf(quantityEditText.getText().toString()));
					Basket.getInstance().getList().add(pizzaDetail);
					Alert.createToast(ctx, getString(R.string.added_to_basket));
				} else {
					PizzaDetail deletePizza = foundDeletedObject();
					if (deletePizza != null) {
						Basket.getInstance().getList().remove(deletePizza);
						Alert.createToast(ctx, getString(R.string.deleted_from_basket));
						goToPanel(new ArrayList<PizzaDetail>(MenuModel.getList()), false);
					}
				}
			}
		});

	}

	private void goToPanel(ArrayList<PizzaDetail> listAdapter, boolean basketMode) {
		Intent intent = null;
		intent = new Intent(ListDetailsActivity.this, ListActivity.class);
		intent.putExtra(getString(R.string.list), listAdapter);
		intent.putExtra(getString(R.string.basketmode), basketMode);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.details_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
		
	}

	private void show(PizzaDetail pizzaDetail) {
		ingrediets.setText(pizzaDetail.getIngredients());
		name.setText(pizzaDetail.getName());
		MenuImageHelper.setPizzaImage(pizzaDetail, image);
	}

	private void savePizzaToDisk(PizzaDetail recipe) {
		SaveRecipeOnDiskDelegate saveRecipeOnDiskDelegate = new SaveRecipeOnDiskDelegate();
		saveRecipeOnDiskDelegate.saveRecipe(this, recipe);
	}

	private PizzaDetail foundDeletedObject() {
		PizzaDetail deletePizza = null;
		for (PizzaDetail p : Basket.getInstance().getList()) {
			if (p.getId() == pizzaDetail.getId()) {
				deletePizza = p;
				break;
			}
		}
		return deletePizza;
	}
}
