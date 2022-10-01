package com.molitics.molitician.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.molitics.molitician.R;

/**
 * Created by rahul on 10/3/2016.
 */
public class PieView extends View {

    public PieView(Context context) {
        super(context);
        init();
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        neutral_paint = new Paint();
        neutral_paint.setColor(getContext().getResources().getColor(R.color.neutral));
        neutral_paint.setAntiAlias(true);
        neutral_paint.setStyle(Paint.Style.FILL);
        yes_paint = new Paint();
        yes_paint.setColor(getContext().getResources().getColor(R.color.red));
        yes_paint.setAntiAlias(true);
        yes_paint.setStyle(Paint.Style.FILL);
        no_paint = new Paint();
        no_paint.setColor(getContext().getResources().getColor(R.color.no_color));
        no_paint.setAntiAlias(true);
        no_paint.setStyle(Paint.Style.FILL);
        whitePaint = new Paint();
        whitePaint.setColor(getContext().getResources().getColor(R.color.white));
        whitePaint.setAntiAlias(true);
        whitePaint.setStyle(Paint.Style.FILL);
        rect = new RectF();
        white_rect = new RectF();
    }

    Paint whitePaint;
    Paint no_paint;
    Paint neutral_paint;
    Paint yes_paint;
    RectF rect;
    RectF white_rect;
    float neturalPercentage = 0;
    float noPercentage = 0;
    int drawRing = 10;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw background circle anyway
        int left = 0;
        int width = getWidth();
        int top = 0;
        rect.set(left, top, left + width, top + width);
        white_rect.set(left + drawRing, top + drawRing, left + width - drawRing, top + width - drawRing);
        canvas.drawArc(rect, -90, 360, true, yes_paint);

        if (neturalPercentage != 0) {
            canvas.drawArc(rect, -90, (360 * neturalPercentage), true, neutral_paint);
        }
        if (noPercentage != 0) {
            canvas.drawArc(rect, -90, neturalPercentage - (360 * noPercentage), true, no_paint);
        }
        canvas.drawArc(white_rect, -90, 360, true, whitePaint);
    }

    public void setNeutralPercentage(float neturalPercentage) {
        this.neturalPercentage = neturalPercentage / 100;
        invalidate();
    }

    public void setNoPercentage(float noPercentage) {
        this.noPercentage = noPercentage / 100;
        invalidate();
    }
}


