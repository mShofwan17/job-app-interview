package interview.dansmultipro.feature_job.list_job

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import dagger.hilt.android.EntryPointAccessors
import interview.dansmultipro.core.module.ModuleDependencies
import interview.dansmultipro.feature_job.R
import interview.dansmultipro.feature_job.adapter.JobAdapter
import interview.dansmultipro.feature_job.databinding.DialogFilterJobBinding
import interview.dansmultipro.feature_job.databinding.FragmentListJobBinding
import interview.dansmultipro.jobapp.PagerFooterLoadAdapter
import interview.dansmultipro.jobapp.extension.setupQueryTextSubmit
import interview.dansmultipro.jobapp.extension.string
import interview.dansmultipro.jobapp.extension.visible
import interview.dansmultipro.source_job.constant.JobConstant.ID_JOB
import kotlinx.coroutines.flow.collectLatest
import launchWhenCreated
import interview.dansmultipro.core.base.BaseFragment
import showToast
import verticalLayoutManager
import javax.inject.Inject


class ListJobFragment : BaseFragment<FragmentListJobBinding>(
    FragmentListJobBinding::inflate
) {


    @Inject
    lateinit var viewModelFactory: ListJobViewModel.Factory
    private val viewModel by viewModels<ListJobViewModel> { viewModelFactory }

    @Inject lateinit var adapter: JobAdapter
    override fun injectComponent(context: Context) {
        DaggerListJobFragmentComponent.factory().create(
            EntryPointAccessors.fromApplication(context, ModuleDependencies::class.java)
        ).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getListData()

        collectListJob()
        collectSearch()
    }

    override fun initView() {
        setupRvAdapter()
        initSearch()
    }

    override fun initListener() {
        binding?.apply {
            imgFilter.setOnClickListener {
                if (edtSearch.query.isNotEmpty()){
                    showDialog()
                } else showToast("Isi Search")
            }

            edtSearch.setOnClickListener {
                edtSearch.isIconified = false
            }
        }
    }

    private fun initSearch(){
        binding?.edtSearch?.apply {
            setupQueryTextSubmit({
                adapter.submitData(lifecycle, PagingData.empty())
                viewModel.getSearchingJob(
                    description = it)
            }, {
                if (it.isNullOrEmpty()) getListData()
            })
        }
    }

    private fun setupRvAdapter(){
        binding?.apply {
            rvJob.visible()
            val linearLayoutManager =
                this@ListJobFragment.verticalLayoutManager()
            adapter.setOnItemClicked {  data ->
                data?.let {
                    val bundle = bundleOf(
                        ID_JOB to data.id
                    )
                    findNavController().navigate(R.id.action_jobFragment_to_jobDetailFragment, bundle)
                }
            }
            adapter.addLoadState(
                isLoading = {  },
                isNotLoading = {  }
            )

            rvJob.apply {
                layoutManager = linearLayoutManager
                adapter = this@ListJobFragment.adapter.withLoadStateFooter(
                    footer = PagerFooterLoadAdapter(
                        onRetryClicked = { this@ListJobFragment.adapter.retry() }
                    )
                )
            }


        }

    }

    private fun getListData(){
       launchWhenCreated {
           adapter.submitData(lifecycle, PagingData.empty())
           viewModel.getListJob()
       }
    }

    private fun showDialog(){
        val dialog = AlertDialog.Builder(requireContext())
        val bind = DialogFilterJobBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setView(bind.root)
        val alertDialog = dialog.create()

        var isFullTime = false

        bind.apply {
            switchFullTime.setOnCheckedChangeListener { _, b->
                isFullTime = b
            }

            btnSearch.setOnClickListener {
                adapter.submitData(lifecycle, PagingData.empty())
                viewModel.getSearchingJob(
                    description = binding?.edtSearch?.query.toString(),
                    location = bind.edtLocation.string(),
                    full_time = isFullTime
                )
                alertDialog.dismiss()
            }

        }

        alertDialog.show()
    }

    private fun collectSearch(){
        launchWhenCreated {
            viewModel.searchJob.collectLatest {
                it?.let {
                    adapter.submitData(lifecycle, PagingData.from(it))
                }
            }
        }
    }

    private fun collectListJob(){
        launchWhenCreated {
            viewModel.listJob.collectLatest {
                it?.let {
                    adapter.submitData(it)
                }
            }
        }
    }



}