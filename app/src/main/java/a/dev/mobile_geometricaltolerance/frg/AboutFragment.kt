package a.dev.mobile_geometricaltolerance.frg

import a.dev.mobile_geometricaltolerance.R
import a.dev.mobile_geometricaltolerance.R.layout
import a.dev.mobile_geometricaltolerance.R.string
import a.dev.mobile_geometricaltolerance.base.BaseFragment
import a.dev.mobile_geometricaltolerance.utils.toSpanned
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class AboutFragment : BaseFragment() {
    internal val tag = "AboutFragment"

    fun newInstance(): AboutFragment {
        return AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(layout.frg_about, container, false)

        val tvAbout = inflate.findViewById(R.id.tv_about_app) as TextView
        tvAbout.text = getString(string.about_info).toSpanned()
        return inflate
    }

    override fun setUp() {
        Log.d(tag, "click back from about")
    }
}




