package com.funhotel.tvllibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author zhiyahan
 * @
 * Bitmap工具
 */

public class BitmapUtil
{
    /**
     * 创建倒影的图片
     * @param srcBitmap
     * @return
     */
    public static Bitmap createReflectedBitmap(Bitmap srcBitmap)
    {
        if (null == srcBitmap)
        {
            return null;
        }

        // The gap between the reflection bitmap and original bitmap. 
        final int REFLECTION_GAP = 8;

        int srcWidth  = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int reflectionWidth  = srcBitmap.getWidth();
        int reflectionHeight = srcBitmap.getHeight() / 4;

        if (0 == srcWidth || srcHeight == 0)
        {
            return null;
        }

        // The matrix
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        try
        {
            // The reflection bitmap, width is same with original's, height is half of original's.
            Bitmap reflectionBitmap = Bitmap.createBitmap(
                    srcBitmap,
                    0,
                    srcHeight / 2,
                    srcWidth,
                    srcHeight / 2,
                    matrix,
                    false);

            if (null == reflectionBitmap)
            {
                return null;
            }

            // Create the bitmap which contains original and reflection bitmap.
            Bitmap bitmapWithReflection = Bitmap.createBitmap(
                    reflectionWidth,
                    srcHeight + reflectionHeight + REFLECTION_GAP,
                    Config.ARGB_8888);

            if (null == bitmapWithReflection)
            {
                return null;
            }

            // Prepare the canvas to draw stuff.
            Canvas canvas = new Canvas(bitmapWithReflection);

            // Draw the original bitmap.
            canvas.drawBitmap(srcBitmap, 0, 0, null);

            // Draw the reflection bitmap.
            canvas.drawBitmap(reflectionBitmap, 0, srcHeight + REFLECTION_GAP, null);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            LinearGradient shader = new LinearGradient(
                    0,
                    srcHeight,
                    0,
                    bitmapWithReflection.getHeight() + REFLECTION_GAP,
                    0x70FFFFFF,
                    0x00FFFFFF,
                    TileMode.MIRROR);
            paint.setShader(shader);
            paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));

            // Draw the linear shader.
            canvas.drawRect(
                    0,
                    srcHeight,
                    srcWidth,
                    bitmapWithReflection.getHeight() + REFLECTION_GAP,
                    paint);

            return bitmapWithReflection;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 读取图片
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    /**
     * 读取图片
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId,Config config){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = config;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }


    public static byte[] compressBmp(String filePath) {
        int orientation=-1;
        orientation=getOrientation(filePath);
        Bitmap bmp=null;
        ByteArrayOutputStream baos = null;
        //将图片转为Bitmap
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        bmp = BitmapFactory.decodeFile(filePath,newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 1280f;
        float ww = 720f;
        int be = 1;
        if (w > h && w > ww) {
            //如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            //如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bmp = BitmapFactory.decodeFile(filePath, newOpts);
        if (orientation!=0) {
            //转正
            bmp=rotateImage(bmp, orientation);
        }
        if (null!=bmp) {
            //对bitmap进行质量压缩
            baos = new ByteArrayOutputStream();
            int options = 100;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length / 1024 > 400&&options>0) {
                baos.reset();
                options -= 5;
                if (options>0) {
                    bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
                }
            }
            DebugUtil.e("lenght>>>>"+baos.toByteArray().length/1024);
            bmp.recycle();
            bmp=null;
        }
        return baos.toByteArray();
    }

    /**
     * @Title: getOrientation
     * @Description: TODO 获取图片的方向
     *  @param filepath
     *  @return 旋转的角度
     */
    public static int getOrientation(String filepath) {
        //根据图片的filepath获取到一个ExifInterface的对象
        int degree=0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }
        }
        return degree;
    }

    /**
     * @Title: rotateImage
     * @Description: TODO 旋转的图片
     *  @param bmp
     *  @param degrees
     *  @return Bitmap
     */
    public static Bitmap rotateImage(Bitmap bmp, int degrees) {
        if (degrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }
        return bmp;
    }


    /**
     * 从SD卡中读取到图片
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapFromFile(String filePath) {
        Bitmap bmp=null;
        ByteArrayOutputStream baos = null;
        //将图片转为Bitmap
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inPreferredConfig = Config.RGB_565;
        //让参数设置已于回收.
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        bmp = BitmapFactory.decodeFile(filePath,newOpts);
        newOpts.inJustDecodeBounds = false;
        //对图片进行大小压缩
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 1920f;
        float ww = 1080f;
        int be = 1;
        if (w > h && w > ww) {
            //如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            //如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bmp = BitmapFactory.decodeFile(filePath, newOpts);
        if (null!=bmp) {
            //对bitmap进行质量压缩
            baos = new ByteArrayOutputStream();
            int options = 20;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //如果压缩的结果大于700k,持续压缩
            while (baos.toByteArray().length / 1024 > 700&&options>0) {
                baos.reset();
                options -= 5;
                if (options>0) {
                    bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
                }
            }
        }
        return bmp;
    }



}