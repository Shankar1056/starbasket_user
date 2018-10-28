package apextechies.starbasket.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import apextechies.starbasket.R
import apextechies.starbasket.adapter.AddressAdapter
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.dialog.AddressDialog
import apextechies.starbasket.model.AddressDataModel
import apextechies.starbasket.model.AddressModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.toolbar.*

class AddressActivity:AppCompatActivity(), AddressDialog.OnAddressListener, AddressAdapter.OnItemClickListener {
    private var mAdapter: AddressAdapter? = null
    val EXTRA_DATA = "extra_data"
    var retrofitDataProvider:RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Select Your Address"
        retrofitDataProvider = RetrofitDataProvider(this)
        mAdapter = AddressAdapter(this)
        rv_address.layoutManager = LinearLayoutManager(this)
        rv_address.adapter = mAdapter

        if (intent.getStringExtra("from").equals("main")){
            tv_proceed_to_pay.text = "Save"
        }

        fab_add.setOnClickListener {
            val dialog = AddressDialog.newInstance(AddressDataModel(), -1)
            dialog.show(supportFragmentManager, null)
        }

        tv_proceed_to_pay.setOnClickListener {
            val intent = Intent()
            intent.putExtra(EXTRA_DATA, mAdapter!!.getSelectedAddress())
            setResult(RESULT_OK, intent)
            finish()
        }

        retrofitDataProvider!!.allAddress(ClsGeneral.getStrPreferences(this, AppConstants.USERID), object : DownlodableCallback<AddressModel> {
            override fun onSuccess(result: AddressModel?) {
                if (result!!.data!!.size>0){
                for (i in 0 until result!!.data!!.size) {
                    mAdapter!!.addItem(result.data!![i], this@AddressActivity)
                }
                    tv_empty.setVisibility(View.GONE)
                } else {
                    tv_empty.setVisibility(View.VISIBLE) }
            }
            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }
        })

        toolbarr.setNavigationOnClickListener {
            finish()
        }
    }


    override fun onDelete(item: AddressDataModel?, position: Int) {
        mAdapter!!.deleteItem(position)
        retrofitDataProvider!!.deleteAddress(item!!.address_id, object : DownlodableCallback<AddressModel> {
            override fun onSuccess(result: AddressModel?) {

                if (mAdapter!!.getItemCount() === 0) {
                    tv_empty.setVisibility(View.VISIBLE)
                }
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }

    override fun onUpdate(item: AddressDataModel?, position: Int) {
        val dialog = AddressDialog.newInstance(item!!, position)
        dialog.show(supportFragmentManager, null)
    }

    override fun onAddAddress(address: AddressDataModel, position: Int) {
        addAddressApi(address)
    }

    private fun addAddressApi(address: AddressDataModel) {
        retrofitDataProvider!!.addUpdateAddress(ClsGeneral.getStrPreferences(this, AppConstants.USERID),  address.address_id, /*address.state_id*/"1", address.pincode, address.address1, address.address2, address.name, address.city, address.landmark, object : DownlodableCallback<AddressModel> {
            override fun onSuccess(result: AddressModel?) {
                for (i in 0 until result!!.data!!.size) {
                    if (result != null) {
                        tv_empty.setVisibility(View.GONE)
                        mAdapter!!.addItem(result.data!![0], this@AddressActivity)
                    }
                }
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }
}