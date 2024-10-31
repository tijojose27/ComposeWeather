package com.example.myweatherapp.data

import android.util.Log
import com.example.myweatherapp.data.model.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WeatherRepositoryImpl(
    private val api: APIService
): WeatherRepository {
    override suspend fun getWeather(): Flow<Result<WeatherData>> {
        return flow {
            val weatherFromAPI = try {
                api.getWeather()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "Error loading weather error loading "))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "Error loading weather HTTP exeception"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "Error loading weather exeception"))
                return@flow
            }
            emit(Result.Success(weatherFromAPI))
        }
    }

    override suspend fun getErrro(): Flow<Result<WeatherData>> {
        return flow {
            Log.e("DEBUG", "errro is got")
            emit(Result.Error(data = null, message = "ERROR"))
        }
    }

}