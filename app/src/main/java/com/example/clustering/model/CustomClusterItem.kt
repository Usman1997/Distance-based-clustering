package com.example.clustering.model

import com.google.maps.android.clustering.ClusterItem

abstract class CustomClusterItem: ClusterItem {
    abstract fun getLabelValue(): Double
}