package com.example.home_management_app.for_you_teen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.home_management_app.R
import com.example.home_management_app.databinding.FragmentForYouTeenBinding
import java.util.Calendar

class ForYouTeenFragment : Fragment() {

    lateinit var binding : FragmentForYouTeenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouTeenBinding.inflate(layoutInflater)

        loadSchoolNotice(2023, Calendar.DECEMBER, 9)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            loadSchoolNotice(year, month, dayOfMonth)
        }

        return binding.root
    }

    private fun loadSchoolNotice(year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }

        val noticeText = when {
            selectedDate.isSameDay(2023, Calendar.DECEMBER, 11) -> "11일 공지사항: 금일은 공지사항이 없습니다."
            selectedDate.isSameDay(2023, Calendar.DECEMBER, 10) -> "10일 공지사항: 금일은 공지사항이 없습니다."
            selectedDate.isSameDay(2023, Calendar.DECEMBER, 9) -> "9일 공지사항: 전북교육아카데미(전북교육청 교육협력과)에서 실시하는 아래 강의에 관심있는 학생, 학부모 및 교직원의 많은 참여 부탁드립니다.  \n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    "강의일시 : 12. 20.(수) 14:00 ~ 16:00\n" +
                    "\n" +
                    "강사명 : 신종호\n" +
                    "\n" +
                    "강연장소 : 전주(전북교육청 창조나래(별관) 3층 시청각실)\n" +
                    "\n" +
                    "주요경력 : 서울대학교 사범대학 교수" +
                    "                    \n" +
                    "                    미네소타대학교 대학원 박사" +
                    "                    \n" +
                    "                    EBS<미래교육 플러스>, tvn<유퀴즈>\n" +
                    " \n" +
                    "\n" +
                    "▣ 강의주제: AI시대, 변화하는 교육 따라잡기\n" +
                    "\n" +
                    "▣ 대상: 학부모, 교직원 및 도민 등 누구나\n" +
                    "\n" +
                    "▣ 교육신청: 2023. 12. 7.(목) 09:00부터 접수순 300명 마감\n" +
                    "\n" +
                    "      - 전북학부모지원센터 누리집(https://www.jbe.go.kr/parents/index.jbe) 접수\n"

            else -> ""
        }

        binding.tvSchoolInfo.text = noticeText
    }

    private fun Calendar.isSameDay(year: Int, month: Int, day: Int): Boolean {
        return get(Calendar.YEAR) == year && get(Calendar.MONTH) == month && get(Calendar.DAY_OF_MONTH) == day
    }
}