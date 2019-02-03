package a.dev.mobile_geometricaltolerance

import a.dev.mobile_geometricaltolerance.R.string
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.toolbar

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        var TYPE = 2
    }

    val TAG = "MainActivity"

    override fun onFragmentAttached() {
        Log.d(TAG, "onFragmentAttached")
    }

    override fun onFragmentDetached(tag: String) {
        Log.d(TAG, "onFragmentDetached")
        supportFragmentManager?.removeFragment(tag = tag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
                openDopuskFrg()
            }
            R.id.nav_loc -> {
                TYPE = TYPE_LOC
                openDopuskFrg()
            }
            R.id.nav_formloc -> {
                TYPE = TYPE_FORMLOC
                openDopuskFrg()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun clearFrg() {

        if (getTagFrg().equals(DopuskFragment().TAG)) onFragmentDetached(DopuskFragment().TAG)
        if (getTagFrg().equals(AboutFragment().TAG)) onFragmentDetached(AboutFragment().TAG)
    }

    fun getTagFrg(): String? {

        val fragment = supportFragmentManager.findFragmentById(R.id.root_view)

        if (fragment != null) {
            if (fragment.tag.equals(AboutFragment().TAG)) {
                return AboutFragment().TAG
            }
            if (fragment.tag.equals(DopuskFragment().TAG)) {
                return DopuskFragment().TAG
            }
        }
        return null
    }

    private fun openDopuskFrg() {
        clearFrg()
        Log.d(TAG, "type = $TYPE")
        supportFragmentManager.addFragment(
            R.id.root_view,
            DopuskFragment().newInstance(),
            DopuskFragment().TAG
        )
    }

    private fun openAboutFrg() {
        clearFrg()
        popBackStackTillEntry(0)
        supportFragmentManager.addFragment(
            R.id.root_view,
            AboutFragment().newInstance(),
            AboutFragment().TAG
        )
    }

    private fun popBackStackTillEntry(entryIndex: Int) {

        if (supportFragmentManager == null) {
            return
        }
        if (supportFragmentManager.backStackEntryCount <= entryIndex) {
            return
        }
        val entry = supportFragmentManager.getBackStackEntryAt(
            entryIndex
        )
        supportFragmentManager.popBackStackImmediate(
            entry.id,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}
