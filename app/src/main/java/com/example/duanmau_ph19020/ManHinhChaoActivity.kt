package com.example.duanmau_ph19020

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import coil.load
import com.example.duanmau_ph19020.databinding.ActivityManHinhChaoBinding

class ManHinhChaoActivity : AppCompatActivity() {
    lateinit var binding:ActivityManHinhChaoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManHinhChaoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        },2000)

    }
}