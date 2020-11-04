package com.tam.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
    val US_UNITS_VIEW = "US_UNITS_VIEW"

    var currentVisibility : String = METRIC_UNITS_VIEW


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        setSupportActionBar(bmi)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATOR BMI"
        }
        bmi.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculate.setOnClickListener {
            if(currentVisibility.equals(METRIC_UNITS_VIEW)){
                if(validateMetricUnits()){
                    val heightValue  : Float = heightId.text.toString().toFloat() / 100
                    val weightValue : Float = weightId.text.toString().toFloat()
                    Toast.makeText(this,heightValue.toString(),Toast.LENGTH_SHORT).show()



                    val bmi = weightValue / (heightValue*heightValue)
                    Toast.makeText(this,bmi.toString(),Toast.LENGTH_SHORT).show()
                    bmiDisplay(bmi)
                } else{
                    Toast.makeText(this,"please enter",Toast.LENGTH_SHORT).show()
                }
            }else{
                if(validateUSUnits()){
                    val usFeet : String = feet.text.toString()
                    val usInch : String = inch.text.toString()
                    val weight : Float = unitWeight.text.toString().toFloat()

                    val heightValue = usInch.toFloat() + usFeet.toFloat() * 12

                    val bmi = 703 * (weight / (heightValue * heightValue))
                    bmiDisplay(bmi)


                }else{
                    Toast.makeText(this,"please enter",Toast.LENGTH_SHORT).show()
                }
            }

        }
        makeVisibilityMetrics()
        radio.setOnCheckedChangeListener { group, i ->

            if(i == R.id.rbMetricUnits){
                makeVisibilityMetrics()
            }else {
                makeVisibilityUs()
            }

        }
    }
    private fun makeVisibilityUs(){

        currentVisibility = US_UNITS_VIEW

        tiMetricWeight.visibility = View.GONE
        tiMetricHeight.visibility = View.GONE
        liBmiVisible.visibility = View.GONE
        weightId.text!!.clear()
        heightId.text!!.clear()


        llHeight.visibility = View.VISIBLE


        llWeight.visibility = View.VISIBLE
    }

    private fun validateMetricUnits():Boolean{
        var isValid = true
        if(weightId.toString().isEmpty()){
            isValid = false
        }else if(heightId.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }
    private fun validateUSUnits():Boolean{
        var isValid = true
        if(unitWeight.toString().isEmpty()){
            isValid = false
        }else if(feet.toString().isEmpty()){
            isValid = false
        }else if(inch.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun makeVisibilityMetrics(){

        currentVisibility = METRIC_UNITS_VIEW


        tiMetricWeight.visibility = View.VISIBLE
        tiMetricHeight.visibility = View.VISIBLE
        weightId.text!!.clear()
        heightId.text!!.clear()


        llHeight.visibility = View.GONE


        llWeight.visibility = View.GONE





    }


    private fun bmiDisplay(bmi : Float){
        val bmiResult : String
        val bmiDescription : String

        if(bmi.compareTo(15f) <= 0){

            bmiResult = "very underWeight"
            bmiDescription = "need to take better care"

        }else if(bmi.compareTo(15f)>0 && bmi.compareTo(16f) <=0){

            bmiResult = "severly underWeight"
            bmiDescription = "need to take better care"
        }else if(bmi.compareTo(16f)>0 && bmi.compareTo(18.5f) <=0){
            bmiResult = "underWeight"
            bmiDescription = "need to take better care"
        }else if(bmi.compareTo(18.5f)>0 && bmi.compareTo(25f) <=0){
            bmiResult = "normal"
            bmiDescription = "congratulations"
        }else if(bmi.compareTo(25f)>0 && bmi.compareTo(30f) <=0){
            bmiResult = "over weight"
            bmiDescription = "need to take better care"
        }else if(bmi.compareTo(30f)>0 && bmi.compareTo(35f) <=0){
            bmiResult = "obese class moderate"
            bmiDescription = "need to take better care"
        }else if(bmi.compareTo(35f)>0 && bmi.compareTo(40f) <=0){
            bmiResult = "obese class severely"
            bmiDescription = "need to take better care dangerous condition"
        }else {
            bmiResult = "obese class very severely"
            bmiDescription = "need to take better care dangerous condition"
        }

        liBmiVisible.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        bmiValueId.text = bmiValue
        bmiType.text = bmiResult
        description.text = bmiDescription


    }

private fun validate() : Boolean{
    var isValid = true
    if(weightId.text.toString().isEmpty())
        isValid = false
    if(heightId.text.toString().isEmpty())
        isValid = false
    return isValid
}
}