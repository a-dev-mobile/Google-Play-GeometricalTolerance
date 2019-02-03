package a.dev.mobile_geometricaltolerance

import a.dev.mobile_geometricaltolerance.RecyclerAdapter.BaseModelHolder
import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import kotlinx.android.synthetic.main.item.view.iv_image
import kotlinx.android.synthetic.main.item.view.wv_info

class RecyclerAdapter(val bases: ArrayList<BaseModel>) :
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

    class BaseModelHolder(var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {


        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("adapter", "click")

            view.wv_info.toggleVisibility()
        }

        fun View.toggleVisibility() {
            if (visibility == View.VISIBLE) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
            }
        }


        fun bindBaseModel(itemBase: BaseModel) {

            val idImage: Int =
                view.context.resources.getIdentifier(itemBase.image, "drawable", view.context.packageName)


            view.iv_image.setImageResource(idImage)

            view.wv_info.visibility = View.GONE

           // view.wv_info.settings.javaScriptEnabled = true;
           // view.wv_info.settings.builtInZoomControls = true;
           // view.wv_info.settings.setSupportZoom(true);

            //view.wv_info.settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            //view.wv_info.settings.setAppCacheEnabled(false);
            view.wv_info.loadDataWithBaseURL(
                null,
                itemBase.info,
                "text/html",
                "utf-8",
                null
            )
        }
    }
}
