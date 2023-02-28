package com.slane.tipcalculator

import android.os.Bundle
import android.view.KeyEvent
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.slane.tipcalculator.databinding.ActivityMainBinding
import kotlin.math.ceil

// Left off Because I'm getting an unknown error!! What the heck
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.editTextNumberDecimal.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                keyEvent == null ||
                keyEvent.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                calculateValues()
                true
            }
            false
        }

        binding.seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, formUser: Boolean) {
                calculateValues()
                 binding.txtTipPercentLabel.text = String.format(getString(R.string.percent_value_format)
                 , progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?){
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.buttonRoundUp.setOnClickListener{
            binding.txtTotalZero.text = String.format(getString(R.string.dollar_value_format),
            ceil(binding.txtTotalZero.text.toString().substring(1).toDouble())
            // The ceil function above is for rounding
            )
        }
    }

    fun calculateValues(){
        val tipPercent = binding.seekBarTip.progress
        if (binding.editTextNumberDecimal.text.isNotEmpty()){
            val totalBill = binding.editTextNumberDecimal.text.toString().toDouble()
            val tip = tipPercent / 100.0 * totalBill
            val total = totalBill + tip
            binding.txtTotalZero.text = String.format(getString(R.string.dollar_value_format), total)
            binding.txtTip.text = String.format(getString(R.string.dollar_value_format), tip)
        }
    }
}