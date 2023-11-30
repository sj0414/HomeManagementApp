package com.example.home_management_app.ForYouOld

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.databinding.FragmentForYouOldQuizRowBinding

class ForYouOldQuizAdapter(val quizList: ArrayList<ForYouOldQuizData>) : RecyclerView.Adapter<ForYouOldQuizAdapter.ViewHolder>() {
    interface OnAnswerChangedListener {
        fun onAnswerChanged(position: Int, answer: String)
    }

    var onAnswerChangedListener: OnAnswerChangedListener? = null

    inner class ViewHolder(val binding: FragmentForYouOldQuizRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.answer.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // 필요한 경우 로직 추가
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 필요한 경우 로직 추가
                }

                override fun afterTextChanged(s: Editable?) {
                    // EditText의 텍스트가 변경되었을 때 호출되는 메서드
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val answer = s.toString()
                        onAnswerChangedListener?.onAnswerChanged(position, answer)
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentForYouOldQuizRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = quizList[position]

        holder.binding.question.text = currentItem.quiz
        holder.binding.answer.setText(currentItem.answer)

        holder.binding.answer.tag = position
    }

    override fun getItemCount(): Int {
        return quizList.size
    }
}
