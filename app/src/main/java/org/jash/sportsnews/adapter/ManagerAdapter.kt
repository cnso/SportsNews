package org.jash.sportsnews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.jash.sportsnews.R
import org.jash.sportsnews.BR
import org.jash.sportsnews.databinding.CategoryItemBinding
import org.jash.mylibrary.model.Category

class ManagerAdapter(val data:MutableList<Any?> = mutableListOf()):Adapter<ManagerViewHolder>() {
    var index = data.indexOfLast { it is String }
    var editable:ObservableField<Boolean> = ObservableField(false)
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val manager = GridLayoutManager(recyclerView.context, 4)
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when(data[position]?.javaClass) {
                String::class.java -> 4
                Category::class.java -> 1
                else -> 1
            }
        }
        recyclerView.layoutManager = manager
    }
    override fun getItemViewType(position: Int): Int = when(data[position]?.javaClass) {
        String::class.java -> R.layout.group_item
        Category::class.java -> R.layout.category_item
        else -> R.layout.category_item
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerViewHolder  =
        ManagerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ManagerViewHolder, position: Int) {
        holder.binding.setVariable(when(data[position]?.javaClass) {
            String::class.java -> BR.name
            Category::class.java -> BR.category
            else -> BR.category
        }, data[position])
        if(data[position] is Category) {
            holder.binding.setVariable(BR.adapter, this)
            holder.itemView.setOnClickListener {
                if(editable.get() != true) {
                    return@setOnClickListener
                }
                val bind = DataBindingUtil.bind<CategoryItemBinding>(it)
                val p = data.indexOf(bind?.category)
                if(p < index) {
                    val temp: Category = data.removeAt(p) as Category
                    var insert =
                        data.indexOfFirst { data.indexOf(it) > index - 1 && it is Category && it.id > temp.id }
                    if (insert == -1) {
                        insert = data.size
                    }
                    data.add(insert, temp)
                    notifyItemMoved(p, insert)
                    index--
                } else {
                    data.add(index, data.removeAt(p))
                    notifyItemMoved(p, index)
                    index++
                }
            }
        }
    }
}
class ManagerViewHolder(val binding: ViewDataBinding):ViewHolder(binding.root)