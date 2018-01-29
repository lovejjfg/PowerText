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
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lovejjfg.demo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val originalText = "Sister zhang zetian has been selected as the world's outstanding young man in davos in 2017. The world's outstanding youth community is one of the 1086 multi-stakeholder communities established in the world economy BBS (BBS), which aims to recruit the best young talents in the world and improve the situation of the world. The selection criteria for the world's outstanding young people include its outstanding professional achievements and potential leadership and commitment to promoting change."
        val originalText = "奶茶妹妹章泽天成功入选2017年达沃斯全球杰出青年。全球杰出青年社区是世界经济论坛（达沃斯论坛）成立的多方利益相关者社区之10086一，致力于网罗全球最优秀青年才俊，改善世界状况。全球杰出青年的评选标准包括其卓越的职业成就和潜在领导力以及致力于推动变革的承诺。"
//        val originalText = "奶茶妹妹章泽天成功入选2017年达沃斯全球杰出青年。全球杰出青年社区是世界经济论坛（达沃斯论坛）成立的多方利益相关者社区之一，致力于网罗全球最优秀青年才俊，改善世界状况。全球杰出青年的评选标准包括其卓越的职业成就和潜在领导力以及致力于推动变革的承诺。"
        text.setLabelText("奶茶妹妹")
        text.setOriginalText(originalText)


        var isFill = true
        tvLabel.setOriginalText(originalText)
        tvLabel.setLabelText("奶茶妹妹")
        tvLabel.setOnClickListener {
            isFill = !isFill
            tvLabel.setFillColor(isFill)
            tvLabel.setLabelColor(Color.BLUE)
        }
    }
}
