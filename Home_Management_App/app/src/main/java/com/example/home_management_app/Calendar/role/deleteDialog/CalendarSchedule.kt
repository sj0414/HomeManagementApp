package com.example.home_management_app.Calendar.role.deleteDialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.R


// ScheduleData 클래스 정의
data class ScheduleData(
    var role: String? = null,
    var specialty: Int? = null,
    var todo: String? = null
)

// 일정 표시용 데이터 클래스
data class CalendarSchedule(
    val date: String,
    val schedules: List<ScheduleData>
)

// RecyclerView의 뷰 홀더
class ScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateTextView: TextView = view.findViewById(R.id.dateTextView)
    private val scheduleTextView: TextView = view.findViewById(R.id.scheduleTextView)

    fun bind(calendarSchedule: CalendarSchedule) {
        dateTextView.text = calendarSchedule.date
        scheduleTextView.text = calendarSchedule.schedules.joinToString(separator = "\n") {
            "${it.todo} (${it.role})"
        }
    }
}

// RecyclerView 어댑터
class ScheduleAdapter(private val schedules: List<CalendarSchedule>) : RecyclerView.Adapter<ScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedules[position])
    }

    override fun getItemCount() = schedules.size
}
