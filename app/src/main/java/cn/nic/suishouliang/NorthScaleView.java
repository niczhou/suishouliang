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

public class NorthScaleView extends ScaleView {
    private int westPadding=0;
    private int startText=0;

    public NorthScaleView(Context context) {
        super(context);
        initView();
    }

    public NorthScaleView(Context context, @Nullable AttributeSet attrs) {
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
        westPadding=(int)(width%mm2dp_x());
        startText=(int)(width/(3*10*mm2dp_x()));
        for(int x=0;x<width;x++){
            int i=(int)((x-westPadding)/mm2dp_x());
            if((x-westPadding)%(10*mm2dp_x())==0){    //draw long mark
                canvas.drawLine(x, 0, x, longMark, paint);
                paint.setTextSize(largeFont);
                canvas.drawText(String.valueOf(i/10-startText),x-mm2dp_x(), longMark+largeFont, paint);
            }
            else if((x-westPadding)%(5*mm2dp_x())==0) {
                canvas.drawLine(x, 0, x, mediumMark, paint);
            }
            else if((x-westPadding)%mm2dp_x()==0){
                canvas.drawLine(x, 0, x, tinyMark, paint);
            }
        }
    }
}
