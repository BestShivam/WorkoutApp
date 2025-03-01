package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import com.example.myapplication.databinding.ActivityBmicalculatorBinding

class BMICalculatorActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBmicalculatorBinding
    private lateinit var radio_group : RadioGroup
    companion object{
        private const val METRIC_UNIT_VIEW = "metric_unit"
        private const val US_UNIT_VIEW = "us_unit"
    }
    private var currentUnitView = METRIC_UNIT_VIEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBmicalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        radio_group = findViewById(R.id.rg_Unit)
        setSupportActionBar(binding.tbBMI)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }
        binding.tbBMI.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.tbBMI.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnCalculator.setOnClickListener {
                // bmi = w/(h*h)
                // w in kg and h in m
                // if h in cm , bmi = w/((h/100)*(h/100))
                var weight: Double?
                var height: Double?
                var bmi = 0.0

                if(currentUnitView == US_UNIT_VIEW){
                    weight = binding.etWeightInPounds.text.toString().toDoubleOrNull() ?: 0.0
                    height= binding.etHeightInInches.text.toString().toDoubleOrNull()  ?: 0.0
                    bmi = (weight * 703) / (height * height)
                } else{
                    weight = binding.etWeightInKG.text.toString().toDoubleOrNull() ?: 0.0
                    height = binding.etHeightInCm.text.toString().toDoubleOrNull() ?: 0.0
                    bmi = (weight * 10000) /  (height * height)
                }

                val type  = BmiType(bmi)
                val description  = BmiDescription(bmi)
                binding.llDisplayResult.visibility = View.VISIBLE
                binding.tvResult.text = String.format("%.2f",bmi)
                binding.tvBMIType.text = type
                binding.tvBMIDescription.text = description

        }
        // in us matrix , weight in pounds (lbs)
        // height in inches
        // bmi = (weight * 703) / (height * height)

        binding.rgUnit.setOnCheckedChangeListener { group, checkedId ->
            if(R.id.rb_us_unit == checkedId){
                setUSUnitView()
            }else{
                setMetricUnitView()
            }
        }
    }
    private fun BmiType(bmi : Double) : String{
        //Underweight: Less than 18.5
        //Normal weight: 18.5–24.9
        //Overweight: 25–29.9
        //Obese: 30 or more
        return if(bmi == 0.0){
            "Enter Weight and Height"
        }
        else if (bmi< 18.5){
            "Underweight"
        }
        else if(18.5 < bmi && bmi < 24.9){
            "Normal Weight"
        }
        else if(25 < bmi && bmi < 29.9){
            "Overweight"
        }
        else {
            "Obese"
        }
    }
    private fun BmiDescription(bmi : Double) : String{
        val desc = if (bmi == 0.0){
            R.string.none
        }
        else if (bmi< 18.5){
            R.string.normal
        }
        else if(18.5 < bmi && bmi < 24.9){
            R.string.normal
        }
        else if(25 < bmi && bmi < 29.9){
            R.string.overweight
        }
        else {
            R.string.obese
        }
        return getString(desc)
    }



    private fun setMetricUnitView(){
        currentUnitView = METRIC_UNIT_VIEW
        binding.tfWeightInKG.visibility = View.VISIBLE
        binding.tfHeightInCM.visibility = View.VISIBLE
        binding.tfWeightInPounds.visibility = View.INVISIBLE
        binding.tfHeightInInches.visibility = View.INVISIBLE
        binding.etWeightInPounds.text!!.clear()
        binding.etHeightInInches.text!!.clear()
        binding.llDisplayResult.visibility = View.INVISIBLE
    }
    private fun setUSUnitView(){
        currentUnitView = US_UNIT_VIEW
        binding.tfWeightInKG.visibility = View.INVISIBLE
        binding.tfHeightInCM.visibility = View.INVISIBLE
        binding.tfWeightInPounds.visibility = View.VISIBLE
        binding.tfHeightInInches.visibility = View.VISIBLE
        binding.etWeightInKG.text!!.clear()
        binding.etHeightInCm.text!!.clear()
        binding.llDisplayResult.visibility = View.INVISIBLE
    }
}
