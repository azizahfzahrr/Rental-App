package com.example.cakratech.features

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.example.cakratech.R
import com.example.cakratech.databinding.ActivityBookingFormBinding
import com.example.core.data.room.entity.BookingEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingForm : AppCompatActivity() {
    private lateinit var binding: ActivityBookingFormBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val booking: BookingEntity? = intent.getParcelableExtra("booking")
        binding = ActivityBookingFormBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.root.applySystemWindowInsets()
        binding.apply {
            tilNama.editText?.setText(booking?.vehicleName)
            tilDateStart.editText?.setText(booking?.dateStart)
            tilDateEnd.editText?.setText(booking?.dateEnd)
            cardViewEdit.setOnClickListener {
                val updatedBooking = BookingEntity(
                    id = booking?.id ?: 0, // Ensure the ID is included
                    codeBook = booking?.codeBook ?: "",
                    vehicleName = tilNama.editText?.text.toString(),
                    vehicleImage = booking?.vehicleImage ?: "",
                    dateStart = tilDateStart.editText?.text.toString(),
                    dateEnd = tilDateEnd.editText?.text.toString()
                )

                updateDataById(updatedBooking.id, updatedBooking)
            }
        }
    }

    private fun View.applySystemWindowInsets(){
        ViewCompat.setOnApplyWindowInsetsListener(this){ view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateDataById(id: Long, updatedData: BookingEntity){
        viewModel.updateBookingById(id, updatedData)
        finish()
    }
}