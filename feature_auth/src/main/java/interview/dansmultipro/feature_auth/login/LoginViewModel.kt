package interview.dansmultipro.feature_auth.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import interview.dansmultipro.core.base.BaseVmFactory
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {
    private var _googleClient = MutableLiveData<GoogleSignInClient>()
    val googleClient get() = _googleClient

    fun initSigninOption(idtoken: String,context: Context){
        val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(idtoken)
            .requestEmail()
            .build()

        _googleClient.value = GoogleSignIn.getClient(context,googleSignInOptions)

    }

    class Factory @Inject constructor(
    ) : BaseVmFactory(
        LoginViewModel()
    )
}