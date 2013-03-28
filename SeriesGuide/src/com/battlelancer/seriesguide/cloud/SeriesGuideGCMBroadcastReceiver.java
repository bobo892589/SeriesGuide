
package com.battlelancer.seriesguide.cloud;

import android.content.Context;

import com.google.android.gcm.GCMBroadcastReceiver;

/**
 * Allows us to use a {@link GCMIntentService} in a non-root package, also named
 * differently from the app flavor package.
 */
public class SeriesGuideGCMBroadcastReceiver extends GCMBroadcastReceiver {

    @Override
    protected String getGCMIntentServiceClassName(Context context) {
        return "com.battlelancer.seriesguide.cloud.GCMIntentService";
    }
}
