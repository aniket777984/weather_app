package com.aniket777984.weather_location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aniket777984.weather_location.POJO.ModelClass
import com.aniket777984.weather_location.Utilities.ApiUtilities
import com.aniket777984.weather_location.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var Api_Key = "37dcbb0e79296a7f09d9133e96369880"

    private lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        supportActionBar?.hide()

        fetchCurrentLocationWeather(23.4833,87.3167)

        activityMainBinding.etGetCityName.setOnEditorActionListener{v,actionId,keyEvent ->

            if(actionId == EditorInfo.IME_ACTION_SEARCH)
            {
                getCityWeather(activityMainBinding.etGetCityName.text.toString())
                val view=this.currentFocus
                if(view!=null)
                {
                    val imm:InputMethodManager=getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken,0)
                    activityMainBinding.etGetCityName.clearFocus()
                }
                true
            }
            else
            false
        }

    }

    private fun getCityWeather(cityName: String)
    {
        ApiUtilities.getApiInterface()?.getCityWeatherData(cityName,Api_Key)?.enqueue(object :Callback<ModelClass>{

            override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {
                if(response.isSuccessful)
                {
                    Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
                    setDataOnViews(response.body())
                }
            }

            override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
            }

        } )
    }

    private fun fetchCurrentLocationWeather(latitude:Double,longitude:Double)
    {
        ApiUtilities.getApiInterface()?.getCurrentWeatherData(latitude,longitude,Api_Key)?.enqueue(object :Callback<ModelClass>{

            override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {
                if(response.isSuccessful)
                {
                    Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
                    setDataOnViews(response.body())
                }
            }

            override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
            }

        } )
    }

    private fun setDataOnViews(body: ModelClass?) {

        val sdf=SimpleDateFormat("dd/MM/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        activityMainBinding.updatedAt.text=currentDate

        activityMainBinding.status.text = body!!.weather[0].main
        activityMainBinding.temp.text = ""+body.main.temp
        activityMainBinding.tempMin.text=""+body.main.temp_min
        activityMainBinding.tempMax.text=""+body.main.temp_max
        activityMainBinding.sunrise.text=""+body.sys.sunrise
        activityMainBinding.sunset.text=""+body.sys.sunset
        activityMainBinding.pressure.text=""+body.main.pressure
        activityMainBinding.humidity.text=""+body.main.humidity
        activityMainBinding.wind.text=""+body.wind.speed
        activityMainBinding.address.text=body!!.name+" , "+body!!.sys.country

    }


}