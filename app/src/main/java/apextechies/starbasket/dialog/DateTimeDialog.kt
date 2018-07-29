package apextechies.starbasket.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager


import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import apextechies.starbasket.R
import apextechies.starbasket.adapter.DateAdapter
import apextechies.starbasket.adapter.TimeAdapter
import apextechies.starbasket.model.DateModel
import apextechies.starbasket.model.TimeModel
import kotlinx.android.synthetic.main.dialog_date_time.*


class DateTimeDialog : DialogFragment(), DateAdapter.OnItemClickListener {
    private var mDateAdapter: DateAdapter? = null
    private var mTimeAdapter: TimeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppThemeDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_date_time, container, false)

        mDateAdapter = DateAdapter(this)
        rv_date.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
        rv_date.adapter = mDateAdapter

        mTimeAdapter = TimeAdapter()
        rv_time.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
        rv_time.adapter = mTimeAdapter

        tv_cancel.setOnClickListener {
            dismiss()
        }

        tv_done.setOnClickListener {
            dismiss()
            (activity as OnDateTimeListener)
                    .onDateTime(mDateAdapter!!.selectedDate, mTimeAdapter!!.selectedTime)
        }

        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.setGravity(Gravity.BOTTOM)

        /*ApiTask.builder(getContext())
			.setGET()
			.setUrl(ApiUrl.TIME_SLOT)
			.setResponseListener(this)
			.setProgressMessage(R.string.fetching_time_slots)
			.exec();*/

        return view
    }

    /*@Override
	public void onSuccess(JSONObject response, int requestCode, Bundle savedData) throws JSONException {
		JSONArray data = response.getJSONArray("data");
		for (int i = 0; i < data.length(); i++) {
			mTimeAdapter.addItem(new TimeModel(data.getJSONObject(i)));
		}
	}*/

    /*@Override
	public void onFailure(int requestCode, Bundle savedData) {

	}*/



    override fun onDateChanged(position: Int) {
        mTimeAdapter!!.notifyDateChanged(position)
    }

    interface OnDateTimeListener {
        fun onDateTime(date: DateModel, time: TimeModel)
    }
}
