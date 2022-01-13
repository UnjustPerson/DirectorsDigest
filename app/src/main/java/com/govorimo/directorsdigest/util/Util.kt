package com.govorimo.directorsdigest.util

import android.content.res.Resources
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import com.govorimo.directorsdigest.R
import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


fun getTommyList(resources: Resources): List<Director>{
    return List<Director>(5) {
        Director(it+1000.toLong(), resources.getString(R.string.tommy_wiseau),  resources.getString(R.string.tommy_bio), String())
    }
}

fun getRoomList(resources: Resources): List<Film>{
    return List<Film>(5) {
        Film(
            it + 1000.toLong(), resources.getString(R.string.the_room),
            resources.getString(R.string.tommy_wiseau),
            2003, String(), String(),
            resources.getString(R.string.room_trailer),
            resources.getString(R.string.room_synopsis),
            resources.getString(R.string.tommy_wiseau)
        )
    }
}

fun findTextToColor(spannableString: SpannableString): IntRange?{
    val colorMeRegex = "\\((.*?)\\)".toRegex()
    val colorMeRange = colorMeRegex.find(spannableString)?.range
    return colorMeRange
}


fun colorText(use: String, color: String): SpannableString{
    val spannableString = SpannableString(use)
    val colorMeColor = ForegroundColorSpan(Color.parseColor(color))
    val colorMeRange = findTextToColor(spannableString)
    if (colorMeRange!=null){
        spannableString.setSpan(
            colorMeColor,
            colorMeRange.first-1, colorMeRange.last+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableString.setSpan(
            RelativeSizeSpan(0.8f), colorMeRange.first-1, colorMeRange.last+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
    return spannableString
}
