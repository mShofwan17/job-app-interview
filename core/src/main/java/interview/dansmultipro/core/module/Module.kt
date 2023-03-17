package interview.dansmultipro.core.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import interview.dansmultipro.core.constant.ModuleConst.DEFAULT_TIME_OUT
import interview.dansmultipro.core.constant.ModuleConst.HEADER_ACCEPT
import interview.dansmultipro.core.constant.ModuleConst.HEADER_APP_JSON
import interview.dansmultipro.core.constant.ModuleConst.HEADER_CONTENT_TYPE
import interview.dansmultipro.core.constant.ModuleConst.SHARED_PREFERENCES
import interview.dansmultipro.core_models.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory
    ) : Retrofit.Builder {
        return Retrofit.Builder().addConverterFactory(gsonConverterFactory)
    }

    @Provides
    @Singleton
    fun provideGsonConverter() : GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient().create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        @HeaderInterceptor headerInterceptor: Interceptor,
        @ChuckInterceptor chuckInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient.Builder{
        return OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(headerInterceptor)
            if (BuildConfig.DEBUG) addInterceptor(chuckInterceptor)
            readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            callTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @HeaderInterceptor
    fun provideHeaderInterceptor() : Interceptor {
        return Interceptor { chain ->
            val request : Request = chain.request().newBuilder().apply {
                addHeader(HEADER_ACCEPT,HEADER_APP_JSON)
                addHeader(HEADER_CONTENT_TYPE, HEADER_APP_JSON)
            }.build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    @ChuckInterceptor
    fun provideChuck(
        @ApplicationContext context: Context
    ) : Interceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideSharedPref(
        application: Application
    ) : SharedPreferences {
        return application.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPrefEdit(
        sharedPreferences: SharedPreferences
    ) : SharedPreferences.Editor {
        return sharedPreferences.edit()
    }
}