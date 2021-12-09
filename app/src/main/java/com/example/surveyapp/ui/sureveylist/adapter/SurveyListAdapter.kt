package com.example.surveyapp.ui.sureveylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.surveyapp.data.model.Survey
import com.example.surveyapp.databinding.SurveyListItemBinding

class SurveyListAdapter(var surveys: MutableList<Survey>, var callback: (id: String) -> Unit) :
    RecyclerView.Adapter<SurveyListAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: SurveyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(survey: Survey) {
            binding.txtSurveyTitle.text = survey.surveyName
            binding.txtNoQuestions.text = "${survey.questions?.size} questions"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SurveyListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(surveys[position])
        holder.binding.root.setOnClickListener {
            surveys[position].surveyId?.let {
                callback(it)
            }
        }

    }

    override fun getItemCount() = surveys.size
}