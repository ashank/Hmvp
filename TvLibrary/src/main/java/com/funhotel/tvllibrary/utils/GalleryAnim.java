package com.funhotel.tvllibrary.utils;

import android.content.Context;
import android.view.View;
import android.widget.Gallery;

import com.open.androidtvwidget.view.MainUpView;


public class GalleryAnim {
    private Context context;
    private int[] icons;
    private Gallery mGallery;
    private MainUpView mainUpView;
    View mOldFocus;
    private int oldPosition;
    public GalleryAnim(Context context, int[] icons, Gallery mGallery, MainUpView mainUpView){
        this.context = context;
        this.icons = icons;
        this.mGallery = mGallery;
        this.mainUpView = mainUpView;
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainUpView.setUpRectResource(com.open.androidtvwidget.R.drawable.white_light_10);
        mainUpView.setShadowDrawable(null);
        mainUpView.setDrawUpRectPadding(5);
    }

    public void startAnim() {
        mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (null != mOldFocus) {
                    mainUpView.setFocusView(mOldFocus, 1.0f);
                }
                mainUpView.setFocusView(view, 1.2f);
                mOldFocus = view;
                oldPosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
int item = Integer.MAX_VALUE / 2;
        item = item - item % icons.length;

        oldPosition=0;
        mGallery.setSelection(oldPosition, true);
    }

    @Override
    public void onResume() {
        mOldFocus=mGallery.getChildAt(oldPosition);
        super.onResume();
    }


    @Override
    public void onStop() {
        mOldFocus=null;
        super.onStop();
    }*/
}
