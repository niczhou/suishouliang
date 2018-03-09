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

public class EastScaleView extends ScaleView {
    private int padding;
    private int startText;

    public EastScaleView(Context context) {
        super(context);
        initView();
    }

    public EastScaleView(Context context, @Nullable AttributeSet attrs) {
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
        Paint p =paint;
        p.setColor(Color.BLUE);
        canvas.drawRect(0,0,width,height,p);
        padding =(int)(height%mm2dp_y());
        startText=(int)(height/(3*10*mm2dp_y()));
        for(int y=0;y<height;y++){
            int i=(int)((y- padding)/mm2dp_y());
            if((y- padding)%(10*mm2dp_y())==0){    //draw long mark
                canvas.drawLine(width-longMark, y, width,y,p);
                paint.setTextSize(largeFont);
                drawVerticalText(canvas,90,String.valueOf(i/10-startText),width-longMark-largeFont,y-mm2dp_x(), paint);
            }
            else if((y- padding)%(5*mm2dp_y())==0) {
                canvas.drawLine(width-mediumMark, y, width,y, paint);
            }
            else if((y- padding)%mm2dp_y()==0){
                canvas.drawLine(width-tinyMark, y, width,y, paint);
            }
        }
//        canvas.restore();
    }
}
