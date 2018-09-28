package apextechies.starbasket.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

class UserOrderListModel {
    @SerializedName("status")
    var status: String? = null
    @SerializedName("msg")
    var msg: String? = null
    @SerializedName("data")
    var data: ArrayList<UserOrderDataListModel>? = null
}

@Parcelize
class UserOrderDataListModel(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("transaction_id")
    var transaction_id: String? = null,
    @SerializedName("price")
    var price: String? = null,
    @SerializedName("delivery_charge")
    var delivery_charge: String? = null,
    @SerializedName("discount")
    var discount: String? = null,
    @SerializedName("coupon_code")
    var coupon_code: String? = null,
    @SerializedName("total_price")
    var total_price: String? = null,
    @SerializedName("address_id")
    var address_id: String? = null,
    @SerializedName("user_id")
    var user_id: String? = null,
    @SerializedName("order_date")
    var order_date: String? = null,
    @SerializedName("order_status")
    var order_status: String? = null,
    @SerializedName("payment_type")
    var payment_type: String? = null,
    @SerializedName("varientdetails")
    var varientdetails: ArrayList<ProductVarientModel>? = null
) : Parcelable

@Parcelize
class ProductVarientModel(
    @SerializedName("product_name")
    var product_name: String? = null,
    @SerializedName("quantity")
    var quantity: String? = null,
    @SerializedName("price")
    var price: String? = null,
    @SerializedName("varient")
    var varient: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("seller_id")
    var seller_id: String? = null

) : Parcelable
