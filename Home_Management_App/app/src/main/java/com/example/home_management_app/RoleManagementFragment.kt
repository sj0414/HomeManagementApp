package com.example.home_management_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.model.RecyclerCalendarConfiguration
import com.example.home_management_app.model.SimpleEvent
import com.example.home_management_app.utilities.CalendarUtils
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.example.home_management_app.databinding.FragmentRoleManagementBinding
import com.example.home_management_app.utilities.vertical.VerticalRecyclerCalendarAdapter
import java.util.*
import kotlin.collections.HashMap
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


data class id_data(
    var google_num: String? = null,
    var id: String? = null,
    var name: String? = null,
    var password: String? = null
)


class RoleManagementFragment : Fragment() {

    private val eventMap: HashMap<Int, SimpleEvent> = HashMap()
    // 받아온 데이터를 받을 리스트
    private val treeList = ArrayList<id_data>()
    lateinit var FDB : DatabaseReference

    lateinit var binding: FragmentRoleManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRoleManagementBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.activity_vertical_calendar, container, false)
        ContextCompat.getColor(requireContext(), R.color.colorAccent)
        val context = requireContext()

        //데이터베이스 초기화
        FDB = FirebaseDatabase.getInstance().reference

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

        val configuration: RecyclerCalendarConfiguration =
            RecyclerCalendarConfiguration(
                calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = true
            )

        configuration.weekStartOffset = RecyclerCalendarConfiguration.START_DAY_OF_WEEK.MONDAY

        // Some Random Events
        for (i in 0..30 step 3) {
            val eventCal = Calendar.getInstance()
            eventCal.add(Calendar.DATE, i * 3)
            val eventDate: Int =
                (CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = eventCal.time,
                    format = CalendarUtils.DB_DATE_FORMAT
                )
                    ?: "0").toInt()
            eventMap[eventDate] = SimpleEvent(
                eventCal.time,
                "Event #$i",
                ContextCompat.getColor(context, R.color.colorAccent)
            )
        }

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
                            )
                                ?: ""

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

        // 데이터 받아오기 : "gu_num"이 "중구"인 데이터 가져오기
        val mDatabase = FirebaseDatabase.getInstance().reference.child("Trees")
        val myTopPostsQuery = mDatabase.child("ID").equalTo("중구")

        myTopPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                treeList.clear()
                for (dataSnapshot in snapshot.children) {
                    val idData = dataSnapshot.getValue(id_data::class.java)
                    treeList.add(idData!!)
                }
                Log.w("MainActivity", "treeList = $treeList")

                // Update TextViews
                if (treeList.size > 0) textView1.text = treeList.getOrNull(0)?.name ?: "N/A"
                if (treeList.size > 1) textView2.text = treeList.getOrNull(1)?.name ?: "N/A"
                if (treeList.size > 2) textView3.text = treeList.getOrNull(2)?.name ?: "N/A"
                if (treeList.size > 3) textView4.text = treeList.getOrNull(3)?.name ?: "N/A"

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "onCancelled", error.toException())
            }
        })

        calendarRecyclerView.adapter = calendarAdapterVertical

        // Inflate the layout for this fragment
        return view
    }

}