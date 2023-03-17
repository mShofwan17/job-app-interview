package interview.dansmultipro.core.network

import interview.dansmultipro.core_models.BuildConfig
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object NetworkProvider {

    fun configRetrofit(
        baseUrl : String? = BuildConfig.BASE_URL,
        authenticator: Authenticator?,
        okHttpClient: OkHttpClient.Builder,
        retrofit: Retrofit.Builder
    ) : Retrofit {
        return  retrofit.run {
            authenticator?.let { okHttpClient.authenticator(authenticator) }
            client(okHttpClient.build())
            baseUrl(baseUrl).build()
        }
    }
}