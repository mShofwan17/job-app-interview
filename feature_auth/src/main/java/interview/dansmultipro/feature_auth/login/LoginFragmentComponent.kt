package interview.dansmultipro.feature_auth.login

import dagger.Component
import dagger.hilt.android.scopes.FragmentScoped
import interview.dansmultipro.core.module.AuthDependencies
import interview.dansmultipro.core.module.ModuleDependencies

@Component(
    dependencies = [ModuleDependencies::class,
        AuthDependencies::class],
)

@FragmentScoped
interface LoginFragmentComponent {
    fun inject(fragment: LoginFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreDependencies: ModuleDependencies,
            authDependencies: AuthDependencies
        ): LoginFragmentComponent
    }
}