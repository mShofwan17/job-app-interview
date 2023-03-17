package interview.dansmultipro.feature_job.detail_job

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.EntryPointAccessors
import interview.dansmultipro.core.model.Job
import interview.dansmultipro.core.module.ModuleDependencies
import interview.dansmultipro.feature_job.databinding.FragmentDetailJobBinding
import interview.dansmultipro.jobapp.extension.gone
import interview.dansmultipro.jobapp.extension.setImageUrl
import interview.dansmultipro.jobapp.extension.visible
import interview.dansmultipro.source_job.constant.JobConstant.ID_JOB
import kotlinx.coroutines.flow.collectLatest
import launchWhenCreated
import interview.dansmultipro.core.base.BaseFragment
import showToast
import javax.inject.Inject


class DetailJobFragment : BaseFragment<FragmentDetailJobBinding>(
    FragmentDetailJobBinding::inflate
) {
    @Inject
    lateinit var viewModelFactory: DetailJobViewModel.Factory
    private val viewModel by viewModels<DetailJobViewModel> { viewModelFactory }
    private var id :String? = ""
    override fun handleArgument(arguments: Bundle) {
        arguments.apply {
            id = arguments.getString(ID_JOB)
        }
    }
    override fun injectComponent(context: Context) {
        DaggerDetailJobFragmentComponent.factory().create(
            EntryPointAccessors.fromApplication(context, ModuleDependencies::class.java)
        ).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getJobDetail(id)
        collectJob()
        observeError()
    }
    override fun initView() {
        binding?.apply {
            txtWebsite.paintFlags = txtWebsite.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }

    private fun setupView(job : Job){
        binding?.apply {
            imgJob.setImageUrl(job.company_logo)
            txtCompany.text = job.company
            txtLocation.text = job.location
            txtWebsite.text = job.company_url

            txtTitle.text = job.title
            txtFulltime.text = job.type
            txtDesc.text = Html.fromHtml(job.description,Html.FROM_HTML_MODE_LEGACY)

            txtWebsite.setOnClickListener {
                val browserIntent =  Intent(Intent.ACTION_VIEW, Uri.parse(job.company_url))
                startActivity(browserIntent)
            }

            btnApply.setOnClickListener {
                val browserIntent =  Intent(Intent.ACTION_VIEW, Uri.parse(job.url))
                startActivity(browserIntent)
            }
        }
    }
    private fun collectJob(){
        launchWhenCreated {
            viewModel.job.collectLatest {
                it?.let {
                    setupView(it)
                }
            }
        }
    }

    override fun observeLoading() {
        viewModel.loading.observe(viewLifecycleOwner){
            if (it == true){
                binding?.mainContainer?.gone()
                binding?.progress?.visible()
            }else{
                binding?.progress?.gone()
                binding?.mainContainer?.visible()
            }
        }
    }

    private fun observeError(){
        viewModel.errorMsg.observe(viewLifecycleOwner){
            it?.let {
                showToast(it)
            }
        }
    }

}