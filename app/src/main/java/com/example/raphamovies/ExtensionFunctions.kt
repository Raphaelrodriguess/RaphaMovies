package com.example.raphamovies

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.raphamovies.domainmodel.Details


fun Details.openYoutube(context: Context?) {
    if (context != null) {
        if (!this.videos.isNullOrEmpty() && !this.videos[0].key.isNullOrEmpty()) {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=${this.videos[0].key}")
            )
            context.startActivity(webIntent)
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.no_video_to_show),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}