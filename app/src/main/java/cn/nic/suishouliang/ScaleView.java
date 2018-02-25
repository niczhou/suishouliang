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
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ScaleView);
        direction=typedArray.getInt(R.styleable.ScaleView_direction,0);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFrame(canvas);

        scroller=new Scroller(getContext());
    }

        private void drawFrame(Canvas canvas){
            int w=getWidth();
            int h=getHeight();
            initPaint();
//            Log.i("nic.print",Integer.toString(direction));
            switch (direction){
                case 0:
                    canvas.drawRect(0, 0, (float) w, (float) h, paint);
                    canvas.drawLine((float)w/2,0,(float)w/2,(float)h/2,paint);
                    break;
                case 1:
                    canvas.drawRect(0, 0, (float) w, (float) h, paint);
                    canvas.drawLine((float)w,(float) h/2,(float)w/2,(float)h/2,paint);
                    break;
                case 2:
                    canvas.drawRect(0, 0, (float) w, (float) h, paint);
                    canvas.drawLine((float)w/2,(float)h/2,(float)w/2,(float)h,paint);
                    break;
                case 3:
                    canvas.drawRect(0, 0, (float) w, (float) h, paint);
                    canvas.drawLine(0,(float) h/2,(float)w/2,(float)h/2,paint);
                    break;
            }
        }
        private void initPaint(){
            paint=new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
            paint.setColor(Color.parseColor("#fe5228"));
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
                return true;

            case MotionEvent.ACTION_MOVE:
                int deltaX=lastX-x;
                smoothScrollBy(deltaX,0);
                lastX =x;
                postInvalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

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
