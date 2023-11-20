package com.example.home_management_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import android.app.AlertDialog
import android.widget.RadioButton
import android.widget.Toast
import com.example.home_management_app.databinding.*

class CalculatorFragment : Fragment() {
    lateinit var binding : FragmentCalculatorBinding
    // database 연결 필요
    val thingData: ArrayList<CalculatorThingData> = ArrayList()
    val accountBookData: ArrayList<CalculatorAccountBookData> = ArrayList()

    var budget: Int = 0
    var total: Int = 0
    var left: Int = 0
    var percent: Double = 0.0
    val accountBookMap = mutableMapOf(
        "식비" to 0,
        "쇼핑" to 0,
        "교육" to 0,
        "의료" to 0,
        "공과금" to 0,
        "통신" to 0,
        "교통" to 0,
        "기타" to 0
    )

    lateinit var adapter: CalculatorThingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    fun init() {
        thingData.add(CalculatorThingData("물품", "작성자", false, View.INVISIBLE))
        thingData.add(CalculatorThingData("휴지", "아빠", false, View.VISIBLE))
        thingData.add(CalculatorThingData("치약", "엄마", false, View.VISIBLE))

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
            // database 연결 필요
            thingData.add(CalculatorThingData(newThing, "사용자", false, View.VISIBLE))

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
    private fun addItemDialog3() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentCalculatorDialog3Binding.inflate(
            LayoutInflater.from(requireContext())
        )

        val expenditureText = dialogBinding.expenditureText1
        val typeRadioGroup = dialogBinding.typeRadioGroup
        val detailText = dialogBinding.expenditureText2
        val buttonAdd = dialogBinding.calDialogFin3

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        // 다이얼로그의 "추가" 버튼 클릭 시
        buttonAdd.setOnClickListener {
            val expenditureString = expenditureText.text.toString()
            val typeId = typeRadioGroup.checkedRadioButtonId
            val typeButton: RadioButton? = dialogBinding.root.findViewById(typeId)
            val type = typeButton?.text.toString()
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
                    if (category == type) {
                        accountBookMap[category] = value + expenditure
                    }
                }

                total += expenditure
                binding.expenditure.text = total.toString()
                left = budget - total
                binding.left.text = left.toString()
                percent = (total.toDouble() / budget.toDouble()) * 100
                binding.use.text = String.format("%.2f", percent)

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

                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "유효한 숫자를 입력하세요.", Toast.LENGTH_SHORT).show()
            }

        }

        dialog.show()
    }

}