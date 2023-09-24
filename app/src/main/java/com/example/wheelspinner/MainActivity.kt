package com.example.wheelspinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.example.wheelspinner.databinding.ActivityMainBinding
import com.prateek.simplespinwheel.OnSpinCompleteListener
import com.prateek.simplespinwheel.SimpleSpinWheel

class MainActivity : AppCompatActivity(), OnSpinCompleteListener, OnClickListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var wheel : SimpleSpinWheel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wheel = binding.simpleSpinWheel

        wheel.setOnSpinCompleteListener(this)
        wheel.setOnClickListener(this)

        wheel.setItems(arrayOf(
            "Item1", "Item2", "Item3", "Item4", "Item5", "Item6"
        ))

        wheel.setColors(
            arrayOf(
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_light
            )
        )

        binding.textView.setOnClickListener{
            wheel.spin()
        }


    }

    override fun onSpinComplete(selectedItem: String) {
       Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()
        binding.textView.text = selectedItem
    }


    override fun onClick(p0: View?) {
        wheel.spin()
    }

}