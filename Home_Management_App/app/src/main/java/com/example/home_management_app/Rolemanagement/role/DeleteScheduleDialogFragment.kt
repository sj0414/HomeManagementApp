package com.example.home_management_app.Rolemanagement.role

import android.app.Dialog
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.home_management_app.databinding.FragmentDeleteScheduleBinding
import com.example.home_management_app.Role_model.EventRepository
import com.example.home_management_app.Role_model.RecyclerCalendarConfiguration
import com.example.home_management_app.Role_model.SimpleEvent
import com.example.home_management_app.Rolemanagement.CalendarUtils
import com.example.home_management_app.Rolemanagement.vertical.VerticalRecyclerCalendarAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DeleteScheduleFragment : DialogFragment() {
    private lateinit var binding: FragmentDeleteScheduleBinding
    private val eventMap: HashMap<Int, SimpleEvent> = HashMap()
    var eventChangeListener: com.example.home_management_app.Role_model.OnEventChangeListener? = null
    private val selectedEventIds = mutableListOf<Int>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDeleteScheduleBinding.inflate(layoutInflater)
        val builder = MaterialAlertDialogBuilder(requireContext())
        setupCalendarAdapter()

        builder.setView(binding.root)
            .setPositiveButton("Delete") { _, _ ->
                deleteSelectedEvents()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }

    private fun setupCalendarAdapter() {
        val calendarRecyclerView = binding.delCalendarRecyclerView
        val configuration: RecyclerCalendarConfiguration = RecyclerCalendarConfiguration(
            calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
            calendarLocale = Locale.getDefault(),
            includeMonthHeader = true
        )

        val savedEvents = EventRepository.getEvents()
        savedEvents.forEach { event ->
            val eventDate: Int = CalendarUtils.dateStringFromFormat(
                locale = configuration.calendarLocale,
                date = event.date,
                format = CalendarUtils.DB_DATE_FORMAT
            )?.toInt() ?: 0
            eventMap[eventDate] = event
        }

        val calendarAdapterVertical: VerticalRecyclerCalendarAdapter =
            VerticalRecyclerCalendarAdapter(
                startDate = Calendar.getInstance().time,
                endDate = Calendar.getInstance().apply { add(Calendar.MONTH, 12) }.time,
                configuration = configuration,
                eventMap = eventMap,
                dateSelectListener = object : VerticalRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date, event: SimpleEvent?) {
                        displayEventsOnSelectedDate(date, savedEvents)
                    }
                }
            )

        calendarRecyclerView.adapter = calendarAdapterVertical
    }


    private fun displayEventsOnSelectedDate(date: Date, savedEvents: List<SimpleEvent>) {
        val eventDateFormat = SimpleDateFormat(CalendarUtils.DB_DATE_FORMAT, Locale.getDefault())
        val eventsOnDate = savedEvents.filter {
            eventDateFormat.format(it.date) == eventDateFormat.format(date)
        }

        binding.checkboxContainer.removeAllViews()
        eventsOnDate.forEach { event ->
            val checkBox = CheckBox(context).apply {
                text = "${event.role} - ${event.title}"
                tag = savedEvents.indexOf(event) // Use the index as a tag for identification
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedEventIds.add(tag as Int)
                    } else {
                        selectedEventIds.remove(tag as Int)
                    }
                }
            }
            binding.checkboxContainer.addView(checkBox)
        }
    }

    private fun deleteSelectedEvents() {
        val eventsToDelete = selectedEventIds.map { index -> EventRepository.getEvents()[index] }
        eventsToDelete.forEach { event -> EventRepository.deleteEvent(event) }

        // Clear the selection and update UI
        selectedEventIds.clear()
        binding.checkboxContainer.removeAllViews()
        Toast.makeText(context, "Selected events deleted", Toast.LENGTH_SHORT).show()
        eventChangeListener?.onEventChanged()
    }

}


