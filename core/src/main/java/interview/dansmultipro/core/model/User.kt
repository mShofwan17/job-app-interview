package interview.dansmultipro.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name :String?,
    val photo :String?
) : Parcelable
