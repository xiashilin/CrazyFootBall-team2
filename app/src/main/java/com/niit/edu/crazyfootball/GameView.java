package com.niit.edu.crazyfootball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;

/**
 * Created by xsl on 2016/11/21.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private Bitmap bitmap, football_bitmap, man_bitmap;
    private Canvas canvas;
    private Rect mSrcRect, mDestRect;
    private boolean flag, isFlag;
    private int canvas_width, canvas_height, pic_width, pic_height, man_width = 890, man_height = 300, football_width, football_height;


    SoundPool soundPool;
    private HashMap<Integer, Integer> soundMap;

    public GameView(Context context) {
        super(context);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundMap = new HashMap<>();
        soundMap.put(1, soundPool.load(context, R.raw.game_music, 1));

        football_width = ((MainActivity) context).getWindowManager().getDefaultDisplay().getWidth() / 2;
        football_height = ((MainActivity) context).getWindowManager().getDefaultDisplay().getHeight() / 2;
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
                canvas.drawBitmap(man_bitmap, man_width, man_height, null);

                if (football_width > 205) {
                    if (isFlag)
                        football_width -= 50;
                    canvas.drawBitmap(football_bitmap, football_width, football_height, null);
                } else {
                    isFlag = false;
                    soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);
                    football_width = canvas_width / 2;
                    football_height = canvas_height / 2;
                    canvas.drawBitmap(football_bitmap, football_width, football_height, null);
                }

            }
        } catch (Exception e) {
        } finally {
            if (canvas != null)
                surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        man_height = (int) event.getY();
        man_width = (int) event.getX();
        isFlag = true;
        return super.onTouchEvent(event);
    }
}
