package com.mil.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mil.weather.R
import com.mil.weather.adapter.CityAdapter
import com.mil.weather.databinding.FragmentFirstBinding
import com.mil.weather.room.AppDatabase
import com.mil.weather.room.factory.CityFactory
import com.mil.weather.room.model.City
import com.mil.weather.room.repository.CityRepository
import com.mil.weather.room.viewmodel.CityViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var appDatabase: AppDatabase
    private lateinit var cityRepository: CityRepository
    private lateinit var cityFactory: CityFactory
    private lateinit var cityViewModel: CityViewModel

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = AppDatabase(this.requireContext())
        cityRepository = CityRepository(appDatabase)
        cityFactory = CityFactory(cityRepository)
        cityViewModel = ViewModelProvider(this, cityFactory)[CityViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        cityViewModel.getAllCities().observe(
            this@FirstFragment,
            { cities -> populateCities(cities) }
        )
    }

    private fun populateCities(cities: List<City>) {
        val recyclerView = _binding!!.rvCities
        val adapter = CityAdapter(cities) {
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment, bundleOf(
                    Pair("cityId", it.cityId)
                )
            )
        }

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}