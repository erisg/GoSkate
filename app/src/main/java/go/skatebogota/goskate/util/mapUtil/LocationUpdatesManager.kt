package go.skatebogota.goskate.util.mapUtil

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.location.Location
import android.os.Looper
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import go.skatebogota.goskate.data.models.LocationConfig
import go.skatebogota.goskate.util.PermissionUtils

class LocationUpdatesManager(
    val activity: FragmentActivity,
    val config: LocationConfig,
    val locationCallback: LocationManagerCallback
) {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var permissionUtils: PermissionUtils

    init {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mSettingsClient = LocationServices.getSettingsClient(activity)
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()
        permissionUtils = PermissionUtils(activity)
    }

    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest!!);
        mLocationSettingsRequest = builder.build();
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = config.interval.toLong()
        mLocationRequest!!.fastestInterval = config.fastestInterval.toLong()
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                if (locationResult != null) {
                    locationCallback.onNewLocation(locationResult.lastLocation)
                }
            }
        }
    }

    fun startLocationUpdates() {
        if (permissionUtils.checkPermission(ACCESS_FINE_LOCATION)) {
            mSettingsClient!!.checkLocationSettings(mLocationSettingsRequest).addOnSuccessListener {
                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback, Looper.myLooper()
                )
            }.addOnFailureListener {
                val rae = it as ResolvableApiException
                rae.startResolutionForResult(activity, 6)
            }
        } else {
            permissionUtils.requestPermission(arrayOf(ACCESS_FINE_LOCATION), 8)
        }

        /* mSettingsClient!!.checkLocationSettings(mLocationSettingsRequest).addOnSuccessListener {
             mFusedLocationClient!!.requestLocationUpdates(
                     mLocationRequest,
                     mLocationCallback, Looper.myLooper()
             );
         }.addOnFailureListener {
             val rae = it as ResolvableApiException
             rae.startResolutionForResult(activity, 6)
         }*/
    }


    fun stopLocationUpdates() {
        mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
    }


    interface LocationManagerCallback {

        fun onNewLocation(location: Location)
    }

}