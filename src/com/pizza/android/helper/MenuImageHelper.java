package com.pizza.android.helper;

import android.widget.ImageView;

import com.eclinic.android.R;
import com.pizza.android.model.PizzaDetail;

public class MenuImageHelper {

	
	public static void setPizzaImage(PizzaDetail i, ImageView pizzaImage) {
		int mod = i.getId() % 6;
		switch (mod) {
		case 0: {
			pizzaImage.setImageResource(R.drawable.pizz0);
			break;
		}
		case 1: {
			pizzaImage.setImageResource(R.drawable.pizz1);
			break;
		}
		case 2: {
			pizzaImage.setImageResource(R.drawable.pizz2);
			break;
		}
		case 3: {
			pizzaImage.setImageResource(R.drawable.pizz3);
			break;
		}
		case 4: {
			pizzaImage.setImageResource(R.drawable.pizz4);
			break;
		}
		case 5: {
			pizzaImage.setImageResource(R.drawable.pizz5);
			break;
		}
		default: {
			pizzaImage.setImageResource(R.drawable.pizz0);
			break;
		}
		}
	}
}
