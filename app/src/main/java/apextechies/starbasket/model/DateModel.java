package apextechies.starbasket.model;

import android.text.format.DateFormat;

import java.util.Calendar;



public class DateModel {
	private final String date;
	
	public DateModel(Calendar calendar) {
		date = DateFormat.format("yyyy-MM-dd", calendar).toString();
	}
	
	public String getDate() {
		return date;
	}
}
