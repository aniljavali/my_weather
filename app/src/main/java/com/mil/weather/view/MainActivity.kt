package com.mil.weather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mil.weather.R
import com.mil.weather.TitleChangeListener
import com.mil.weather.databinding.ActivityMainBinding
import com.mil.weather.room.AppDatabase
import com.mil.weather.room.factory.CityFactory
import com.mil.weather.room.model.City
import com.mil.weather.room.repository.CityRepository
import com.mil.weather.room.viewmodel.CityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), TitleChangeListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var appDatabase: AppDatabase
    private lateinit var cityRepository: CityRepository
    private lateinit var cityFactory: CityFactory
    private lateinit var cityViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        appDatabase = AppDatabase(this)
        cityRepository = CityRepository(appDatabase)
        cityFactory = CityFactory(cityRepository)
        cityViewModel = ViewModelProvider(this, cityFactory)[CityViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        // populate seed when db is empty
        cityViewModel.getCityById(1).observe(this) {
            if (it == null) {
                seedData()
            }
        }
    }

    private fun seedData() {
        CoroutineScope(Dispatchers.Main).launch {
            cityViewModel.insertCity(City(cityId = null, cityName = "Chicago", state = "IL"))
            cityViewModel.insertCity(City(cityId = null, cityName = "Milwaukee", state = "WI"))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun updateTitle(title: String) {
        binding.toolbar.title = title
    }
}