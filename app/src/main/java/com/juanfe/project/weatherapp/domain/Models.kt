package com.juanfe.project.weatherapp.domain

data class SearchModel(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)

data class RootForecastModel(
    val location: LocationModel,
    val current: CurrentModel,
    val forecast: ForecastModel,
)

data class LocationModel(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tzId: String,
    val localtimeEpoch: Double,
    val localtime: String,
)

data class CurrentModel(
    val lastUpdatedEpoch: Double,
    val lastUpdated: String,
    val tempC: Double,
    val tempF: Double,
    val isDay: Double,
    val condition: ConditionModel,
    val windMph: Double,
    val windKph: Double,
    val windDegree: Double,
    val windDir: String,
    val pressureMb: Double,
    val pressureIn: Double,
    val precipMm: Double,
    val precipIn: Double,
    val humidity: Double,
    val cloud: Double,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val windchillC: Double,
    val windchillF: Double,
    val heatIndexC: Double,
    val heatIndexF: Double,
    val dewPointC: Double,
    val dewPointF: Double,
    val visKm: Double,
    val visMiles: Double,
    val uv: Double,
    val gustMph: Double,
    val gustKph: Double,
)

data class ConditionModel(
    val text: String,
    val icon: String,
    val code: Double,
)

data class ForecastModel(
    val forecastDay: List<ForecastDayModel>,
)

data class ForecastDayModel(
    val date: String,
    val dateEpoch: Double,
    val day: DayModel,
    val astro: AstroModel,
    val hour: List<HourModel>,
)

data class DayModel(
    val maxTempC: Double,
    val maxTempF: Double,
    val minTempC: Double,
    val minTempF: Double,
    val avgTempC: Double,
    val avgTempF: Double,
    val maxWindMph: Double,
    val maxWindKph: Double,
    val totalPrecipMm: Double,
    val totalPrecipIn: Double,
    val totalSnowCm: Double,
    val avgVisKm: Double,
    val avgVisMiles: Double,
    val avgHumidity: Double,
    val dailyWillItRain: Double,
    val dailyChanceOfRain: Double,
    val dailyWillItSnow: Double,
    val dailyChanceOfSnow: Double,
    val condition: Condition2Model,
    val uv: Double,
)

data class Condition2Model(
    val text: String,
    val icon: String,
    val code: Double,
)

data class AstroModel(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonSet: String,
    val moonPhase: String,
    val moonIllumination: Double,
    val isMoonUp: Double,
    val isSunUp: Double,
)

data class HourModel(
    val timeEpoch: Double,
    val time: String,
    val tempC: Double,
    val tempF: Double,
    val isDay: Double,
    val condition: Condition3Model,
    val windMph: Double,
    val windKph: Double,
    val windDegree: Double,
    val windDir: String,
    val pressureMb: Double,
    val pressureIn: Double,
    val precipMm: Double,
    val precipIn: Double,
    val snowCm: Double,
    val humidity: Double,
    val cloud: Double,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val windchillC: Double,
    val windchillF: Double,
    val heatIndexC: Double,
    val heatIndexF: Double,
    val dewPointC: Double,
    val dewPointF: Double,
    val willItRain: Double,
    val chanceOfRain: Double,
    val willItSnow: Double,
    val chanceOfSnow: Double,
    val visKm: Double,
    val visMiles: Double,
    val gustMph: Double,
    val gustKph: Double,
    val uv: Double,
)

data class Condition3Model(
    val text: String,
    val icon: String,
    val code: Double,
)

data class WeatherDetailModel(
    val title: String,
    val description: String,
    val typeIcon: TypeDetail
)


enum class TypeDetail{
    PRESSURE,
    HUMIDITY,
    WIND,
    VIS,
    DEWPOINT,
    UV
}