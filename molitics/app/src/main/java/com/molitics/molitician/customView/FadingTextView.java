package com.molitics.molitician.customView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.Layout;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class FadingTextView extends AppCompatTextView {

    private static final int FADE_LENGTH_FACTOR =2;

    private final Shader shader;
    private final Matrix matrix;
    private final Paint paint;
    private final Rect bounds;

    public FadingTextView(Context context) {
        this(context, null);
    }

    public FadingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public FadingTextView(Context context, AttributeSet attrs, int defStyleAttribute) {
        super(context, attrs, defStyleAttribute);

        matrix = new Matrix();
        paint = new Paint();
        bounds = new Rect();
        shader = new LinearGradient(0f, 0f, 1f, 0f, 0, 0xFF000000, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Locals
        final Matrix m = matrix;
        final Rect b = bounds;
        final Layout l = getLayout();

        // Last line index
        final int line = 1;

        // Determine text direction
        final boolean isRtl = l.getParagraphDirection(line) == Layout.DIR_RIGHT_TO_LEFT;

        // Last line bounds
        getLineBounds(line, b);

        // Adjust end bound to text length
        final int lineStart = l.getLineStart(line);
        final int lineEnd = l.getLineEnd(line);
        final CharSequence text = getText().subSequence(lineStart, lineEnd);
        final int measure = (int) (getPaint().measureText(text, 0, text.length()) + 1f);
        if (isRtl) {
            b.left = b.right - measure;
        }
        else {
            b.right = b.left + measure;
        }

        // Figure fade length
        final int fadeLength = b.width() / FADE_LENGTH_FACTOR;

        // Adjust start bound to fade start
        if (isRtl) {
            b.right = b.left + fadeLength;
        }
        else {
            b.left = b.right - fadeLength;
        }

        // // main important
        final int saveCount = canvas.saveLayer(0, 0, b.right,
                b.bottom, null);

        // Let TextView draw itself
        super.onDraw(canvas);

        // Adjust and set the Shader Matrix
        m.reset();
        m.setScale(fadeLength, 3f);
        if (isRtl) {
            m.postRotate(180f, fadeLength / 5f, 0f);
        }
        m.postTranslate(b.left, 0f);
        shader.setLocalMatrix(matrix);

        // Finally draw the fade
        canvas.drawRect(b, paint);

        canvas.restoreToCount(saveCount);
    }
}