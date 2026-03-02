package com.fenghongzhang.youbo_2307.widget;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fenghongzhang.youbo_2307.R;

public class FengTextView extends ViewGroup {

    Paint paint = new Paint();
    String string;
    public FengTextView(Context context) {
        this(context, null);
    }

    public FengTextView(Context context, @Nullable AttributeSet attrs) {
       this(context, attrs, 0);

    }

    public FengTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FengTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        paint.setColor(0xff000000);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.FengTextView);
        string  = typedArray.getString(R.styleable.FengTextView_text);

        float dimension = typedArray.getDimension(R.styleable.FengTextView_size, 100);
        paint.setTextSize(dimension);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(100, 100, 100 + child.getMeasuredWidth(), 100 + child.getMeasuredHeight());
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                size = 100;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }


        int Hmode = MeasureSpec.getMode(heightMeasureSpec);
        int Hsize = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                Hsize = 100;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(size, mode), MeasureSpec.makeMeasureSpec(Hsize, Hmode));
    }



    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(string, 100, 100, paint);

    }
}
