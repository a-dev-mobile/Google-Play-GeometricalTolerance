package a.dev.mobile_geometricaltolerance.adapter

import a.dev.mobile_geometricaltolerance.R
import a.dev.mobile_geometricaltolerance.adapter.RecyclerAdapter.BaseModelHolder
import a.dev.mobile_geometricaltolerance.base.BaseModel
import a.dev.mobile_geometricaltolerance.utils.inflate
import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.iv_image
import kotlinx.android.synthetic.main.item.view.ll_root
import kotlinx.android.synthetic.main.item.view.wv_info

class RecyclerAdapter(private val bases: ArrayList<BaseModel>) :
    RecyclerView.Adapter<BaseModelHolder>() {

    override fun onBindViewHolder(holder: BaseModelHolder, position: Int) {
        holder.bindBaseModel(bases[position])
    }

    override fun getItemCount(): Int = bases.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseModelHolder {
        val inflatedView = parent.inflate(R.layout.item, false)
        Log.d("RecyclerAdapter", "onCreateViewHolder")
        return BaseModelHolder(inflatedView)
    }

    class BaseModelHolder(private var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {


        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("adapter", "click")

            view.wv_info.toggleVisibility()
        }

        private fun View.toggleVisibility() {

            visibility = if (visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }


        @SuppressLint("SetJavaScriptEnabled")
        fun bindBaseModel(itemBase: BaseModel) {

            val idImage: Int =
                view.context.resources.getIdentifier(itemBase.image, "drawable", view.context.packageName)

            val folder = itemBase.folder
            view.iv_image.setImageResource(idImage)

            view.wv_info.visibility = View.GONE

            val webSettings = view.wv_info.settings
            webSettings.javaScriptEnabled = true
            webSettings.javaScriptCanOpenWindowsAutomatically = true
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                webSettings.allowFileAccessFromFileURLs = true
                webSettings.allowUniversalAccessFromFileURLs = true
            }

            /* view.wv_info.loadDataWithBaseURL(
                 null,
                 itemBase.folder,
                 "text/html",
                 "utf-8",
                 null
             )
            */

            view.wv_info.loadUrl("file:///android_asset/html/$folder/index.html")
//            view.wv_info.loadUrl("file:///android_asset/html/straightness/index.html")
        }
    }
}
