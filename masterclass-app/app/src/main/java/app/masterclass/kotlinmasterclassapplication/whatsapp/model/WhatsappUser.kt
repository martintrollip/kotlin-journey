package app.masterclass.kotlinmasterclassapplication.whatsapp.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Martin Trollip
 */
data class WhatsappUser(
    var uid: String = "",
    var displayName: String = "",
    var status: String = "Hello there!",
    var image: String = "default"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(displayName)
        parcel.writeString(status)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WhatsappUser> {
        override fun createFromParcel(parcel: Parcel): WhatsappUser {
            return WhatsappUser(parcel)
        }

        override fun newArray(size: Int): Array<WhatsappUser?> {
            return arrayOfNulls(size)
        }
    }
}