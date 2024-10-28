package com.juanfe.project.weatherapp.ui.search

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.search.SearchView
import com.juanfe.project.weatherapp.R
import com.juanfe.project.weatherapp.databinding.FragmentSearchBinding
import com.juanfe.project.weatherapp.domain.CurrentModel
import com.juanfe.project.weatherapp.domain.ForecastDayModel
import com.juanfe.project.weatherapp.domain.RootForecastModel
import com.juanfe.project.weatherapp.domain.SearchModel
import com.juanfe.project.weatherapp.domain.TypeDetail
import com.juanfe.project.weatherapp.domain.WeatherDetailModel
import com.juanfe.project.weatherapp.ui.search.adapter.detail.WeatherDetailAdapter
import com.juanfe.project.weatherapp.ui.search.adapter.forecast.ForecastDayAdapter
import com.juanfe.project.weatherapp.ui.search.adapter.search.SearchHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private lateinit var forecastDayAdapter: ForecastDayAdapter
    private lateinit var weatherDetailAdapter: WeatherDetailAdapter

    private lateinit var cityName: String

    private val searchViewModel: SearchViewModel by viewModels()
    private var searchViewOpen = false


    // Launcher para solicitud de permisos de ubicación
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                getDeviceLocation()
            } else {
                //Pueden ser un componente de material 3
                Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        requestLocationPermissions()
        initObservers()
        initListeners()
        setUpRecyclerView()
        onBack()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.viewState.collect { viewState ->
                    updateUi(viewState)
                }
            }
        }
    }


    private fun initListeners() {
        binding.apply {

            searchBar.textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            searchBar.textView.setHintTextColor(ContextCompat.getColor(requireContext(),R.color.white))

            /*searchView.editText.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            searchView.editText.setHintTextColor(ContextCompat.getColor(requireContext(),R.color.white))*/

            searchView.addTransitionListener { _, transitionState, _ ->
                if (transitionState == SearchView.TransitionState.SHOWING) {
                    searchViewOpen = true
                    searchViewModel.handleIntent(UserIntent.TapSearch)
                }
                if (transitionState == SearchView.TransitionState.HIDING) {
                    searchHistoryAdapter.updateList(listOf())
                }
            }

            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Not necessary
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Not necessary

                }

                override fun afterTextChanged(query: Editable?) {
                    if (!query.isNullOrEmpty() && query.length >= 3) {
                        searchViewModel.handleIntent(UserIntent.SearchLocation(query.toString()))
                    }
                }
            })

            searchView.editText.setOnEditorActionListener { query, _, _ ->
                val text = query?.text.toString()
                searchBar.setText(text)
                searchView.hide()
                if (text.isNotEmpty() && text.length >= 3) searchViewModel.handleIntent(UserIntent.GetForecast(text))
                true
            }
        }
    }

/*    private fun setThemeBasedOnPreference(isLightTheme: Boolean) {
        if (isLightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }*/

    private fun requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            getDeviceLocation()
        }
    }

    //Can move for viewModel
    private fun getDeviceLocation() {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // get location only if the app have permission
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    Log.e("Location", "lat: ${it.latitude} && lon: ${it.longitude}")
                    cityName = getCityNameFromLocation(it.latitude, it.longitude) ?: "Bogota"
                    searchViewModel.handleIntent(UserIntent.GetForecast(cityName))
                } ?: run {
                    Toast.makeText(context, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }

    //Can move for viewModel
    private fun getCityNameFromLocation(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.firstOrNull()?.locality
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpRecyclerView() {
        searchRV()
        forecastDaysRv()
        weatherDetailRv()
    }

    private fun weatherDetailRv() {
        binding.weatherDetailRv.layoutManager = GridLayoutManager(requireContext(), 2)

        weatherDetailAdapter = WeatherDetailAdapter(listOf())

        binding.weatherDetailRv.adapter = weatherDetailAdapter
    }

    private fun forecastDaysRv() {
        binding.forecastDaysRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        forecastDayAdapter = ForecastDayAdapter(listOf())

        binding.forecastDaysRv.adapter = forecastDayAdapter
    }

    private fun searchRV() {
        binding.searchResultsRv.layoutManager = LinearLayoutManager(requireContext())

        //listOf podria ser la ubicacion actual
        searchHistoryAdapter = SearchHistoryAdapter(listOf()) { query, search ->
            if (search) {
                searchViewModel.handleIntent(UserIntent.GetForecast(query))
                binding.searchBar.setText(query)
                binding.searchView.hide()
            } else {
                binding.searchView.setText(query)
                binding.searchBar.setText(query)
            }
        }

        binding.searchResultsRv.adapter = searchHistoryAdapter
    }


    private fun onBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (searchViewOpen) {
                        binding.progress.visibility = View.GONE
                        binding.searchView.hide()
                        searchViewOpen = false

                    } else {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            })
    }

    private fun updateUi(viewState: SearchViewState) {
        when (viewState) {
            is SearchViewState.Error -> showErrorState(viewState.errorMsg)
            is SearchViewState.Loading -> showLoadingState()
            is SearchViewState.SearchLocationSuccess -> showSearchLocationSuccess(viewState.searchLocationModel)
            is SearchViewState.ForecastSuccess -> showForecastSuccess(viewState.getForecast)
        }
    }

    private fun showErrorState(errorMsg: String) {
        clearAdapters()
        binding.apply {
            msgInformation.apply {
                text = errorMsg
                visibility = View.VISIBLE
            }
            progress.visibility = View.GONE
            weatherInfo.visibility = View.GONE
        }
    }

    private fun showLoadingState() {
        clearAdapters()
        binding.apply {
            progress.visibility = View.VISIBLE
            weatherInfo.visibility = View.GONE
            msgInformation.visibility = View.GONE
        }
    }

    private fun showSearchLocationSuccess(searchLocation: List<SearchModel>) {
        if (searchLocation.isNotEmpty()) {
            searchHistoryAdapter.updateList(searchLocation)
        }
    }

    private fun showForecastSuccess(rootForecast: RootForecastModel) {
        binding.weatherInfo.visibility = View.VISIBLE
        drawForecast(rootForecast)
        forecastDayAdapter.updateList(rootForecast.forecast.forecastDay)
        drawChar(rootForecast.forecast.forecastDay)
        drawCardInfo(rootForecast.current)
        binding.progress.visibility = View.GONE
    }

    private fun clearAdapters() {
        searchHistoryAdapter.updateList(emptyList())
        forecastDayAdapter.updateList(emptyList())
    }

    private fun drawCardInfo(current: CurrentModel) {
        val weatherDetailModel = createWeatherDetailModel(current)
        weatherDetailAdapter.updateList(weatherDetailModel)
    }

    private fun createWeatherDetailModel(current: CurrentModel) =
        listOf(
            WeatherDetailModel(
                getString(R.string.pressure),
                "${current.pressureMb} mb",
                TypeDetail.PRESSURE
            ),
            WeatherDetailModel(
                getString(R.string.humidity),
                "${current.humidity} %",
                TypeDetail.HUMIDITY
            ),
            WeatherDetailModel(
                getString(R.string.wind_kph),
                "${current.windKph} km/h",
                TypeDetail.WIND
            ),
            WeatherDetailModel(getString(R.string.vis_km), "${current.visKm} km", TypeDetail.VIS),
            WeatherDetailModel(
                getString(R.string.dew_point_c),
                "${current.dewPointC} °C",
                TypeDetail.DEWPOINT
            ),
            WeatherDetailModel(getString(R.string.uv), "${current.uv}", TypeDetail.UV)
        )

    private fun drawChar(days: List<ForecastDayModel>) {
        val avgTempC = days.map { it.day.avgTempC }

        val entries = avgTempC.mapIndexed { index, value ->
            Entry(index.toFloat(), value.toFloat())
        }

        // Create the DataSet and configure its properties
        val dataSet = LineDataSet(entries, "").apply {
            color = ContextCompat.getColor(requireContext(), R.color.white) // Line color
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.white) // Value text color
            valueTextSize = 10f
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.white)) // Circle color
            setDrawCircles(true)
            setDrawCircleHole(true)
            lineWidth = 1f
            valueFormatter = object : ValueFormatter() {
                override fun getPointLabel(entry: Entry?): String {
                    return "${entry?.y?.toInt()}°C"
                }
            }
        }

        // Create the LineData with the DataSet
        val lineData = LineData(dataSet)

        binding.lineChart.apply {
            data = lineData

            xAxis.apply {
                setDrawAxisLine(false) // Hide X axis line
                setDrawLabels(false) // Hide X axis labels
                setDrawGridLines(false) // Hide X axis grid lines
                position = XAxis.XAxisPosition.BOTTOM
            }

            axisLeft.apply {
                setDrawAxisLine(false) // Hide Y axis line (left)
                setDrawLabels(false) // Hide Y axis labels (left)
                setDrawGridLines(false) // Hide Y axis grid lines (left)
            }

            axisRight.isEnabled = false // Disable right Y axis completely
            description.isEnabled = false
            legend.isEnabled = false // Hide legend
            isDragEnabled = false // Disable drag
            setScaleEnabled(false) // Disable scaling
            setPinchZoom(false) // Disable pinch zoom
            isDoubleTapToZoomEnabled = false // Disable double tap to zoom
            animateY(1000)
        }
    }

    private fun drawForecast(forecast: RootForecastModel) {
        binding.apply {
            val temp = "${forecast.current.tempC.toInt()}°C"
            val maxMinTemp =
                "${forecast.forecast.forecastDay[0].day.maxTempC.toInt()} / ${forecast.forecast.forecastDay[0].day.minTempC.toInt()}°C"
            val location = "${forecast.location.name} "
            nameCity.text = location
            nameCountry.text = forecast.location.country
            tempCity.text = temp
            weatherCity.text = forecast.current.condition.text
            maxMinTempCity.text = maxMinTemp
        }
    }

}