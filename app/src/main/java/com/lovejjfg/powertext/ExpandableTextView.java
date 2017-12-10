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
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by joe on 2017/12/8.
 * Email: lovejjfg@gmail.com
 */
@SuppressWarnings("unused")
public class ExpandableTextView extends LabelTextView {

    private int mDefaultLineCount = 2;
    private String mMoreHint = "更多";
    private boolean isExpand = false;
    private static final int HINT_COLOR = 0xffff4081;
    private int mHintColor = HINT_COLOR;


    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        mDefaultLineCount = a.getInt(R.styleable.ExpandableTextView_defaultLineCount, mDefaultLineCount);
        mMoreHint = a.getString(R.styleable.ExpandableTextView_defaultMoreHint);
        mHintColor = a.getColor(R.styleable.ExpandableTextView_moreHintColor, HINT_COLOR);
        isExpand = a.getBoolean(R.styleable.ExpandableTextView_isExpand, false);
        if (TextUtils.isEmpty(mMoreHint)) {
            mMoreHint = "更多";
        }
        a.recycle();
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void setOriginalText(CharSequence text) {
        mOriginalText = text;
        updateText();
    }

    public void setExpanded(boolean isExpand) {
        if (this.isExpand != isExpand) {
            this.isExpand = isExpand;
            setMaxLines(Integer.MAX_VALUE);
            updateText();
        }
    }

    public void setMoreHint(String moreHint) {
        this.mMoreHint = moreHint;
        updateText();
    }

    public boolean isExpanded() {
        return isExpand;
    }

    @Override
    protected void updateText() {
        super.updateText();
        if (TextUtils.isEmpty(mOriginalText)) {
            return;
        }
        calculateLines();
    }

    private void calculateLines() {

        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Layout layout = getLayout();
                if (layout == null || isExpand) {
                    setMaxLines(Integer.MAX_VALUE);
                    getViewTreeObserver().removeOnPreDrawListener(
                            this);
                    return true;
                }
                int lineCount = layout.getLineCount();
                if (lineCount > mDefaultLineCount) {
                    String substring;
                    if (!TextUtils.isEmpty(mLabelText)) {
                        String mText = "{" + mLabelText + "}" + mOriginalText;
                        substring = mText.substring(0, layout.getLineEnd(mDefaultLineCount - 1) - mMoreHint.length() - 1);
                    } else {
                        substring = mOriginalText.toString().substring(0, layout.getLineEnd(mDefaultLineCount - 1) - mMoreHint.length() - 1);
                    }
                    SpannableStringBuilder mOriginalBuilder = new SpannableStringBuilder(String.format("%s...%s", substring, mMoreHint));
                    mOriginalBuilder.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            setExpanded(true);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                            ds.setColor(mHintColor);
                            ds.bgColor = Color.TRANSPARENT;

                        }
                    }, substring.length() + 3, mOriginalBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if (!TextUtils.isEmpty(mLabelText)) {
                        LabelDrawable drawable = new LabelDrawable(mLabelText, mLabelTextSize, mLabelColor, mStrokeWidth, mStrokeColor, mLabelPaddingH, mLabelPaddingV, mLabelMargin, mFillColor, mLabelRadius);
                        CenterImageSpan imageSpan = new CenterImageSpan(drawable, DynamicDrawableSpan.ALIGN_BOTTOM, ExpandableTextView.this, mStrokeWidth);
                        mOriginalBuilder.setSpan(imageSpan, 0, mLabelText.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                    setText(mOriginalBuilder);
                }

                getViewTreeObserver().removeOnPreDrawListener(
                        this);
                return false;
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        setExpanded(((SavedState) state).isExband);
        super.onRestoreInstanceState(((SavedState) state).getSuperState());

    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        return new SavedState(parcelable, isExpand);
    }

    private static class SavedState extends BaseSavedState {
        boolean isExband;

        SavedState(Parcelable superState, boolean isExband) {
            super(superState);
            this.isExband = isExband;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(isExband ? 1 : 0);
        }

        private SavedState(Parcel in) {
            super(in);
            this.isExband = in.readInt() != 0;
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };

    }

}
