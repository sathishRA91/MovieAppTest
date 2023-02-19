package com.example.movieapptest.base

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sathish on 19,February,2023
 */
class AppConstant {

    companion object {

        const val ImagePath = "https://image.tmdb.org/t/p/original"

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImage(imageView: ImageView, imageUrl: String) {

            val cal = Calendar.getInstance()
            cal.time = Date()

            Glide.with(imageView.context)
                .load(ImagePath + imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(ObjectKey(cal.get(Calendar.DAY_OF_WEEK)))
                .priority(Priority.HIGH)
                .into(imageView)
        }


        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        @BindingAdapter("releaseOnText")
        @JvmStatic
        fun parseReleaseOn(view: TextView?, movieDate: String?) {
            val format = SimpleDateFormat("yyyy-MM-dd")
            val df = SimpleDateFormat("dd/MMM/yyyy", Locale.US)
            var releasedate: Date? = null
            if (movieDate != null && movieDate.isNotEmpty()) {
                try {
                    releasedate = format.parse(movieDate)
                    view!!.text = "Released On: " + df.format(releasedate!!)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }

        }
    }
}