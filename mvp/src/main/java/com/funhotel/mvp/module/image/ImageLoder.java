package com.funhotel.mvp.module.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.funhotel.mvp.utils.DebugUtil;
import com.funhotel.mvp.utils.DesityUtil;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @Title: ImageLoder
 * @Description: Glide图片加载类
 * @author: LinWeiDong
 * @data: 2016/1/13 16:18
 */
public class ImageLoder {

    private final static String TAG = "ImageLoder";
    private static ImageLoder mImageLoder;
    private DiskCacheStrategy mDiskCacheStrategy;
    private int placeholder=0;
    private int error=0;
    /**
     * 图片宽高
     */
    private int width, height;
    /**
     * 四个方向的magin值
     */
    private int margin;
    /**
     * 设置圆角时的圆角半径
     */
    private int radius;
    /**
     * 是否把原图按比例扩大或缩小到ImageView的ImageView的高度，居中显示
     */
    private boolean isFitCenter;
    private DrawableRequestBuilder drawableRequestBuilder;
    private RequestListener requestListener;
    private ExecutorService singleThreadExecutor = Executors.newCachedThreadPool();
    private WeakReference<Context> weakReference;

    public static ImageLoder newInstance( Context context) {
        if (null == mImageLoder) {
            mImageLoder = new ImageLoder(context);
        }
        return mImageLoder;
    }

    public ImageLoder(Context context) {
        weakReference=new WeakReference<Context>(context.getApplicationContext());
        init();
    }

    private void init(){
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }
        if (this.placeholder==0){
            this.placeholder = android.R.color.transparent;
        }

        if (this.error==0){
            this.error = android.R.color.transparent;
        }
    }

    /**
     * 设置缓存的尺寸
     * @param mDiskCacheStrategy
     */
    public void setDiskCacheStrategy(DiskCacheStrategy mDiskCacheStrategy) {
        this.mDiskCacheStrategy = mDiskCacheStrategy;
    }


    /**
     * 设置占位图
     *
     * @param placeholder
     */
    public void setplaceholder(@DrawableRes int placeholder) {
        this.placeholder = placeholder;

    }


    public void setMargin(int margin) {
        this.margin = margin;
    }

    /**
     * 设置错误占位图
     *
     * @param error
     */

    public void setError(@DrawableRes int error) {
        this.error = error;
    }

    /**
     * 设置图片圆角半径
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * 是否把原图按比例扩大或缩小到ImageView的ImageView的高度，居中显示
     * @param fitCenter
     */
    public void setFitCenter(boolean fitCenter) {
        isFitCenter = fitCenter;
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    public void override(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 设置转换.圆形,方形,等图形的转变
     *
     * @param drawableRequestBuilder
     */
    public void setTransform(DrawableRequestBuilder drawableRequestBuilder) {
        this.drawableRequestBuilder = drawableRequestBuilder;
    }

    /**
     * 设置 RequestListener
     * @param requestListener
     */
    public void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }


    /**
     * 加载网络的图片的方法
     * @param url         图片地址
     * @param imageView   ImageView 控件
     */
    public void load(String url, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        //按照原图比例放大缩小到view的宽高显示
        if (isFitCenter){
            loadFitCenter(url,imageView);
            return;
        }
        //按照指定高度大小显示
        if (width>0&&height>0){
            loadWithWH(url,imageView);
            return;
        }
        //默认情况
        Glide.with(weakReference.get())
            .load(url)
            .asBitmap()//加载静态图
            .centerCrop()
            .placeholder(placeholder)//设置占位图
            .diskCacheStrategy(mDiskCacheStrategy)
            .error(placeholder)//加载错误图
            .into(imageView);
    }

    /**
     * 加载网络的图片的方法 fitCenter
     * @param url         图片地址
     * @param imageView   ImageView 控件
     */
    private void loadFitCenter(String url, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        Glide.with(weakReference.get())
                .load(url)
                .asBitmap()//加载静态图
                .fitCenter()
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .into(imageView);
    }



    /**
     * 加载网络的图片的方法（圆角）
     * @param url         图片地址
     * @param imageView   ImageView 控件
     */
    public void loadCorners(String url, ImageView imageView) {
        if (null == imageView) {
            return;
        }

        Glide.with(weakReference.get())
                .load(url)
                .asBitmap()//加载静态图
                .centerCrop()
                .diskCacheStrategy(mDiskCacheStrategy)
                .transform(new CenterCrop(weakReference.get()),
                        new RoundedCornersTransformation(weakReference.get(),
                                DesityUtil.dp2px(weakReference.get(),
                                        radius), 0))
                .error(placeholder)//加载错误图
                .placeholder(placeholder)//设置占位图
                .into(imageView);

    }

    /**
     * 加载本地gif图片
     * @param resID       图片地址
     * @param imageView   ImageView 控件
     */
    public void loadGif(int resID, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        Glide.with(weakReference.get())
                .load(resID)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    /**
     * 加载本地图片
     * @param resID     本地图片資源ID 如 R.mipmap.list_grade
     * @param imageView ImageView 控件
     */
    public void loadLocalImage(Context context, int resID, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        Glide.with(context.getApplicationContext())
                .load(resID)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }





    /**
     * 按照指定的宽高加载图片
     * @param url         图片地址
     * @param imageView   ImageView 控件
     */
    private void loadWithWH(String url, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        Glide.with(weakReference.get())
                .load(url)
                .asBitmap()//加载静态图
                .centerCrop()
                .override(width, height)
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(error)//加载错误图
                .into(imageView);
    }

    /**
     * 按照指定的宽高加载圆形图片网络的图片的方法(圆形)
     *
     * @param url         图片地址
     * @param imageView   ImageView 控件
     */
    private void loadCircleImageWithWH(String url, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        Glide.with(weakReference.get())
                .load(url)
                .asBitmap()//加载静态图
                .centerCrop()
                .override(width, height)
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .transform(new CropCircleTransformation(weakReference.get()))//加载圆形图
                .into(imageView);
    }

    /**
     * 加载网络的图片的方法(圆形)
     *
     * @param context
     * @param url         图片地址
     * @param imageView   ImageView 控件
     * @param placeholder 加载过程或者加载失败时显示的图片资源文件
     */
    public void loadCircleImage(Context context, String url, ImageView imageView, int placeholder) {
        if (null == imageView) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }
        if (placeholder <= 0) {
            placeholder = android.R.color.transparent;
        }
        Glide.with(context.getApplicationContext())
                .load(url)
                .asBitmap()//加载静态图
                .centerCrop()
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .transform(new CropCircleTransformation(context))//加载圆形图
                .into(imageView);
    }

    /**
     * 加载网络的图片的方法(圆形带边框)
     *
     * @param context
     * @param url         图片地址
     * @param imageView   ImageView 控件
     * @param placeholder 加载过程或者加载失败时显示的图片资源文件
     */
    public void loadCircleBorderImage(Context context, String url, ImageView imageView, int placeholder, int borderWidth, int bordercolor) {
        if (null == imageView) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }
        if (placeholder <= 0) {
            placeholder = android.R.color.transparent;
        }
        Glide.with(context.getApplicationContext())
                .load(url)
                .asBitmap()//加载静态图
                .centerCrop()
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
//                .transform(new GlideCircleTransform(context))//不带边框的圆形图片加载
                .transform(new GlideCircleTransform(context, borderWidth, bordercolor))////带边框的圆形图片加载
                .into(imageView);
    }

    /**
     * 加载个人中心的头像和底图背景的毛玻璃效果
     *
     * @param context
     * @param url
     * @param imageAvatar     头像
     * @param imageBackground 底图背景、毛玻璃
     * @param bg1             头像默认图
     * @param bg2             底图默认图
     */
    public void loadMineAvatar(final Context context, String url, final ImageView imageAvatar, final ImageView imageBackground, final int bg1, final int bg2) {
        if (null == imageAvatar) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }

        Glide.with(context.getApplicationContext())
                .load(url)
                .asBitmap()//加载静态图
                .centerCrop()
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .transform(new CropCircleTransformation(context))//加载圆形图
                .into(imageAvatar);

    }

    /**
     * 加载圆角
     *
     * @param context
     * @param url
     * @param imageAvatar
     * @param radius
     * @param margin
     */
    public void loadCornersAvatar(final Context context, String url, ImageView imageAvatar, int placeholder, int radius, int margin, int width, int height) {
        if (null == imageAvatar) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }

        Glide.with(context.getApplicationContext())
                .load(url)
                .centerCrop()
                .override(width, height)
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .fitCenter()
                .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, dip2px(context, radius), margin))//加载圆角图
                .into(imageAvatar);
    }

    public void loadAppointCornresImage(Context context, String url, ImageView imageAvatar, int placeholder, RoundedCornersTransformation.CornerType type) {
        if (null == imageAvatar) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }

        Glide.with(context.getApplicationContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .fitCenter()
                .bitmapTransform(new RoundedCornersTransformation(context, dip2px(context, 3), 0, type))//加载圆形图
                .into(imageAvatar);
    }

    /**
     * 加载毛玻璃效果图
     *
     * @param context
     * @param url
     * @param imageAvatar
     * @param placeholder
     */
    public void loadBliurImage(Context context, String url, ImageView imageAvatar, int placeholder, int radius) {
        if (null == imageAvatar) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }

        Glide.with(context.getApplicationContext())
                .load(url)
                .asBitmap()//加载静态图
                .centerCrop()
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .transform(new BlurTransformation(context, radius))
//                .bitmapTransform(new BlurTransformation(context, radius))
                .into(imageAvatar);
    }

    /**
     * 加载聊天图片的方法
     *
     * @param context
     * @param url         图片地址
     * @param imageView   ImageView 控件
     * @param placeholder 加载过程或者加载失败时显示的图片资源文件
     */
    public void loadChatImage(Context context, String url, ImageView imageView, int placeholder, int relate, RoundedCornersTransformation.CornerType type) {
        if (null == imageView) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.SOURCE;
        }
        if (placeholder <= 0) {
            placeholder = android.R.color.transparent;
        }

        Glide.with(context.getApplicationContext())
                .load(url)
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .override(width, height)
                .centerCrop()
                .fitCenter()
                .bitmapTransform(new CenterCrop(context.getApplicationContext()),
                        new MaskTransformation(context, relate))
                .into(imageView);
    }

    /**
     * 加载聊天毛玻璃图片的方法
     *
     * @param context
     * @param url         图片地址
     * @param imageView   ImageView 控件
     * @param placeholder 加载过程或者加载失败时显示的图片资源文件
     * @param radius      毛玻璃化的程度
     */
    public void loadChatBlurImage(Context context, String url, ImageView imageView, int placeholder, int relate, RoundedCornersTransformation.CornerType type, int radius) {
        if (null == imageView) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.SOURCE;
        }
        if (placeholder <= 0) {
            placeholder = android.R.color.transparent;
        }

        Glide.with(context.getApplicationContext())
                .load(url)
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .override(width, height)
                .centerCrop()
                .fitCenter()
                .bitmapTransform(new CenterCrop(context.getApplicationContext()),
                        new BlurTransformation(context, radius),
                        new MaskTransformation(context, relate))
                .into(imageView);
    }

    /**
     * 获取视频缩略图 280*210
     *
     * @param context
     * @param uri         uri
     * @param imageView   ImageView 控件
     * @param placeholder 加载过程或者加载失败时显示的图片资源文件
     */
    public void loadChatImage(Context context, Uri uri, ImageView imageView, int placeholder, int relate, RoundedCornersTransformation.CornerType type) {
        if (null == imageView) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.SOURCE;
        }
        if (placeholder <= 0) {
            placeholder = android.R.color.transparent;
        }

        Glide.with(context.getApplicationContext())
                .load(uri)
                .asBitmap()//加载静态图
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .override(width, height)
                .centerCrop()
                .transform(new MaskTransformation(context, relate))
                .into(imageView);
    }

    /**
     * 加载聊天图片的方法
     *
     * @param context
     * @param resID       图片资源
     * @param imageView   ImageView 控件
     * @param placeholder 加载过程或者加载失败时显示的图片资源文件
     */
    public void loadChatImage(Context context, int resID, ImageView imageView, int placeholder, int relate, RoundedCornersTransformation.CornerType type) {
        if (null == imageView) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }
        if (placeholder <= 0) {
            placeholder = android.R.color.transparent;
        }

        Glide.with(context.getApplicationContext())
                .load(resID)
                .asBitmap()//加载静态图
                .diskCacheStrategy(mDiskCacheStrategy)
                .placeholder(placeholder)//设置占位图
                .error(placeholder)//加载错误图
                .centerCrop()
                .override(width, height)
                .transform(new MaskTransformation(context, relate))
                .into(imageView);
    }

    /**
     * 加载图片的方法
     *
     * @param context
     * @param url         图片地址
     * @param imageView   ImageView 控件
     * @param placeholder 加载过程或者加载失败时显示的图片资源文件
     */
    public void loadImage(final Context context, String url, final ImageView imageView, int placeholder) {
        if (null == imageView) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }
        if (placeholder <= 0) {
            placeholder = android.R.color.transparent;
        }

        Glide.with(context.getApplicationContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(mDiskCacheStrategy)
                .error(placeholder)//加载错误图
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Bitmap bm = GlideBitmapDrawable.class.cast(resource).getBitmap();
                        if (null != bm) {
                            DebugUtil.i(TAG + " getHeight = " + bm.getHeight());
                            DebugUtil.i(TAG + " getWidth = " + bm.getWidth());
                        } else {
                            DebugUtil.i(TAG + " ============null============");
                        }
                        imageView.setImageBitmap(bm);
                        return false;
                    }
                })
                .into(imageView);
    }


    public void onStart(Context context) {
        Glide.with(context).onStart();
    }


    public void onStop(Context context) {
        Glide.with(context).onStop();
    }

    /**
     * 清除Glide在APP中的缓存, 包括在内存中的缓存以及本地缓存
     *
     * @param context
     * @author: LinWeiDong
     */
    public boolean clearCache(Context context) {
        try {
            Glide.get(context.getApplicationContext()).clearMemory();//需要在主线程里执行
            DebugUtil.d(TAG + " ------------ Glide clearMemory -----------");
            singleThreadExecutor.execute(new clearDiskCacheRunnerBle(context));
        } catch (Exception e) {
            e.printStackTrace();
            DebugUtil.d(TAG + " ------------ 清理 Glide 缓存失败-----------");
            return false;
        }
        return true;
    }

    /**
     * 清楚Glide DiskCache
     *
     * @author: LinWeiDong
     */
    private class clearDiskCacheRunnerBle implements Runnable {
        private Context mContext;

        public clearDiskCacheRunnerBle(Context context) {
            mContext = context;
        }

        @Override
        public void run() {
            Looper.prepare();
            Glide.get(mContext.getApplicationContext()).clearDiskCache();//不能在主线程里执行
            DebugUtil.d(TAG + " ------------ Glide clearDiskCache -----------");
            Looper.loop();
        }
    }

    /**
     * 加载图片选择器的图片的方法，带有成功还是失败的监听
     *
     * @param context
     * @param url         图片地址
     * @param imageView   ImageView 控件
     * @param pbLoading   ProgressBar 加载中控件
     * @param placeholder 加载过程或者加载失败时显示的图片资源文件
     */
    private RequestListener mRequestListener;

    public void loadPreview(Context context, String url, ImageView imageView, final ProgressBar pbLoading, int placeholder) {
        if (null == imageView) {
            return;
        }
        if (null == mDiskCacheStrategy) {
            mDiskCacheStrategy = DiskCacheStrategy.RESULT;
        }
        if (placeholder <= 0) {
            placeholder = android.R.color.transparent;
        }
        if (null == mRequestListener) {
            mRequestListener = new RequestListener() {
                @Override
                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                    pbLoading.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                    pbLoading.setVisibility(View.GONE);
                    return false;
                }
            };
        }
        Glide.with(context.getApplicationContext())
                .load(url)
                .asBitmap()//加载静态图
                .fitCenter()
                .diskCacheStrategy(mDiskCacheStrategy)
                .error(placeholder)//加载错误图
                .listener(mRequestListener)
                .into(imageView);
    }

    //加载圆形（带边或不带边）
    static class GlideCircleTransform extends BitmapTransformation {

        private Paint mBorderPaint;
        private float mBorderWidth;

        public GlideCircleTransform(Context context) {
            super(context);
        }

        public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
            super(context);
            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;

            mBorderPaint = new Paint();
            mBorderPaint.setDither(true);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(borderColor);
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }


        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }



        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            if (mBorderPaint != null) {
                float borderRadius = r - mBorderWidth / 2;
                canvas.drawCircle(r, r, borderRadius, mBorderPaint);
            }
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
