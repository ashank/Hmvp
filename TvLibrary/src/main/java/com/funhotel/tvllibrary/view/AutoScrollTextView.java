package com.funhotel.tvllibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

public class AutoScrollTextView extends TextView implements OnClickListener {
	
	public final static String TAG = AutoScrollTextView.class.getSimpleName();

	private float textLength = 0f;// 文本长度
	private float viewWidth = 0f;//控件的宽度
	private float step = 0f;// 文字的横坐标
	float count=0f;//步长
	private float y = 0f;// 文字的纵坐标
	private float temp_view_plus_text_length = 0.0f;// 用于计算的临时变量
	private float temp_view_plus_two_text_length = 0.0f;// 用于计算的临时变量
	public boolean isStarting = false;// 是否开始滚动
	private Paint paint = null;// 绘图样式
	private String text = "";// 文本内容
	
	private static int i=250;
	
	private Runnable runnabl=new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			handler.sendEmptyMessage(0);
		}
	};
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				postInvalidate();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};

	public AutoScrollTextView(Context context) {
		super(context);
		initView();
	}

	public AutoScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		paint = getPaint();
		setOnClickListener(this);
	}

	/**
	 * 文本初始化，每次更改文本内容或文本效果等之后都需要重新初始化
	 */
	public void init(WindowManager windowManager, int color) {
		paint = getPaint();
		paint.setColor(color);
		paint.setAntiAlias(true);//抗锯齿
		//Paint.Style.STROKE 、Paint.Style.FILL、Paint.Style.FILL_AND_STROKE 
		//意思分别为 空心 、实心、实心与空心 
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);//设置画笔为圆滑状
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setFilterBitmap(true);
		paint.setStrokeJoin(Paint.Join.ROUND);
		
		text = getText().toString();
		textLength = paint.measureText(text);
		viewWidth = getWidth();
		if (viewWidth == 0) {
			if (windowManager != null) {
				Display display = windowManager.getDefaultDisplay();
				viewWidth = display.getWidth();
				//步长
				count=viewWidth/i;
			}
		}
		step=textLength;
		//最初绘制的位置
		temp_view_plus_text_length = viewWidth + textLength;
		//最后绘制的位置
		temp_view_plus_two_text_length = viewWidth + textLength * 2;
		y = getTextSize() + getPaddingTop();

	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);

		ss.step = step;
		ss.isStarting = isStarting;
		return ss;

	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		step = ss.step;
		isStarting = ss.isStarting;

	}

	public static class SavedState extends BaseSavedState {
		public boolean isStarting = false;
		public float step = 0.0f;

		SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeBooleanArray(new boolean[] { isStarting });
			out.writeFloat(step);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}

			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}
		};

		@SuppressWarnings("unused")
		private SavedState(Parcel in) {
			super(in);
			boolean[] b = null;
			in.readBooleanArray(b);
			if (b != null && b.length > 0)
				isStarting = b[0];
			step = in.readFloat();
		}
	}

	/**
	 * 开始滚动
	 */
	public void startScroll() {
		isStarting = true;
		postInvalidate();
//		new Thread(runnabl).start();
	}

	/**
	 * 停止滚动
	 */
	public void stopScroll() {
		isStarting = false;
		postInvalidate();
		new Thread(runnabl).start();
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (text != null) {
			canvas.drawText(text, temp_view_plus_text_length - step, y, paint);
			Log.e(TAG, "onDraw: " + (double)(temp_view_plus_text_length - step));
		}
		if (!isStarting) {
			return;
		}
		//滚动的步数
		step+=count;
		//当绘制一次完毕时，将步数重置，重新开始绘制
		if (step > temp_view_plus_two_text_length){
			step=textLength;
		}
		postInvalidate();
//		new Thread(runnabl).start();
		Log.e(TAG, "onDraw: " );

	}

	
	/*点击事件*/
	@Override
	public void onClick(View v) {
		if (isStarting)
			stopScroll();
		else
			startScroll();

	}

}
