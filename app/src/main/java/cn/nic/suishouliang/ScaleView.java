package cn.nic.suishouliang;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by nic on 2018/3/7.
 */

public abstract class ScaleView extends View {
    protected static final String TAG = "nex.print";
    protected int direction;
    protected int width;
    protected int height;
    protected Paint paint;
    protected boolean isMovable = true;
    protected Scroller scroller;
    protected float x_dpi=403.411f;
    protected float y_dpi=403.041f;
    protected int largeFont =38;
    protected int longMark=56;
    protected int mediumMark=36;
    protected int tinyMark=24;
    private int lastX;
    private int lastY;
    private int markColor;

//    protected OnScrollListener onScrollListener;

    public ScaleView(Context context) {
        super(context);
        initView();
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.ScaleView);
//        direction = typedArray.getInt(R.styleable.ScaleView_orientation,0);
        markColor = typedArray.getColor(R.styleable.ScaleView_direction,Color.BLUE);


        typedArray.recycle();
        initView();
    }
        //init view
        protected void initView(){
            paint = new Paint();
            paint.setColor(Color.parseColor("#fe5228"));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);

            scroller = new Scroller(getContext());
        }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = 0;
        int h = 0;
        switch (direction){
            case 0:
            case 2:
                w = 3 * measureSize(widthMeasureSpec,480);
                h = measureSize(heightMeasureSpec,120);
                break;
            case 1:
            case 3:
                w = measureSize(widthMeasureSpec,120);
                h = 3 * measureSize(heightMeasureSpec,480);
                break;
        }
        setMeasuredDimension(w,h);
    }
        //measure size
        private int measureSize(int measureSpec,int defaultSize){
            int mode = MeasureSpec.getMode(measureSpec);
            int size = MeasureSpec.getSize(measureSpec);
            int measuredSize = defaultSize;
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
        width = getWidth();
        height =getHeight();
        super.onDraw(canvas);
        Log.v(TAG,"w/h:"+width+"/"+height);
        drawView(canvas);
    }
        //drawView
        protected abstract void drawView(Canvas canvas);
        //mm2dp
        protected int mm2dp_x(){return (int) (x_dpi/25.4f+0.5f);}
        protected int mm2dp_y(){ return (int) (y_dpi/25.4f+0.5f);}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        if(isMovable == true){
            switch (direction) {
                case 0:
                case 2:
                    int x = (int) event.getX();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (scroller != null && scroller.isFinished()) {
                                scroller.abortAnimation();
                            }
                            lastX = x;
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            int deltaX = lastX - x;
                            smoothScrollBy(deltaX, 0); //smooth move
                            lastX = x;
                            postInvalidate();
                            return true;
                        case MotionEvent.ACTION_UP:
                            return true;
                    }
                    break;
                case 1:
                case 3:
                    int y = (int) event.getY();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (scroller != null && scroller.isFinished()) {
                                scroller.abortAnimation();
                            }
                            lastY = y;
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            int deltaY = lastY - y;
                            smoothScrollBy(0,deltaY); //smooth move
                            lastY = y;
                            postInvalidate();
                            return true;
                        case MotionEvent.ACTION_UP:
                            return true;
                    }
                    break;
            }
        }
        return  super.onTouchEvent(event);
    }
        //smooth move by
        private void smoothScrollBy(int dx,int dy){
            scroller.startScroll(scroller.getFinalX(),scroller.getFinalY(),dx,dy);
        }
        private void smoothScrollTo(int fx,int fy){
            int dx = fx - scroller.getFinalX();
            int dy = fy - scroller.getFinalY();
            smoothScrollBy(dx,dy);
        }

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

    protected void drawVerticalText(Canvas canvas,int angle,String string,float x,float y,Paint paint){
        canvas.save();
        canvas.rotate(angle,x,y);
        canvas.drawText(string,x,y,paint);
        canvas.restore();
    }

//    protected interface OnScrollListener{
//        void onScroll();
//    }
//    public void setOnScrollListener(OnScrollListener onScrollListener){
//        this.onScrollListener = onScrollListener;
//    }
}
