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
    val localtimeEpoch: Long,
    val localtime: String,
)

data class CurrentModel(
    val lastUpdatedEpoch: Long,
    val lastUpdated: String,
    val tempC: Double,
    val tempF: Double,
    val isDay: Long,
    val condition: ConditionModel,
    val windMph: Double,
    val windKph: Double,
    val windDegree: Long,
    val windDir: String,
    val pressureMb: Long,
    val pressureIn: Double,
    val precipMm: Long,
    val precipIn: Long,
    val humidity: Long,
    val cloud: Long,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val windchillC: Double,
    val windchillF: Double,
    val heatIndexC: Double,
    val heatIndexF: Double,
    val dewPointC: Double,
    val dewPointF: Double,
    val visKm: Long,
    val visMiles: Long,
    val uv: Long,
    val gustMph: Double,
    val gustKph: Double,
)

data class ConditionModel(
    val text: String,
    val icon: String,
    val code: Long,
)

data class ForecastModel(
    val forecastDay: List<ForecastDayModel>,
)

data class ForecastDayModel(
    val date: String,
    val dateEpoch: Long,
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
    val totalSnowCm: Long,
    val avgVisKm: Double,
    val avgVisMiles: Long,
    val avgHumidity: Long,
    val dailyWillItRain: Long,
    val dailyChanceOfRain: Long,
    val dailyWillItSnow: Long,
    val dailyChanceOfSnow: Long,
    val condition: Condition2Model,
    val uv: Long,
)

data class Condition2Model(
    val text: String,
    val icon: String,
    val code: Long,
)

data class AstroModel(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonSet: String,
    val moonPhase: String,
    val moonIllumination: Long,
    val isMoonUp: Long,
    val isSunUp: Long,
)

data class HourModel(
    val timeEpoch: Long,
    val time: String,
    val tempC: Double,
    val tempF: Double,
    val isDay: Long,
    val condition: Condition3Model,
    val windMph: Double,
    val windKph: Double,
    val windDegree: Long,
    val windDir: String,
    val pressureMb: Long,
    val pressureIn: Double,
    val precipMm: Double,
    val precipIn: Double,
    val snowCm: Long,
    val humidity: Long,
    val cloud: Long,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val windchillC: Double,
    val windchillF: Double,
    val heatIndexC: Double,
    val heatIndexF: Double,
    val dewPointC: Double,
    val dewPointF: Double,
    val willItRain: Long,
    val chanceOfRain: Long,
    val willItSnow: Long,
    val chanceOfSnow: Long,
    val visKm: Double,
    val visMiles: Long,
    val gustMph: Double,
    val gustKph: Double,
    val uv: Long,
)

data class Condition3Model(
    val text: String,
    val icon: String,
    val code: Long,
)
