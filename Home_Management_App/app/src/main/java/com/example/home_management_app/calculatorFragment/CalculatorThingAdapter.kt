package com.example.home_management_app.calculatorFragment;

import android.view.LayoutInflater
import java.util.ArrayList;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.home_management_app.databinding.FragmentCalculatorThingRowBinding;

class CalculatorThingAdapter(val thingList: ArrayList<CalculatorThingData>) : RecyclerView.Adapter<CalculatorThingAdapter.ViewHolder>(){
    interface OnItemClickListener {
        fun OnItemClick(data: CalculatorThingData, pos:Int, binding: FragmentCalculatorThingRowBinding);
    }

    var itemClickListener: OnItemClickListener?=null

    inner class ViewHolder(val binding: FragmentCalculatorThingRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.thingCheckbox.setOnClickListener {
                itemClickListener?.OnItemClick(thingList[adapterPosition], adapterPosition, binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentCalculatorThingRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = thingList[position]

        // 뷰 홀더에 데이터를 바인딩
        holder.binding.thingCheckbox.isChecked = currentItem.check
        holder.binding.thingCheckbox.visibility = currentItem.visibility

        // 체크박스 클릭 리스너 설정
        holder.binding.thingCheckbox.setOnClickListener {
            // 클릭 시 isChecked 값을 반전시키고, 리스너 호출
            currentItem.check = !currentItem.check
            itemClickListener?.OnItemClick(currentItem, position, holder.binding)
        }

        holder.binding.thing.text = currentItem.thing
        holder.binding.writer.text = currentItem.writer
    }

    override fun getItemCount(): Int {
        return thingList.size
    }
}
