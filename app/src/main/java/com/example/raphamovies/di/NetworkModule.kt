package com.example.raphamovies.di

import com.example.raphamovies.appConstants
import com.example.raphamovies.network.NetworkResponseAdapterFactory
import com.example.raphamovies.network.TmdbApi
import com.example.raphamovies.repository.HomeDataSource
import com.example.raphamovies.viewmodel.DetailsViewModel
import com.example.raphamovies.viewmodel.HomeViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { providesInterceptor() }
    single { loggingClient(get()) }
    single { providesRetrofitInstance(get()) }
    single { tmdbApi(get()) }
}

    fun providesInterceptor(): Interceptor {
            return Interceptor { chain ->
                val newUrl = chain.request().url
                    .newBuilder()
                    .addQueryParameter("api_key", appConstants.TMDB_API_KEY)
                    .build()

                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }
        }

        fun loggingClient(authInterceptor: Interceptor): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .addNetworkInterceptor(authInterceptor)
                .build()
        }

        fun providesRetrofitInstance(logginClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(appConstants.TMDB_BASE_URL)
                .client(logginClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .build()
        }

        fun tmdbApi(retrofit: Retrofit): TmdbApi {
            return retrofit.create(TmdbApi::class.java)

    }
