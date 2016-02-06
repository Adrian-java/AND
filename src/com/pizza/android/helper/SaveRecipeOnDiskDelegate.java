package com.pizza.android.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.pizza.android.domain.PizzaDetail;

public class SaveRecipeOnDiskDelegate {

	private static final String FILENAME = "clinic.txt";

	public void saveRecipe(Context context, PizzaDetail recipe) {
		Log.e("kweh", "onHandleIntent");
		if (!isExternalStorageWritable()) {
			Toast.makeText(context, "Could not save file - external storage is not writable",
					Toast.LENGTH_LONG).show();
			return;
		}

		File file = new File(context.getExternalFilesDir(null), FILENAME);

		file.getParentFile().mkdirs();

		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(buildRecipeText(context, recipe).getBytes());
			fos.close();
			Toast.makeText(context, "File saved at " + file.getAbsolutePath(), Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			Toast.makeText(context, "Error during saving file", Toast.LENGTH_LONG).show();
		}
	}

	private String buildRecipeText(Context context, PizzaDetail i) {
		return i.toString();
	}

	private boolean isExternalStorageWritable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		}
		return false;
	}
}
