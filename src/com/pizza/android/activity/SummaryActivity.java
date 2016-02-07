package com.pizza.android.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.eclinic.android.R;

public class SummaryActivity extends Activity {

	private TextView summaryTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initField();
		String name = getIntent().getExtras().getString("name");
		String phone = getIntent().getExtras().getString("phone");
		String info = getIntent().getExtras().getString("info");
		String summary = getIntent().getExtras().getString("summary");

		setSummaryText(name,phone,info,summary);

	}

	private void setSummaryText(String name, String phone, String info, String summary) {
		String str = getString(R.string.summ);
		str += getString(R.string.personal_data);
		str += name+getString(R.string._tel_)+phone+getString(R.string.new_line);
		str+= getString(R.string.add_info);
		str+=info+getString(R.string.new_line);
		str+=getString(R.string.to_pay)+summary;
		
		summaryTextView.setText(str);

	}

	private void initField() {
		summaryTextView = (TextView) findViewById(R.id.summaryTextView);
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
