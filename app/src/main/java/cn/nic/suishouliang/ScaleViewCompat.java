package cn.nic.suishouliang;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by nic on 2018/3/7.
 */

public class ScaleViewCompat extends View {
    private static final String TAG = "nex.print";
    private int width;
    private int height;
    private int direction;
    private Paint paint;
    int largeFont =38;
    int longMark=56;
    int mediumMark=36;
    int tinyMark=24;
    int westPadding=0;
    int northPadding=0;
    int startText=0;
    float x_dpi=403.411f;
    float y_dpi=403.041f;

    public ScaleViewCompat(Context context) {
        super(context);
        initView();
    }

    public ScaleViewCompat(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.ScaleViewCompat);
        direction = typedArray.getInt(R.styleable.ScaleViewCompat_dir,0);

        typedArray.recycle();
        initView();
    }

        //init view
        private void initView(){
            paint = new Paint();
            paint.setColor(Color.parseColor("#fe5228"));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);

        }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
        switch (direction){
            case 0:
            case 2:
                width = 3*measureSize(widthMeasureSpec,480);
                height = measureSize(heightMeasureSpec,120);
                break;
            case 1:
            case 3:
                width = measureSize(widthMeasureSpec,120);
                height = 3 * measureSize(heightMeasureSpec,480);
                break;
        }
        setMeasuredDimension(width,height);
    }
        //measure size
        private int measureSize(int measureSpec,int defaultSize){
            int mode = MeasureSpec.getMode(measureSpec);
            int size = MeasureSpec.getSize(measureSpec);
            int measuredSize = 0;
            switch (mode){
                case MeasureSpec.EXACTLY:
                    measuredSize = size;
                    break;
                case MeasureSpec.AT_MOST:
                    measuredSize = defaultSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    measuredSize = defaultSize;
                    break;
            }
            return measuredSize;
        }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        Log.v(TAG,"w/h:"+width+"/"+height);
        drawView(canvas);
    }
        //implement drawView
        private void drawView(Canvas canvas){


//            drawEastScale(canvas);
            switch (direction){
                case 0:
                    drawNorthScale(canvas);
                    break;
                case 1:
                    drawEastScale(canvas);
                    break;
            }
        }
            //draw north
            private void drawNorthScale(Canvas canvas){
                Paint p =paint;
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
            //draw east scale
            private void drawEastScale(Canvas canvas){
                Paint p =paint;
                p.setColor(Color.BLUE);
                canvas.drawRect(0,0,width,height,p);
                canvas.save();
                canvas.rotate(90,width/2,height/2);
                canvas.drawRect(0,0,height,width,p);
                canvas.restore();
                northPadding=(int)(height%mm2dp_y());
                startText=(int)(height/(3*10*mm2dp_y()));
                for(int y=0;y<height;y++){
                    int i=(int)((y-westPadding)/mm2dp_x());
                    if((y-northPadding)%(10*mm2dp_y())==0){    //draw long mark
                        canvas.drawLine(width-longMark, y, width, y, paint);
                        paint.setTextSize(largeFont);
                        canvas.drawText(String.valueOf(i/10-startText),y-mm2dp_x(), longMark+largeFont, paint);
                    }
                    else if((y-northPadding)%(5*mm2dp_y())==0) {
                        canvas.drawLine(width-mediumMark, y, width, y, paint);
                    }
                    else if((y-northPadding)%mm2dp_y()==0){
                        canvas.drawLine(width-tinyMark, y, width, y, paint);
                    }
                }
            }

            //mm to dp
            private int mm2dp_x(){
                return (int) (x_dpi/25.4f+0.5f);
            }
            private int mm2dp_y(){ return (int) (y_dpi/25.4f+0.5f);}
}
