package apextechies.starbasket.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class CartModel {

    @SerializedName("status")
val status: String?= null
    @SerializedName("msg")
val msg: String?= null
    @SerializedName("data")
val data: ArrayList<CartDataModel>?= null
}

@Parcelize
class CartDataModel(
        @SerializedName("id")
        val id: String?= null,
        @SerializedName("user_id")
        val user_id: String?= null,
        @SerializedName("product_id")
        val product_id: String?= null,
        @SerializedName("quantity")
        var quantity: String = "0",
        @SerializedName("name")
        val name: String?= null,
        @SerializedName("price")
        val price: String?= null,
        @SerializedName("image")
        val image: String?= null,
        @SerializedName("varient")
        val varient: String?= null
) : Parcelable
