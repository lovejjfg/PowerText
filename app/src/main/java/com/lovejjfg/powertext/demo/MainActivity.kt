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

package com.lovejjfg.powertext.demo

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.lovejjfg.demo.R
import kotlinx.android.synthetic.main.activity_main.text
import kotlinx.android.synthetic.main.activity_main.tvLabel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val originalText = "Sister zhang zetian has been selected as the world's outstanding young man in davos in 2017. The world's outstanding youth community is one of the 1086 multi-stakeholder communities established in the world economy BBS (BBS), which aims to recruit the best young talents in the world and improve the situation of the world. The selection criteria for the world's outstanding young people include its outstanding professional achievements and potential leadership and commitment to promoting change."
//        val originalText =
//            "奶茶妹妹章泽天成功入选2017年达沃斯全球杰出青年。"
        val originalText = "Revision 27.1.1\n" +
            "(April 2018)\n" +
            "Fixed issues\n" +
            "AsyncListDiffer doesn't call getChangePayload (AOSP issue 73961809)\n" +
            "Fragment ViewModel's onCleared not called (AOSP issue 74139250)\n" +
            "RecyclerView.setRecycledViewPool() increases attachCount even when adapter is null\n" +
            "RecyclerView NPE if SmoothScroller.onStop calling stop() or startSmoothScroller()\n" +
            "Fragment Replacement transaction causes previous fragment to flicker after new fragment is shown (AOSP issue 74051124)"

        val string = SpannableString(originalText)

//        string.setSpan(StyleSpan(Typeface.BOLD), 0, 200, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        string.setSpan(ForegroundColorSpan(Color.GREEN), 0, 200, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
//        string.setSpan(StyleSpan(Typeface.BOLD), 11, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        string.setSpan(
//            ForegroundColorSpan(Color.GREEN),
//            indexOf,
//            originalText.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )

//        text.setLabelText("奶茶妹妹")
        text.setOriginalText(string)

        var isFill = true
        tvLabel.setOriginalText(string)
//        tvLabel.setLabelText("奶茶妹妹1")
        tvLabel.setOnClickListener {
            isFill = !isFill
            tvLabel.setFillColor(isFill)
            tvLabel.setLabelColor(Color.BLUE)
        }
    }
}
