package com.example.bimageview;

import java.net.ContentHandler;

import com.example.bimageview.photoview.sample.SimpleSampleActivity;
import com.example.bimageview.photoview.sample.ViewPagerActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);
		Button button4 = (Button) findViewById(R.id.button4);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.button1:
			IntentTools(this,SimpleSampleActivity.class);
			break;
		case R.id.button2:
			IntentTools(this,ViewPagerActivity.class);
			break;
		case R.id.button3:
			IntentTools(this,ViewPagerActivity.class);
			break;
		case R.id.button4:
			IntentTools(this, BImageViewActivity.class);
			break;

		default:
			break;
		}
		
	}
public void IntentTools(Context content,Class clz){
	startActivity(new Intent(content,clz));
}
}
