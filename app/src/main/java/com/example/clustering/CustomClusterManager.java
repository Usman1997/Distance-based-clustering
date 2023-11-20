package com.example.clustering;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.clustering.algo.CustomNonHierarchicalDistanceBasedAlgorithm;
import com.example.clustering.model.CustomClusterItem;
import com.example.clustering.view.CustomClusterRenderer;
import com.example.clustering.view.CustomDefaultClusterRenderer;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.algo.Algorithm;
import com.google.maps.android.clustering.algo.PreCachingAlgorithmDecorator;
import com.google.maps.android.clustering.algo.ScreenBasedAlgorithm;
import com.google.maps.android.clustering.algo.ScreenBasedAlgorithmAdapter;
import com.google.maps.android.collections.MarkerManager;

import java.util.Collection;
import java.util.Set;

public class CustomClusterManager<T extends CustomClusterItem> implements
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private final MarkerManager mMarkerManager;
    private final MarkerManager.Collection mMarkers;
    private final MarkerManager.Collection mClusterMarkers;

    private ScreenBasedAlgorithm<T> mAlgorithm;
    private CustomClusterRenderer<T> mRenderer;

    private GoogleMap mMap;
    private CameraPosition mPreviousCameraPosition;

    private OnClusterItemClickListener<T> mOnClusterItemClickListener;
    private OnClusterInfoWindowClickListener<T> mOnClusterInfoWindowClickListener;
    private OnClusterInfoWindowLongClickListener<T> mOnClusterInfoWindowLongClickListener;
    private OnClusterItemInfoWindowClickListener<T> mOnClusterItemInfoWindowClickListener;
    private OnClusterItemInfoWindowLongClickListener<T> mOnClusterItemInfoWindowLongClickListener;

    private OnClusterClickListener<T> mOnClusterClickListener;

    public CustomClusterManager(Context context, GoogleMap map) {
        this(context, map, new MarkerManager(map));
    }

    public CustomClusterManager(Context context, GoogleMap map, MarkerManager markerManager) {
        mMap = map;
        mMarkerManager = markerManager;
        mClusterMarkers = markerManager.newCollection();
        mMarkers = markerManager.newCollection();
        mRenderer = new CustomDefaultClusterRenderer<>(context, map, this);
        mAlgorithm = new ScreenBasedAlgorithmAdapter<>(new PreCachingAlgorithmDecorator<>(
                new CustomNonHierarchicalDistanceBasedAlgorithm<T>()));

        mRenderer.onAdd();
    }

    public MarkerManager.Collection getMarkerCollection() {
        return mMarkers;
    }

    public MarkerManager.Collection getClusterMarkerCollection() {
        return mClusterMarkers;
    }

    public MarkerManager getMarkerManager() {
        return mMarkerManager;
    }

    public void setRenderer(CustomClusterRenderer<T> renderer) {
        mRenderer.setOnClusterClickListener(null);
        mRenderer.setOnClusterItemClickListener(null);
        mClusterMarkers.clear();
        mMarkers.clear();
        mRenderer.onRemove();
        mRenderer = renderer;
        mRenderer.onAdd();
        mRenderer.setOnClusterClickListener(mOnClusterClickListener);
        mRenderer.setOnClusterInfoWindowClickListener(mOnClusterInfoWindowClickListener);
        mRenderer.setOnClusterInfoWindowLongClickListener(mOnClusterInfoWindowLongClickListener);
        mRenderer.setOnClusterItemClickListener(mOnClusterItemClickListener);
        mRenderer.setOnClusterItemInfoWindowClickListener(mOnClusterItemInfoWindowClickListener);
        mRenderer.setOnClusterItemInfoWindowLongClickListener(mOnClusterItemInfoWindowLongClickListener);
    }

    public void setAlgorithm(Algorithm<T> algorithm) {
        if (algorithm instanceof ScreenBasedAlgorithm) {
            setAlgorithm((ScreenBasedAlgorithm<T>) algorithm);
        } else {
            setAlgorithm(new ScreenBasedAlgorithmAdapter<>(algorithm));
        }
    }

    public void setAlgorithm(ScreenBasedAlgorithm<T> algorithm) {
        algorithm.lock();
        try {
            final Algorithm<T> oldAlgorithm = getAlgorithm();
            mAlgorithm = algorithm;

            if (oldAlgorithm != null) {
                oldAlgorithm.lock();
                try {
                    algorithm.addItems(oldAlgorithm.getItems());
                } finally {
                    oldAlgorithm.unlock();
                }
            }
        } finally {
            algorithm.unlock();
        }

        if (mAlgorithm.shouldReclusterOnMapMovement()) {
            mAlgorithm.onCameraChange(mMap.getCameraPosition());
        }
    }

    public void setAnimation(boolean animate) {
        mRenderer.setAnimation(animate);
    }

    public CustomClusterRenderer<T> getRenderer() {
        return mRenderer;
    }

    public Algorithm<T> getAlgorithm() {
        return mAlgorithm;
    }

    /**
     * Removes all items from the cluster manager. After calling this method you must invoke
     * {@link #cluster()} for the map to be cleared.
     */
    public void clearItems() {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            algorithm.clearItems();
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Adds items to clusters. After calling this method you must invoke {@link #cluster()} for the
     * state of the clusters to be updated on the map.
     *
     * @param items items to add to clusters
     * @return true if the cluster manager contents changed as a result of the call
     */
    public boolean addItems(Collection<T> items) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.addItems(items);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Adds an item to a cluster. After calling this method you must invoke {@link #cluster()} for
     * the state of the clusters to be updated on the map.
     *
     * @param myItem item to add to clusters
     * @return true if the cluster manager contents changed as a result of the call
     */
    public boolean addItem(T myItem) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.addItem(myItem);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Removes items from clusters. After calling this method you must invoke {@link #cluster()} for
     * the state of the clusters to be updated on the map.
     *
     * @param items items to remove from clusters
     * @return true if the cluster manager contents changed as a result of the call
     */
    public boolean removeItems(Collection<T> items) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.removeItems(items);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Removes an item from clusters. After calling this method you must invoke {@link #cluster()}
     * for the state of the clusters to be updated on the map.
     *
     * @param item item to remove from clusters
     * @return true if the item was removed from the cluster manager as a result of this call
     */
    public boolean removeItem(T item) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.removeItem(item);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Updates an item in clusters. After calling this method you must invoke {@link #cluster()} for
     * the state of the clusters to be updated on the map.
     *
     * @param item item to update in clusters
     * @return true if the item was updated in the cluster manager, false if the item is not
     * contained within the cluster manager and the cluster manager contents are unchanged
     */
    public boolean updateItem(T item) {
        final Algorithm<T> algorithm = getAlgorithm();
        algorithm.lock();
        try {
            return algorithm.updateItem(item);
        } finally {
            algorithm.unlock();
        }
    }

    /**
     * Force a re-cluster on the map. You should call this after adding, removing, updating,
     * or clearing item(s).
     */
    public void cluster() {
        Set<? extends Cluster<T>> cluster = getAlgorithm().getClusters(0);
        mRenderer.onClustersChanged(cluster);
    }

    /**
     * Might re-cluster.
     */
    @Override
    public void onCameraIdle() {
        if (mRenderer instanceof GoogleMap.OnCameraIdleListener) {
            ((GoogleMap.OnCameraIdleListener) mRenderer).onCameraIdle();
        }

        mAlgorithm.onCameraChange(mMap.getCameraPosition());

        // delegate clustering to the algorithm
        if (mAlgorithm.shouldReclusterOnMapMovement()) {
            // cluster();

            // Don't re-compute clusters if the map has just been panned/tilted/rotated.
        } else if (mPreviousCameraPosition == null || mPreviousCameraPosition.zoom != mMap.getCameraPosition().zoom) {
            mPreviousCameraPosition = mMap.getCameraPosition();
            //  cluster();
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return getMarkerManager().onMarkerClick(marker);
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        getMarkerManager().onInfoWindowClick(marker);
    }

    /**
     * Sets a callback that's invoked when a Cluster is tapped. Note: For this listener to function,
     * the ClusterManager must be added as a click listener to the map.
     */
    public void setOnClusterClickListener(OnClusterClickListener<T> listener) {
        mOnClusterClickListener = listener;
        mRenderer.setOnClusterClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when a Cluster info window is tapped. Note: For this listener to function,
     * the ClusterManager must be added as a info window click listener to the map.
     */
    public void setOnClusterInfoWindowClickListener(OnClusterInfoWindowClickListener<T> listener) {
        mOnClusterInfoWindowClickListener = listener;
        mRenderer.setOnClusterInfoWindowClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when a Cluster info window is long-pressed. Note: For this listener to function,
     * the ClusterManager must be added as a info window click listener to the map.
     */
    public void setOnClusterInfoWindowLongClickListener(OnClusterInfoWindowLongClickListener<T> listener) {
        mOnClusterInfoWindowLongClickListener = listener;
        mRenderer.setOnClusterInfoWindowLongClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when an individual ClusterItem is tapped. Note: For this
     * listener to function, the ClusterManager must be added as a click listener to the map.
     */
    public void setOnClusterItemClickListener(OnClusterItemClickListener<T> listener) {
        mOnClusterItemClickListener = listener;
        mRenderer.setOnClusterItemClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when an individual ClusterItem's Info Window is tapped. Note: For this
     * listener to function, the ClusterManager must be added as a info window click listener to the map.
     */
    public void setOnClusterItemInfoWindowClickListener(OnClusterItemInfoWindowClickListener<T> listener) {
        mOnClusterItemInfoWindowClickListener = listener;
        mRenderer.setOnClusterItemInfoWindowClickListener(listener);
    }

    /**
     * Sets a callback that's invoked when an individual ClusterItem's Info Window is long-pressed. Note: For this
     * listener to function, the ClusterManager must be added as a info window click listener to the map.
     */
    public void setOnClusterItemInfoWindowLongClickListener(OnClusterItemInfoWindowLongClickListener<T> listener) {
        mOnClusterItemInfoWindowLongClickListener = listener;
        mRenderer.setOnClusterItemInfoWindowLongClickListener(listener);
    }

    /**
     * Called when a Cluster is clicked.
     */
    public interface OnClusterClickListener<T extends CustomClusterItem> {
        /**
         * Called when cluster is clicked.
         * Return true if click has been handled
         * Return false and the click will dispatched to the next listener
         */
        boolean onClusterClick(Cluster<T> cluster, Marker marker);
    }

    /**
     * Called when a Cluster's Info Window is clicked.
     */
    public interface OnClusterInfoWindowClickListener<T extends CustomClusterItem> {
        void onClusterInfoWindowClick(Cluster<T> cluster);
    }

    /**
     * Called when a Cluster's Info Window is long clicked.
     */
    public interface OnClusterInfoWindowLongClickListener<T extends CustomClusterItem> {
        void onClusterInfoWindowLongClick(Cluster<T> cluster);
    }

    /**
     * Called when an individual ClusterItem is clicked.
     */
    public interface OnClusterItemClickListener<T extends CustomClusterItem> {

        /**
         * Called when {@code item} is clicked.
         *
         * @param item the item clicked
         * @return true if the listener consumed the event (i.e. the default behavior should not
         * occur), false otherwise (i.e. the default behavior should occur).  The default behavior
         * is for the camera to move to the marker and an info window to appear.
         */
        boolean onClusterItemClick(T item, Marker marker);
    }

    /**
     * Called when an individual ClusterItem's Info Window is clicked.
     */
    public interface OnClusterItemInfoWindowClickListener<T extends CustomClusterItem> {
        void onClusterItemInfoWindowClick(T item);
    }

    /**
     * Called when an individual ClusterItem's Info Window is long clicked.
     */
    public interface OnClusterItemInfoWindowLongClickListener<T extends CustomClusterItem> {
        void onClusterItemInfoWindowLongClick(T item);
    }
}
