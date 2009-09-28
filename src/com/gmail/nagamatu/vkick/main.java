package com.gmail.nagamatu.vkick;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnDismissListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class main extends Activity {
	private static final String TAG = "vkick";
	private static final String PREFS_NAME = "vkickSettings";
	private static final String FIRST_TIME = "first_time";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((Button)findViewById(R.id.okbutton))
        	.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText edit = (EditText)findViewById(R.id.assetid);
				Log.d(TAG, edit.getText().toString());
				kickVending(edit.getText().toString());
			}});
        ((Button)findViewById(R.id.clearbutton))
    	.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			EditText edit = (EditText)findViewById(R.id.assetid);
			edit.setText("");
		}});
        
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean(FIRST_TIME, true)) {
        	this.showDialog(0);
        }
    }
    
    private boolean kickVending(final String assetId) {
    	Uri uri = Uri.parse("market://details?id=" + assetId); 
    	Intent it = new Intent(Intent.ACTION_VIEW, uri); 
    	startActivity(it);
    	return true;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);

    	// Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.info:
    		Log.d(TAG, "Menu Info");
    		showDialog(0);
    		return true;
    	}
    	return false;
    }
    
    protected Dialog onCreateDialog(int id) {
    	super.onCreateDialog(id);
    	
    	Log.d(TAG, "onCreateDialog: " + id);
    	Dialog dialog = new Dialog(this);
    	dialog.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				Log.d(TAG, "onDismiss");
			}
    	});

    	dialog.setContentView(R.layout.about);
    	dialog.setTitle(getResources().getString(R.string.info_title));

    	((Button)dialog.findViewById(R.id.about_ok)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "About onClick");

		    	// Save default delay value into preferences
		        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		        SharedPreferences.Editor editor = settings.edit();
		        editor.putBoolean(FIRST_TIME, false);
		        editor.commit();

				dismissDialog(0);
			}
    	});

    	return dialog;
    }
}