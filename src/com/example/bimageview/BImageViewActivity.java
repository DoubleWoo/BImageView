package com.example.bimageview;

import com.example.bimageview.photoview.PhotoView;
import com.example.bimageview.photoview.sample.HackyViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class BImageViewActivity extends Activity {

	private ViewPager mViewPager;
	static TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mViewPager = new HackyViewPager(this);
		setContentView(R.layout.bimageview);
		mViewPager = (ViewPager) findViewById(R.id.bimageview);
		textView = (TextView) findViewById(R.id.pagenum);

		mViewPager.setAdapter(new SamplePagerAdapter());
	}

	static class SamplePagerAdapter extends PagerAdapter {

		private static int[] sDrawables = { R.drawable.wallpaper,
				R.drawable.a1, R.drawable.a2, R.drawable.a3,
				R.drawable.wallpaper, R.drawable.wallpaper };

		@Override
		public int getCount() {
			return sDrawables.length;
		}

		@SuppressLint("ShowToast")
		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			//photoView.setImageResource(sDrawables[position]);
photoView.setImageUrl("http://10.0.2.2:8080/jsoup/mm.jpg");
System.out.println("http://f5.topit.me/5/1b/25/1147935385854251b5o.jpg");
			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			textView.setText("这是第"+position+"页");
			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}
}
