import com.juanfe.project.weatherapp.data.network.response.AstroResponse
import com.juanfe.project.weatherapp.data.network.response.Condition2Response
import com.juanfe.project.weatherapp.data.network.response.Condition3Response
import com.juanfe.project.weatherapp.data.network.response.ConditionResponse
import com.juanfe.project.weatherapp.data.network.response.CurrentResponse
import com.juanfe.project.weatherapp.data.network.response.DayResponse
import com.juanfe.project.weatherapp.data.network.response.ForecastDayResponse
import com.juanfe.project.weatherapp.data.network.response.ForecastResponse
import com.juanfe.project.weatherapp.data.network.response.HourResponse
import com.juanfe.project.weatherapp.data.network.response.LocationResponse
import com.juanfe.project.weatherapp.data.network.response.RootForecastResponse
import com.juanfe.project.weatherapp.data.network.response.SearchResponse
import com.juanfe.project.weatherapp.domain.AstroModel
import com.juanfe.project.weatherapp.domain.Condition2Model
import com.juanfe.project.weatherapp.domain.Condition3Model
import com.juanfe.project.weatherapp.domain.ConditionModel
import com.juanfe.project.weatherapp.domain.CurrentModel
import com.juanfe.project.weatherapp.domain.DayModel
import com.juanfe.project.weatherapp.domain.ForecastDayModel
import com.juanfe.project.weatherapp.domain.ForecastModel
import com.juanfe.project.weatherapp.domain.HourModel
import com.juanfe.project.weatherapp.domain.LocationModel
import com.juanfe.project.weatherapp.domain.RootForecastModel
import com.juanfe.project.weatherapp.domain.SearchModel

fun SearchResponse.toDomain() = SearchModel(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon
)

fun RootForecastResponse.toDomain() = RootForecastModel(
    location = location.toDomain(),
    current = current.toDomain(),
    forecast = forecast.toDomain()
)

fun LocationResponse.toDomain() = LocationModel(
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    tzId = tzId,
    localtimeEpoch = localtimeEpoch,
    localtime = localtime
)

fun CurrentResponse.toDomain() = CurrentModel(
    lastUpdatedEpoch = lastUpdatedEpoch,
    lastUpdated = lastUpdated,
    tempC = tempC,
    tempF = tempF,
    isDay = isDay,
    condition = condition.toDomain(),
    windMph = windMph,
    windKph = windKph,
    windDegree = windDegree,
    windDir = windDir,
    pressureMb = pressureMb,
    pressureIn = pressureIn,
    precipMm = precipMm,
    precipIn = precipIn,
    humidity = humidity,
    cloud = cloud,
    feelsLikeC = feelsLikeC,
    feelsLikeF = feelsLikeF,
    windchillC = windchillC,
    windchillF = windchillF,
    heatIndexC = heatIndexC,
    heatIndexF = heatIndexF,
    dewPointC = dewPointC,
    dewPointF = dewPointF,
    visKm = visKm,
    visMiles = visMiles,
    uv = uv,
    gustMph = gustMph,
    gustKph = gustKph
)

fun ConditionResponse.toDomain() = ConditionModel(
    text = text,
    icon = icon,
    code = code
)

fun ForecastResponse.toDomain() = ForecastModel(
    forecastDay = forecastDay.map { it.toDomain() }
)

fun ForecastDayResponse.toDomain() = ForecastDayModel(
    date = date,
    dateEpoch = dateEpoch,
    day = day.toDomain(),
    astro = astro.toDomain(),
    hour = hour.map { it.toDomain() }
)

fun DayResponse.toDomain() = DayModel(
    maxTempC = maxTempC,
    maxTempF = maxTempF,
    minTempC = minTempC,
    minTempF = minTempF,
    avgTempC = avgTempC,
    avgTempF = avgTempF,
    maxWindMph = maxWindMph,
    maxWindKph = maxWindKph,
    totalPrecipMm = totalPrecipMm,
    totalPrecipIn = totalPrecipIn,
    totalSnowCm = totalSnowCm,
    avgVisKm = avgVisKm,
    avgVisMiles = avgVisMiles,
    avgHumidity = avgHumidity,
    dailyWillItRain = dailyWillItRain,
    dailyChanceOfRain = dailyChanceOfRain,
    dailyWillItSnow = dailyWillItSnow,
    dailyChanceOfSnow = dailyChanceOfSnow,
    condition = condition.toDomain(),
    uv = uv
)

fun Condition2Response.toDomain() = Condition2Model(
    text = text,
    icon = icon,
    code = code
)

fun AstroResponse.toDomain() = AstroModel(
    sunrise = sunrise,
    sunset = sunset,
    moonrise = moonrise,
    moonSet = moonSet,
    moonPhase = moonPhase,
    moonIllumination = moonIllumination,
    isMoonUp = isMoonUp,
    isSunUp = isSunUp
)

fun HourResponse.toDomain() = HourModel(
    timeEpoch = timeEpoch,
    time = time,
    tempC = tempC,
    tempF = tempF,
    isDay = isDay,
    condition = condition.toDomain(),
    windMph = windMph,
    windKph = windKph,
    windDegree = windDegree,
    windDir = windDir,
    pressureMb = pressureMb,
    pressureIn = pressureIn,
    precipMm = precipMm,
    precipIn = precipIn,
    snowCm = snowCm,
    humidity = humidity,
    cloud = cloud,
    feelsLikeC = feelsLikeC,
    feelsLikeF = feelsLikeF,
    windchillC = windchillC,
    windchillF = windchillF,
    heatIndexC = heatIndexC,
    heatIndexF = heatIndexF,
    dewPointC = dewPointC,
    dewPointF = dewPointF,
    willItRain = willItRain,
    chanceOfRain = chanceOfRain,
    willItSnow = willItSnow,
    chanceOfSnow = chanceOfSnow,
    visKm = visKm,
    visMiles = visMiles,
    gustMph = gustMph,
    gustKph = gustKph,
    uv = uv
)

fun Condition3Response.toDomain() = Condition3Model(
    text = text,
    icon = icon,
    code = code
)
