package interview.dansmultipro.core.paging

import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallback<T : Any>(
    private val areContentTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val areItemTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val getChangePayloadCallback: ((oldItem: T, newItem: T) -> Any?)? = null
) :
    DiffUtil.ItemCallback<T>() {

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentTheSame(oldItem, newItem)
    }

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return areItemTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        return if (getChangePayloadCallback == null) super.getChangePayload(oldItem, newItem)
        else getChangePayloadCallback.invoke(oldItem, newItem)

    }

}