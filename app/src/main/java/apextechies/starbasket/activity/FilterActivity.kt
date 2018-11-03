package apextechies.starbasket.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import apextechies.starbasket.R
import apextechies.starbasket.fragment.FilterListFragment
import apextechies.starbasket.fragment.TypeOfFilterFragment
import kotlinx.android.synthetic.main.toolbar.*

class FilterActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Filter"
        searchRL.visibility = View.GONE

        fragmentManager.beginTransaction().replace(R.id.catFL, TypeOfFilterFragment("")).commit()
        fragmentManager.beginTransaction().replace(R.id.listFL, FilterListFragment("")).commit()

        toolbarr.setNavigationOnClickListener {
            finish()
        }
    }
}