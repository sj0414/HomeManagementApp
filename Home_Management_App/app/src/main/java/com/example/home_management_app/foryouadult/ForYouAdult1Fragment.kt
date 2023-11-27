package com.example.home_management_app.foryouadult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home_management_app.databinding.FragmentForYouAdult1Binding

class ForYouAdult1Fragment : Fragment() {
    private lateinit var binding: FragmentForYouAdult1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouAdult1Binding.inflate(inflater, container, false)

        binding.calendarView.setOnDateChangeListener {
                _,
                year,
                month,
                dayOfMonth ->
            loadSchoolNotice(year, month, dayOfMonth)
        }

        return binding.root
    }

    private fun loadSchoolNotice(year: Int, month: Int, dayOfMonth: Int) {
        // 여기에 웹 스크레이핑 로직을 추가
        // 예시: Jsoup.connect("https://school-website.com/notice?date=$year-$month-$dayOfMonth").get()
        // 결과를 TextView에 표시
        // binding.textViewNotice.text = "가져온 가정통신문 내용"
    }
}

