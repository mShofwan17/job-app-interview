package interview.dansmultipro.feature_auth.login

import dagger.Component
import dagger.hilt.android.scopes.FragmentScoped
import interview.dansmultipro.core.module.ModuleDependencies

@Component(
    dependencies = [ModuleDependencies::class],
)

@FragmentScoped
interface LoginFragmentComponent {
    fun inject(fragment: LoginFragment)
    @Component.Factory
    interface Factory {
        fun create(
            coreDependencies: ModuleDependencies
        ): LoginFragmentComponent
    }
}