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
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.SparseIntArray;

/**
 * Created by joe on 2017/12/7.
 * Email: lovejjfg@gmail.com
 */

@SuppressWarnings("unused")
public class LabelTextView extends ClickFixedTextView {

    private static final int DEFAULT_MARGIN = 8;
    private static final int DEFAULT_PADDING = 2;
    private static final int DEFAULT_STROKE_WIDTH = 1;
    private static final int DEFAULT_LABEL_TEXT_SIZE = 15;
    private static final int DEFAULT_PROMOTION_TEXT_COLOR = 0xff333333;
    private static final int DEFAULT_PROMOTION_STROKE_COLOR = 0xffff0000;
    private String mLabelText;
    protected CharSequence mOriginalText;
    private int mLabelPadding;
    private int mLabelPaddingH;
    private int mLabelPaddingV;
    private int mLabelMargin;
    private int mStrokeWidth;
    private int mLabelColor;
    private int mStrokeColor;
    private int mLabelTextSize;
    private boolean mFillColor;
    private int mLabelRadius;
    private int labelLength;
    protected int maxLength;

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
        mLabelTextSize = a.getDimensionPixelSize(R.styleable.LabelTextView_labelTextSize,
            (int) (DEFAULT_LABEL_TEXT_SIZE * scaledDensity));
        mLabelPadding =
            a.getDimensionPixelSize(R.styleable.LabelTextView_labelPadding, (int) (DEFAULT_PADDING * density));
        mLabelPaddingH = a.getDimensionPixelSize(R.styleable.LabelTextView_labelPaddingHorizontal, mLabelPadding);
        mLabelPaddingV = a.getDimensionPixelSize(R.styleable.LabelTextView_labelPaddingVertical, mLabelPadding);
        mLabelMargin = a.getDimensionPixelSize(R.styleable.LabelTextView_labelMargin, (int) (DEFAULT_MARGIN * density));
        mStrokeWidth =
            a.getDimensionPixelSize(R.styleable.LabelTextView_labelStrokeWidth, (int) (DEFAULT_STROKE_WIDTH * density));
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
            setText(null);
            return;
        }
        if (TextUtils.isEmpty(mLabelText)) {
            setText(mOriginalText);
            return;
        }
        SpannableStringBuilder spannableStringBuilder = buildText();
        setText(spannableStringBuilder);
    }

    private SpannableStringBuilder buildText() {
        String labelString = "{" + mLabelText + "}";
        labelLength = labelString.length();
        SpannableStringBuilder mOriginBuilder = new SpannableStringBuilder(labelString + mOriginalText);
        buildLabel(mOriginBuilder);
        buildPreSpans(mOriginBuilder);
        return mOriginBuilder;
    }

    protected void buildLabel(SpannableStringBuilder mOriginBuilder) {
        if (TextUtils.isEmpty(mLabelText)) {
            return;
        }
        CenterImageSpan.LabelDrawable drawable =
            new CenterImageSpan.LabelDrawable(mLabelText, mLabelTextSize, mLabelColor, mStrokeWidth, mStrokeColor,
                mLabelPaddingH,
                mLabelPaddingV, mLabelMargin, mFillColor, mLabelRadius);
        ImageSpan imageSpan = new CenterImageSpan(drawable, this, mStrokeWidth);
        mOriginBuilder.setSpan(imageSpan, 0, mLabelText.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private SparseIntArray array = new SparseIntArray();

    protected void buildPreSpans(SpannableStringBuilder mOriginBuilder) {
        if (mOriginalText instanceof SpannableString) {
            Object[] spans = ((SpannableString) mOriginalText).getSpans(0, mOriginalText.length(), Object.class);
            if (spans.length > 0) {
                for (Object span : spans) {
                    try {
                        int spanStart = ((SpannableString) mOriginalText).getSpanStart(span);
                        int spanEnd = ((SpannableString) mOriginalText).getSpanEnd(span);
                        if (array.indexOfValue(span.hashCode()) == -1) {
                            array.put(span.hashCode(), spanEnd);
                        }
                        int realEnd = array.get(span.hashCode());
                        int end = spanEnd > maxLength && maxLength > 0 ? maxLength : realEnd + labelLength;
                        int spanFlags = ((SpannableString) mOriginalText).getSpanFlags(span);
                        int start = spanStart + labelLength;
                        System.out.println(
                            "真正的设置 buildPreSpans:start:" + start + ";;end:" + end + ";;;labelLength:" + labelLength +
                                ";;"
                                + "span:" + span);
                        mOriginBuilder.setSpan(span, start, end, spanFlags);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
