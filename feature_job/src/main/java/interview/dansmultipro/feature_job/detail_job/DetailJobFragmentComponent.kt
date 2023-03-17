package interview.dansmultipro.feature_job.detail_job

import dagger.Component
import dagger.hilt.android.scopes.FragmentScoped
import interview.dansmultipro.core.module.ModuleDependencies
import interview.dansmultipro.source_job.module.JobModule

@Component(
    dependencies = [ModuleDependencies::class],
    modules = [
        JobModule::class
    ]
)

@FragmentScoped
interface DetailJobFragmentComponent {
    fun inject(fragment: DetailJobFragment)
    @Component.Factory
    interface Factory {
        fun create(
            coreDependencies: ModuleDependencies
        ): DetailJobFragmentComponent
    }
}