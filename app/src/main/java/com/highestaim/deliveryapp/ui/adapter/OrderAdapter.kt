package com.highestaim.deliveryapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.highestaim.deliveryapp.R
import com.highestaim.deliveryapp.dto.Order
import com.highestaim.deliveryapp.util.Utils.isTimeOut
import com.highestaim.deliveryapp.util.showIf
import kotlinx.android.synthetic.main.order_item.view.address_1
import kotlinx.android.synthetic.main.order_item.view.address_2
import kotlinx.android.synthetic.main.order_item.view.deliveryTime
import kotlinx.android.synthetic.main.order_item.view.estimatedTime
import kotlinx.android.synthetic.main.order_item.view.orderId
import kotlinx.android.synthetic.main.order_item_selected.view.*

class OrderAdapter : ListAdapter<Order, OrderAdapter.MyViewHolder>(
    OrderDiffCallBacks()
) {

    private val VIEW_TYPE_ITEM = R.layout.order_item
    private val VIEW_TYPE_SELECTED = R.layout.order_item_selected
    private val VIEW_TYPE_FINISHED = R.layout.order_item_finished

    private var selectedItemPosition: Int = -1

    var onNavigateClick: ((Order) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            VIEW_TYPE_SELECTED -> return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(VIEW_TYPE_SELECTED, parent, false)
            )
            VIEW_TYPE_FINISHED -> return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(VIEW_TYPE_FINISHED, parent, false)
            )
            else -> MyViewHolder(
                LayoutInflater.from(parent.context).inflate(VIEW_TYPE_ITEM, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            getItem(position).isSelected && selectedItemPosition != -1 -> VIEW_TYPE_SELECTED
            getItem(position).isFinished -> VIEW_TYPE_FINISHED
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initData(getItem(position), position)

        if (holder.itemViewType != VIEW_TYPE_FINISHED) {
            holder.itemView.setOnClickListener {
                if (selectedItemPosition == position) {
                    getItem(position).isSelected = !getItem(position).isSelected
                    notifyItemChanged(position)
                } else {
                    if (selectedItemPosition != -1) {

                        getItem(selectedItemPosition).isSelected = false
                        getItem(position).isSelected = true

                        notifyItemChanged(selectedItemPosition)
                        notifyItemChanged(position)
                    } else {
                        getItem(position).isSelected = true
                        notifyItemChanged(position)
                    }
                }
                selectedItemPosition = position
            }
        }


        holder.itemView.finishImageView?.setOnClickListener {
            getItem(position).isFinished = true
            getItem(position).isSelected = false
            notifyItemChanged(position)
        }

        holder.itemView.navigationImageView?.setOnClickListener {
            onNavigateClick?.invoke(getItem(position))
        }
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun initData(order: Order?, position: Int) {
            val count = position.plus(1)
            itemView.count?.text = count.toString()

            itemView.estimatedTime?.text = order?.estimatedTime
            itemView.deliveryTime?.text = "${order?.startTime}-${order?.endTime}"

            itemView.address_1?.text = order?.address?.address
            itemView.address_2?.text = order?.address?.city
            itemView.orderId?.text = order?.id.toString()

            handleTimeOut(order)

        }

        private fun handleTimeOut(order: Order?){

            val isTimeOut = isTimeOut(deliveryStartTime = order?.startTime, deliveryEndTime = order?.endTime, currentTime = order?.estimatedTime)

            if (isTimeOut) {
                itemView.estimatedTime?.setTextColor(ContextCompat.getColor(itemView.context, R.color.est_time_error))
            } else {
                itemView.estimatedTime?.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
            }
            itemView.timeOut?.showIf(isTimeOut)
        }

    }
}

private class OrderDiffCallBacks : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return newItem == oldItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return newItem == oldItem
    }
}

