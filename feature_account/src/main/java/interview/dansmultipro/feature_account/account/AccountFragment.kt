package interview.dansmultipro.feature_account.account

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.EntryPointAccessors
import interview.dansmultipro.core.module.ModuleDependencies
import interview.dansmultipro.feature_account.databinding.FragmentAccountBinding
import interview.dansmultipro.jobapp.extension.setImageUrl
import interview.dansmultipro.jobapp.utils.ConfigPref
import interview.dansmultipro.core.base.BaseFragment
import showToast
import javax.inject.Inject


class AccountFragment : BaseFragment<FragmentAccountBinding>(
    FragmentAccountBinding::inflate
) {

    @Inject lateinit var configPref: ConfigPref
    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun injectComponent(context: Context) {
        DaggerAccountFragmentComponent.factory().create(
            EntryPointAccessors.fromApplication(context, ModuleDependencies::class.java)
        ).inject(this)
    }


    override fun initView() {
        val user = configPref.getUser()
        binding?.apply {
            imgProfile.setImageUrl(user?.photo)
            txtNama.text = user?.name
        }
    }

    override fun initListener() {
        binding?.apply {
            btnLogout.setOnClickListener {
                showToast("Berhasil Logout")
                configPref.saveUser(null)
                configPref.setStatusLogin(false)

                firebaseAuth.signOut()
                requireActivity().finish()
                requireActivity().startActivity(requireActivity().intent)
            }
        }
    }


}