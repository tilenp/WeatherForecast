## WeatherForecast

An application that shows tomorrow's weather forecast for specific cities. The app is written in Kotlin and using the MVVM desing pattern.

## API Key

Create an [API key][1].

Add your API key to `API_KEY = "your_api_key"` in `Constants.kt` file.

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
