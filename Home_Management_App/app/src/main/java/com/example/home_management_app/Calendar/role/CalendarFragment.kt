package com.example.home_management_app.Calendar.role

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.R
import com.example.home_management_app.databinding.FragmentCalendarBinding
import com.example.home_management_app.model.EventRepository
import com.example.home_management_app.model.OnEventChangeListener
import com.example.home_management_app.model.RecyclerCalendarConfiguration
import com.example.home_management_app.model.SimpleEvent
import com.example.home_management_app.Calendar.CalendarUtils

import com.example.home_management_app.Calendar.ScheduleActionBottomSheetDialogFragment
import com.example.home_management_app.Calendar.vertical.VerticalRecyclerCalendarAdapter
import com.example.home_management_app.databinding.FragmentRoleManagementBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random

class CalendarFragment : Fragment(), OnEventChangeListener {
    private val eventMap: HashMap<Int, SimpleEvent> = HashMap()
    // 받아온 데이터를 받을 리스트
    lateinit var FDB : DatabaseReference
    lateinit var binding: FragmentCalendarBinding
    private val configuration: RecyclerCalendarConfiguration =
        RecyclerCalendarConfiguration(
            calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
            calendarLocale = Locale.getDefault(),
            includeMonthHeader = true
        )


    override fun onEventChanged() {
        updateCalendar()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 이벤트와 역할 목록을 정의합니다.
        val eventList = listOf("가족과 저녁 식사", "가족과 나들이", "헬스", "안과 진료")
        val roleList = listOf("엄마", "아빠", "아들", "딸")
        val random = Random()

        // 기존의 이벤트 생성 루프
        for (i in 0..30 step 3) {
            val eventCal = Calendar.getInstance()
            eventCal.add(Calendar.DATE, i * 3)

            // 랜덤 이벤트와 역할을 선택
            val selectedEvent = eventList[random.nextInt(eventList.size)]
            val selectedRole = roleList[random.nextInt(roleList.size)]

            val eventDate: Int =
                (CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = eventCal.time,
                    format = CalendarUtils.DB_DATE_FORMAT
                ) ?: "0").toInt()

            val simpleEvent = SimpleEvent(
                date = eventCal.time,
                role = selectedRole,
                title = selectedEvent,
                color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
            )

            // 이벤트를 EventRepository에 추가
            EventRepository.addEvent(simpleEvent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalendarBinding.inflate(layoutInflater)
        ContextCompat.getColor(requireContext(), R.color.colorAccent)
        val context = requireContext()

        // FloatingActionButton 설정
        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener { v: View? -> showScheduleActionBottomSheet() }

        val calendarRecyclerView: RecyclerView = binding.calendarRecyclerView
        val textView1 = binding.textView
        val textView2 = binding.textView2
        val textView3 = binding.textView3
        val textView4 = binding.textView4

        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 12)

        configuration.weekStartOffset = RecyclerCalendarConfiguration.START_DAY_OF_WEEK.MONDAY

        updateCalendar()

        val calendarAdapterVertical: VerticalRecyclerCalendarAdapter =
            VerticalRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = endCal.time,
                configuration = configuration,
                eventMap = eventMap,
                dateSelectListener = object : VerticalRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date, event: SimpleEvent?) {
                        val selectedDate: String =
                            CalendarUtils.dateStringFromFormat(
                                locale = configuration.calendarLocale,
                                date = date,
                                format = CalendarUtils.LONG_DATE_FORMAT
                            ) ?: ""

                        if (event != null) {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Event Clicked")
                                .setMessage(
                                    String.format(
                                        Locale.getDefault(),
                                        "Date: %s\n\nEvent: %s",
                                        selectedDate,
                                        event.title
                                    )
                                )
                                .create()
                                .show()
                        }
                    }
                }
            );


        calendarRecyclerView.adapter = calendarAdapterVertical

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun showScheduleActionBottomSheet() {
        val bottomSheet = ScheduleActionBottomSheetDialogFragment().apply {
            setEventChangeListener(this@CalendarFragment)
        }
        bottomSheet.show(parentFragmentManager, "ScheduleActionBottomSheet")
    }


    private fun updateCalendar() {
        val savedEvents = EventRepository.getEvents() // EventRepository에서 최신 이벤트를 가져옵니다.

        // 현재 년도와 달을 얻음
        val currentCalendar = Calendar.getInstance()
        val currentYear = currentCalendar.get(Calendar.YEAR)
        val currentMonth = currentCalendar.get(Calendar.MONTH)

        // 현재 달의 이벤트를 필터링하고 역할별로 분류
        val (momEvents, dadEvents, sonEvents, daughterEvents) = savedEvents
            .filter {
                val eventCalendar = Calendar.getInstance().apply { time = it.date }
                eventCalendar.get(Calendar.YEAR) == currentYear &&
                        eventCalendar.get(Calendar.MONTH) == currentMonth
            }
            .groupBy { it.role }
            .let { groups ->
                listOf(
                    groups["엄마"]?.joinToString("\n") { it.title }.orEmpty(),
                    groups["아빠"]?.joinToString("\n") { it.title }.orEmpty(),
                    groups["아들"]?.joinToString("\n") { it.title }.orEmpty(),
                    groups["딸"]?.joinToString("\n") { it.title }.orEmpty()
                )
            }

        // 각 TextView에 역할별 이벤트 표시
        binding.textView.text = momEvents
        binding.textView2.text = dadEvents
        binding.textView3.text = sonEvents
        binding.textView4.text = daughterEvents


        eventMap.clear()
        for (event in savedEvents) {
            val eventDate: Int = CalendarUtils.dateStringFromFormat(
                locale = configuration.calendarLocale,
                date = event.date,
                format = CalendarUtils.DB_DATE_FORMAT
            )?.toInt() ?: continue
            eventMap[eventDate] = event
        }

        // 달력 어댑터에 변경 알림
        binding.calendarRecyclerView.adapter?.notifyDataSetChanged()
    }
}