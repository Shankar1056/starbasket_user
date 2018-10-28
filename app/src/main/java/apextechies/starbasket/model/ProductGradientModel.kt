package apextechies.starbasket.model

import com.google.gson.annotations.SerializedName

class ProductGradientModel(

        @SerializedName("status")
        val status: String? = null,
        @SerializedName("msg")
        val msg: String? = null,
        @SerializedName("data")
        val data: ArrayList<ProductGradientDataModel>? = null

)

class ProductGradientDataModel (
        @SerializedName("id")
        var id: String?= null,
        @SerializedName("prod_id")
        var prod_id: String?= null,
        @SerializedName("about_product")
        var about_product: String?= null,
        @SerializedName("product_ingradient")
        var product_ingradient: String?= null,
        @SerializedName("product_nuetrition")
        var product_nuetrition: String?= null,
        @SerializedName("status")
        var status: String?= null
)