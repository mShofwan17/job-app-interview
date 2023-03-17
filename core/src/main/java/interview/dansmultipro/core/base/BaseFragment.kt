package interview.dansmultipro.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import interview.dansmultipro.core.Inflate

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate : Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding

    abstract fun injectComponent(context: Context)
    open fun handleArgument(arguments : Bundle) {}

    open fun initListener(){ }

    open fun initView(){ }

    open fun getData(){ }

    open fun observeLoading(){ }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectComponent(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            handleArgument(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        getData()
        observeLoading()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}