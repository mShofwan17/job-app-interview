package interview.dansmultipro.source_job.module

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import interview.dansmultipro.core.network.NetworkProvider
import interview.dansmultipro.source_job.repository.IJobRepository
import interview.dansmultipro.source_job.repository.JobRepository
import interview.dansmultipro.source_job.services.JobServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit


@dagger.Module
@InstallIn(FragmentComponent::class)
object JobModule {
    @Provides
    @FragmentScoped
    fun provideService(
        okHttpClient: OkHttpClient.Builder,
        retrofit: Retrofit.Builder
    ) : JobServices {
        return NetworkProvider.configRetrofit(
            authenticator = null,
            okHttpClient = okHttpClient,
            retrofit = retrofit
        ).create(JobServices::class.java)
    }

    @Provides
    @FragmentScoped
    fun provideRepository(
        services: JobServices
    ) : IJobRepository {
        return JobRepository(services)
    }

}