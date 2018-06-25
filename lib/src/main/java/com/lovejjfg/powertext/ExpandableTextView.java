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
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

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
        setMovementMethod(FixedLinkMovementMethod.getInstance());
    }

    @Override
    public void setOriginalText(CharSequence text) {
        super.setOriginalText(text);
        updateText();
    }

    public void setExpanded(boolean isExpand) {
        if (this.isExpand != isExpand) {
            this.isExpand = isExpand;
        }
        updateText();
        if (mListener != null) {
            mListener.onExpandChange(isExpand);
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
        if (!isExpand) {
            setMaxLines(mDefaultLineCount);
        } else {
            setMaxLines(Integer.MAX_VALUE);
        }
        super.updateText();
        calculateLineCount();
    }

    private boolean calculateLineCount() {
        CharSequence mText = getText();
        if (TextUtils.isEmpty(mText)) {
            return true;
        }
        Layout layout = getLayout();
        if (layout == null || isExpand) {
            setMaxLines(Integer.MAX_VALUE);
            return false;
        }
        int lineCount = layout.getLineCount();
        SpannableStringBuilder mOriginalBuilder;
        if (lineCount > mDefaultLineCount) {
            CharSequence substring;
            int moreLength = 0;
            moreLength = getMoreLength(layout, moreLength, mText);
            substring = mText.subSequence(0, layout.getLineEnd(mDefaultLineCount - 1) - moreLength);
            mOriginalBuilder =
                new SpannableStringBuilder(String.format("%s...%s", substring, mMoreHint));
            buildMoreSpan(mOriginalBuilder, substring);
            buildLabel(mOriginalBuilder);
            buildPreSpans(mOriginalBuilder);
            setMaxLines(mDefaultLineCount);
            setText(mOriginalBuilder);
        }
        return true;
    }

    private void buildMoreSpan(SpannableStringBuilder mOriginalBuilder, CharSequence substring) {
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
    }

    private int getMoreLength(Layout layout, int moreLength, CharSequence mText) {
        int lastLine = mDefaultLineCount - 1;
        moreLength++;
        CharSequence newText =
            mText.subSequence(layout.getLineStart(lastLine), layout.getLineEnd(lastLine) - moreLength);

        while (getPaint().measureText(newText + "..." + mMoreHint) > layout.getWidth()) {
            moreLength++;
            newText = mText.subSequence(layout.getLineStart(lastLine), layout.getLineEnd(lastLine) - moreLength);
        }
        return moreLength;
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

        public static final Creator<SavedState> CREATOR =
            new Creator<SavedState>() {
                public SavedState createFromParcel(Parcel in) {
                    return new SavedState(in);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
    }

    @Nullable
    private ExpandChangeListener mListener;

    public void setOnExpandChangeListener(ExpandChangeListener listener) {
        mListener = listener;
    }

    public interface ExpandChangeListener {
        void onExpandChange(boolean expanded);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int lineCount = getLayout().getLineCount();
        if (calculateLineCount()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
