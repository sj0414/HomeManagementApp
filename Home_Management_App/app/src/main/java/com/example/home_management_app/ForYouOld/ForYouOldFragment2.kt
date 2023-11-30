package com.example.home_management_app.ForYouOld

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.home_management_app.databinding.*

class ForYouOldFragment2 : Fragment() {
    lateinit var binding : FragmentForYouOld2Binding

    lateinit var adapter: ForYouOldQuizAdapter

    var score:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouOld2Binding.inflate(inflater, container, false)
        init()

        return binding.root
    }

    fun init() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        adapter = ForYouOldQuizAdapter(ArrayList<ForYouOldQuizData>())
        adapter.quizList.add(ForYouOldQuizData("1. ㅊㅅ", "", "참새")) //참새
        adapter.quizList.add(ForYouOldQuizData("2. ㅋㄲㄹ", "", "코끼리")) //코끼리
        adapter.quizList.add(ForYouOldQuizData("3. ㅇ룩ㅁ", "", "얼룩말")) //얼룩말
        adapter.quizList.add(ForYouOldQuizData("4. ㄷㄹ뇽", "", "도롱뇽")) //도롱뇽
        adapter.quizList.add(ForYouOldQuizData("5. ㅎㅁ", "", "하마")) //하마
        adapter.quizList.add(ForYouOldQuizData("6. ㅊㅅㅁ", "", "청설모")) //청설모
        adapter.quizList.add(ForYouOldQuizData("7. ㄲ마ㄱ", "", "까마귀")) //까마귀
        adapter.quizList.add(ForYouOldQuizData("8. ㅁㄷ지", "", "멧돼지")) //멧돼지
        adapter.quizList.add(ForYouOldQuizData("9. ㄷ람ㅈ", "", "다람쥐")) //다람쥐
        adapter.quizList.add(ForYouOldQuizData("10. ㄷㅁㅂ", "", "도마뱀")) //도마뱀

        adapter.onAnswerChangedListener = object : ForYouOldQuizAdapter.OnAnswerChangedListener {
            override fun onAnswerChanged(position: Int, answer: String) {
                // 리스트에 답변 업데이트
                adapter.quizList[position].answer = answer
            }
        }

        adapter.notifyDataSetChanged()

        binding.recyclerView.adapter = adapter

        binding.submit.setOnClickListener{
            for (quizData in adapter.quizList) {
                if (quizData.a == quizData.answer) {
                    score += 10
                }
            }

            dialog()
            //Toast.makeText(requireContext(), "Answers reset.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentForYouOldQuizDialogBinding.inflate(
            LayoutInflater.from(requireContext())
        )

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        dialogBinding.score.text = score.toString()

        dialogBinding.end.setOnClickListener {

            // submit 버튼 클릭 시 answer 값을 초기화하고, score도 초기화
            for (quizData in adapter.quizList) {
                quizData.answer = ""
            }
            adapter.notifyDataSetChanged()
            score = 0

            dialog.dismiss()
        }

        dialog.show()
    }
}