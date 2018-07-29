package apextechies.starbasket.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.json.JSONException
import org.json.JSONObject

import java.io.Serializable

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 09 Aug 2017 at 4:36 PM
 */


class StateModel(
        @SerializedName ("status")
        val status: String?= null,
        @SerializedName ("msg")
        val msg: String?= null,
        @SerializedName ("data")
        val data: ArrayList<StateDataModel>?= null
)

@Parcelize
class StateDataModel(
    @SerializedName ("id")
    val id_state: String?= null,
    @SerializedName ("name")
    val name: String?= null
): Parcelable

