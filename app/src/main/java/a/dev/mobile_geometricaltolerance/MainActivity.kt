package a.dev.mobile_geometricaltolerance

import a.dev.mobile_geometricaltolerance.R.string
import a.dev.mobile_geometricaltolerance.base.BaseActivity
import a.dev.mobile_geometricaltolerance.frg.AboutFragment
import a.dev.mobile_geometricaltolerance.frg.ToleranceFragment
import a.dev.mobile_geometricaltolerance.utils.AssetDBOpenHelper
import a.dev.mobile_geometricaltolerance.utils.TYPE_FORM
import a.dev.mobile_geometricaltolerance.utils.TYPE_LOC
import a.dev.mobile_geometricaltolerance.utils.TYPE_ORIENT
import a.dev.mobile_geometricaltolerance.utils.TYPE_RUNOUT
import a.dev.mobile_geometricaltolerance.utils.addFragment
import a.dev.mobile_geometricaltolerance.utils.removeFragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView

import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //сохранять тип допусков показаных в последний раз
        openToleranceFrg()

        setUpDrawerMenu()
        AssetDBOpenHelper(this).openDatabase()
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_about -> openAboutFrg()
            R.id.nav_form -> {
                TYPE = TYPE_FORM
                openToleranceFrg ()
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
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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

        if (getTagFrg().equals(ToleranceFragment().TAG)) onFragmentDetached(ToleranceFragment().TAG)
        if (getTagFrg().equals(AboutFragment().TAG)) onFragmentDetached(AboutFragment().TAG)
    }

    private fun getTagFrg(): String? {

        val fragment = supportFragmentManager.findFragmentById(R.id.root_view)

        if (fragment != null) {
            if (fragment.tag.equals(AboutFragment().TAG)) {
                return AboutFragment().TAG
            }
            if (fragment.tag.equals(ToleranceFragment().TAG)) {
                return ToleranceFragment().TAG
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





        supportFragmentManager.addFragment(
            R.id.root_view,
            ToleranceFragment().newInstance(),
            ToleranceFragment().TAG
        )
    }

    private fun openAboutFrg() {
        clearFrg()

        supportFragmentManager.addFragment(
            R.id.root_view,
            AboutFragment().newInstance(),
            AboutFragment().TAG
        )
    }
}
