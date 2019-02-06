package a.dev.mobile_geometricaltolerance.frg

import a.dev.mobile_geometricaltolerance.utils.AssetDBOpenHelper
import a.dev.mobile_geometricaltolerance.MainActivity
import a.dev.mobile_geometricaltolerance.R
import a.dev.mobile_geometricaltolerance.R.layout
import a.dev.mobile_geometricaltolerance.adapter.RecyclerAdapter
import a.dev.mobile_geometricaltolerance.base.BaseFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ToleranceFragment()  : BaseFragment() {

    internal val TAG = "ToleranceFragment"

    private lateinit var dbHelper: AssetDBOpenHelper
    private lateinit var mAdapter: RecyclerAdapter
    fun newInstance(): ToleranceFragment =
        ToleranceFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(layout.content_main, container, false)
        dbHelper = AssetDBOpenHelper(getBaseActivity()!!)
        dbHelper.openDatabase()

        val recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(getBaseActivity())




        mAdapter =
            RecyclerAdapter(dbHelper.getDataDB(MainActivity.TYPE))
        recyclerView.adapter = mAdapter


        return view
    }

    override fun setUp() {

        Log.d(TAG, "click dopusk")
    }
}
