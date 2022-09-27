package com.ariefzuhri.gamedisc.di

import android.content.Context
import com.ariefzuhri.gamedisc.BuildConfig
import com.ariefzuhri.gamedisc.common.util.getHostName
import com.ariefzuhri.gamedisc.data.source.remote.network.ApiService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [LoggerModule::class])
@InstallIn(value = [SingletonComponent::class])
class NetworkModule {

    companion object {
        private const val NETWORK_TIMEOUT_SECONDS = 120L
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .build()
    }

    @Singleton
    @Provides
    fun provideCertificatePinner(): CertificatePinner {
        val hostName = BuildConfig.rawgBaseUrl.getHostName()
        return CertificatePinner.Builder()
            .add(hostName, BuildConfig.rawgPublicKey1)
            .add(hostName, BuildConfig.rawgPublicKey2)
            .add(hostName, BuildConfig.rawgPublicKey3)
            .add(hostName, BuildConfig.rawgPublicKey4)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        certificatePinner: CertificatePinner,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .certificatePinner(certificatePinner)
            .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .callTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.rawgBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}