package cn.nic.suishouliang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by nic on 2018/3/7.
 */

public class ScaleView_East extends ScaleView {
    private boolean isFirstScroll=true;

    public ScaleView_East(Context context) {
        super(context);
        initView();
    }

    public ScaleView_East(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        direction = 1;
    }

    @Override
    protected void drawView(Canvas canvas) {
        Paint p = paint;
        p.setColor(Color.BLUE);
        canvas.drawRect(0,0,width,height,p);
        //draw h/2 to h
        for(int y=height/2;y<=height;y++){
            int i=(int)((y-height/2)/mm2dp_y());
            if((y-height/2)%(10*mm2dp_y())==0){    //draw long mark
                canvas.drawLine(width-longMark,y,width,y, paint);
                paint.setTextSize(largeFont);
                drawVerticalText(String.valueOf(i/10),width-longMark-largeFont,y-0.6f*mm2dp_y(),90, paint,canvas);
            }
            else if((y-height/2)%(5*mm2dp_x())==0) {
                canvas.drawLine(width-mediumMark,y,width,y, paint);
            }
            else if((y-height/2)%mm2dp_x()==0){
                canvas.drawLine(width-tinyMark,y,width,y, paint);
            }
        }
        for(int y=0;y<height/2;y++){
            int i=(int)((height/2-y)/mm2dp_y());
            if((height/2-y)%(10*mm2dp_y())==0){    //draw long mark
                canvas.drawLine(width-longMark,y,width,y, paint);
                paint.setTextSize(largeFont);
                drawVerticalText(String.valueOf(i/10),width-longMark-largeFont,y-0.6f*mm2dp_y(),90, paint,canvas);
            }
            else if((height/2-y)%(5*mm2dp_x())==0) {
                canvas.drawLine(width-mediumMark,y,width,y, paint);
            }
            else if((height/2-y)%mm2dp_x()==0){
                canvas.drawLine(width-tinyMark,y,width,y, paint);
            }
        }
        if(isFirstScroll){
            smoothScrollBy(0,height/2);
            isFirstScroll=false;
            invalidate();
        }
    }
}
