package com.carlosmesquita.technicaltest.n26.bitlue.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carlosmesquita.technicaltest.n26.bitlue.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}