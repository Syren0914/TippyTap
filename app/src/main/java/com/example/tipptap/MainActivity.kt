package com.example.tipptap

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator as ArgbEvaluator

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENTAGE = 15

class MainActivity : AppCompatActivity() {
    private lateinit var etbase_amount: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var Tv_percentage_feeling: TextView
    private lateinit var Tip: TextView
    private lateinit var Total_tip: TextView
    private lateinit var Feeling: TextView
    private lateinit var number_ofp: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etbase_amount = findViewById(R.id.etbase_amount)
        seekBar = findViewById(R.id.seekBar)
        Tv_percentage_feeling = findViewById(R.id.Tv_percentage_feeling)
        Total_tip = findViewById(R.id.Total_tip)
        Tip = findViewById(R.id.Tip)
        Feeling = findViewById(R.id.Feeling)
        number_ofp = findViewById(R.id.number_ofp)
        seekBar.progress = INITIAL_TIP_PERCENTAGE
        Tv_percentage_feeling.text = "$INITIAL_TIP_PERCENTAGE%"
        updateTipdescription(INITIAL_TIP_PERCENTAGE)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "OnProgressChanged $p1")
                Tv_percentage_feeling.text = "$p1%"
                computeTipAndTotal()
                updateTipdescription(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        number_ofp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()
            }


        })
        etbase_amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()

            }

        })



    }

    @SuppressLint("RestrictedApi")
    private fun updateTipdescription(TipPercent: Int) {
       val tipDescription = when(TipPercent){
           in 0..9 -> "Poor"
           in 10..14 -> "Acceptable"
           in 15..19 -> "Good"
           in 20..24 -> "Great"
           else -> "Amazing"
       }




        Feeling.text = tipDescription
        val color = ArgbEvaluator().evaluate(
            TipPercent.toFloat() / seekBar.max,
            ContextCompat.getColor(this, R.color.Color_Worst_Tip),
            ContextCompat.getColor(this, R.color.Color_Best_Tip)
        ) as Int
        Feeling.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if (etbase_amount.text.isEmpty()){

            Tip.text = ""
            Total_tip.text = ""
            return

        }
        if(number_ofp.text.isEmpty()){
            return
        }

        //1. Get the value of the base and tip percent
        val baseAmount = etbase_amount.text.toString().toDouble()
        val tipPercent = seekBar.progress
        val peoples = number_ofp.text.toString().toDouble()

        //2. Compute the and total
        val tipAmount = baseAmount * tipPercent /100
        val totalAmount = (tipAmount+ baseAmount).toFloat()
        val totalAmount2 = totalAmount / peoples
        //3. Update the UI
        Tip.text = "%.2f".format(tipAmount)
        Total_tip.text = "%.2f".format(totalAmount2)
    }

}