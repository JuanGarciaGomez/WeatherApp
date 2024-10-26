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
    val localtimeEpoch: Long,
    val localtime: String,
)

data class CurrentResponse(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("is_day")
    val isDay: Long,
    val condition: ConditionResponse,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_degree")
    val windDegree: Long,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("pressure_mb")
    val pressureMb: Long,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Long,
    @SerializedName("precip_in")
    val precipIn: Long,
    val humidity: Long,
    val cloud: Long,
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
    val visKm: Long,
    @SerializedName("vis_miles")
    val visMiles: Long,
    val uv: Long,
    @SerializedName("gust_mph")
    val gustMph: Double,
    @SerializedName("gust_kph")
    val gustKph: Double,
)

data class ConditionResponse(
    val text: String,
    val icon: String,
    val code: Long,
)

data class ForecastResponse(
    @SerializedName("forecastday")
    val forecastDay: List<ForecastDayResponse>,
)

data class ForecastDayResponse(
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Long,
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
    val totalSnowCm: Long,
    @SerializedName("avgvis_km")
    val avgVisKm: Double,
    @SerializedName("avgvis_miles")
    val avgVisMiles: Long,
    val avgHumidity: Long,
    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Long,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Long,
    @SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Long,
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Long,
    val condition: Condition2Response,
    val uv: Long,
)

data class Condition2Response(
    val text: String,
    val icon: String,
    val code: Long,
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
    val moonIllumination: Long,
    @SerializedName("is_moon_up")
    val isMoonUp: Long,
    @SerializedName("is_sun_up")
    val isSunUp: Long,
)

data class HourResponse(
    @SerializedName("time_epoch")
    val timeEpoch: Long,
    val time: String,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("is_day")
    val isDay: Long,
    val condition: Condition3Response,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_degree")
    val windDegree: Long,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("pressure_mb")
    val pressureMb: Long,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("snow_cm")
    val snowCm: Long,
    val humidity: Long,
    val cloud: Long,
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
    val willItRain: Long,
    @SerializedName("chance_of_rain")
    val chanceOfRain: Long,
    @SerializedName("will_it_snow")
    val willItSnow: Long,
    @SerializedName("chance_of_snow")
    val chanceOfSnow: Long,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Long,
    @SerializedName("gust_mph")
    val gustMph: Double,
    @SerializedName("gust_kph")
    val gustKph: Double,
    val uv: Long,
)

data class Condition3Response(
    val text: String,
    val icon: String,
    val code: Long,
)
