package com.app.google_maps_wear_activity_kotlin

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.wear.widget.SwipeDismissFrameLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : WearableActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    public override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        setAmbientEnabled()

        setContentView(R.layout.activity_maps)

        swipe_dismiss_root_container.addCallback(object : SwipeDismissFrameLayout.Callback() {
            override fun onDismissed(layout: SwipeDismissFrameLayout?) {
                layout?.visibility = View.GONE
                finish()
            }
        })

        swipe_dismiss_root_container.setOnApplyWindowInsetsListener { _, insetsArg ->
            val insets = swipe_dismiss_root_container.onApplyWindowInsets(insetsArg)

            val params = map_container.layoutParams as FrameLayout.LayoutParams

            params.setMargins(
                    insets.systemWindowInsetLeft,
                    insets.systemWindowInsetTop,
                    insets.systemWindowInsetRight,
                    insets.systemWindowInsetBottom)
            map_container.layoutParams = params

            insets
        }

        val mapFragment = map as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(applicationContext, R.string.intro_text, duration)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
