package interview.dansmultipro.core.module

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ModuleDependencies {
    fun provideRetrofit() : Retrofit.Builder
    fun provideOkHttpBuilder() : OkHttpClient.Builder
    fun provideSharedPref() : SharedPreferences
    fun provideSharedPrefEdit() : SharedPreferences.Editor
}