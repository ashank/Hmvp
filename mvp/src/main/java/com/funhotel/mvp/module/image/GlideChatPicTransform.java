package com.funhotel.mvp.module.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @Title: GlideChatPicTransform
 * @Description: 图片气泡的方法
 * @author: LinWeiDong
 * @data: 2016/2/15 15:26
 */

public class GlideChatPicTransform extends BitmapTransformation {
    private  static  Context context;
    private   int direct=0;
    public GlideChatPicTransform(Context context,int direct) {
        super(context);
        this.context=context;
        this.direct=direct;
    }
    @Override
    public String getId() {
        return getClass().getName();
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private  Bitmap circleCrop(BitmapPool pool, Bitmap bitmapimg) {
        if (bitmapimg == null) return null;

        //1 = direction right
        //0 = direction left
        Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth(),
                bitmapimg.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmapimg.getWidth(), bitmapimg.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        if(direct == 0) {
            //左边
            canvas.drawRect(0, 0, bitmapimg.getWidth() - 30, bitmapimg.getHeight(), paint);
            Path path = new Path();
            path.moveTo(bitmapimg.getWidth()-30, 0);
            path.lineTo(bitmapimg.getWidth(), 0);
            path.lineTo(bitmapimg.getWidth()-30, 20);
            canvas.drawPath(path,paint);
        }else if (direct == 1) {
            //右边
           canvas.drawRect(30, 0, bitmapimg.getWidth(), bitmapimg.getHeight(), paint);
            Path path = new Path();
            path.moveTo(30, 0);
            path.lineTo(0, 0);
            path.lineTo(30, 20);
            canvas.drawPath(path,paint);
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapimg, rect, rect, paint);
        return output;
    }
}