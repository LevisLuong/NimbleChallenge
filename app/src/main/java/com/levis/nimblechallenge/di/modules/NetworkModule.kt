package com.levis.nimblechallenge.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.levis.nimblechallenge.BuildConfig
import com.levis.nimblechallenge.core.common.DATE_TIME_PATTERN_RESPONSE
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.data.network.adapter.AuthAuthenticator
import com.levis.nimblechallenge.data.network.adapter.AuthInterceptor
import com.levis.nimblechallenge.data.network.adapter.JsonApiResponseDeserializer
import com.levis.nimblechallenge.data.network.dtos.BaseDataResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setDateFormat(DATE_TIME_PATTERN_RESPONSE)
            .registerTypeAdapter(BaseDataResponse::class.java, JsonApiResponseDeserializer<Any>())
            .setLenient()
            .setPrettyPrinting()
            .create()

    @Provides
    @Singleton
    fun provideHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .authenticator(authAuthenticator)
            .addInterceptor(authInterceptor)
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logging)
        }

        return clientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .baseUrl(BuildConfig.APIUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}