package interview.dansmultipro.feature_auth.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.EntryPointAccessors
import goBack
import interview.dansmultipro.core.base.BaseFragment
import interview.dansmultipro.core.model.User
import interview.dansmultipro.core.module.AuthDependencies
import interview.dansmultipro.core.module.ModuleDependencies
import interview.dansmultipro.feature_auth.databinding.FragmentLoginBinding
import interview.dansmultipro.jobapp.MainActivity
import interview.dansmultipro.jobapp.utils.ConfigPref
import showToast
import javax.inject.Inject


class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    @Inject
    lateinit var viewModelFactory: LoginViewModel.Factory
    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }
    @Inject lateinit var pref: ConfigPref
    @Inject lateinit var auth: FirebaseAuth
    companion object {
        const val SIGN_GOOGLE = 100
        const val SIGN_FACEBOOK = 101
    }
    override fun injectComponent(context: Context) {
        DaggerLoginFragmentComponent.factory().create(
            EntryPointAccessors.fromApplication(context, ModuleDependencies::class.java),
            EntryPointAccessors.fromApplication(context, AuthDependencies::class.java)
        ).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLogin()
        goBack { requireActivity().finish() }

    }

    override fun initView() {
       // viewModel.initSigninOption(getString(interview.dansmultipro.jobapp.R.string.google_clinet_id),requireContext())
    }
    override fun initListener() {
        super.initListener()
        binding?.apply {
            btnGoogle.setOnClickListener {
                viewModel.loginAsAnonymous()
            }

            btnFacebook.setOnClickListener {
              //  pref.setStatusLogin(true)
                MainActivity.open(requireActivity())
                requireActivity().finish()
            }
        }
    }

    private fun observeLogin(){
        viewModel.currentUser.observe(viewLifecycleOwner) {
            it?.let {
                pref.setStatusLogin(true)
                val user = User(
                    it.displayName,
                    it.photoUrl?.toString()
                )
                pref.saveUser(user)
                MainActivity.open(requireActivity())
                requireActivity().finish()
            }
        }
    }

    override fun observeErrorMessage() {
        viewModel.errorMsg.observe(viewLifecycleOwner){
            it?.let {
                showToast(it)
            }
        }
    }


}