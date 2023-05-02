package interview.dansmultipro.feature_auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import interview.dansmultipro.core.base.BaseViewModel
import interview.dansmultipro.core.base.BaseVmFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : BaseViewModel() {
/*    private var _googleClient = MutableLiveData<GoogleSignInClient>()
    val googleClient get() = _googleClient

    fun initSigninOption(idtoken: String,context: Context){
        val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(idtoken)
            .requestEmail()
            .build()

        _googleClient.value = GoogleSignIn.getClient(context,googleSignInOptions)

    }*/

    private var _currentUser = MutableLiveData<FirebaseUser?>(null)
    val currentUser get() = _currentUser

    fun loginAsAnonymous() {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                _currentUser.value = user
            } else {
                setErrorMessage(task.exception?.message)
            }
        }
    }


    class Factory @Inject constructor(
        auth: FirebaseAuth
    ) : BaseVmFactory(
        LoginViewModel(
            auth
        )
    )
}