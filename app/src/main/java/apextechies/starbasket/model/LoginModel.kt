package apextechies.starbasket.model

import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("status")
var status: String?= null
    @SerializedName("msg")
var msg: String?= null
    @SerializedName("data")
var data: ArrayList<LoginDataModel>?= null
}

class LoginDataModel {

    @SerializedName("id")
var id: String?= null
    @SerializedName("name")
var name: String?= null
    @SerializedName("last_name")
var last_name: String?= null
    @SerializedName("email")
var email: String?= null
    @SerializedName("mobile")
var mobile: String?= null
    @SerializedName("address")
var address: String?= null
    @SerializedName("device_token")
var device_token: String?= null
    @SerializedName("created_at")
var created_at: String?= null
    @SerializedName("status")
var status: String?= null
}
