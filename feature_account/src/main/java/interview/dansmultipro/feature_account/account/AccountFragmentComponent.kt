package interview.dansmultipro.feature_account.account

import dagger.Component
import dagger.hilt.android.scopes.FragmentScoped
import interview.dansmultipro.core.module.AuthDependencies
import interview.dansmultipro.core.module.ModuleDependencies

@Component(
    dependencies = [
        ModuleDependencies::class,
        AuthDependencies::class],
)

@FragmentScoped
interface AccountFragmentComponent {
    fun inject(fragment: AccountFragment)
    @Component.Factory
    interface Factory {
        fun create(
            coreDependencies: ModuleDependencies,
            authDependencies: AuthDependencies
        ): AccountFragmentComponent
    }
}