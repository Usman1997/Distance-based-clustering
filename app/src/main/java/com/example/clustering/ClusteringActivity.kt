package com.example.clustering

import android.content.Context
import com.example.clustering.model.HeatmapLabel
import com.example.clustering.model.HeatmapLabelData
import com.example.clustering.util.BitmapGenerator
import com.example.clustering.view.CustomDefaultClusterRenderer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster

class ClusteringActivity : MapActivity(),
    CustomClusterManager.OnClusterClickListener<HeatmapLabel>,
    CustomClusterManager.OnClusterInfoWindowClickListener<HeatmapLabel>,
    CustomClusterManager.OnClusterItemClickListener<HeatmapLabel>,
    CustomClusterManager.OnClusterItemInfoWindowClickListener<HeatmapLabel> {

    private lateinit var mClusterManager: CustomClusterManager<HeatmapLabel>

    override fun cluster() {
        val map = getMap()
        map?.let { googleMap ->
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(51.503186, -0.126446), 9.5f))
            mClusterManager = CustomClusterManager(this, googleMap)
            mClusterManager.renderer = CustomRenderer(this, googleMap, mClusterManager)
            googleMap.setOnCameraIdleListener(mClusterManager)
            googleMap.setOnMarkerClickListener(mClusterManager)
            googleMap.setOnInfoWindowClickListener(mClusterManager)
            mClusterManager.setOnClusterClickListener(this)
            mClusterManager.setOnClusterInfoWindowClickListener(this)
            mClusterManager.setOnClusterItemClickListener(this)
            mClusterManager.setOnClusterItemInfoWindowClickListener(this)
            addItems()
            mClusterManager.cluster()
        }
    }

    private fun addItems() {
        val list = getData()
        for (i in list.indices) {
            val data = list[i]
            val surgeLabel = HeatmapLabel(
                id = i,
                data.latLng,
                BitmapGenerator.generateBitmap(this, data.label),
                data.label,
                data.value
            )
            mClusterManager.addItem(surgeLabel)
        }
    }

    private fun getData(): List<HeatmapLabelData> {

        return listOf(

            // Area 1
            HeatmapLabelData(1.1, LatLng(51.54518458011435, -0.06258386112549827), "x1.1c"),
            HeatmapLabelData(1.1, LatLng(51.55293795894575, -0.06683620229142698), "x1.1d"),
            HeatmapLabelData(1.1, LatLng(51.53952479741153, -0.07052277632393239), "x1.1h"),
            HeatmapLabelData(1.1, LatLng(51.56069093200335, -0.07108985497753217), "x1.1j"),
            HeatmapLabelData(1.1, LatLng(51.53804667600809, -0.1028468959034521), "x1.1m"),
            HeatmapLabelData(1.1, LatLng(51.54580030305447, -0.10710564877552287), "x1.1o"),

            HeatmapLabelData(1.2, LatLng(51.54937121952756, -0.08697044217640913), "x1.2f"),
            HeatmapLabelData(1.2, LatLng(51.55503167970897, -0.079030665209378), "x1.2h"),
            HeatmapLabelData(1.2, LatLng(51.54727844142394, -0.07477606494689498), "x1.2k"),
            HeatmapLabelData(1.2, LatLng(51.557124316816164, -0.09122730188779786), "x1.2l"),
            HeatmapLabelData(1.2, LatLng(51.54161771617307, -0.08271489478349703), "x1.2o"),
            HeatmapLabelData(1.2, LatLng(51.54370955158518, -0.09490918577545156), "x1.2p"),

            HeatmapLabelData(1.3, LatLng(51.52253820438197, -0.09433332726728304), "x1.3b"),
            HeatmapLabelData(1.3, LatLng(51.53386380713774, -0.07846065914919488), "x1.3c"),

            HeatmapLabelData(1.5, LatLng(51.5282016094191, -0.08639750949801946), "x1.5b"),
            HeatmapLabelData(1.7, LatLng(51.53029264298886, -0.09858945558727365), "x1.7a"),
            HeatmapLabelData(2.0, LatLng(51.535955783319295, -0.09065269169797961), "x2.0a"),

            // Area 2
            HeatmapLabelData(1.1, LatLng(51.52548507935017, -0.029714255981393035), "x1.1u"),
            HeatmapLabelData(1.1, LatLng(51.52758138356955, -0.04189759667564442), "x1.1v"),

            // Area 3
            HeatmapLabelData(1.1, LatLng(51.459870741980076, -0.01589450877537533), "x1.1r"),
            HeatmapLabelData(1.2, LatLng(51.46553223262593, -0.007964980329165997), "x1.2r"),
            HeatmapLabelData(1.4, LatLng(51.4676285589182, -0.020132460093431975), "x1.4b"),

            // Area 4
            HeatmapLabelData(1.1, LatLng(51.48166806926755, -0.06089393441541109), "x1.1b"),
            HeatmapLabelData(1.1, LatLng(51.47391106671278, -0.05664791970867718), "x1.1w"),
            HeatmapLabelData(1.2, LatLng(51.476003076975466, -0.06882407795949705), "x1.2i"),
            HeatmapLabelData(1.4, LatLng(51.468245810043385, -0.06457711796494388), "x1.4e"),

            // Area 5
            HeatmapLabelData(1.1, LatLng(51.57619565925798, -0.0796010971502126), "x1.1f"),

            // Area 6
            HeatmapLabelData(1.1, LatLng(51.53804667600809, -0.1028468959034521), "x1.1m"),

            // Area 7
            HeatmapLabelData(1.1, LatLng(51.52580242557861, -0.19558916137863605), "x1.1i"),

            // Area 8
            HeatmapLabelData(1.1, LatLng(51.54431284718589, -0.139442533208013), "x1.1l"),
            HeatmapLabelData(1.1, LatLng(51.53864593497826, -0.1473789424982357), "x1.1n"),
            HeatmapLabelData(1.2, LatLng(51.52105080473358, -0.12665382443367665), "x1.2a"),
            HeatmapLabelData(1.2, LatLng(51.54222520853455, -0.12724181718465183), "x1.2g"),
            HeatmapLabelData(1.3, LatLng(51.52880522523117, -0.13091541370158233), "x1.3d"),
            HeatmapLabelData(1.5, LatLng(51.53655923951044, -0.13517831643964984), "x1.5a"),
            HeatmapLabelData(1.6, LatLng(51.53447145862757, -0.12297985958373434), "x1.6b"),

            // Area 9
            HeatmapLabelData(1.1, LatLng(51.486446909118, -0.1297373836137212), "x1.1a"),
            HeatmapLabelData(1.1, LatLng(51.478690339801766, -0.12547915487137898), "x1.1e"),
            HeatmapLabelData(1.1, LatLng(51.46224082204745, -0.19375000902900383), "x1.1g"),
            HeatmapLabelData(1.1, LatLng(51.46999801976183, -0.198018769448141), "x1.1k"),
            HeatmapLabelData(1.1, LatLng(51.49211661271493, -0.12180767405747608), "x1.1p"),
            HeatmapLabelData(1.1, LatLng(51.47775481206734, -0.20228884382846996), "x1.1q"),
            HeatmapLabelData(1.1, LatLng(51.48853242756679, -0.14192654396098514), "x1.1s"),
            HeatmapLabelData(1.1, LatLng(51.45599840900746, -0.1571838944059474), "x1.1t"),
            HeatmapLabelData(1.1, LatLng(51.48135152822416, -0.18216713374203636), "x1.1x"),

            HeatmapLabelData(1.2, LatLng(51.50971418803255, -0.14251994088473516), "x1.2b"),
            HeatmapLabelData(1.2, LatLng(51.46016110225396, -0.18155914317295396), "x1.2c"),
            HeatmapLabelData(1.2, LatLng(51.48343190645115, -0.19436260231435282), "x1.2d"),
            HeatmapLabelData(1.2, LatLng(51.50195883361916, -0.13825777606140885), "x1.2e"),
            HeatmapLabelData(1.2, LatLng(51.50404406930791, -0.15045144803325536), "x1.2j"),
            HeatmapLabelData(1.2, LatLng(51.48286057485175, -0.14985513080170057), "x1.2q"),
            HeatmapLabelData(1.2, LatLng(51.48077599912259, -0.1376660604207577), "x1.2p"),

            HeatmapLabelData(1.3, LatLng(51.47567537704624, -0.1900934689415576), "x1.3a"),
            HeatmapLabelData(1.3, LatLng(51.49478247629562, -0.17850701397123658), "x1.3e"),
            HeatmapLabelData(1.3, LatLng(51.49837274383707, -0.15838192099467313), "x1.3f"),
            HeatmapLabelData(1.3, LatLng(51.4962884510905, -0.14618833964546474), "x1.3g"),

            HeatmapLabelData(1.4, LatLng(51.479270064488205, -0.16997382754240672), "x1.4a"),
            HeatmapLabelData(1.4, LatLng(51.492700211747604, -0.1663113596675002), "x1.4c"),
            HeatmapLabelData(1.4, LatLng(51.46375603202208, -0.16144589332899706), "x1.4d"),
            HeatmapLabelData(1.4, LatLng(51.471513250644406, -0.16570920418826202), "x1.4f"),

            HeatmapLabelData(1.6, LatLng(51.46791844218505, -0.18582564941412788), "x1.6a"),
            HeatmapLabelData(1.6, LatLng(51.500455951499916, -0.1705776673439737), "x1.6c"),

            HeatmapLabelData(1.8, LatLng(51.47359485637474, -0.177900255551649), "x1.8a"),
            HeatmapLabelData(1.8, LatLng(51.4849440663973, -0.16204636548745993), "x1.8b"),

            HeatmapLabelData(1.9, LatLng(51.46583777940799, -0.1736346905314113), "x1.9a"),
            HeatmapLabelData(1.9, LatLng(51.48702647316732, -0.17423976395034046), "x1.9b"),

            )
    }

    private inner class CustomRenderer(
        context: Context, map: GoogleMap, clusterManager: CustomClusterManager<HeatmapLabel>
    ) : CustomDefaultClusterRenderer<HeatmapLabel>(context, map, clusterManager) {


        override fun onBeforeClusterItemRendered(
            item: HeatmapLabel,
            markerOptions: MarkerOptions
        ) {
            markerOptions.position(item.position).title(item.title)
                .icon(BitmapDescriptorFactory.fromBitmap(item.bitmap))
        }

        override fun onBeforeClusterRendered(
            cluster: Cluster<HeatmapLabel>, markerOptions: MarkerOptions
        ) {
            val items = cluster.items.groupBy { it.value }
            val list = items.values.filter { it.size > 1 }
            list.map {
                val item = it.first()
                val bitmap =
                    BitmapGenerator.generateBitmap(this@ClusteringActivity,item.title)
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            }
        }

        override fun onClusterItemUpdated(item: HeatmapLabel, marker: Marker) {
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(item.bitmap))
            marker.title = item.title
        }

        override fun onClusterUpdated(cluster: Cluster<HeatmapLabel>, marker: Marker) {
            val items = cluster.items.groupBy { it.value }
            val list = items.values.filter { it.size > 1 }
            list.map {
                val item = it.first()
                val bitmap =
                    BitmapGenerator.generateBitmap(this@ClusteringActivity,item.title)
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
            }
        }

        override fun shouldRenderAsCluster(cluster: Cluster<HeatmapLabel>): Boolean {
            return cluster.size > 1
        }
    }

    override fun onClusterClick(cluster: Cluster<HeatmapLabel>?, marker: Marker?): Boolean {
        return true
    }

    override fun onClusterInfoWindowClick(cluster: Cluster<HeatmapLabel>?) {}

    override fun onClusterItemClick(item: HeatmapLabel?, marker: Marker?): Boolean {
        return true
    }

    override fun onClusterItemInfoWindowClick(item: HeatmapLabel?) {}
}
