package interview.dansmultipro.core.module

import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AuthDependencies {
    fun provideFirebaseAuth() : FirebaseAuth
}