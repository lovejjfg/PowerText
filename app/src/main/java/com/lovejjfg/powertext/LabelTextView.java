/*
 *
 *  Copyright (c) 2017.  Joe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.lovejjfg.powertext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by joe on 2017/12/7.
 * Email: lovejjfg@gmail.com
 */

@SuppressWarnings("unused")
public class LabelTextView extends AppCompatTextView {

    private static final int DEFAULT_MARGIN = 10;
    private static final int DEFAULT_STROKE_WIDTH = 1;
    private static final int DEFAULT_LABEL_TEXT_SIZE = 15;
    private static final int DEFAULT_PROMOTION_TEXT_COLOR = 0xff333333;
    private static final int DEFAULT_PROMOTION_STROKE_COLOR = 0xffff0000;
    public String mLabelText;
    public CharSequence mOriginalText;
    public int mLabelPadding;
    public int mLabelPaddingH;
    public int mLabelPaddingV;
    public int mLabelMargin;
    public int mStrokeWidth;
    public int mLabelColor;
    protected int mStrokeColor;
    public int mLabelTextSize;
    protected boolean mFillColor;
    protected int mLabelRadius;
    private Drawable mLabelDrawable;

    public LabelTextView(Context context) {
        this(context, null);
    }

    public LabelTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LabelTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabelTextView);
        mLabelText = a.getString(R.styleable.LabelTextView_labelText);
        mOriginalText = a.getString(R.styleable.LabelTextView_originalText);
        float density = getResources().getDisplayMetrics().density;
        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        setText(mOriginalText);
        mLabelTextSize = a.getDimensionPixelSize(R.styleable.LabelTextView_labelTextSize, (int) (DEFAULT_LABEL_TEXT_SIZE * scaledDensity));
        mLabelPadding = a.getDimensionPixelSize(R.styleable.LabelTextView_labelPadding, (int) (DEFAULT_MARGIN * density));
        mLabelPaddingH = a.getDimensionPixelSize(R.styleable.LabelTextView_labelPaddingHorizontal, mLabelPadding);
        mLabelPaddingV = a.getDimensionPixelSize(R.styleable.LabelTextView_labelPaddingVertical, mLabelPadding);
        mLabelMargin = a.getDimensionPixelSize(R.styleable.LabelTextView_labelMargin, (int) (DEFAULT_MARGIN * density));
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.LabelTextView_labelStrokeWidth, (int) (DEFAULT_STROKE_WIDTH * density));
        mLabelRadius = a.getDimensionPixelSize(R.styleable.LabelTextView_labelStrokeRadius, 0);
        mLabelColor = a.getColor(R.styleable.LabelTextView_labelTextColor, DEFAULT_PROMOTION_TEXT_COLOR);
        mStrokeColor = a.getColor(R.styleable.LabelTextView_labelStrokeColor, DEFAULT_PROMOTION_STROKE_COLOR);
        mFillColor = a.getBoolean(R.styleable.LabelTextView_labelFillColor, false);
        a.recycle();
        updateText();
    }

    public void setOriginalText(CharSequence text) {
        if (!TextUtils.equals(text, mOriginalText)) {
            mOriginalText = text;
            setText(mOriginalText);
            updateText();
        }
    }

    public void setLabelDrawable(Drawable labelDrawable) {
        if (mLabelDrawable != labelDrawable) {
            mLabelDrawable = labelDrawable;
            updateText();
        }
    }


    public void setLabelText(String promotionText) {
        if (!TextUtils.equals(this.mLabelText, promotionText)) {
            this.mLabelText = promotionText;
            updateText();
        }
    }

    public void setLabelPadding(int promotionPadding) {
        if (mLabelPadding != promotionPadding) {
            this.mLabelPadding = promotionPadding;
            updateText();
        }
    }

    public void setLabelPaddingH(int promotionPaddingH) {
        if (mLabelPaddingH != promotionPaddingH) {
            this.mLabelPaddingH = promotionPaddingH;
            updateText();
        }
    }

    public void setLabelPaddingV(int promotionPaddingV) {
        if (mLabelPaddingV != promotionPaddingV) {
            this.mLabelPaddingV = promotionPaddingV;
            updateText();
        }
    }

    public void setLabelMargin(int promotionMargin) {
        if (mLabelMargin != promotionMargin) {
            this.mLabelMargin = promotionMargin;
            updateText();
        }
    }

    public void setStrokeWidth(int strokeWidth) {
        if (mStrokeWidth != strokeWidth) {
            this.mStrokeWidth = strokeWidth;
            updateText();
        }
    }

    public void setLabelColor(int promotionColor) {
        if (mLabelColor != promotionColor) {
            this.mLabelColor = promotionColor;
            updateText();
        }
    }

    public void setStrokeColor(int strokeColor) {
        if (mStrokeColor != strokeColor) {
            this.mStrokeColor = strokeColor;
            updateText();
        }
    }

    public void setLabelTextSize(int promotionTextSize) {
        if (mLabelTextSize != promotionTextSize) {
            this.mLabelTextSize = promotionTextSize;
            updateText();
        }
    }

    public void setFillColor(boolean fillColor) {
        if (mFillColor != fillColor) {
            this.mFillColor = fillColor;
            updateText();
        }
    }


    protected void updateText() {
        if (TextUtils.isEmpty(mOriginalText)) {
            return;
        }
        if (TextUtils.isEmpty(mLabelText)) {
            setText(mOriginalText);
            return;
        }
        if (isAttachedToWindow()) {
            buildLabelText();
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    buildLabelText();
                }
            });
        }


    }

    private void buildLabelText() {
        SpannableStringBuilder mOriginBuilder = new SpannableStringBuilder("{" + mLabelText + "}" + mOriginalText);
        if (mLabelDrawable == null) {
            LabelDrawable drawable = new LabelDrawable(mLabelText, mLabelTextSize, mLabelColor, mStrokeWidth, mStrokeColor, mLabelPaddingH, mLabelPaddingV, mLabelMargin, mFillColor, mLabelRadius);
            CenterImageSpan imageSpan = new CenterImageSpan(drawable, DynamicDrawableSpan.ALIGN_BOTTOM, LabelTextView.this, mStrokeWidth);
            mOriginBuilder.setSpan(imageSpan, 0, mLabelText.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            mOriginBuilder.setSpan(mLabelDrawable, 0, mLabelText.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        setText(mOriginBuilder);
    }


    protected static class LabelDrawable extends Drawable {

        private Paint mPaint;
        private Paint mTextPaint;

        private int mBorderColor = 0xff656565;
        private int mOutStroke;
        private final Rect bounds;
        private int mTextMargin = 15;
        private int mTextPaddingH = 15;
        private int mTextPaddingV = 15;
        private final String mText;
        private int mLabelColor;
        private int mLabelSize;
        private int ddy;
        private boolean mFillColor;
        private RectF mRectf;
        private int mLabelRadius;

        //maybe use builder
        LabelDrawable(String text, int mLabelSize, int promotionColor, int strokeWidth, int strokeColor, int paddingH, int paddingV, int margin, boolean fillColor, int labelRadius) {
            this.mTextPaddingH = paddingH;
            this.mTextPaddingV = paddingV;
            this.mFillColor = fillColor;
            mTextMargin = margin;
            mOutStroke = strokeWidth;
            this.mBorderColor = strokeColor;
            this.mLabelColor = promotionColor;
            this.mLabelSize = mLabelSize;
            mLabelRadius = labelRadius;
            mText = text;
            preparePaint();
            bounds = new Rect();
            mTextPaint.getTextBounds(text, 0, text.length(), bounds);
            if (bounds.top < 0) {
                ddy = bounds.top;
                bounds.top = 0;
                bounds.bottom -= ddy;
            }
            bounds.right = bounds.right + mTextPaddingH * 2 + mTextMargin;
            bounds.bottom = bounds.bottom + mTextPaddingV * 2;
            setBounds(bounds);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRectf = new RectF(bounds);
            mRectf.right -= mTextMargin;
            mRectf.top += mOutStroke;
            mRectf.left += mOutStroke * 0.5f;

        }

        void preparePaint() {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            if (mFillColor) {
                mPaint.setStyle(Paint.Style.FILL);
            } else {
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(mOutStroke);
            }
            mPaint.setColor(mBorderColor);
            mTextPaint = new Paint(mPaint);
            mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mTextPaint.setTextSize(mLabelSize);
            mTextPaint.setColor(mLabelColor);
            mTextPaint.setStrokeWidth(1f);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
        }


        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.drawRoundRect(mRectf, mLabelRadius, mLabelRadius, mPaint);
            canvas.drawText(mText, mRectf.centerX(), mTextPaddingV - ddy + mOutStroke * 0.5f, mTextPaint);

        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.UNKNOWN;
        }

    }

    protected static class CenterImageSpan extends ImageSpan {

        private int initialDescent;
        private float lineSpacingExtra;
        private int strokeWidth;


        CenterImageSpan(Drawable d, int verticalAlignment, TextView textView, int strokeWidth) {
            super(d, verticalAlignment);
            lineSpacingExtra = textView.getLineSpacingExtra();
            this.strokeWidth = strokeWidth;
        }


        @Override
        public void draw(Canvas canvas, CharSequence text,
                         int start, int end, float x,
                         int top, int y, int bottom, Paint paint) {
            Drawable b = getCachedDrawable();
            canvas.save();
            int transY = (int) (bottom - b.getBounds().bottom - lineSpacingExtra - strokeWidth * 0.5f);
            canvas.translate(x, transY);
            b.draw(canvas);
            canvas.restore();
        }


        @Override
        public int getSize(Paint paint, CharSequence text,
                           int start, int end,
                           Paint.FontMetricsInt fm) {
            Drawable d = getCachedDrawable();
            Rect rect = d.getBounds();
            int extraSpace = 0;
            if (fm != null) {

                int textHeight = fm.descent - fm.ascent;
                if (rect.bottom - rect.top - textHeight >= 0) {
                    initialDescent = fm.descent;
                    extraSpace = rect.bottom - rect.top - textHeight;
                }
                fm.descent = extraSpace / 2 + initialDescent;
                fm.bottom = fm.descent;
                fm.ascent = -rect.bottom + fm.descent + rect.top;
                fm.top = fm.ascent;


            }

            return rect.right;
        }

        // Redefined locally because it is a private member from DynamicDrawableSpan
        private Drawable getCachedDrawable() {
            WeakReference<Drawable> wr = mDrawableRef;
            Drawable d = null;

            if (wr != null)
                d = wr.get();

            if (d == null) {
                d = getDrawable();
                mDrawableRef = new WeakReference<>(d);
            }

            return d;
        }

        private WeakReference<Drawable> mDrawableRef;
    }
}
