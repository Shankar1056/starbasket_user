package apextechies.starbasket.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import java.util.ArrayList
import apextechies.starbasket.R
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import kotlinx.android.synthetic.main.dialog_address.*
import kotlinx.android.synthetic.main.dialog_address.view.*


@SuppressLint("ValidFragment")
class AddressDialog(private val addrss: AddressDataModel, private val position: Int) : DialogFragment(), View.OnClickListener {
    var retrofitDataProvider: RetrofitDataProvider?= null

    private val stateList = ArrayList<StateDataModel>()
    private val pincodeList = ArrayList<PinCodeDataModel>()

    private val address: AddressDataModel?
        get() {
            var isValid = true

            if (et_name.text.toString().length < 3) {
                til_name.setError(getString(R.string.invalid_name))
                isValid = false
            } else {
                til_name.setError(null)
            }

            if (et_address1.text.toString().trim().length < 5) {
                til_address1.setError(getString(R.string.invalid_address))
                isValid = false
            } else {
                til_address1.setError(null)
            }

            if (et_address2.getText().toString().trim().length < 5) {
                til_address2.setError(getString(R.string.invalid_address))
                isValid = false
            } else {
                til_address2.setError(null)
            }

            if (et_address2.getText().toString().trim().length < 5) {
                til_address2.setError(getString(R.string.invalid_address))
                isValid = false
            } else {
                til_address2.setError(null)
            }

            if ( et_landmark.getText().toString().trim().length < 5) {
                til_landmark.setError(getString(R.string.invalid_landmark))
                isValid = false
            } else {
                til_landmark.setError(null)
            }

            if (et_city.getText().toString().trim().length < 3) {
                til_city.setError(getString(R.string.invalid_city))
                isValid = false
            } else {
                til_city.setError(null)
            }



            if (isValid) {
                val address = arguments!!.getParcelable(ARG_ADDRESS) as AddressDataModel
                address.name = et_name.text.toString().trim()
                address.address1 = et_address1.text.toString().trim()
                address.address2 = et_address2.text.toString().trim()
                address.landmark = et_landmark.text.toString().trim()
                address.city = et_city.text.toString().trim()
                address.state_name = (stateList[acs_state.getSelectedItemPosition()].name)
                address.pincode = (pincodeList[acs_pncode.getSelectedItemPosition()].pincode)

                return address
            }

            return null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppThemeDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_address, container, false)
        retrofitDataProvider = RetrofitDataProvider((activity))
        view.findViewById<View>(R.id.tv_add).setOnClickListener(this)

        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.setGravity(Gravity.BOTTOM)

        view.et_name.setText(addrss.name)
        view.et_address1.setText(addrss.address1)
        view.et_address2.setText(addrss.address2)
        view.et_landmark.setText(addrss.landmark)
        view.et_city.setText(addrss.city)
//        view.et_pincode.setText(addrss.pincode)
        //view.stateList[position].name


    retrofitDataProvider!!.stateList(object : DownlodableCallback<StateModel> {
    override fun onSuccess(result: StateModel?) {
       val stateLst =  ArrayList<String>();
        if (result != null) {
            for (i in 0 until result.data!!.size) {
                stateList.add(result.data[i])
                stateLst.add(result.data[i].name!!);
            }
            acs_state.adapter = ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, stateLst);
            acs_state.setSelection(0);
        }
    }

    override fun onFailure(error: String?) {
    }

    override fun onUnauthorized(errorNumber: Int) {
    }
})
        retrofitDataProvider!!.getPincode(object : DownlodableCallback<PinCodeModel> {
    override fun onSuccess(result: PinCodeModel?) {
       val pincodeLst =  ArrayList<String>();
        if (result != null) {
            for (i in 0 until result.data!!.size) {
                pincodeList.add(result.data!![i])
                pincodeLst.add(result.data!![i].pincode!!);
            }
            acs_pncode.adapter = ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, pincodeLst);
            acs_pncode.setSelection(0)
        }
    }

    override fun onFailure(error: String?) {
    }

    override fun onUnauthorized(errorNumber: Int) {
    }
})

        init()
        return view
    }

    private fun init() {
        val address = arguments!!.getParcelable(ARG_ADDRESS) as AddressDataModel
        if (address!=null) {
            try {
                et_name.setText(address.name)
                et_address1.setText(address.address1)
                et_address2.setText(address.address2)
                et_landmark.setText(address.landmark)
                et_city.setText(address.city)
            }
            catch (e: Exception){

            }
        }
    }

    override fun onClick(v: View) {
        val address = address
        if (address != null) {
            dismiss()
            (activity as OnAddressListener).onAddAddress(address, arguments!!.getInt(ARG_POSITION, -1))
        }
    }


    interface OnAddressListener {
        fun onAddAddress(address: AddressDataModel, position: Int)
    }

    companion object {
        private val ARG_ADDRESS = "arg_address"
        private val ARG_POSITION = "arg_position"

        fun newInstance(address: AddressDataModel, position: Int): AddressDialog {
            val args = Bundle()
            args.putParcelable(ARG_ADDRESS, address)
            args.putInt(ARG_POSITION, position)
            val fragment = AddressDialog(address, position)
            fragment.arguments = args
            return fragment
        }
    }
}
