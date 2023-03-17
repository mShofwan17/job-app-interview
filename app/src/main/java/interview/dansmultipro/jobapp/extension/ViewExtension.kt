package interview.dansmultipro.jobapp.extension

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.bumptech.glide.Glide
import interview.dansmultipro.jobapp.R

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun EditText.string() : String{
    return this.text.toString()
}

fun EditText.empty() : Boolean{
    return this.string().isEmpty()
}

fun SearchView.setupQueryTextSubmit(
    onTextSubmit: (String?) -> Unit,
    onTextChange: (String?) -> Unit
) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            onTextSubmit.invoke(query)
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            onTextChange.invoke(query)
            return true
        }
    })
}

fun ImageView.setImageUrl(url : String?){
    Glide.with(this.context)
        .load(url)
        .error(R.drawable.gotoh)
        .into(this)
}