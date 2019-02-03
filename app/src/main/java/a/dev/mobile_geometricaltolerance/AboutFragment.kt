package a.dev.mobile_geometricaltolerance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AboutFragment : BaseFragment() {
    internal val TAG = "AboutFragment"

    fun newInstance(): AboutFragment {
        return AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.frg_about, container, false)

    override fun setUp() {
        Log.d(TAG, "click back from about")
    }
}




