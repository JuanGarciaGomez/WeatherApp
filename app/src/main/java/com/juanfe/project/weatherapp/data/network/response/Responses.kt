package com.juanfe.project.weatherapp.data.network.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)
data class RootForecastResponse(
    val location: LocationResponse,
    val current: CurrentResponse,
    val forecast: ForecastResponse,
)

data class LocationResponse(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @SerializedName("tz_id")
    val tzId: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Double,
    val localtime: String,
)

data class CurrentResponse(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Double,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("is_day")
    val isDay: Double,
    val condition: ConditionResponse,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_degree")
    val windDegree: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("pressure_mb")
    val pressureMb: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("precip_in")
    val precipIn: Double,
    val humidity: Double,
    val cloud: Double,
    @SerializedName("feelslike_c")
    val feelsLikeC: Double,
    @SerializedName("feelslike_f")
    val feelsLikeF: Double,
    @SerializedName("windchill_c")
    val windchillC: Double,
    @SerializedName("windchill_f")
    val windchillF: Double,
    @SerializedName("heatindex_c")
    val heatIndexC: Double,
    @SerializedName("heatindex_f")
    val heatIndexF: Double,
    @SerializedName("dewpoint_c")
    val dewPointC: Double,
    @SerializedName("dewpoint_f")
    val dewPointF: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Double,
    val uv: Double,
    @SerializedName("gust_mph")
    val gustMph: Double,
    @SerializedName("gust_kph")
    val gustKph: Double,
)

data class ConditionResponse(
    val text: String,
    val icon: String,
    val code: Double,
)

data class ForecastResponse(
    @SerializedName("forecastday")
    val forecastDay: List<ForecastDayResponse>,
)

data class ForecastDayResponse(
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Double,
    val day: DayResponse,
    val astro: AstroResponse,
    val hour: List<HourResponse>,
)

data class DayResponse(
    @SerializedName("maxtemp_c")
    val maxTempC: Double,
    @SerializedName("maxtemp_f")
    val maxTempF: Double,
    @SerializedName("mintemp_c")
    val minTempC: Double,
    @SerializedName("mintemp_f")
    val minTempF: Double,
    @SerializedName("avgtemp_c")
    val avgTempC: Double,
    @SerializedName("avgtemp_f")
    val avgTempF: Double,
    @SerializedName("maxwind_mph")
    val maxWindMph: Double,
    @SerializedName("maxwind_kph")
    val maxWindKph: Double,
    @SerializedName("totalprecip_mm")
    val totalPrecipMm: Double,
    @SerializedName("totalprecip_in")
    val totalPrecipIn: Double,
    @SerializedName("totalsnow_cm")
    val totalSnowCm: Double,
    @SerializedName("avgvis_km")
    val avgVisKm: Double,
    @SerializedName("avgvis_miles")
    val avgVisMiles: Double,
    val avgHumidity: Double,
    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Double,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Double,
    @SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Double,
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Double,
    val condition: Condition2Response,
    val uv: Double,
)

data class Condition2Response(
    val text: String,
    val icon: String,
    val code: Double,
)

data class AstroResponse(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    @SerializedName("moonset")
    val moonSet: String,
    @SerializedName("moon_phase")
    val moonPhase: String,
    @SerializedName("moon_illumination")
    val moonIllumination: Double,
    @SerializedName("is_moon_up")
    val isMoonUp: Double,
    @SerializedName("is_sun_up")
    val isSunUp: Double,
)

data class HourResponse(
    @SerializedName("time_epoch")
    val timeEpoch: Double,
    val time: String,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("is_day")
    val isDay: Double,
    val condition: Condition3Response,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_degree")
    val windDegree: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("pressure_mb")
    val pressureMb: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("snow_cm")
    val snowCm: Double,
    val humidity: Double,
    val cloud: Double,
    @SerializedName("feelslike_c")
    val feelsLikeC: Double,
    @SerializedName("feelslike_f")
    val feelsLikeF: Double,
    @SerializedName("windchill_c")
    val windchillC: Double,
    @SerializedName("windchill_f")
    val windchillF: Double,
    @SerializedName("heatindex_c")
    val heatIndexC: Double,
    @SerializedName("heatindex_f")
    val heatIndexF: Double,
    @SerializedName("dewpoint_c")
    val dewPointC: Double,
    @SerializedName("dewpoint_f")
    val dewPointF: Double,
    @SerializedName("will_it_rain")
    val willItRain: Double,
    @SerializedName("chance_of_rain")
    val chanceOfRain: Double,
    @SerializedName("will_it_snow")
    val willItSnow: Double,
    @SerializedName("chance_of_snow")
    val chanceOfSnow: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Double,
    @SerializedName("gust_mph")
    val gustMph: Double,
    @SerializedName("gust_kph")
    val gustKph: Double,
    val uv: Double,
)

data class Condition3Response(
    val text: String,
    val icon: String,
    val code: Double,
)
