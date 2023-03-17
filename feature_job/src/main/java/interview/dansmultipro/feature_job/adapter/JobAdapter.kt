package interview.dansmultipro.feature_job.adapter

import interview.dansmultipro.core.model.Job
import interview.dansmultipro.core.paging.BaseDiffCallback
import interview.dansmultipro.core.paging.BasePagingAdapter
import interview.dansmultipro.feature_job.databinding.ItemListJobBinding
import interview.dansmultipro.jobapp.extension.gone
import interview.dansmultipro.jobapp.extension.setImageUrl
import javax.inject.Inject

class JobAdapter @Inject constructor() : BasePagingAdapter<Job, ItemListJobBinding>(
    ItemListJobBinding::inflate,
    BaseDiffCallback(
        { oldItem, newItem ->  oldItem == newItem },
        { oldItem, newItem ->  oldItem.id == newItem.id }
    )
) {

    override fun onBindData(data: Job?, binding: ItemListJobBinding, position: Int) {
        data?.let {
            binding.apply {
                txtJob.text = data.title
                txtCompany.text = data.company
                txtLocation.text = data.location

                imgJob.setImageUrl(data.company_logo)
            }
        }

    }
}