package com.example.clustering

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

abstract class MapActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)
        setUpMap()
    }

    override fun onMapReady(p0: GoogleMap) {
        if (mMap != null) {
            return
        }
        mMap = p0
        cluster()
    }

    private fun setUpMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    protected fun getMap(): GoogleMap? = mMap

    protected abstract fun cluster()
}