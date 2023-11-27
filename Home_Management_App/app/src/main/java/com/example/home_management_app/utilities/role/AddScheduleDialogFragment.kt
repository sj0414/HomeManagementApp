package com.example.home_management_app.utilities.role

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.home_management_app.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class AddScheduleDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the Builder class for convenient dialog construction
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.role_fragment_add_schedule, null)

        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        val checkBoxImportant: CheckBox = view.findViewById(R.id.checkBoxImportant)
        val editTextScheduleContent: EditText = view.findViewById(R.id.editTextScheduleContent)

        // ScheduleData 클래스 정의 (예시)
        data class ScheduleData(
            var role: String? = null,
            var specialty: Int? = null,
            var todo: String? = null
        )


        builder.setView(view)
            // Add action buttons
            .setPositiveButton("저장") { dialog, id ->
                // User clicked OK button
                val selectedDate = calendarView.date
                val isImportant = checkBoxImportant.isChecked
                val scheduleContent = editTextScheduleContent.text.toString()

                // TODO: Handle the schedule saving logic here
            }
            .setNegativeButton("취소") { dialog, id ->
                // User cancelled the dialog
                dialog.cancel()
            }

        // Create the AlertDialog object and return it
        return builder.create()
    }
}
