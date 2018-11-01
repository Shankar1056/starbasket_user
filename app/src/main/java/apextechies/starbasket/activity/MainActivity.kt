package apextechies.starbasket.activity

import android.content.Intent
import android.graphics.Color
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.Gravity
import android.widget.TextView
import apextechies.starbasket.R
import apextechies.starbasket.adapter.CategoryAdapter
import apextechies.starbasket.adapter.SubCategoryAdapter
import apextechies.starbasket.adapter.ViewPagerAdapter
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.common.Utilz
import apextechies.starbasket.fragment.ImageFragment
import apextechies.starbasket.listener.OnClickListenr
import apextechies.starbasket.login.ChangePassword
import apextechies.starbasket.login.LoginActivity
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.cart_item_count.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.navigation_drawer.*


class MainActivity : AppCompatActivity(), CategoryAdapter.OnItemClickListener, SubCategoryAdapter.OnItemClickListener, ViewPager.OnPageChangeListener {

    private var isAscending = true
    private var userScrollChange = false
    private var prevPos = 0
    private var previousState = ViewPager.SCROLL_STATE_IDLE
    private var mAdapter: ViewPagerAdapter? = null
    private var retrofitDataProvider: RetrofitDataProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        retrofitDataProvider = RetrofitDataProvider(this)
        for (i in 0 until ll_navigation.getChildCount()) {
//            ll_navigation.getChildAt(i).setOnClickListener(this)
        }

        mAdapter = ViewPagerAdapter(getSupportFragmentManager());
        vp_banner.setScrollDurationFactor(3.0)
        vp_banner.setAdapter(mAdapter)
        vp_banner.addOnPageChangeListener(this)

        rv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_subcategory.isNestedScrollingEnabled = false
        tab_layout.setupWithViewPager(vp_banner)

        getBanner()
        gethomeCategory()
        slideViewPager()

        nav_my_cart.setOnClickListener {
            if (ClsGeneral.getStrPreferences(this@MainActivity, AppConstants.USERID).equals("")) {
                gotoogin()
            }
            else {
                changebackcolor(nav_my_cart)
                drawer_layout.closeDrawer(Gravity.LEFT)
                startActivity(Intent(this@MainActivity, CartActivity::class.java))
            }
        }
        nav_my_addresses.setOnClickListener {
            if (ClsGeneral.getStrPreferences(this@MainActivity, AppConstants.USERID).equals("")) {
                gotoogin()
            }
            else {
                changebackcolor(nav_my_addresses)
                drawer_layout.closeDrawer(Gravity.LEFT)
                startActivity(Intent(this@MainActivity, AddressActivity::class.java)
                        .putExtra("from", "main"))
            }
        }
        nav_share.setOnClickListener {
            drawer_layout.closeDrawer(Gravity.LEFT)
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/html"
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("play store link"))
            startActivity(Intent.createChooser(sharingIntent, "Share using"))
        }
        nav_logout.setOnClickListener {
            Utilz.displayMessageAlertWithCllbak("Ary yo sure you want to logged out from this app", this, object : OnClickListenr {
                override fun onClick(posInt: Int) {
                    ClsGeneral.setBoolean(this@MainActivity, "islogout", true)
                    ClsGeneral.setPreferences(this@MainActivity, AppConstants.USERID, "")
                    startActivity(Intent(this@MainActivity, SplashScreen::class.java))
                    finishAffinity()
                }
            })

        }
        nav_my_orders.setOnClickListener {
            if (ClsGeneral.getStrPreferences(this@MainActivity, AppConstants.USERID).equals("")) {
                gotoogin()
            }
            else {
                changebackcolor(nav_my_orders)
                drawer_layout.closeDrawer(Gravity.LEFT)
                startActivity(Intent(this@MainActivity, OrderActivity::class.java))
            }
        }

        cartLL.setOnClickListener {
            drawer_layout.closeDrawer(Gravity.LEFT)
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
        action_cart.setOnClickListener {
            if (ClsGeneral.getStrPreferences(this@MainActivity, AppConstants.USERID).equals("")) {
                gotoogin()
            }
            else {
                startActivity(Intent(this@MainActivity, CartActivity::class.java))
            }
        }
        uploadPres.setOnClickListener {
            if (ClsGeneral.getStrPreferences(this@MainActivity, AppConstants.USERID).equals("")) {
                gotoogin()
            }
            else {
                startActivity(Intent(this@MainActivity, WriteUploadPrecription::class.java))
            }
        }
        changePassword.setOnClickListener {
            startActivity(Intent(this@MainActivity, ChangePassword::class.java))
        }

        tv_mobile.text = ClsGeneral.getStrPreferences(this, AppConstants.MOBILE)
        tv_name.text = ClsGeneral.getStrPreferences(this, AppConstants.USERNAME) + "" + ClsGeneral.getStrPreferences(this, AppConstants.USERLASTNAME)

        et_search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun gotoogin() {
        Utilz.displayMessageAlertWithCllbak("Please sign in first to see your orders", this@MainActivity, object : OnClickListenr {
            override fun onClick(posInt: Int) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finishAffinity()
            }
        })
    }

    private fun slideViewPager() {

        val handler = Handler()
        val delay = 2000 //milliseconds

        handler.postDelayed(object : Runnable {
            override fun run() {
                val nextPos: Int

                if (isAscending) {
                    nextPos = (vp_banner.getCurrentItem() + 1) % mAdapter!!.getCount()
                    isAscending = nextPos < mAdapter!!.getCount() - 1
                } else {
                    nextPos = (vp_banner.getCurrentItem() - 1) % mAdapter!!.getCount()
                    isAscending = nextPos < 1
                }

                vp_banner.setCurrentItem(nextPos, true)
                handler.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
        /*var handler = Handler()

        object : Runnable {
            override fun run() {
                try {
                    val nextPos: Int

                    if (isAscending) {
                        nextPos = (vp_banner.getCurrentItem() + 1) % mAdapter!!.getCount()
                        isAscending = nextPos < mAdapter!!.getCount() - 1
                    } else {
                        nextPos = (vp_banner.getCurrentItem() - 1) % mAdapter!!.getCount()
                        isAscending = nextPos < 1
                    }

                    vp_banner.setCurrentItem(nextPos, true)
                } catch (e: ArithmeticException) {
                }

                handler.postDelayed(this, 1000)
            }
        }*/

    }


    private fun changebackcolor(nav: TextView?) {
        nav_my_cart.setBackgroundColor(Color.TRANSPARENT)
        nav_my_addresses.setBackgroundColor(Color.TRANSPARENT)
        nav_my_orders.setBackgroundColor(Color.TRANSPARENT)
        nav!!.setBackgroundColor(resources.getColor(R.color.drawer_back_color))

    }

    private fun getBanner() {
        retrofitDataProvider!!.homeBanner(object : DownlodableCallback<HomeBannerModel> {
            override fun onSuccess(result: HomeBannerModel?) {

                for (i in 0 until result!!.data!!.size) {
                    mAdapter!!.addItem(ImageFragment.newInstance(result.data!![i].banner!!))
                }
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }
        })
    }

    private fun gethomeCategory() {
        var list = ArrayList<CategoryDataModel>()
        retrofitDataProvider!!.category(object : DownlodableCallback<CategoryModel> {
            override fun onSuccess(result: CategoryModel?) {
                for (i in 0 until result!!.data!!.size) {
                    if (result.data!![i].status.equals("1"))
                        list.add(CategoryDataModel(result.data!![i].id, result.data!![i].name, result.data!![i].icon, result.data!![i].status, result.data!![i].subcat))
                }
                if (list.size > 0) {
                    rv_category.adapter = CategoryAdapter(this@MainActivity, list)
                    rv_subcategory.adapter = SubCategoryAdapter(this@MainActivity, list)
                }

                getCartItem()
            }


            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }
        })
    }

    private fun getCartItem() {
        retrofitDataProvider!!.cartItem(ClsGeneral.getStrPreferences(this, AppConstants.USERID), object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {
                tv_notif_count.text = result!!.data!!.size.toString()
                ClsGeneral.setPreferences(this@MainActivity, AppConstants.CARTCOUNT, result!!.data!!.size.toString())
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }


    override fun onResume() {
        super.onResume()
        tv_notif_count.text = ClsGeneral.getStrPreferences(this, AppConstants.CARTCOUNT)
    }


    override fun onPageScrollStateChanged(state: Int) {
        if (previousState == ViewPager.SCROLL_STATE_DRAGGING && state == ViewPager.SCROLL_STATE_SETTLING) {
            userScrollChange = true
        } else if (previousState == ViewPager.SCROLL_STATE_SETTLING && state == ViewPager.SCROLL_STATE_IDLE) {
            userScrollChange = false
        }

        previousState = state
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (userScrollChange) {
            isAscending = prevPos < position && position < mAdapter!!.getCount() - 1 && position > 0
        }

        prevPos = position
    }

    override fun onItemClick(item: CategorysSubcatModel) {
        val intent = Intent(this, ProductListActivity::class.java)
        intent.putExtra("name", item.name)
        intent.putExtra("id", item.id)
        intent.putExtra("icon", item.icon)
        startActivity(intent)

    }

    override fun onViewAll(item: CategoryDataModel) {
        val intent = Intent(this, CategoryActivity::class.java)
        /*intent.putExtra("name", item.name)
        intent.putExtra("id", item.id)
        intent.putExtra("icon", item.icon)*/
        intent.putExtra(EXTRA_DATA, item)
        startActivity(intent)
    }

    override fun onItemClick(item: CategoryDataModel) {
        val intent = Intent(this, CategoryActivity::class.java)
        /*intent.putExtra("name", item.name)
        intent.putExtra("id", item.id)
        intent.putExtra("icon", item.icon)*/
        intent.putExtra(EXTRA_DATA, item)
        startActivity(intent)

    }
}
