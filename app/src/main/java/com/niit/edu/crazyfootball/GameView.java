package com.niit.edu.crazyfootball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by xsl on 2016/11/21.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private Bitmap bitmap, football_bitmap, man_bitmap;
    private Canvas canvas;
    private Rect mSrcRect, mDestRect;
    private boolean flag;
    private int canvas_width, canvas_height, pic_width, pic_height;


    /*定义一个矩形*/
    Rect rect = new Rect(0, 0, 400, 400);
    /*定义一个Region*/
    Region region = new Region(rect);

    public GameView(Context context) {
        super(context);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        flag = true;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.football_field1);
        football_bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.football);
        man_bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.man);

        pic_width = bitmap.getWidth();
        pic_height = bitmap.getHeight();
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        flag = false;
    }

    @Override
    public void run() {
        while (flag) {
            draw();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        try {
            canvas = surfaceHolder.lockCanvas();
            canvas_width = canvas.getWidth();
            canvas_height = canvas.getHeight();
            mSrcRect = new Rect(0, 0, pic_width, pic_height);
            mDestRect = new Rect(0, 0, canvas_width, canvas_height);
            if (canvas != null) {
                canvas.drawBitmap(bitmap, mSrcRect, mDestRect, null);
                canvas.drawBitmap(football_bitmap, canvas_width / 2, canvas_height / 2, null);
                canvas.drawBitmap(man_bitmap, canvas_width / 2 + 100, canvas_height / 2 - 160, null);
            }
        } catch (Exception e) {
        } finally {
            if (canvas != null)
                surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }
}
