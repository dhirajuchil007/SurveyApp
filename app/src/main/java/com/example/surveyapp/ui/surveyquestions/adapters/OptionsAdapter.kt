package com.example.surveyapp.ui.surveyquestions.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.surveyapp.R
import com.example.surveyapp.data.model.Option
import com.example.surveyapp.databinding.MultiSelectionOptionItemBinding
import com.example.surveyapp.databinding.SingleSelectionOptionItemBinding

class OptionsAdapter(var options: MutableList<Option>?, var questionType: Int?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class SingleOptionViewHolder(var binding: SingleSelectionOptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(option: Option) {
            binding.radioOption.text = option.optionText

            if (option.isSelected) {
                binding.radioOption.setTextColor(binding.radioOption.resources.getColor(R.color.white))
                binding.constraintOption.setBackgroundColor(
                    binding.constraintOption.resources.getColor(
                        R.color.purple_200
                    )
                )
                binding.radioOption.buttonTintList = ColorStateList.valueOf(
                    binding.radioOption.resources.getColor(R.color.white)
                )
                binding.radioOption.isChecked = true
            } else {
                binding.radioOption.setTextColor(binding.radioOption.resources.getColor(R.color.black))
                binding.constraintOption.setBackgroundColor(
                    binding.constraintOption.resources.getColor(
                        R.color.white
                    )
                )
                binding.radioOption.buttonTintList = ColorStateList.valueOf(
                    binding.radioOption.resources.getColor(R.color.purple_200)
                )
                binding.radioOption.isChecked = false
            }
        }

    }

    class MultiOptionViewHolder(var binding: MultiSelectionOptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: Option) {
            binding.txtOptionText.text = option.optionText

            if (option.isSelected) {
                binding.constraintOption.setBackgroundColor(
                    binding.constraintOption.resources.getColor(
                        R.color.purple_200
                    )
                )
                binding.txtOptionText.setTextColor(binding.txtOptionText.resources.getColor(R.color.white))
            } else {
                binding.constraintOption.setBackgroundColor(
                    binding.constraintOption.resources.getColor(
                        R.color.white
                    )
                )
                binding.txtOptionText.setTextColor(binding.txtOptionText.resources.getColor(R.color.black))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            1 -> SingleOptionViewHolder(
                SingleSelectionOptionItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            3 -> MultiOptionViewHolder(
                MultiSelectionOptionItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> SingleOptionViewHolder(
                SingleSelectionOptionItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SingleOptionViewHolder) {
            val singleOptionViewHolder = holder as SingleOptionViewHolder
            singleOptionViewHolder.bind(options!![position])
            holder.itemView.setOnClickListener {
                deactivateAll()
                setActive(position)
            }
        } else {
            val multiOptionViewHolder = holder as MultiOptionViewHolder
            multiOptionViewHolder.bind(options!![position])
            holder.itemView.setOnClickListener {
                options!![position].isSelected = !options!![position].isSelected
                notifyItemChanged(position)
            }
        }
    }

    private fun setActive(position: Int) {
        options?.let {
            it[position].isSelected = true
            notifyItemChanged(position)
        }
    }

    private fun deactivateAll() {
        options?.forEachIndexed { index, option ->
            option.isSelected = false
            notifyItemChanged(index)
        }
    }

    override fun getItemCount(): Int {
        return options?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return questionType ?: 0
    }
}


