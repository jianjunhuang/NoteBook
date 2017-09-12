package com.jinjunhuang.notebook.view.weiget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.demo.jianjunhuang.mvptools.utils.UiUtils;
import com.jinjunhuang.notebook.R;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/12.
 */

public class WelcomeView extends View {

    private Bitmap bitmap;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int degree;

    private Camera camera = new Camera();

    private ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "degree", 0, 180);

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    public WelcomeView(Context context) {
        super(context);
    }

    public WelcomeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WelcomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.notebook_welcome);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        objectAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        objectAnimator.end();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = getWidth();
        float y = getHeight();
        float centerX = x / 2;
        float centerY = y / 2;
        float posX = centerX - bitmap.getWidth() / 2;
        float posY = centerY - bitmap.getHeight() / 2;

        //绘制右半部分
        canvas.save();
        canvas.clipRect(centerX, 0, x, y);
        canvas.drawBitmap(bitmap, posX, posY, paint);
        canvas.restore();

        //绘制左半
        canvas.save();
        canvas.clipRect(0, 0, centerX, y);
        canvas.drawBitmap(bitmap, posX, posY, paint);
        canvas.restore();


        //绘制右半动画
        canvas.save();
        if (degree < 90) {
            canvas.clipRect(centerX, 0, x, y);
        } else {
            canvas.clipRect(0, 0, centerX, y);
        }
        camera.save();
        camera.setLocation(0,0,20);
        camera.rotateY(degree);
        canvas.translate(centerX, centerY);//把投影移动回来
        camera.applyToCanvas(canvas);//投影到 canvas
        canvas.translate(-centerX, -centerY);//移动到轴心
        camera.restore();
        canvas.drawBitmap(bitmap, posX, posY, paint);
        canvas.restore();
    }
}
