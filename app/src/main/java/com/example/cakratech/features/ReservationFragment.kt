package com.example.cakratech.features

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cakratech.databinding.FragmentReservationBinding
import com.example.cakratech.adapter.BookingListAdapter
import com.example.core.data.network.Result
import com.example.core.data.room.entity.BookingEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReservationFragment : Fragment() {
    private lateinit var binding: FragmentReservationBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter: BookingListAdapter by lazy { BookingListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvBookingList.layoutManager = layoutManager
        binding.rvBookingList.adapter = adapter
        updateData()
       // insertNewdata(BookingEntity(0,"code booking", "nama kendaraan", "url gambar", "tanggal awal", "tanggal akhir"))
    }

    private fun updateData(){
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.getCarData().observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        getLocalData()
                    }
                    is Result.Error -> {
                        Toast.makeText(activity, "Error: ${result.errorMessage} \n Showimg Local Data", Toast.LENGTH_SHORT).show()
                        getLocalData()
                    }
                    else -> {
                        Log.d("MainViewModel", "Loading...")
                    }
                }
            }
        }
    }

    private fun getLocalData() {
        adapter.setOnDeleteListener (rvClickListenerDelete)
        adapter.setOnUpdateListener(rvClickListenerUpdate)
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.getLocalData().observe(viewLifecycleOwner) { result ->
                adapter.submitList(result)
            }
        }
    }

    private fun insertNewdata(dataBooking: BookingEntity){
        viewModel.insertBooking(dataBooking)
    }

    private fun deleteDataByIndex(selectedItem: Long){
        viewModel.deleteBookingByIndex(selectedItem)
    }

    private val rvClickListenerDelete: (BookingEntity) -> Unit =
        { item ->
            viewModel.viewModelScope.launch {
                deleteDataByIndex(item.id)
                Toast.makeText(activity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                getLocalData()
            }
    }

    private val rvClickListenerUpdate: (BookingEntity) -> Unit =
        { item ->
        startActivity(Intent(activity, BookingForm::class.java).apply {
            Log.d("ReservationFragment", "Item clicked: $item")
            putExtra("booking", item)
        })
    }

}