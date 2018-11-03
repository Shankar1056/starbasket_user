package apextechies.starbasket.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class ProductModel {

    @SerializedName("status")
    val status: String? = null
    @SerializedName("msg")
    val msg: String? = null
    @SerializedName("data")
    val data: ArrayList<ProductDataModel>? = null
}

@Parcelize
class ProductDataModel(
        @SerializedName("iswishlist")
        var iswishlist: Boolean = false,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("sub_cat_id")
        val sub_cat_id: String? = null,
        @SerializedName("sub_sub_cat_id")
        val sub_sub_cat_id: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("created_date")
        val created_date: String? = null,
        @SerializedName("seller_id")
        val seller_id: String? = null,
        var selectedIndes: Int? = 0,
        @SerializedName("unitdetails")
        val unitdetails: ArrayList<UnitDetailsModel>? = null,
        @SerializedName("sellerdetails")
        val sellerdetails: SellerDetailsModel? = null
) : Parcelable

@Parcelize
class SellerDetailsModel(
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("mobile_number")
        val mobile_number: String? = null,
        @SerializedName("email_id")
        val email_id: String? = null,
        @SerializedName("business_name")
        val business_name: String? = null,
        @SerializedName("status")
        val status: String? = null,
        @SerializedName("created_date")
        val created_date: String? = null
) : Parcelable

@Parcelize
class UnitDetailsModel(
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("prod_id")
        val prod_id: String? = null,
        @SerializedName("unit")
        var unit: String? = null,
        @SerializedName("varient")
        var varient: String? = null,
        @SerializedName("actual_price")
        val actual_price: String? = null,
        @SerializedName("selling_price")
        val selling_price: String? = null,
        @SerializedName("discount")
        val discount: String? = null,
        @SerializedName("short_description")
        val short_description: String? = null,
        @SerializedName("full_description")
        val full_description: String? = null,
        @SerializedName("created_date")
        val created_date: String? = null,
        @SerializedName("status")
        val status: String? = null
) : Parcelable

