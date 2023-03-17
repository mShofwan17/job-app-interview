package interview.dansmultipro.core.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import interview.dansmultipro.core.Inflate

abstract class BasePagingAdapter<T : Any, VB : ViewBinding>(
    private val inflate: Inflate<VB>,
    baseDiffCallback: BaseDiffCallback<T>
) : PagingDataAdapter<T, BasePagingAdapter.BaseViewHolder<T, VB>>(baseDiffCallback) {

    abstract fun onBindData(data: T?, binding: VB, position: Int)

    protected val dataList = mutableListOf<T>()
    private var onItemClicked : (T?) -> Unit ={}
    fun setOnItemClicked(onItemClicked : (T?) -> Unit){
        this.onItemClicked = onItemClicked
    }


    class BaseViewHolder<T : Any, VB : ViewBinding>(
        private val binding: VB,
        private val adapter: BasePagingAdapter<T, VB>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: T?) {
            adapter.onBindData(data, binding, absoluteAdapterPosition).run {
                binding.root.setOnClickListener {
                    adapter.onItemClicked.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB> {
        val binding = inflate.invoke(
            LayoutInflater.from(parent.context), parent, false
        )
        return BaseViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        holder.onBind(getItem(position))
    }

    fun addLoadState(
        isLoading: (() -> Unit),
        isEmptyData: (() -> Unit?)? = null,
        isNotLoading: (() -> Unit)
    ){
       this.addLoadStateListener { loadState ->
           when(loadState.source.refresh){
               is LoadState.Loading -> isLoading.invoke()
               is LoadState.NotLoading -> {
                   isNotLoading.invoke()
                   if (loadState.append.endOfPaginationReached && this.itemCount==0) isEmptyData?.invoke()
               }
               else -> Unit
           }
       }
    }
}