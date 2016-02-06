package com.pizza.android.rest.client;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pizza.android.domain.Contact;
import com.pizza.android.rest.HttpMethod;
import com.pizza.android.rest.NetworkRequest;
import com.pizza.android.rest.RestConfig;

public class ContactRestClient {

	Contact contact;

	public interface OnContactDownloadedListener {
		void onContactDownloaded();
	}

	public void fetchPizza(OnContactDownloadedListener listener) throws Exception {
		String json = getContactFromServer(RestConfig.CONTACT_GET);
		setVisitViews(json);
		listener.onContactDownloaded();
	}

	public Contact getContact() {
		return contact;
	}

	private void setVisitViews(String s) throws Exception {
		JSONArray pizzaArray = null;
		pizzaArray = new JSONArray(s);

		for (int i = 0; i < pizzaArray.length(); ++i) {
			JSONObject contactObject = pizzaArray.getJSONObject(i);
			Contact contact = new Contact();
			contact.setName(contactObject.getString("name"));
			contact.setCity(contactObject.getString("city"));
			contact.setPhone(contactObject.getString("phone"));
			contact.setStreet(contactObject.getString("street"));
			contact.setHours(contactObject.getString("hours"));
		}

	}

	private String getContactFromServer(String restPath) throws IOException {
		return new NetworkRequest(restPath, HttpMethod.GET).execute();
	}
}