package interview.dansmultipro.jobapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import interview.dansmultipro.jobapp.databinding.ActivityMainBinding
import interview.dansmultipro.jobapp.utils.ConfigPref
import interview.dansmultipro.jobapp.extension.gone
import interview.dansmultipro.jobapp.extension.setupWithDynamicNavController
import interview.dansmultipro.jobapp.extension.visible
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var currentNavController : NavController?=null
    @Inject lateinit var configPref: ConfigPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation(){
        binding.bottomNavMenu.apply {
            val controller = setupWithDynamicNavController(
                bottomMenuIds(), navGraphIds(), supportFragmentManager,
                R.id.fragment_container, intent
            ) { true }

            goToLogin(controller)
            controller.observe(this@MainActivity) {
                currentNavController = it
                it.addOnDestinationChangedListener{ _, destination, _ ->
                    when(destination.label){
                        getString(R.string.job_label), getString(R.string.account_label)
                        -> visible() else -> gone()
                    }
                }
            }

        }
    }
    private fun navGraphIds() : List<Int> {
        return listOf(
            R.navigation.home_navigation,
            R.navigation.account_navigation
        )
    }
    private fun bottomMenuIds() : List<Int> {
        return listOf(
            R.id.menu_job,
            R.id.menu_account
        )
    }

    private fun goToLogin(controller: LiveData<NavController>) {
        if (!configPref.getStatusLogin()) {
            controller.value?.navigate(
                R.id.action_to_auth
            )
        }
    }

    companion object {
        fun open(activity: Activity, bundle: Bundle?=null){
            val intent = Intent(activity, MainActivity::class.java)
            bundle?.let { intent.putExtras(it) }
            activity.startActivity(intent)
        }
    }
}