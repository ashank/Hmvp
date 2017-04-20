package com.open.androidtvwidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.open.androidtvwidget.view.MainUpView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * GridView Demo测试.
 */
public class DemoGridViewActivity extends Activity {

	private List<Map<String, Object>> data;
	private MainUpView mainUpView1;
	private View oldView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_grid_view);

		GridView gridView = (GridView) findViewById(R.id.gridView);
		mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
		mainUpView1.setUpRectResource(R.drawable.white_light_10);
		mainUpView1.setShadowDrawable(null);
		mainUpView1.setDrawUpRectPadding(10);

		getData();

		String[] from = { "text" };
		int[] to = { R.id.textView };

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);
		gridView.setAdapter(simpleAdapter);
		simpleAdapter.notifyDataSetChanged();
		//
		gridView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d("testtest", "testtest view:" + view);
				if (null!=oldView){
					mainUpView1.setFocusView(oldView, 1.0f);
				}

				mainUpView1.setFocusView(view, 1.3f);
				oldView=view;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), "position : " + position, Toast.LENGTH_LONG).show();
			}
		});
		// 不知道为何设置 0 不显示.(暂时无解)
		gridView.setSelection(1);
	}

	public List<Map<String, Object>> getData() {
		data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 200; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", "item" + i);
			data.add(map);
		}
		return data;
	}
}
