package com.example.home_management_app.utilities.role

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DeleteScheduleDialogFragment : DialogFragment() {

    // ScheduleData 클래스 정의
    data class ScheduleData(
        var role: String? = null,
        var specialty: Int? = null,
        var todo: String? = null
    )
    private fun fetchCalendarData() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Group/QJQ088/groupManager/Calendar")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val snapshot = databaseRef.get().await()
                if (snapshot.exists()) {
                    snapshot.children.forEach { yearSnapshot ->
                        val year = yearSnapshot.key ?: return@forEach

                        yearSnapshot.children.forEach { monthSnapshot ->
                            val month = monthSnapshot.key ?: return@forEach

                            monthSnapshot.children.forEach { daySnapshot ->
                                val day = daySnapshot.key ?: return@forEach

                                daySnapshot.children.forEach { userSnapshot ->
                                    val userId = userSnapshot.key ?: return@forEach
                                    val scheduleData = userSnapshot.getValue(ScheduleData::class.java)

                                    if (scheduleData != null) {
                                        Log.d("FirebaseData", "Date: $year-$month-$day, User: $userId, Schedule: $scheduleData")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Log.d("FirebaseData", "No data available.")
                }
            } catch (e: Exception) {
                Log.e("FirebaseData", "Error fetching data", e)
            }
        }
    }

    fun newInstance(scheduleId: String?): DeleteScheduleDialogFragment? {
        val fragment = DeleteScheduleDialogFragment()
        val args = Bundle()
        args.putString("scheduleId", scheduleId) // 인자로 일정 ID를 추가합니다.
        fragment.arguments = args // 프래그먼트에 인자 설정
        return fragment // 프래그먼트 인스턴스 반환
    }

    // 이 DialogFragment를 생성할 때 삭제할 멤버의 ID를 전달받는 것으로 가정합니다.
    companion object {
        fun newInstance(memberId: String): DeleteScheduleDialogFragment {
            val args = Bundle()
            args.putString("memberId", memberId)
            val fragment = DeleteScheduleDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val database = FirebaseDatabase.getInstance().getReference("groupManager/Calendar")
        val memberId = arguments?.getString("memberId")
            ?: throw IllegalStateException("Member ID is required")

        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setMessage("선택한 멤버의 일정을 모두 삭제하시겠습니까?")
            .setPositiveButton("삭제") { dialog, id ->
                // 멤버 ID를 사용하여 해당 멤버의 일정을 삭제
                memberId.let { mId ->
                    database.child(mId).removeValue().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // 일정 삭제 성공 처리
                            Toast.makeText(requireContext(), "일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            // 일정 삭제 실패 처리
                            Log.e("DeleteSchedule", "Failed to delete schedule: ${task.exception}")
                            Toast.makeText(requireContext(), "일정 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("취소") { dialog, id ->
                // 사용자가 취소한 경우
                dialog.cancel()
            }

        // AlertDialog 생성 및 반환
        return builder.create()
    }

}
