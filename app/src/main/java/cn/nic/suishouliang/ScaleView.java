package cn.nic.suishouliang;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by nic on 2018/1/31.
 */

public class ScaleView extends View {
    private Paint paint;
    String direction;

    public ScaleView(Context context) {
        super(context);
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ScaleView);
        direction=typedArray.getString(R.styleable.ScaleView_direction);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFrame(canvas);
    }

    private void drawFrame(Canvas canvas){
        int w=getWidth();
        int h=getHeight();
        initPaint();
        Log.v("nic.print",direction);
//        if(direction=="south") {
            canvas.drawRect(0, 0, (float) w, (float) h, paint);
//        }else  if(direction=="west"){
            canvas.drawOval(new RectF(0, 0, (float) w, (float) h), paint);
//        }
    }

    private void initPaint(){
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(Color.parseColor("#fe5228"));
    }

}
