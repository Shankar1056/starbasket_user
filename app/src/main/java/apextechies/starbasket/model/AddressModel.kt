package apextechies.starbasket.model

import android.os.Parcelable
import apextechies.starbasket.R.string.*
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

import org.json.JSONException
import org.json.JSONObject

import java.io.Serializable


class AddressModel(

        @SerializedName("status")
        val status: String?= null,
        @SerializedName("msg")
        val msg: String?= null,
        @SerializedName("data")
        var data: ArrayList<AddressDataModel>?= null

)

@Parcelize
class AddressDataModel(
        @SerializedName("state_id")
        var state_id: String?= null,
        @SerializedName("state_name")
        var state_name: String?= null,
        @SerializedName("pincode")
        var pincode: String?= null,
        @SerializedName("address1")
        var address1: String?= null,
        @SerializedName("address2")
        var address2: String?= null,
        @SerializedName("name")
        var name: String?= null,
        @SerializedName("city")
        var city: String?= null,
        @SerializedName("landmark")
        var landmark: String?= null,
        @SerializedName("id")
        var address_id: String?= null,
        @SerializedName("user_id")
        var user_id: String?= null
): Parcelable
