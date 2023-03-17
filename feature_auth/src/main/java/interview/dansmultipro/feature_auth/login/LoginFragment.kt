package interview.dansmultipro.feature_auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.EntryPointAccessors
import goBack
import interview.dansmultipro.core.model.User
import interview.dansmultipro.core.module.ModuleDependencies
import interview.dansmultipro.feature_auth.databinding.FragmentLoginBinding
import interview.dansmultipro.jobapp.MainActivity
import interview.dansmultipro.jobapp.utils.ConfigPref
import interview.dansmultipro.core.base.BaseFragment
import showToast
import javax.inject.Inject


class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    @Inject
    lateinit var viewModelFactory: LoginViewModel.Factory
    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }
    @Inject lateinit var pref: ConfigPref
    private val firebaseAuth = FirebaseAuth.getInstance()
    companion object {
        const val SIGN_GOOGLE = 100
        const val SIGN_FACEBOOK = 101
    }
    override fun injectComponent(context: Context) {
        DaggerLoginFragmentComponent.factory().create(
            EntryPointAccessors.fromApplication(context, ModuleDependencies::class.java)
        ).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack { requireActivity().finish() }

    }

    override fun initView() {
        viewModel.initSigninOption(getString(interview.dansmultipro.jobapp.R.string.google_clinet_id),requireContext())
    }
    override fun initListener() {
        super.initListener()
        binding?.apply {
            btnGoogle.setOnClickListener {
                viewModel.googleClient.observe(viewLifecycleOwner) {
                    it?.let {
                        val intent = it.getSignInIntent()
                        startActivityForResult(intent,SIGN_GOOGLE)
                    }
                }
            }

            btnFacebook.setOnClickListener {
              //  pref.setStatusLogin(true)
                MainActivity.open(requireActivity())
                requireActivity().finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            SIGN_GOOGLE -> {
                handleGoogleLogin(data)
            }
            SIGN_FACEBOOK -> {

            }
        }
    }

    private fun handleGoogleLogin(data:Intent?){
       val signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
       if (signInAccountTask.isSuccessful){
           try {
               val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
               googleSignInAccount?.let {
                   val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken,null)
                   firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener {
                       if (it.isSuccessful){
                           val firebaseUser = firebaseAuth.currentUser
                           val user = User(
                               name = firebaseUser?.displayName,
                               photo = firebaseUser?.photoUrl?.toString()
                           )

                           pref.saveUser(user)
                           pref.setStatusLogin(true)
                           MainActivity.open(requireActivity())
                           requireActivity().finish()
                       }else{
                           it.exception?.message?.let { it1 -> showToast(it1) }
                       }
                   }
               }
           } catch (e: ApiException){
               e.message?.let { showToast(it) }
           }
       }else{
           Log.d("handleGoogleLogin", "handleGoogleLogin: ${signInAccountTask.exception}")
           showToast("SHA-1 Debug laptop HP")

       }
    }

}