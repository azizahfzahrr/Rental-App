package com.example.cakratech.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cakratech.R
import com.example.cakratech.databinding.ItemBookingListBinding
import com.example.core.data.room.entity.BookingEntity

class BookingListAdapter: RecyclerView.Adapter<BookingListAdapter.ViewHolder>() {

    private var deleteListener: ((BookingEntity) -> Unit)? = null
    private var updateListener: ((BookingEntity) -> Unit)? = null
    private val data: MutableList<BookingEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingListAdapter.ViewHolder {
        return ViewHolder(
            ItemBookingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: BookingListAdapter.ViewHolder, position: Int) {
        holder.setData(data[position], deleteListener, updateListener)
    }

    override fun getItemCount(): Int = data.size

    fun submitList(list: List<BookingEntity>){
        val initSize = itemCount
        data.clear()
        notifyItemRangeRemoved(0, initSize)
        data.addAll(list)
        notifyItemRangeInserted(0, data.size)
    }

    inner class ViewHolder(private val binding: ItemBookingListBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(
            item: BookingEntity,
            deleteClickListener: ((BookingEntity) -> Unit)?,
            updateClickListener: ((BookingEntity) -> Unit)?
            ){
            binding.cvDelete.setOnClickListener {
                deleteClickListener?.invoke(item)
            }
            binding.cvUpdate.setOnClickListener {
                updateClickListener?.invoke(item)
            }
            binding.tvBookingId.text = item.codeBook
            binding.tvCehicleName.text = item.vehicleName
            binding.tvTimeStart.text = item.dateStart
            binding.tvTimeEnd.text = item.dateEnd
            Glide.with(itemView.context)
                .load(item.vehicleImage)
                .placeholder(R.drawable.img_brio)
                .into(binding.ivVehicle)
        }
    }

    fun setOnDeleteListener(listener: ((BookingEntity) -> Unit)?){
        this.deleteListener = listener
    }

    fun setOnUpdateListener(listener: ((BookingEntity) -> Unit)?){
        this.updateListener = listener
    }
}