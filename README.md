## WeatherForecast

An application that shows tomorrow's weather forecast for specific cities. The app is written in Kotlin using the MVVM desing pattern. Databse contains a pre-filled table for locations that are shown in FragmentLocations and a table for forecasts that are show in FragmentForecast. Forecasts are retrieved from MetaWeather API and filtered by date, so only the tomorrow's forecast is stored into the database.  

## Libraries

- Dagger 2
- Navigation component
- Retrofit
- Room
- RxJava 2
- Glide
- Mockito
- JUnit

## Testing

The project contains  [Local unit tests][2] and [Instrumented tests][3].

Just run `./gradlew test` or `./gradlew connectedAndroidTest`

[2]: app/src/test/java/com/example/weatherforecast/
[3]: app/src/androidTest/java/com/example/weatherforecast/
