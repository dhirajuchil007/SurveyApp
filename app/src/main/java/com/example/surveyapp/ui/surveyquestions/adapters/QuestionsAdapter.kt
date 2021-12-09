package com.example.surveyapp.ui.surveyquestions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveyapp.data.model.Question
import com.example.surveyapp.databinding.QuizQuestionBinding

class QuestionsAdapter(
    var questions: MutableList<Question>,
    var context: Context,
    var onItemClick: OnItemClick
) :
    RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    interface OnItemClick {
        fun next(position: Int): Unit

        fun previous(position: Int): Unit

    }

    class QuestionViewHolder(var binding: QuizQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question, position: Int, totalQuestions: Int, context: Context) {
            binding.txtQuestion.text = question.questionText
            binding.txtQuestionNo.text = "Question ${position + 1}/${totalQuestions}"

            when (question.questionType) {
                1 -> {
                    setUpRecyclerView(question, context)
                }
                2 -> {
                    showTextField()
                }
                3 -> {
                    setUpRecyclerView(question, context)
                }
            }
        }

        private fun showTextField() {
            binding.textInputLayout.visibility = View.VISIBLE
            binding.recyclerOptions.visibility = View.GONE
        }

        private fun setUpRecyclerView(question: Question, context: Context) {
            binding.textInputLayout.visibility = View.GONE
            binding.recyclerOptions.visibility = View.VISIBLE
            binding.recyclerOptions.itemAnimator = null
            binding.recyclerOptions.layoutManager = LinearLayoutManager(context)
            val adapter = OptionsAdapter(question.options, question.questionType)
            binding.recyclerOptions.adapter = adapter
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            QuizQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position], position, questions.size, context)

        holder.binding.btnNext.setOnClickListener {
            onItemClick.next(position)
        }

        holder.binding.btnPrevious.setOnClickListener {
            onItemClick.previous(position)
        }

    }

    override fun getItemCount(): Int {
        return questions.size
    }
}