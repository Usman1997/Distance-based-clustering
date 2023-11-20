package com.example.clustering.model

import com.google.android.gms.maps.model.LatLng

data class HeatmapLabelData(
    val value: Double,
    val latLng: LatLng,
    val label: String
)