package com.pizza.android.rest.client;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pizza.android.model.Contact;
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
		JSONObject contactObject = new JSONObject(s);
		contact = new Contact();
		contact.setName(contactObject.getString("name"));
		JSONObject addressObject = contactObject.getJSONObject("address");
		contact.setCity(addressObject.getString("city"));
		contact.setStreet(addressObject.getString("street"));
		contact.setPhone(contactObject.getString("phone"));
		contact.setHours(contactObject.getString("hours"));

	}

	private String getContactFromServer(String restPath) throws IOException {
		return new NetworkRequest(restPath, HttpMethod.GET).execute();
	}
}