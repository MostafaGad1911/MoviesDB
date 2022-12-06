package mostafagad.projects.movies.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import mostafagad.projects.movies.R

object Ext {

    fun String.loadImg(img: ImageView, ctx: Context) {
        Glide.with(ctx).load(this).placeholder(R.drawable.progress_animation)
            .into(img)
    }

    fun String.toast(ctx: Context) =
        Toast.makeText(ctx, this, Toast.LENGTH_LONG).show()

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

    @JvmStatic //https://stackoverflow.com/questions/58049179/required-databindingcomponent-is-null-in-class-agentdetailsactivitybindingimpl
    @BindingAdapter("app:loadImage")
    fun loadPictureFromUrl(ivUserImage: ImageView, path: String?) {
        (path != null).let {
            if (it){
                Glide.with(ivUserImage.context)
                    .load("https://image.tmdb.org/t/p/original/$path")
                    .placeholder(R.drawable.loading_image)
                    .apply(RequestOptions.centerCropTransform())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .override(512, 512)
                    .into(ivUserImage)
            }
        }
    }


}
