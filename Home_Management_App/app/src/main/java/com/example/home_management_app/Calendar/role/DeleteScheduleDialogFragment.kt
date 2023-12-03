package com.example.home_management_app.Calendar.role

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.databinding.FragmentDeleteScheduleBinding
import com.example.home_management_app.model.EventRepository
import com.example.home_management_app.model.RecyclerCalendarConfiguration
import com.example.home_management_app.model.SimpleEvent
import com.example.home_management_app.Calendar.CalendarUtils
import com.example.home_management_app.Calendar.vertical.VerticalRecyclerCalendarAdapter
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DeleteScheduleFragment : DialogFragment() {
    lateinit var binding: FragmentDeleteScheduleBinding
    private val eventMap: HashMap<Int, SimpleEvent> = HashMap()
    private val rdb = Firebase.database.getReference("Group").child("QJQ088")
        .child("groupManager")
        .child("Calendar")
    private val selectedEvents = mutableListOf<String>()
    private val configuration: RecyclerCalendarConfiguration =
        RecyclerCalendarConfiguration(
            calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
            calendarLocale = Locale.getDefault(),
            includeMonthHeader = true
        )
    // 불러온 이벤트를 저장할 리스트와 선택된 이벤트 ID를 저장할 리스트
    var eventChangeListener: com.example.home_management_app.model.OnEventChangeListener? = null
    private val selectedEventIds = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDeleteScheduleBinding.inflate(inflater, container, false)
        val calendarRecyclerView: RecyclerView = binding.delCalendarRecyclerView


        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 12)

        // EventRepository에서 이벤트 목록을 가져와 달력에 표시합니다.
        val savedEvents = EventRepository.getEvents()
        for (event in savedEvents) {
            val eventDate: Int =
                (CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = event.date,
                    format = CalendarUtils.DB_DATE_FORMAT
                ) ?: "0").toInt()

            eventMap[eventDate] = event


        }

        val calendarAdapterVertical: VerticalRecyclerCalendarAdapter =
            VerticalRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = endCal.time,
                configuration = configuration,
                eventMap = eventMap,
                dateSelectListener = object : VerticalRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date, event: SimpleEvent?) {
                        val selectedDateFormat = SimpleDateFormat(CalendarUtils.LONG_DATE_FORMAT, configuration.calendarLocale)
                        val selectedDateFormatted = selectedDateFormat.format(date)

                        val eventDateFormat = SimpleDateFormat(CalendarUtils.DB_DATE_FORMAT, configuration.calendarLocale)

                        val eventsOnDate = savedEvents.filter {
                            eventDateFormat.format(it.date) == eventDateFormat.format(date)
                        }

                        val eventsText = eventsOnDate.joinToString("\n") { "${it.role} - ${it.title}" }

                        binding.eventsTextView.text = if (eventsOnDate.isNotEmpty()) eventsText else "No events on this date"

                        selectedEvents.clear()
                        selectedEvents.addAll(eventsOnDate.map { savedEvents.indexOf(it).toString() })

                        binding.checkboxContainer.removeAllViews() // 체크박스 컨테이너를 비웁니다.
                        eventsOnDate.forEach { simpleEvent ->
                            val checkBox = CheckBox(context).apply {
                                text = "${simpleEvent.role} - ${simpleEvent.title}"
                                setOnCheckedChangeListener { _, isChecked ->
                                    val eventIndex = savedEvents.indexOf(simpleEvent)
                                    if (isChecked) {
                                        if (eventIndex !in selectedEventIds) {
                                            selectedEventIds.add(eventIndex)
                                        }
                                    } else {
                                        selectedEventIds.remove(eventIndex)
                                    }
                                }
                            }
                            binding.checkboxContainer.addView(checkBox)
                        }

                    }

                }
            );

        binding.buttonDeleteEvent.setOnClickListener {
            // 선택된 이벤트들을 삭제합니다.
            selectedEventIds.sortedDescending().forEach { index ->
                val eventToRemove = savedEvents[index]
                EventRepository.deleteEvent(eventToRemove)  // 이벤트를 삭제하는 함수 호출
            }

            // EventRepository의 데이터를 업데이트합니다.
            val updatedEvents = EventRepository.getEvents()
            eventMap.clear()
            for (event in updatedEvents) {
                val eventDate: Int = CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = event.date,
                    format = CalendarUtils.DB_DATE_FORMAT
                )?.toInt() ?: continue
                eventMap[eventDate] = event
            }

            // UI 및 달력 어댑터 업데이트
            Toast.makeText(context, "Selected events deleted", Toast.LENGTH_SHORT).show()
            selectedEventIds.clear()
            binding.eventsTextView.text = "No events selected"
            binding.checkboxContainer.removeAllViews()
            calendarAdapterVertical.notifyDataSetChanged()  // 어댑터에 데이터 변경 알림

            eventChangeListener?.onEventChanged()
        }

        calendarRecyclerView.adapter = calendarAdapterVertical
        return binding.root
    }
}


