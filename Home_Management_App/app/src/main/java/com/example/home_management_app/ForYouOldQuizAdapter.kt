package com.example.home_management_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.databinding.FragmentForYouOldNewsRowBinding
import com.example.home_management_app.databinding.FragmentForYouOldQuizRowBinding

class ForYouOldQuizAdapter(val quizList: ArrayList<ForYouOldQuizData>) : RecyclerView.Adapter<ForYouOldQuizAdapter.ViewHolder>() {
    interface OnAnswerChangedListener {
        fun onAnswerChanged(position: Int, answer: String)
    }

    var onAnswerChangedListener: OnAnswerChangedListener? = null

    inner class ViewHolder(val binding: FragmentForYouOldQuizRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // ViewHolder가 생성될 때 이벤트 리스너 설정
            binding.answer.setOnKeyListener { _, _, _ ->
                // 사용자가 EditText에 입력할 때마다 호출
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val answer = binding.answer.text.toString()
                    onAnswerChangedListener?.onAnswerChanged(position, answer)
                }
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentForYouOldQuizRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForYouOldQuizAdapter.ViewHolder, position: Int) {
        val currentItem = quizList[position]

        holder.binding.question.text = currentItem.quiz
        holder.binding.answer.setText(currentItem.answer)

        holder.binding.answer.tag = position
    }

    override fun getItemCount(): Int {
        return quizList.size
    }
}
