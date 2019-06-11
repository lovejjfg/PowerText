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
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by joe on 2018/1/29.
 * Email: lovejjfg@gmail.com
 */

public class ClickFixedTextView extends AppCompatTextView {
    public boolean linkClicked;

    public ClickFixedTextView(Context context) {
        super(context);
    }

    public ClickFixedTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickFixedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        return !linkClicked && super.performClick();
    }
}
