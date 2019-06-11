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

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;

/**
 * Created by joe on 2018/7/12.
 * Email: lovejjfg@gmail.com
 */
class CenterImageSpan extends ImageSpan {

    private int initialDescent;
    private TextView textView;
    private int strokeWidth;

    CenterImageSpan(Drawable d, TextView textView, int strokeWidth) {
        super(d, DynamicDrawableSpan.ALIGN_BOTTOM);
        this.textView = textView;
        this.strokeWidth = strokeWidth;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text,
        int start, int end, float x,
        int top, int y, int bottom, @NonNull Paint paint) {
        Drawable b = getCachedDrawable();
        canvas.save();
        int transY = (int) ((bottom - b.getBounds().bottom - strokeWidth) * 0.5f);
        float transY1 = textView.getLayout().getLineCount() > 1 ? textView.getLineSpacingExtra() : 0;
        transY -= transY1 * 0.5f;
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text,
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

        if (wr != null) {
            d = wr.get();
        }

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }

        return d;
    }

    private WeakReference<Drawable> mDrawableRef;

    static class LabelDrawable extends Drawable {
        private Paint mPaint;
        private Paint mTextPaint;

        private int mBorderColor;
        private int mOutStroke;
        private final Rect bounds;
        private int mTextMargin;
        private int mTextPaddingH;
        private int mTextPaddingV;
        private final String mText;
        private int mLabelColor;
        private int mLabelSize;
        private int ddy;
        private boolean mFillColor;
        private RectF mRectf;
        private int mLabelRadius;

        //maybe use builder
        LabelDrawable(String text, int mLabelSize, int promotionColor, int strokeWidth, int strokeColor, int paddingH,
            int paddingV, int margin, boolean fillColor, int labelRadius) {
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
}
