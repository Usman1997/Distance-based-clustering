package com.example.clustering.model

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng

data class HeatmapLabel(
    val id: Int,
    val latLngPosition: LatLng,
    val bitmap: Bitmap,
    val markerTitle: String,
    val value: Double,
) : CustomClusterItem() {
    override fun getLabelValue(): Double = value

    override fun getPosition(): LatLng = latLngPosition

    override fun getTitle(): String = markerTitle

    override fun getSnippet(): String? = null

    override fun getZIndex(): Float? = null

}