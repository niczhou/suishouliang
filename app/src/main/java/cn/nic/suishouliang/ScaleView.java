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
import android.view.MotionEvent;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Scroller;

/**
 * Created by nic on 2018/1/31.
 */

public class ScaleView extends View {
    private static String TAG = "nic.print" ;
    private Paint paint;
    private int direction;
    protected Scroller scroller;
    private int lastX;

    protected OnScrollListener onScrollListener;

    public ScaleView(Context context) {
        super(context);
        initView();
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ScaleView);
        direction=typedArray.getInt(R.styleable.ScaleView_direction,0);
        typedArray.recycle();
        initView();
    }
        private void initView(){
            paint=new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
            paint.setColor(Color.parseColor("#fe5228"));

            scroller=new Scroller(getContext());
        }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawView(canvas);
    }
//Methods to draw scale
        private void drawView(Canvas canvas){
            int w=getWidth();
            int h=getHeight();
            drawFrame(canvas,w,h);
            drawMarks(canvas,w,h);
        }
            private void drawFrame(Canvas canvas,int width,int height){
                //draw frame,need add bg
                canvas.drawRect(0, 0, (float)width, (float) height, paint);
            }

            private void drawMarks(Canvas canvas,int width, int height){
                switch (direction){
                    case 0:
                        canvas.drawLine((float)width/2,0,(float)width/2,(float)height/2,paint);
                        break;
                    case 1:
                        canvas.drawLine((float)width,(float) height/2,(float)width/2,(float)height/2,paint);
                        break;
                    case 2:
                        canvas.drawLine((float)width/2,(float)height/2,(float)width/2,(float)height,paint);
                        break;
                    case 3:
                        canvas.drawLine(0,(float) height/2,(float)width/2,(float)height/2,paint);
                        break;
                }
                ///
            }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int) event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(scroller!=null && !scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                lastX = x;
//                Log.v("nic.print","touch down "+Integer.toString(x));
                return true;
            case MotionEvent.ACTION_MOVE:
                int deltaX=lastX-x;
                smoothScrollBy(deltaX,0);
                lastX =x;
                postInvalidate();
//                Log.v("nic.print","touch move "+Integer.toString(deltaX));
                return true;
        }
        return super.onTouchEvent(event);
    }

//Methods to handle scroll
        @Override
        public void computeScroll() {
            super.computeScroll();
            // 判断Scroller是否执行完毕
            if (scroller.computeScrollOffset()) {
                scrollTo(scroller.getCurrX(), scroller.getCurrY());
                // 通过重绘来不断调用computeScroll
                invalidate();
            }
        }

        public void smoothScrollBy(int dx, int dy) {
            scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), dx, dy);
        }

        public void smoothScrollTo(int fx, int fy) {
            int dx = fx - scroller.getFinalX();
            int dy = fy - scroller.getFinalY();
            smoothScrollBy(dx, dy);
        }

    private interface OnScrollListener{
        void onScroll(int scale);
    }
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
}
