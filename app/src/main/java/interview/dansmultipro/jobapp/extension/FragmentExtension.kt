import android.app.Activity
import android.content.DialogInterface
import android.net.Uri
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

fun Fragment.launchWhenResumed(callback: suspend () -> Unit) {
    this.lifecycleScope.launchWhenResumed {
        callback.invoke()
    }
}

fun Fragment.launchWhenCreated(callback: suspend () -> Unit) {
    this.lifecycleScope.launchWhenCreated {
        callback.invoke()
    }
}

fun Fragment.launchWhenStarted(callback: () -> Unit) {
    this.lifecycleScope.launchWhenStarted {
        callback.invoke()
    }
}

fun Fragment.horizontalLayoutManager(reverseLayout: Boolean = false) =
    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, reverseLayout)

fun Fragment.verticalLayoutManager(reverseLayout: Boolean = false) =
    LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, reverseLayout)

fun Fragment.goBack(callback: () -> Unit){
    requireActivity()
        .onBackPressedDispatcher
        .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                // Do custom work here

                // if you want onBackPressed() to be called as normal afterwards
                if (isEnabled) {
                    isEnabled = false

                    callback.invoke()
                }
            }
        })
}

fun Fragment.showInfoDialog(
    title: String,
    message: String,
    icon: Int?,
    positiveButtonTitle: String? = "Selesai",
    positiveAction: ((DialogInterface) -> Unit)? = null
) {
    AlertDialog.Builder(this.requireContext())
        .apply {
            setTitle(title)
            setMessage(message)
            icon.let { icon }
            setPositiveButton(positiveButtonTitle) { dialog, _ ->
                dialog.dismiss()
                positiveAction?.invoke(dialog)
            }
            show()
        }
}

fun Fragment.showChoiceDialog(
    title: String,
    message: String,
    icon: Int?,
    positiveAction: (DialogInterface) -> Unit,
    negativeAction: (DialogInterface) -> Unit
) {
    AlertDialog.Builder(this.requireContext())
        .apply {
            setTitle(title)
            setMessage(message)
            icon.let { icon }
            setPositiveButton(
                "Selesai"
            ) { dialog, _ ->
                positiveAction.invoke(dialog)
            }
            setNegativeButton("Tidak") { dialog, _ ->
                negativeAction.invoke(dialog)
            }
            show()
        }
}

fun Fragment.showToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this.requireContext(), message, duration).show()
}

fun ActivityResult?.getResult(
    result: (Uri?, path: String?) -> Unit,
) {
    if (this?.resultCode == Activity.RESULT_OK) {
        val uri = this.data?.data
        result.invoke(uri, uri?.path)
    }
}
