package a.dev.mobile_geometricaltolerance

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class DopuskFragment()  : BaseFragment() {

    internal val TAG = "DopuskFragment"

    private lateinit var dbHelper: AssetDBOpenHelper
    private lateinit var mAdapter: RecyclerAdapter
    fun newInstance(): DopuskFragment = DopuskFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.content_main, container, false)
        dbHelper = AssetDBOpenHelper(getBaseActivity()!!)
        dbHelper.openDatabase()

        val recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(getBaseActivity())




        mAdapter = RecyclerAdapter(dbHelper.getDataDB(MainActivity.TYPE))
        recyclerView.adapter = mAdapter


        return view
    }

    override fun setUp() {

        Log.d(TAG, "click dopusk")
    }
}
