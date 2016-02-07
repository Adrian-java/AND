package com.pizza.android.rest.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pizza.android.model.PizzaDetail;
import com.pizza.android.rest.HttpMethod;
import com.pizza.android.rest.NetworkRequest;
import com.pizza.android.rest.RestConfig;

public class PizzaRestClient {

	private final List<PizzaDetail> pizzas = new ArrayList<>();

	public interface OnPizzaDownloadedListener {
		void onPizzasDownloaded();
	}

	public void fetchPizza(OnPizzaDownloadedListener listener)
			throws Exception {
		String pizzaMenu = RestConfig.MENU_GET;
		String json = getPizzaFromServer(pizzaMenu);
		pizzas.clear();
		setPizza(json);
		listener.onPizzasDownloaded();
	}

	public int getPizzaNumber() {
		return pizzas.size();
	}

	public List<PizzaDetail> getAllPizzas() {
		return pizzas;
	}

	private void setPizza(String s) throws Exception {
		JSONArray pizzaArray = null;
		try {
			pizzaArray = new JSONArray(s);

			for (int i = 0; i < pizzaArray.length(); ++i) {
				JSONObject VisitViewsObject = pizzaArray.getJSONObject(i);
				PizzaDetail pizza = new PizzaDetail();
				pizza.setName(VisitViewsObject.getString("name"));
				pizza.setIngredients(VisitViewsObject.getString("ingredients"));
				pizza.setPrice(VisitViewsObject.getDouble("price"));
				pizza.setId(VisitViewsObject.getInt("id"));
				pizzas.add(pizza);
			}

		} catch (JSONException e) {
			throw new Exception("Cannot download VisitViews");
		}
	}

	private String getPizzaFromServer(String restPath) throws IOException {
		return new NetworkRequest(restPath, HttpMethod.GET).execute();
	}
}