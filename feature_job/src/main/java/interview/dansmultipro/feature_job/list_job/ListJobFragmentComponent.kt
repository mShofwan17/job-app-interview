package interview.dansmultipro.feature_job.list_job

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
interface ListJobFragmentComponent {
    fun inject(fragment: ListJobFragment)
    @Component.Factory
    interface Factory {
        fun create(
            coreDependencies: ModuleDependencies
        ): ListJobFragmentComponent
    }
}