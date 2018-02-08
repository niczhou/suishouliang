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
    private Paint paint;
    private int direction;
    protected Scroller scroller;
    private int LastX;

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
            Log.i("nic.print",Integer.toString(direction));

            switch (direction){
                case 0:
                    canvas.drawRect(0, 0, (float) w, (float) h, paint);
                    canvas.drawLine((float)w/2,0,(float)w/2,(float)h/2,paint);
                    break;
                case 1:
                    canvas.drawRect(0, 0, (float) w, (float) h, paint);
                    canvas.drawLine((float)w,(float) h/2,(float)w/2,(float)h/2,paint);
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
                }else{
                    scroller=new Scroller(getContext());
                    LastX=x;
                    Log.i("nic.print",Integer.toString(x));
                    return true;
                }
            case MotionEvent.ACTION_MOVE:
                int deltaX=LastX-x;
                return true;
        }


        return super.onTouchEvent(event);
    }

    private interface OnScrollListener{
            void onScroll(int scale);
        }

        public void setOnScrollListener(OnScrollListener onScrollListener) {
            this.onScrollListener = onScrollListener;
        }

}
