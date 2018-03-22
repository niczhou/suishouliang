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

public class ScaleView_North extends ScaleView {
    private boolean isFirstScroll = true;

    public ScaleView_North(Context context) {
        super(context);
        initView();
    }

    public ScaleView_North(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        direction = 0;
    }

    @Override
    protected void drawView(Canvas canvas) {
        Paint p = paint;
        p.setColor(Color.RED);
        canvas.drawRect(0,0,width,height,p);
        for(int x=width/2;x<=width;x++){
            int i=(int)((x-width/2)/mm2dp_x());
            if((x-width/2)%(10*mm2dp_x())==0){    //draw long mark
                canvas.drawLine(x, 0, x, longMark, paint);
                paint.setTextSize(largeFont);
                canvas.drawText(String.valueOf(i/10),x-0.6f*mm2dp_x(), longMark+largeFont, paint);
            }
            else if((x-width/2)%(5*mm2dp_x())==0) {
                canvas.drawLine(x, 0, x, mediumMark, paint);
            }
            else if((x-width/2)%mm2dp_x()==0){
                canvas.drawLine(x, 0, x, tinyMark, paint);
            }
        }
        for(int x=0;x<width/2;x++){
            int i=(int)((width/2-x)/mm2dp_x());
            if((width/2-x)%(10*mm2dp_x())==0){    //draw long mark
                canvas.drawLine(x, 0, x, longMark, paint);
                paint.setTextSize(largeFont);
                canvas.drawText(String.valueOf(i/10),x-0.6f*mm2dp_x(), longMark+largeFont, paint);
            }
            else if((width/2-x)%(5*mm2dp_x())==0) {
                canvas.drawLine(x, 0, x, mediumMark, paint);
            }
            else if((width/2-x)%mm2dp_x()==0){
                canvas.drawLine(x, 0, x, tinyMark, paint);
            }
        }
        if(isFirstScroll){
            smoothScrollBy(width/2,0);
            isFirstScroll=false;
            invalidate();
        }
    }
}
