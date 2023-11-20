/*
 * Copyright 2023 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.clustering.view;

import androidx.annotation.StyleRes;

import com.example.clustering.CustomClusterManager;
import com.example.clustering.model.CustomClusterItem;
import com.google.maps.android.clustering.Cluster;

import java.util.Set;

/**
 * Renders clusters.
 */
public interface CustomClusterRenderer<T extends CustomClusterItem> {

    /**
     * Called when the view needs to be updated because new clusters need to be displayed.
     *
     * @param clusters the clusters to be displayed.
     */
    void onClustersChanged(Set<? extends Cluster<T>> clusters);

    void setOnClusterClickListener(CustomClusterManager.OnClusterClickListener<T> listener);

    void setOnClusterInfoWindowClickListener(CustomClusterManager.OnClusterInfoWindowClickListener<T> listener);

    void setOnClusterInfoWindowLongClickListener(CustomClusterManager.OnClusterInfoWindowLongClickListener<T> listener);

    void setOnClusterItemClickListener(CustomClusterManager.OnClusterItemClickListener<T> listener);

    void setOnClusterItemInfoWindowClickListener(CustomClusterManager.OnClusterItemInfoWindowClickListener<T> listener);

    void setOnClusterItemInfoWindowLongClickListener(CustomClusterManager.OnClusterItemInfoWindowLongClickListener<T> listener);

    /**
     * Called to set animation on or off
     */
    void setAnimation(boolean animate);

    /**
     * Sets the length of the animation in milliseconds.
     */
    void setAnimationDuration(long animationDurationMs);

    /**
     * Called when the view is added.
     */
    void onAdd();

    /**
     * Called when the view is removed.
     */
    void onRemove();

    /**
     * Called to determine the color of a Cluster.
     */
    int getColor(int clusterSize);

    /**
     * Called to determine the text appearance of a cluster.
     */
    @StyleRes
    int getClusterTextAppearance(int clusterSize);
}
