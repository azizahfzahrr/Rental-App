package com.example.cakratech

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.cakratech.databinding.ActivityMainBinding
import com.example.cakratech.features.HomeFragment
import com.example.cakratech.features.MainViewModel
import com.example.cakratech.features.ReservationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.root.applySystemWindowInsets()
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.selectedItemId = R.id.nav_beranda
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_beranda -> replaceFragment(HomeFragment())
                R.id.nav_history -> replaceFragment(ReservationFragment())
//                R.id.nav_help -> replaceFragment(RiwayatFragment())
//                R.id.nav_profil -> replaceFragment(FProfil())
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.apply {
            replace(R.id.FragContainer, fragment)
            commit()
        }
    }

    private fun View.applySystemWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}