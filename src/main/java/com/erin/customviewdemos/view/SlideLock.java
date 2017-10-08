package com.erin.customviewdemos.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.erin.customviewdemos.OnUnlockListener;
import com.erin.customviewdemos.R;


/**
 * Created by zhiyuan on 17/1/7.
 */

public class SlideLock extends View {

    private Bitmap jiesuo_bg;
    private Bitmap jiesuo_button;
    private int bg_width;
    private int bg_height;
    private int block_width;
    private int measuredWidth;
    private int measuredHeight;
    private float downX;
    private float downY;
    private float currentX;
    private float currentY;
    private boolean isOnBlock;
    private int left;
    private int right;
    private OnUnlockListener onUnlockListener;

    public SlideLock(Context context) {
        super(context);
        init();
    }

    private void init() {
        jiesuo_bg = BitmapFactory.decodeResource(getResources(), R.mipmap.jiesuo_bg);
        jiesuo_button = BitmapFactory.decodeResource(getResources(), R.mipmap.jiesuo_button);
        bg_width = jiesuo_bg.getWidth();
        bg_height = jiesuo_bg.getHeight();
        block_width = jiesuo_button.getWidth();
    }

    public SlideLock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public SlideLock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(jiesuo_bg, measuredWidth / 2 - bg_width / 2, measuredHeight / 2 - bg_height / 2, null);
        //控制边界
        if(currentX<left){
            currentX=left;
        }else if(currentX>right){
            currentX=right;
        }
        canvas.drawBitmap(jiesuo_button, currentX, currentY, null);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
        //获取一开始的位置
        currentX = measuredWidth / 2 - bg_width / 2;
        currentY = measuredHeight / 2 - bg_height / 2;
        left = measuredWidth / 2 - bg_width / 2;
        right = measuredWidth/2+bg_width/2-block_width;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //判断手指是否按在了小球上
                downX = event.getX();
                downY = event.getY();
                isOnBlock = isOnBlock(downX,downY);
                if(isOnBlock){
                    Toast.makeText(getContext(),"按到了",Toast.LENGTH_SHORT).show();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isOnBlock){
                    //获取最新的位置
                    float moveX = event.getX();
                    currentX=moveX-block_width/2;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                isOnBlock=false;
                if(currentX<right-5){
                    //应该弹回去
                    currentX=left;
                }else{
                    if(onUnlockListener!=null){
                        Toast.makeText(getContext(), "解锁", Toast.LENGTH_SHORT).show();
                        onUnlockListener.setUnlock(true);
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /**
     *
     * @param downX
     * @param downY
     * @return
     */
    private boolean isOnBlock(float downX, float downY) {
        //先计算圆心点
        float rx = currentX + block_width / 2;
        float ry = currentY + block_width / 2;

        double distance = Math.sqrt((downX - rx) * (downX - rx) + (downY - ry) * (downY - ry));
        if(distance<block_width/2){
            return true;
        }
        return false;
    }
    public void setOnUnlockListener(OnUnlockListener onUnlockListener) {
        this.onUnlockListener=onUnlockListener;
    }
}
