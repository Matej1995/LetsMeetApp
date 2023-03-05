package cz.sandera.letsmeet.domain.location

import android.location.Location

interface LocationTracker {

    /**
     * Get user current location.
     */
    suspend fun getCurrentLocation(): Location?
}