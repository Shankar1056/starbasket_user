package apextechies.starbasket.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList



class CheckoutModel {
    @SerializedName("price")
    var price: String? = null
    @SerializedName("delivery_charge")
    var delivery_charge: String? = null
    @SerializedName("discount")
    var discount: String? = null
    @SerializedName("coupon_code")
    var coupon_code: String? = null
    @SerializedName("total_price")
    var total_price: String? = null
    @SerializedName("address_id")
    var address_id: String? = null
    @SerializedName("user_id")
    var user_id: String? = null
    @SerializedName("payment_type")
    var payment_type: String? = null
    @SerializedName("order_product_history")
    var order_product_history: ArrayList<ProductDetailsModel>? = null


}

class ProductDetailsModel {
    @SerializedName("product_name")
    var product_name: String? = null
    @SerializedName("quantity")
    var quantity: String? = null
    @SerializedName("price")
    var price: String? = null
    @SerializedName("varient")
    var varient: String? = null
    @SerializedName("image")
    var image: String? = null
    @SerializedName("seller_id")
    var seller_id: String? = null
    constructor(product_name: String?, quantity: String?, price: String?, varient: String?, image: String?, seller_id: String?) {
        this.product_name = product_name
        this.quantity = quantity
        this.price = price
        this.varient = varient
        this.image = image
        this.seller_id = seller_id
    }
}
