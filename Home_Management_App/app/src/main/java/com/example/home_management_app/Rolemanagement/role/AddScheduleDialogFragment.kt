package com.example.home_management_app.Rolemanagement.role

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.home_management_app.R
import com.example.home_management_app.Role_model.EventRepository
import com.example.home_management_app.Role_model.SimpleEvent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.home_management_app.Role_model.OnEventChangeListener
import java.util.Calendar


class AddScheduleDialogFragment : DialogFragment() {
    private lateinit var selectedDate: Calendar
    private var selectedRole: String = "" // 초기값 할당
    var eventChangeListener: OnEventChangeListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.role_fragment_add_schedule, null)

        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        val checkBoxImportant: CheckBox = view.findViewById(R.id.checkBoxImportant)
        val editTextScheduleContent: EditText = view.findViewById(R.id.editTextScheduleContent)

        val buttonFamilyMember1: Button = view.findViewById(R.id.btnFamilyMember1)
        val buttonFamilyMember2: Button = view.findViewById(R.id.btnFamilyMember2)
        val buttonFamilyMember3: Button = view.findViewById(R.id.btnFamilyMember3)
        val buttonFamilyMember4: Button = view.findViewById(R.id.btnFamilyMember4)

        val buttons = listOf(buttonFamilyMember1, buttonFamilyMember2, buttonFamilyMember3, buttonFamilyMember4)


        // 모든 버튼에 대해 리스너 설정
        buttons.forEach { button ->
            button.setOnClickListener {
                selectedRole = button.text.toString() // 버튼을 클릭하면 선택된 역할 업데이트
            }
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
        }

        builder.setView(view)
            .setPositiveButton("저장") { _, _ ->
                val role = if (selectedRole.isNotEmpty()) selectedRole else "기본 역할" // 선택된 역할이 없으면 기본값 사용
                val isImportant = checkBoxImportant.isChecked
                val scheduleContent = editTextScheduleContent.text.toString()

                // SimpleEvent 객체 생성 및 EventRepository에 추가
                val newEvent = SimpleEvent(selectedDate.time, role, scheduleContent, ContextCompat.getColor(requireContext(), R.color.colorAccent))
                EventRepository.addEvent(newEvent)

                eventChangeListener?.onEventChanged()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }
}
