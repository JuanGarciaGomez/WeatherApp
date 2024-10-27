package com.juanfe.project.weatherapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
import com.google.android.material.search.SearchView
import com.juanfe.project.weatherapp.R
import com.juanfe.project.weatherapp.databinding.FragmentSearchBinding
import com.juanfe.project.weatherapp.domain.CurrentModel
import com.juanfe.project.weatherapp.domain.ForecastDayModel
import com.juanfe.project.weatherapp.domain.RootForecastModel
import com.juanfe.project.weatherapp.domain.TypeDetail
import com.juanfe.project.weatherapp.domain.WeatherDetailModel
import com.juanfe.project.weatherapp.ui.search.adapter.detail.WeatherDetailAdapter
import com.juanfe.project.weatherapp.ui.search.adapter.forecast.ForecastDayAdapter
import com.juanfe.project.weatherapp.ui.search.adapter.search.SearchHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private lateinit var forecastDayAdapter: ForecastDayAdapter
    private lateinit var weatherDetailAdapter: WeatherDetailAdapter

    private val searchViewModel: SearchViewModel by viewModels()
    private var searchViewOpen = false


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
                    if (!query.isNullOrEmpty()) {
                        searchViewModel.handleIntent(UserIntent.SearchLocation(query.toString()))
                    }
                }
            })

            searchView.editText.setOnEditorActionListener { query, _, _ ->
                val text = query?.text.toString()
                searchBar.setText(text)
                searchView.hide()
                if (text.isNotEmpty()) searchViewModel.handleIntent(UserIntent.SearchLocation(text))
                true
            }
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
            is SearchViewState.Error -> {
                searchHistoryAdapter.updateList(listOf())
                forecastDayAdapter.updateList(listOf())
                binding.msgInformation.visibility = View.VISIBLE
                binding.msgInformation.text = viewState.errorMsg
                hideLoading(visibleRv = false)
            }

            is SearchViewState.Loading -> {
                if (viewState.firstOpen) {
                    binding.weatherInfo.visibility = View.GONE
                    binding.progress.visibility = View.GONE
                    binding.msgInformation.text =
                        requireContext().getString(R.string.search_something)
                } else {
                    searchHistoryAdapter.updateList(listOf())
                    forecastDayAdapter.updateList(listOf())
                    binding.msgInformation.visibility = View.GONE
                    binding.progress.visibility = View.VISIBLE
                }

            }

            is SearchViewState.SearchLocationSuccess -> {
                val searchLocation = viewState.searchLocationModel
                if (searchLocation.isNotEmpty()) {
                    searchHistoryAdapter.updateList(searchLocation)
                }
                binding.weatherInfo.visibility = View.VISIBLE
                hideLoading(visibleRv = true)
            }

            is SearchViewState.ForecastSuccess -> {
                val rootForecast = viewState.getForecast
                binding.weatherInfo.visibility = View.VISIBLE
                drawForecast(viewState.getForecast)
                forecastDayAdapter.updateList(rootForecast.forecast.forecastDay)
                hideLoading(visibleRv = true)
                drawChar(rootForecast.forecast.forecastDay)
                drawCardInfo(rootForecast.current)
            }
        }

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
            color = ContextCompat.getColor(requireContext(), R.color.primary) // Line color
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.primary) // Value text color
            valueTextSize = 10f
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.primary)) // Circle color
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

    private fun hideLoading(visibleRv: Boolean, visibleProgress: Boolean = false) {
        binding.searchResultsRv.isVisible = visibleRv
        binding.forecastDaysRv.isVisible = visibleRv
        binding.progress.isVisible = visibleProgress
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