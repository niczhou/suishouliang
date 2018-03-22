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
    protected boolean isLeftOnly = false;
    protected boolean isRightOnly =true;
    private boolean isMovable = false;
    protected int margin = 46;
    protected int padding = 16;
    protected boolean isFirstScroll = true;

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
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w=120;
        int h=120;
        switch (direction){
            case 0:
            case 2:
                w = 4* measureSize(widthMeasureSpec,800);
                h = measureSize(heightMeasureSpec,120);
                break;
            case 1:
            case 3:
                w = measureSize(widthMeasureSpec,120);
                h = 4*measureSize(heightMeasureSpec,800);
                break;
        }
//        Log.v(TAG, w+"-w/h-"+h);
        setMeasuredDimension(w,h);
    }
        //measure size
        private int measureSize(int measureSpec,int defaultSize){
            int mode = MeasureSpec.getMode(measureSpec);
            int size = MeasureSpec.getSize(measureSpec);
            int mSize = defaultSize;
            switch (mode){
                case MeasureSpec.EXACTLY:
                    mSize = size;
                    break;
                case MeasureSpec.AT_MOST:
                    mSize = defaultSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    mSize = defaultSize;
                    break;
            }
            return mSize;
        }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (direction) {
            case 0:
            case 2:
                width = 4 * getWidth();
                height = getHeight();
                break;
            case 1:
            case 3:
                width = getWidth();
                height = 4 * getHeight();
                break;
        }
//        width = getWidth();
//        height = getHeight();
        super.onDraw(canvas);
        drawView(canvas);
        if(isFirstScroll) {
            fitScroll(margin, padding);
            isFirstScroll = false;
        }
    }
        //drawView
        protected abstract void drawView(Canvas canvas);
        protected abstract void fitScroll(int margin, int padding);
        //mm2dp
        protected int mm2dp_x(){return (int) (x_dpi/25.4f+0.5f);}
        protected int mm2dp_y(){ return (int) (y_dpi/25.4f+0.5f);}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        if(isMovable) {
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
                            smoothScrollBy(0, deltaY); //smooth move
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
        protected void smoothScrollBy(int dx,int dy){
            scroller.startScroll(scroller.getFinalX(),scroller.getFinalY(),dx,dy);
        }
        protected void smoothScrollTo(int fx,int fy){
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

    protected void drawVerticalText(String string,float x,float y,int angle,Paint paint,Canvas canvas){
        canvas.save();
        canvas.rotate(angle,x,y);
        canvas.drawText(string,x,y,paint);
        canvas.restore();
    }
    public void setMovable(boolean movable) {
        isMovable = movable;
    }
    public boolean isMovable(){return isMovable;}
//    protected interface OnScrollListener{
//        void onScroll();
//    }
//    public void setOnScrollListener(OnScrollListener onScrollListener){
//        this.onScrollListener = onScrollListener;
//    }
}
