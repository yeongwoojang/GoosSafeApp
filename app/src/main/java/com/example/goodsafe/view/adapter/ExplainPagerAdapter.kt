package com.example.goodsafe.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.goodsafe.R
import com.example.goodsafe.model.vo.Explain
import kotlinx.android.synthetic.main.item_view.view.*

class ExplainPagerAdapter(private val list : ArrayList<Explain>) :PagerAdapter(){
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_view,container,false)

        view.title_text.text = list[position].title
        view.content.text = list[position].content
        view.subContent.text = list[position].subContent

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


}