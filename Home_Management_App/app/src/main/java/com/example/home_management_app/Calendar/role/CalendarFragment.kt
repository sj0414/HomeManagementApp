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
import com.example.home_management_app.Role_model.EventRepository
import com.example.home_management_app.Role_model.OnEventChangeListener
import com.example.home_management_app.Role_model.RecyclerCalendarConfiguration
import com.example.home_management_app.Role_model.SimpleEvent
import com.example.home_management_app.Calendar.CalendarUtils
import com.example.home_management_app.Calendar.ScheduleActionBottomSheetDialogFragment
import com.example.home_management_app.Calendar.vertical.VerticalRecyclerCalendarAdapter
import com.example.home_management_app.databinding.FragmentCalendarBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random

class CalendarFragment : Fragment(), OnEventChangeListener {
    private val eventMap: HashMap<Int, List<SimpleEvent>> = HashMap()
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

        // 역할 별 색상 매핑
        val roleColorMap = hashMapOf(
            "엄마" to R.color.RoleCategorization_1,
            "아빠" to R.color.RoleCategorization_2,
            "아들" to R.color.RoleCategorization_3,
            "딸" to R.color.RoleCategorization_4
        )

        // 기존의 이벤트 생성 루프
        for (i in 0..30 step 3) {
            val eventCal = Calendar.getInstance()
            eventCal.add(Calendar.DATE, i*4)

            // 랜덤 이벤트와 역할을 선택
            val selectedEvent = eventList[random.nextInt(eventList.size)]
            val selectedRole = roleList[random.nextInt(roleList.size)]
            val isImportant = random.nextBoolean()

            val eventColor = ContextCompat.getColor(requireContext(), roleColorMap[selectedRole] ?: R.color.RoleCategorization_5)
            val simpleEvent = SimpleEvent(
                date = eventCal.time,
                role = selectedRole,
                title = selectedEvent,
                color = eventColor,
                isImportant = isImportant
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

        // FloatingActionButton 설정
        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener { v: View? -> showScheduleActionBottomSheet() }

        val calendarRecyclerView: RecyclerView = binding.calendarRecyclerView

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
                    override fun onDateSelected(date: Date, event: List<SimpleEvent>?) {
                        val selectedDate: String = CalendarUtils.dateStringFromFormat(
                            locale = configuration.calendarLocale,
                            date = date,
                            format = CalendarUtils.LONG_DATE_FORMAT
                        ) ?: ""

                        // 선택된 날짜에 있는 모든 이벤트를 찾습니다.
                        val eventsOnSelectedDate = EventRepository.getEvents().filter {
                            CalendarUtils.isSameDay(it.date, date)
                        }

                        // 이벤트들의 제목을 리스트로 만듭니다.
                        val eventsDescriptions = eventsOnSelectedDate.joinToString("\n") {
                            "${it.title} - ${it.role}"
                        }

                        // 이벤트가 하나라도 있으면 AlertDialog를 표시합니다.
                        if (eventsOnSelectedDate.isNotEmpty()) {
                            AlertDialog.Builder(requireContext())
                                .setTitle("이벤트 목록")
                                .setMessage("날짜: $selectedDate\n\n$eventsDescriptions")
                                .setPositiveButton("확인", null)
                                .show()
                        } else {
                            // 이벤트가 없는 경우 사용자에게 알립니다.
                            AlertDialog.Builder(requireContext())
                                .setTitle("이벤트 없음")
                                .setMessage("선택한 날짜에 이벤트가 없습니다.")
                                .setPositiveButton("확인", null)
                                .show()
                        }
                    }
                }
            )
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

        // 현재 날짜
        val currentDate = Calendar.getInstance()

        // 현재 년도와 달을 얻음
        val currentYear = currentDate.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH)

        // 현재 달의 이벤트를 필터링하고 역할별로 분류
        val filteredEvents = savedEvents.filter {
            val eventCalendar = Calendar.getInstance().apply { time = it.date }
            eventCalendar.get(Calendar.YEAR) == currentYear && eventCalendar.get(Calendar.MONTH) == currentMonth
        }
        val eventsByRole = filteredEvents.groupBy { it.role }

        val importantEvents = savedEvents
            .filter { it.isImportant && it.date.after(currentDate.time) } // 현재 시간 이후의 이벤트만 고려
            .sortedBy { it.date } // 날짜에 따라 오름차순으로 정렬
            .take(3) // 상위 3개의 이벤트 선택

        // 각 TextView에 역할별 이벤트 표시
        binding.textView.text = eventsByRole["엄마"]?.joinToString("\n") { it.title }.orEmpty()
        binding.textView2.text = eventsByRole["아빠"]?.joinToString("\n") { it.title }.orEmpty()
        binding.textView3.text = eventsByRole["아들"]?.joinToString("\n") { it.title }.orEmpty()
        binding.textView4.text = eventsByRole["딸"]?.joinToString("\n") { it.title }.orEmpty()

        // 중요한 이벤트를 textView7에 표시
        binding.textView7.text = importantEvents.joinToString("\n") { "${it.title} - ${it.role}" }

        // eventMap 업데이트
        eventMap.clear()
        savedEvents.forEach { event ->
            val eventDate: Int = CalendarUtils.dateStringFromFormat(
                locale = configuration.calendarLocale,
                date = event.date,
                format = CalendarUtils.DB_DATE_FORMAT
            )?.toInt() ?: return@forEach

            val eventsOnDate = eventMap.getOrDefault(eventDate, mutableListOf()).toMutableList()
            eventsOnDate.add(event)
            eventMap[eventDate] = eventsOnDate
        }

        // 달력 어댑터에 변경 알림
        binding.calendarRecyclerView.adapter?.notifyDataSetChanged()
    }



}