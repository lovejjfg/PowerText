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

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lovejjfg.demo.R
import com.lovejjfg.powerrecycle.PowerAdapter
import com.lovejjfg.powerrecycle.holder.PowerHolder
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_list.*
import kotlinx.android.synthetic.main.holder_text.view.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
//        setSupportActionBar(toolbar)
//        toolbar.setTitle()
        toolbar.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        mRecycler.layoutManager = LinearLayoutManager(this)
        val listAdapter = ListAdapter()
        mRecycler.adapter = listAdapter
        val list = ArrayList<ListBean>()
        (0..20).mapTo(list) {
            val d = (Math.random() * 10).toInt()
            if (d % 2 == 0) {
                ListBean("${it}奶茶妹妹章泽天成功入选2017年达沃斯全球杰出青年。", false)
            } else {
                ListBean("${it}今日，金山云宣布继 D 系列两次融资累计 5.2 亿美元后", false)
            }
        }

        listAdapter.setList(list)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    inner class ListAdapter : PowerAdapter<ListBean>() {
        override fun onViewHolderBind(holder: PowerHolder<ListBean>?, position: Int) {
            holder?.onBind(list[position])
        }

        override fun onViewHolderCreate(parent: ViewGroup?, viewType: Int): PowerHolder<ListBean> {
            return ListHolder(LayoutInflater.from(parent?.context).inflate(R.layout.holder_text, parent!!, false))
        }

    }

    class ListHolder(view: View) : PowerHolder<ListBean>(view, true) {
        override fun onBind(t: ListBean?) {
            val view = itemView.text
            view.setOnExpandChangeListener {
                println("onclick change..." + it)
                t?.expand = it
            }
            view.isExpanded = t?.expand!!
            view.setOriginalText(t?.text)
            view.setOnClickListener {
                println("click.....")
            }
            itemView.setOnClickListener {
                println("itemView onClick ...")
            }
        }

    }

    class ListBean(var text: String?, var expand: Boolean)

}
