package com.jacobshinta.tipCal

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity



class MainActivity : ComponentActivity() {
    private var tipCurrent = 0.15
    private var isCustomTip = false
    private lateinit var billAmount: EditText
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView
    private lateinit var btnLow: Button
    private lateinit var btnMed: Button
    private lateinit var btnHigh: Button
    private lateinit var btnCustom: Button
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        billAmount = findViewById(R.id.et_bill)
        tipAmount = findViewById(R.id.tv_tip_amount)
        totalAmount = findViewById(R.id.tv_total)
        btnLow = findViewById(R.id.btn_low)
        btnMed = findViewById(R.id.btn_mid)
        btnHigh = findViewById(R.id.btn_high)
        btnCustom = findViewById(R.id.btn_custom)

        btnLow.backgroundTintList = resources.getColorStateList(R.color.button_color)

        billAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                computeTipAndTotal()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        btnLow.setOnClickListener{
            tipCurrent = .15
            reload(0)
            isCustomTip = false
            computeTipAndTotal()
        }
        btnMed.setOnClickListener{
            tipCurrent = .18
            reload(1)
            isCustomTip = false
            computeTipAndTotal()
        }
        btnHigh.setOnClickListener{
            tipCurrent = .20
            reload(2)
            isCustomTip = false
            computeTipAndTotal()
        }
        btnCustom.setOnClickListener{
            reload(3)
            showCustomTipDialog()
        }
    }
    private fun computeTipAndTotal() {
        if (billAmount.text.isEmpty()) {
            tipAmount.text = ""
            totalAmount.text = ""
            return
        }
        val billCalc = billAmount.text.toString().toDouble()

        val tipCalc = if (isCustomTip) tipCurrent else billCalc * tipCurrent
        val totalCalc = billCalc + tipCalc

        tipAmount.text = "%.2f".format(tipCalc)
        totalAmount.text = "%.2f".format(totalCalc)
    }

    private fun showCustomTipDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Custom Tip")

        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, _ ->
            val customTip = input.text.toString()
            if (customTip.isNotEmpty()) {
                isCustomTip = true
                btnCustom.text = "$$customTip"
                tipCurrent = customTip.toDouble()
                computeTipAndTotal()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    fun reload(btnSelected: Int) {
        when(btnSelected) {
            1 -> {
                btnLow.backgroundTintList = resources.getColorStateList(R.color.button_color_gray)
                btnMed.backgroundTintList    = resources.getColorStateList(R.color.button_color)
                btnHigh.backgroundTintList = resources.getColorStateList(R.color.button_color_gray)
                btnCustom.backgroundTintList = resources.getColorStateList(R.color.button_color_gray)
            }
            2 -> {
                btnLow.backgroundTintList = resources.getColorStateList(R.color.button_color_gray)
                btnMed.backgroundTintList    = resources.getColorStateList(R.color.button_color_gray)
                btnHigh.backgroundTintList = resources.getColorStateList(R.color.button_color)
                btnCustom.backgroundTintList = resources.getColorStateList(R.color.button_color_gray)
            }
            3 -> {
                btnLow.backgroundTintList = resources.getColorStateList(R.color.button_color_gray)
                btnMed.backgroundTintList    = resources.getColorStateList(R.color.button_color_gray)
                btnHigh.backgroundTintList = resources.getColorStateList(R.color.button_color_gray)
                btnCustom.backgroundTintList = resources.getColorStateList(R.color.button_color)
            }
            else -> {
                btnLow.backgroundTintList = resources.getColorStateList(R.color.button_color)
                btnMed.backgroundTintList    = resources.getColorStateList(R.color.button_color_gray)
                btnHigh.backgroundTintList = resources.getColorStateList(R.color.button_color_gray)
                btnCustom.backgroundTintList = resources.getColorStateList(R.color.button_color_gray)
            }
        }
    }
}
