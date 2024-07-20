package com.example.weather.extensions

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerAdapter<ITEM, HOLDER : RecyclerView.ViewHolder> : RecyclerView.Adapter<HOLDER>() {

    private val itemList: MutableList<ITEM> = mutableListOf()
    protected open val realFirstItemPosition = FIRST_ITEM_POSITION

    @SuppressLint("NotifyDataSetChanged")
    open fun set(items: List<ITEM>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    open fun replace(items: List<ITEM>) {
        itemList.clear()
        itemList.addAll(items)
        notifyItemRangeChanged(realFirstItemPosition, realFirstItemPosition + size())
    }

    fun add(item: ITEM, position: Int = itemCount - 1) {
        itemList.add(position, item)
        notifyItemInserted(position + realFirstItemPosition)
    }

    fun addAtFirst(item: ITEM) {
        add(item, FIRST_ITEM_POSITION)
    }

    fun addAll(items: List<ITEM>) {
        itemList.addAll(items)
        notifyItemRangeInserted(realFirstItemPosition + itemCount - items.size, items.size)
    }

    fun replace(item: ITEM, position: Int) {
        itemList[position] = item
        notifyItemChanged(position + realFirstItemPosition)
    }

    fun removeFirst(position: Int) {
        if (position > DEFAULT_ITEM_POSITION) {
            itemList.removeAt(position)
            notifyItemRemoved(position + realFirstItemPosition)
        }
    }

    fun removeFirst(predicate: (ITEM) -> Boolean) {
        val position = itemList.indexOfFirst(predicate)
        if (position > DEFAULT_ITEM_POSITION) {
            itemList.removeAt(position)
            notifyItemRemoved(position + realFirstItemPosition)
        }
    }

    fun item(position: Int): ITEM? = if (position < itemList.size) itemList[position] else null

    fun item(predicate: (ITEM) -> Boolean) = itemList.find(predicate)

    fun items(): List<ITEM> = itemList

    fun clear() {
        val size = size()
        itemList.clear()
        notifyItemRangeRemoved(realFirstItemPosition, size)
    }

    fun size() = itemList.size

    fun isEmpty() = items().isEmpty()

    override fun getItemCount() = size()

    companion object {
        const val FIRST_ITEM_POSITION = 0
        const val DEFAULT_ITEM_POSITION = -1
    }
}
