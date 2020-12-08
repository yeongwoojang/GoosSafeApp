package com.example.goodsafe.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goodsafe.R
import com.example.goodsafe.model.vo.Explain
import com.example.goodsafe.view.adapter.ExplainPagerAdapter
import kotlinx.android.synthetic.main.activity_explain.*
import kotlinx.android.synthetic.main.activity_explain.view.*

class ExplainActivity : AppCompatActivity() {

    companion object{
        val explain = arrayListOf<Explain>(
            Explain("1. 응급상황인지 아닌지 확인한다.","응급 상황 시 고려할점","1. 자기 자신을 위험한 상황에 노출시키지 않는다.\n\n2. 주변에 기름이나 가스같은 위험물질이 있는지 확인한다.\n\n3. 혼자서 너무 많은 것을 하려고 하지 않는다."),
            Explain("2. 무엇을 할 것인지 알아본다.","환자의 상태 파악\n" +
                    "응급상황 시 우선 순위","1. 현장 상황 및 주변 환경이 안전한지 파악한다.\n\n" +
                    "2. 환자 상태를 확인한다.\n\n" +
                    "3. 응급한 문제에 대하여 도움을 제공한다.\n\n" +
                    "4. 다른 사람에게 도움을 요청한다.\n\n" +
                    "5. 환자의 상태가 위급하다고 생각되면 119에 구급차 요청"),
            Explain("3. 구급차를 부른다.","응급상황 시 사람들은 당황하여 구조요청시기를 놓치는 경우가 있다.\n" +
                    "구조요청을 하지 않고 일반차량을 이용하여 이송하는 경우 환자에게 심각한 위험을 초래 할 수 있다.\n\n*응급환자를 신고할 때 천천히 또박또박 전달해야하는 사항들*\n\n",
                    "1. 환자가 발생한 위치, 주소 및 전화번호를 정확히 알려준다.\n\n" +
                    "2. 응급상황이 발생한 경위와 환자의 상태를 정확히 알려준다.\n\n" +
                    "3. 주위의 위험요소 유무 : 화재, 사고, 위험물질 등을 정확히 알려준다.\n\n" +
                    "4. 환자의 수를 정확히 알려준다."),
            Explain("4. 환자를 안전한 장소로 옮긴 후 응급처치를 실시한다.","대부분의 생명구조 활동은 가장 가까이에 있던 사람이 응급조치를 취했을 경우에 효과가 크다. 즉, 주위에 있는 사람의 즉각적인 응급조치가 가장 바람직하다.","")
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explain)

        val adapter = ExplainPagerAdapter(explain)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)

    }
}