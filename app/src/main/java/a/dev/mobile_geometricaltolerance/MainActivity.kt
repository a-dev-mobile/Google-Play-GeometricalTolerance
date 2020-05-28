package a.dev.mobile_geometricaltolerance

import a.dev.mobile_geometricaltolerance.R.string
import a.dev.mobile_geometricaltolerance.base.BaseActivity
import a.dev.mobile_geometricaltolerance.frg.AboutFragment
import a.dev.mobile_geometricaltolerance.frg.ToleranceFragment
import a.dev.mobile_geometricaltolerance.utils.AssetDBOpenHelper
import a.dev.mobile_geometricaltolerance.utils.PREF_TYPE
import a.dev.mobile_geometricaltolerance.utils.SHARED_PREFERENCES
import a.dev.mobile_geometricaltolerance.utils.TYPE_FORM
import a.dev.mobile_geometricaltolerance.utils.TYPE_LOC
import a.dev.mobile_geometricaltolerance.utils.TYPE_ORIENT
import a.dev.mobile_geometricaltolerance.utils.TYPE_RUNOUT
import a.dev.mobile_geometricaltolerance.utils.addFragment
import a.dev.mobile_geometricaltolerance.utils.removeFragment
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView

import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.toolbar

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        var TYPE = 1
    }

    private val tag = "MainActivity"

    override fun onFragmentAttached() {
        Log.d(tag, "onFragmentAttached")
    }

    override fun onFragmentDetached(tag: String) {
        Log.d(this.tag, "onFragmentDetached")
        supportFragmentManager?.removeFragment(tag = tag)
    }

    private lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TYPE = loadIntFromFile()
        //сохранять тип допусков показаных в последний раз
        openToleranceFrg()

        setUpDrawerMenu()
        AssetDBOpenHelper(this).openDatabase()

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, "ca-app-pub-6155876762943258~2413814075")

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = findViewById(R.id.ad_view)

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)






    }

    private fun setUpDrawerMenu() {
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, string.navigation_drawer_open, string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun loadIntFromFile(): Int {
        val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        var i = 1
        if (sharedPreferences.contains(PREF_TYPE)) {
            i = sharedPreferences.getInt(PREF_TYPE, i)
        }
        return i
    }

    private fun saveIntInFile(value: Int) {
        val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(PREF_TYPE, value)
        editor.apply()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else  {
            drawer_layout.openDrawer(GravityCompat.START)
    //        exitFromApp()
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_about -> openAboutFrg()
            R.id.nav_form -> {
                TYPE = TYPE_FORM
                openToleranceFrg()
            }
            R.id.nav_orient -> {
                TYPE = TYPE_ORIENT
                openToleranceFrg()
            }
            R.id.nav_loc -> {
                TYPE = TYPE_LOC
                openToleranceFrg()
            }
            R.id.nav_runout -> {
                TYPE = TYPE_RUNOUT
                openToleranceFrg()
            }

            R.id.nav_send -> sendEmail()
            R.id.navItemRateUs -> appRate()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun appRate() {

        val marketUri = Uri.parse("market://details?id=a.dev.mobile_geometricaltolerance")
        val marketIntent = Intent(Intent.ACTION_VIEW).setData(marketUri)
        startActivity(marketIntent)
    }

    private fun sendEmail() {

        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/email"
        i.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email)))

        val themeEmail = "Google Play app - Geometrical Tolerance"
        i.putExtra(Intent.EXTRA_SUBJECT, themeEmail)
        i.putExtra(Intent.EXTRA_TEXT, "")

        try {
            startActivity(Intent.createChooser(i, R.string.email_send_app.toString()))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this@MainActivity, R.string.email_no_app, Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFrg() {

        if (getTagFrg().equals(ToleranceFragment().tag)) onFragmentDetached(ToleranceFragment().tag)
        if (getTagFrg().equals(AboutFragment().tag)) onFragmentDetached(AboutFragment().tag)
    }

    private fun getTagFrg(): String? {

        val fragment = supportFragmentManager.findFragmentById(R.id.root_view)

        if (fragment != null) {
            if (fragment.tag.equals(AboutFragment().tag)) {
                return AboutFragment().tag
            }
            if (fragment.tag.equals(ToleranceFragment().tag)) {
                return ToleranceFragment().tag
            }
        }
        return null
    }

    private fun openToleranceFrg() {
        clearFrg()

        var type = "Допуски"
        if (TYPE == TYPE_FORM) type = "Допуски формы"
        if (TYPE == TYPE_ORIENT) type = "Допуски ориентации"
        if (TYPE == TYPE_LOC) type = "Допуски месторасположения"
        if (TYPE == TYPE_RUNOUT) type = "Допуски биения"
        toolbar.title = type



        saveIntInFile(TYPE)

        supportFragmentManager.addFragment(
            R.id.root_view,
            ToleranceFragment().newInstance(),
            ToleranceFragment().tag
        )
    }
    private var backPressed: Long = 0
    @SuppressLint("StringFormatInvalid")
    private fun exitFromApp() {

        if (backPressed + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(baseContext, getString(R.string.post_to_exit, Toast.LENGTH_SHORT), Toast.LENGTH_SHORT)
                .show()
        }
        backPressed = System.currentTimeMillis()
    }

    private fun openAboutFrg() {
        clearFrg()
        toolbar.title = getString(R.string.nav_menu_about_app)
        supportFragmentManager.addFragment(
            R.id.root_view,
            AboutFragment().newInstance(),
            AboutFragment().tag
        )
    }

    /** Called when leaving the activity  */
    public override fun onPause() {
        mAdView.pause()
        super.onPause()
    }

    /** Called when returning to the activity  */
    public override fun onResume() {
        super.onResume()
        mAdView.resume()
    }

    /** Called before the activity is destroyed  */
    public override fun onDestroy() {
        mAdView.destroy()
        super.onDestroy()
    }




}
