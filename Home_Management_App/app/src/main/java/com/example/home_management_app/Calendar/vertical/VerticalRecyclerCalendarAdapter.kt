package com.example.home_management_app.Calendar.vertical

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.R
import com.example.home_management_app.Role_model.RecyclerCalendarConfiguration
import com.example.home_management_app.Role_model.RecyclerCalenderViewItem
import com.example.home_management_app.Role_model.SimpleEvent
import com.example.home_management_app.Calendar.CalendarUtils
import com.example.home_management_app.Calendar.vertical.adapter.RecyclerCalendarBaseAdapter
import java.util.*
import kotlin.collections.HashMap

class VerticalRecyclerCalendarAdapter(
    startDate: Date,
    endDate: Date,
    val configuration: RecyclerCalendarConfiguration,
    val eventMap: HashMap<Int, List<SimpleEvent>>,
    val dateSelectListener: OnDateSelected
) : RecyclerCalendarBaseAdapter(startDate, endDate, configuration) {

    interface OnDateSelected {
        fun onDateSelected(date: Date, event: List<SimpleEvent>?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_vertical, parent, false)
        return MonthCalendarViewHolder(
            view
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        calendarItem: RecyclerCalenderViewItem
    ) {
        val monthViewHolder: MonthCalendarViewHolder = holder as MonthCalendarViewHolder
        monthViewHolder.itemView.visibility = View.VISIBLE
        monthViewHolder.layoutEventContainer.visibility = View.GONE

        monthViewHolder.itemView.setOnClickListener(null)

        if (calendarItem.isHeader) {
            val selectedCalendar = Calendar.getInstance(Locale.getDefault())
            selectedCalendar.time = calendarItem.date

            val month: String = CalendarUtils.dateStringFromFormat(
                locale = configuration.calendarLocale,
                date = selectedCalendar.time,
                format = CalendarUtils.DISPLAY_MONTH_FORMAT
            ) ?: ""
            val year = selectedCalendar[Calendar.YEAR].toLong()

            monthViewHolder.textViewDay.text = year.toString()
            monthViewHolder.textViewDate.text = month
        } else if (calendarItem.isEmpty) {
            monthViewHolder.itemView.visibility = View.GONE
            monthViewHolder.textViewDay.text = ""
            monthViewHolder.textViewDate.text = ""
        } else {
            val calendarDate = Calendar.getInstance(Locale.getDefault())
            calendarDate.time = calendarItem.date

            val day: String = CalendarUtils.dateStringFromFormat(
                locale = configuration.calendarLocale,
                date = calendarDate.time,
                format = CalendarUtils.DISPLAY_WEEK_DAY_FORMAT
            ) ?: ""

            monthViewHolder.textViewDay.text = day

            val dateInt: Int =
                (CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarDate.time,
                    format = CalendarUtils.DB_DATE_FORMAT
                ) ?: "0").toInt()

            if (!calendarItem.isHeader && !calendarItem.isEmpty) {
                val calendarDate = Calendar.getInstance(Locale.getDefault())
                calendarDate.time = calendarItem.date
                val dateInt: Int = CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarDate.time,
                    format = CalendarUtils.DB_DATE_FORMAT
                )?.toInt() ?: "0".toInt()

                val events = eventMap[dateInt]
                val uniqueRoles = events?.map { it.role }?.toSet() ?: emptySet()
                monthViewHolder.layoutEventContainer.visibility = if (uniqueRoles.isEmpty()) View.GONE else View.VISIBLE
                (monthViewHolder.layoutEventContainer as ViewGroup).removeAllViews()

                uniqueRoles.forEach { role ->
                    val firstEventWithRole = events?.firstOrNull { it.role == role }
                    firstEventWithRole?.let { event ->
                        val eventView = View(holder.itemView.context).apply {
                            layoutParams = LinearLayout.LayoutParams(10.dpToPx(holder.itemView.context), 10.dpToPx(holder.itemView.context))
                            val eventColor = if (event.color.isColorResource()) {
                                ContextCompat.getColor(holder.itemView.context, event.color)
                            } else {
                                event.color
                            }
                            setBackgroundColor(eventColor)
                        }
                        monthViewHolder.layoutEventContainer.addView(eventView)
                    }
                }
            }

            monthViewHolder.textViewDate.text =
                CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarDate.time,
                    format = CalendarUtils.DISPLAY_DATE_FORMAT
                ) ?: ""

            monthViewHolder.itemView.setOnClickListener {
                dateSelectListener.onDateSelected(calendarItem.date, eventMap[dateInt])
            }
        }
    }

    // 색상 리소스 ID 여부를 확인하는 확장 함수
    fun Int.isColorResource(): Boolean {
        return this and 0xFF000000.toInt() == 0
    }

    fun Int.dpToPx(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
        ).toInt()
    }

    class MonthCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDay: TextView = itemView.findViewById(R.id.textCalenderItemVerticalDay)
        val textViewDate: TextView = itemView.findViewById(R.id.textCalenderItemVerticalDate)
        val layoutEventContainer: View = itemView.findViewById(R.id.layoutEventContainer)
    }
}