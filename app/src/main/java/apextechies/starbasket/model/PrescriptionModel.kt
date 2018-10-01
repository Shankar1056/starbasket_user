package apextechies.starbasket.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class PrescriptionModel {

    @SerializedName("status")
var status: String?= null
    @SerializedName("msg")
var msg: String?= null
    @SerializedName("data")
var data: ArrayList<PrescriptionDataModel>?= null


}

@Parcelize
class PrescriptionDataModel(

        @SerializedName("id")
        var id: String?= null,
        @SerializedName("user_id")
        var user_id: String?= null,
        @SerializedName("prescription")
        var prescription: String?= null,
        @SerializedName("order_date")
        var order_date: String?= null,
        @SerializedName("order_status")
        var order_status: String?= null,
        @SerializedName("status")
        var status: String?= null,
        @SerializedName("transaction_id")
        var transaction_id: String?= null

) : Parcelable