package apextechies.starbasket.model

import com.google.gson.annotations.SerializedName

class PinCodeModel(
    @SerializedName("status")
    val status: String?= null,
    @SerializedName("msg")
    val msg: String?= null,
    @SerializedName("data")
    var data: ArrayList<PinCodeDataModel>?= null
)

class PinCodeDataModel(
    @SerializedName("id")
    val id: String?= null,
    @SerializedName("pincode")
    val pincode: String?= null,
    @SerializedName("status")
    var status: String?= null
)
