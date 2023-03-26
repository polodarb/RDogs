package com.polodarb.rdogs.di

import com.polodarb.rdogs.data.remote.adapter.ResultAdapterFactory
import com.polodarb.rdogs.data.remote.api.ListBreedsAPI
import com.polodarb.rdogs.data.remote.api.PhotosBreedAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val serverUrl: String = "https://dog.ceo/api/"

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return createRetrofit(serverUrl, okHttpClient).build()
    }

    @Provides
    @Singleton
    @Named("listBreedsRetrofit")
    fun provideListBreedsRetrofit(retrofit: Retrofit): ListBreedsAPI {
        return retrofit.create(ListBreedsAPI::class.java)
    }

    @Provides
    @Singleton
    @Named("photosBreedRetrofit")
    fun providePhotosBreedRetrofit(retrofit: Retrofit): PhotosBreedAPI {
        return retrofit.create(PhotosBreedAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
//            .addInterceptor(Che)
            .build()
    }

    private fun createRetrofit(url: String, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(ResultAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}