package com.example.myapplication.di

import com.example.myapplication.data.services.BMIApiService
import com.example.myapplication.data.services.MenuApiService
import com.example.myapplication.data.services.WorkoutApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val apiKey = ""

    @Provides
    @Singleton
    fun provideWorkoutApiService(): WorkoutApiService {
        val host = "exercisedb.p.rapidapi.com"
        val baseUrl = "https://exercisedb.p.rapidapi.com"

        val interceptor = getInterceptor(host)
        val httpClient = getHttpClient(interceptor)
        val retrofit = getRetrofit(baseUrl,httpClient)

        return retrofit.create(WorkoutApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMenuApiService(): MenuApiService {

        val host = "keto-diet.p.rapidapi.com"
        val baseUrl = "https://keto-diet.p.rapidapi.com"

        val interceptor = getInterceptor(host)
        val httpClient = getHttpClient(interceptor)
        val retrofit = getRetrofit(baseUrl,httpClient)

        return retrofit.create(MenuApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideBMIApiService(): BMIApiService {

        val host = "bmi-calculator6.p.rapidapi.com"
        val baseUrl = "https://bmi-calculator6.p.rapidapi.com"

        val interceptor = getInterceptor(host)
        val httpClient = getHttpClient(interceptor)
        val retrofit = getRetrofit(baseUrl,httpClient)

        return retrofit.create(BMIApiService::class.java)
    }

    private fun getInterceptor(host : String) = Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", apiKey)
            .addHeader("X-RapidAPI-Host", host)
            .build()
        chain.proceed(request)
    }

    private fun getRetrofit(baseUrl : String , httpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    private fun getHttpClient(interceptor: Interceptor) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}
