package com.mil.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mil.weather.AppConfig
import com.mil.weather.R
import com.mil.weather.TitleChangeListener
import com.mil.weather.adapter.ForecastAdapter
import com.mil.weather.databinding.FragmentSecondBinding
import com.mil.weather.retrofit.Forecast
import com.mil.weather.retrofit.IService
import com.mil.weather.retrofit.RetrofitInstance
import com.mil.weather.room.AppDatabase
import com.mil.weather.room.factory.CityFactory
import com.mil.weather.room.factory.WeatherFactory
import com.mil.weather.room.model.Weather
import com.mil.weather.room.repository.CityRepository
import com.mil.weather.room.repository.WeatherRepository
import com.mil.weather.room.viewmodel.CityViewModel
import com.mil.weather.room.viewmodel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private lateinit var appDatabase: AppDatabase
    private lateinit var cityRepository: CityRepository
    private lateinit var cityFactory: CityFactory
    private lateinit var cityViewModel: CityViewModel

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherFactory: WeatherFactory
    private lateinit var weatherViewModel: WeatherViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = AppDatabase(this.requireContext())
        cityRepository = CityRepository(appDatabase)
        cityFactory = CityFactory(cityRepository)
        cityViewModel = ViewModelProvider(this, cityFactory)[CityViewModel::class.java]

        weatherRepository = WeatherRepository(appDatabase)
        weatherFactory = WeatherFactory(weatherRepository)
        weatherViewModel = ViewModelProvider(this, weatherFactory)[WeatherViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        getForecast(requireArguments().getInt("cityId"))
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun getForecast(cityId: Int) {
        cityViewModel.getCityById(cityId).observe(this@SecondFragment) { city ->

            (activity as TitleChangeListener).updateTitle(getString(R.string.city_weather, city.cityName))
            val iService: IService = RetrofitInstance.instance!!.create(IService::class.java)
            val call: Call<Forecast> = iService.getForecast(city.cityName, AppConfig.WEATHER_API_KEY)
            call.enqueue(object : Callback<Forecast> {
                override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                    val weatherList = response.body()!!.list!!.map {
                        Weather(
                            weatherId = null,
                            cityId = cityId,
                            date = System.currentTimeMillis(),
                            temp = it?.main?.temp!!,
                            icon = it.weather?.get(0)?.icon
                        )
                    }
                    cacheForecast(cityId, weatherList)
                    populateWeather(weatherList)
                    binding.progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<Forecast>, t: Throwable?) {
                    // populate weather from Cache (DB)
                    weatherViewModel.getCityWeather(cityId).observe(this@SecondFragment) {
                        populateWeather(it)
                        binding.progressBar.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun cacheForecast(cityId: Int, weatherList: List<Weather>) {
        CoroutineScope(Dispatchers.IO).launch {
            if (weatherList.isNotEmpty()) {
                weatherViewModel.deleteWeatherByCityId(cityId)
                weatherList.forEach {
                    weatherViewModel.insertWeather(it)
                }
            }
        }
    }

    private fun populateWeather(daily: List<Weather>) {
        val recyclerView = _binding!!.rvForecast
        val adapter = ForecastAdapter(this.context, daily)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}