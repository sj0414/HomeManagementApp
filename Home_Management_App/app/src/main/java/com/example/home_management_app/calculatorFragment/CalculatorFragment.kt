package com.example.home_management_app.calculatorFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import android.app.AlertDialog
import android.widget.Toast
import android.widget.AdapterView
import com.example.home_management_app.databinding.*
import android.graphics.Color
import android.util.Log
import com.example.home_management_app.data.UserData
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class CalculatorFragment : Fragment() {
    lateinit var binding : FragmentCalculatorBinding
    // database 연결 필요
    val thingData: ArrayList<CalculatorThingData> = ArrayList()
    //private val calViewModel: CalculatorViewModel by viewModels()
    val accountBookData: ArrayList<CalculatorAccountBookData> = ArrayList()

    var budget: Int = 1000000
    var total: Int = 400000
    var left: Int = 600000
    var percent: Double = 40.0
    var leftpercent: Double = 60.0
    lateinit var type: String
    val accountBookMap = mutableMapOf(
        "식비" to 200000,
        "쇼핑" to 150000,
        "교육" to 100000,
        "의료" to 0,
        "공과금" to 0,
        "통신" to 0,
        "교통" to 50000,
        "기타" to 0
    )

    lateinit var adapter: CalculatorThingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)

        init()
        pie1()
        pie2()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChart()
    }

    private fun initChart() {
        // 차트 설정 코드
        setupPieChart()

        // 차트에 애니메이션 적용
        //binding.chart1.animateY(1500, Easing.EaseInOutQuad) // Y축을 기준으로 회전하는 애니메이션
        // 혹은
         binding.chart1.animateX(1500, Easing.EaseInOutQuad) // X축을 기준으로 회전하는 애니메이션
    }

    private fun setupPieChart() {
        // 차트 설정
        // 예: 데이터 설정, 스타일 지정 등
    }
    fun init() {
        // database 연결
        thingData.add(CalculatorThingData("물품", "작성자", false, View.INVISIBLE))
        thingData.add(CalculatorThingData("휴지", "아빠", false, View.VISIBLE))
        thingData.add(CalculatorThingData("치약", "엄마", false, View.VISIBLE))

        accountBookData.add(CalculatorAccountBookData(200000, "식비", "."))
        accountBookData.add(CalculatorAccountBookData(150000, "쇼핑", "."))
        accountBookData.add(CalculatorAccountBookData(100000, "교육", "."))
        accountBookData.add(CalculatorAccountBookData(50000, "교통", "."))

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        adapter = CalculatorThingAdapter(thingData)
        binding.recyclerView.adapter = adapter

        binding.calButton1.setOnClickListener {
            addItemDialog1()
        }
        binding.calButton2.setOnClickListener {
            addItemDialog2()
        }
        binding.calButton3.setOnClickListener {
            addItemDialog3()
        }

        binding.expenditure.text = total.toString()
        binding.budget.text = budget.toString()
        binding.use.text = percent.toString()
        binding.left.text = left.toString()

        val v1: Double = (accountBookMap["식비"]!!.toDouble() / total.toDouble()) * 100
        binding.use1.text = String.format("%.2f", v1)
        val v2: Double = (accountBookMap["쇼핑"]!!.toDouble() / total.toDouble()) * 100
        binding.use2.text = String.format("%.2f", v2)
        val v3: Double = (accountBookMap["교육"]!!.toDouble() / total.toDouble()) * 100
        binding.use3.text = String.format("%.2f", v3)
        val v4: Double = (accountBookMap["의료"]!!.toDouble() / total.toDouble()) * 100
        binding.use4.text = String.format("%.2f", v4)
        val v5: Double = (accountBookMap["공과금"]!!.toDouble() / total.toDouble()) * 100
        binding.use5.text = String.format("%.2f", v5)
        val v6: Double = (accountBookMap["통신"]!!.toDouble() / total.toDouble()) * 100
        binding.use6.text = String.format("%.2f", v6)
        val v7: Double = (accountBookMap["교통"]!!.toDouble() / total.toDouble()) * 100
        binding.use7.text = String.format("%.2f", v7)
        val v8: Double = (accountBookMap["기타"]!!.toDouble() / total.toDouble()) * 100
        binding.use8.text = String.format("%.2f", v8)
    }

    private fun addItemDialog1() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentCalculatorDialog1Binding.inflate(
            LayoutInflater.from(requireContext())
        )

        val thingText = dialogBinding.thingText
        val buttonAdd = dialogBinding.calDialogFin1

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        // 다이얼로그의 "추가" 버튼 클릭 시
        buttonAdd.setOnClickListener {
            val newThing = thingText.text.toString()
            arguments?.getSerializable("UserData").let { data ->
                val userData = data as UserData
                thingData.add(CalculatorThingData(newThing, userData.role, false, View.VISIBLE))
            }

            adapter.notifyDataSetChanged()

            dialog.dismiss()
        }

        dialog.show()
    }
    private fun addItemDialog2() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentCalculatorDialog2Binding.inflate(
            LayoutInflater.from(requireContext())
        )

        val budgetText = dialogBinding.budgetText
        val buttonAdd = dialogBinding.calDialogFin2

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        // 다이얼로그의 "추가" 버튼 클릭 시
        buttonAdd.setOnClickListener {
            val budgetString = budgetText.text.toString()

            var budget1: Int? = try {
                budgetString.toInt()
            } catch (e: NumberFormatException) {
                null // 변환 실패 시 null 반환
            }

            if (budget1 != null) {
                budget = budget1
                binding.budget.text = budgetString
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "유효한 숫자를 입력하세요.", Toast.LENGTH_SHORT).show()
            }

        }

        dialog.show()
    }

    inner class CustomOnItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            type = parent?.getItemAtPosition(position).toString()
        }
    }
    private fun addItemDialog3() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentCalculatorDialog3Binding.inflate(
            LayoutInflater.from(requireContext())
        )

        val expenditureText = dialogBinding.expenditureText1
        dialogBinding.spinner.onItemSelectedListener = CustomOnItemSelectedListener()
        //val typeRadioGroup = dialogBinding.typeRadioGroup
        val detailText = dialogBinding.expenditureText2
        val buttonAdd = dialogBinding.calDialogFin3

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        // 다이얼로그의 "추가" 버튼 클릭 시
        buttonAdd.setOnClickListener {
            val expenditureString = expenditureText.text.toString()
            //val typeId = typeRadioGroup.checkedRadioButtonId
            //val typeButton: RadioButton? = dialogBinding.root.findViewById(typeId)
            //val type = typeButton?.text?.toString()
            //val type = typeButton?.takeIf { it.isChecked }?.text?.toString()
            val detail = detailText.text.toString()

            val expenditure: Int? = try {
                expenditureString.toInt()
            } catch (e: NumberFormatException) {
                null // 변환 실패 시 null 반환
            }

            if (expenditure != null) {
                // database 연결 필요
                accountBookData.add(CalculatorAccountBookData(expenditure, type, detail))
                for ((category, value) in accountBookMap) {
                    if (category == type)
                        accountBookMap[category] = value + expenditure
                }

                total += expenditure
                binding.expenditure.text = total.toString()
                left = budget - total
                binding.left.text = left.toString()
                percent = (total.toDouble() / budget.toDouble()) * 100
                leftpercent = 100.0 - percent
                binding.use.text = String.format("%.2f", percent)

                pie1()
                //for ((category, value) in accountBookMap)
                //    Log.d("Map", "$category, $accountBookMap[$category]")

                val v1: Double = (accountBookMap["식비"]!!.toDouble() / total.toDouble()) * 100
                binding.use1.text = String.format("%.2f", v1)
                val v2: Double = (accountBookMap["쇼핑"]!!.toDouble() / total.toDouble()) * 100
                binding.use2.text = String.format("%.2f", v2)
                val v3: Double = (accountBookMap["교육"]!!.toDouble() / total.toDouble()) * 100
                binding.use3.text = String.format("%.2f", v3)
                val v4: Double = (accountBookMap["의료"]!!.toDouble() / total.toDouble()) * 100
                binding.use4.text = String.format("%.2f", v4)
                val v5: Double = (accountBookMap["공과금"]!!.toDouble() / total.toDouble()) * 100
                binding.use5.text = String.format("%.2f", v5)
                val v6: Double = (accountBookMap["통신"]!!.toDouble() / total.toDouble()) * 100
                binding.use6.text = String.format("%.2f", v6)
                val v7: Double = (accountBookMap["교통"]!!.toDouble() / total.toDouble()) * 100
                binding.use7.text = String.format("%.2f", v7)
                val v8: Double = (accountBookMap["기타"]!!.toDouble() / total.toDouble()) * 100
                binding.use8.text = String.format("%.2f", v8)

                pie2()

                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "유효한 값을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

        }

        dialog.show()
    }

    private fun pie1() {
        val pieChart: PieChart = binding.chart1
        val pieEntries = ArrayList<PieEntry>()

        pieEntries.add(PieEntry(percent.toFloat(), "사용"))
        pieEntries.add(PieEntry(leftpercent.toFloat(), "미사용"))

        val dataSet = PieDataSet(pieEntries, "Categories")
        dataSet.colors = mutableListOf(
            Color.rgb(159, 189, 213),
            Color.rgb(255, 255, 255),
        )
        dataSet.setDrawValues(false)

        val pieData = PieData(dataSet)

        pieChart.data = pieData

        pieChart.description.isEnabled = false
        pieChart.isRotationEnabled = false
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.setDrawEntryLabels(true)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setEntryLabelTextSize(5f)
        pieChart.setCenterText("")
        val legend: Legend = pieChart.legend
        legend.isEnabled = false

        pieChart.invalidate()
    }
    private fun pie2() {
        val pieChart: PieChart = binding.chart2
        val pieEntries = ArrayList<PieEntry>()

        val v1 = (accountBookMap["식비"]!!.toDouble() / total.toDouble()) * 100
        val v2 = (accountBookMap["쇼핑"]!!.toDouble() / total.toDouble()) * 100
        val v3 = (accountBookMap["교육"]!!.toDouble() / total.toDouble()) * 100
        val v4 = (accountBookMap["의료"]!!.toDouble() / total.toDouble()) * 100
        val v5 = (accountBookMap["공과금"]!!.toDouble() / total.toDouble()) * 100
        val v6 = (accountBookMap["통신"]!!.toDouble() / total.toDouble()) * 100
        val v7 = (accountBookMap["교통"]!!.toDouble() / total.toDouble()) * 100
        val v8 = (accountBookMap["기타"]!!.toDouble() / total.toDouble()) * 100

        pieEntries.add(PieEntry(v1.toFloat(), "식비"))
        pieEntries.add(PieEntry(v2.toFloat(), "쇼핑"))
        pieEntries.add(PieEntry(v3.toFloat(), "교육"))
        pieEntries.add(PieEntry(v4.toFloat(), "의료"))
        pieEntries.add(PieEntry(v5.toFloat(), "공과금"))
        pieEntries.add(PieEntry(v6.toFloat(), "통신"))
        pieEntries.add(PieEntry(v7.toFloat(), "교통"))
        pieEntries.add(PieEntry(v8.toFloat(), "기타"))

        val dataSet = PieDataSet(pieEntries, "Categories")
        dataSet.colors = mutableListOf(
            Color.rgb(255, 198, 179),
            Color.rgb(255, 236, 179),
            Color.rgb(255, 255, 179),
            Color.rgb(217, 255, 179),
            Color.rgb(179, 236, 255),
            Color.rgb(179, 198, 255),
            Color.rgb(198, 179, 255),
            Color.rgb(255, 179, 255),
        )
        dataSet.setDrawValues(false)

        val pieData = PieData(dataSet)

        pieChart.data = pieData

        pieChart.description.isEnabled = false
        pieChart.isRotationEnabled = false
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.setDrawEntryLabels(false)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setEntryLabelTextSize(5f)
        pieChart.setCenterText("")
        val legend: Legend = pieChart.legend
        legend.isEnabled = false

        pieChart.invalidate()
    }
}


