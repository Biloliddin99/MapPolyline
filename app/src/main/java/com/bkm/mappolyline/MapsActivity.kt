package com.bkm.mappolyline

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.bkm.mappolyline.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.Task

private const val TAG = "MapsActivity"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    var marker: Marker? = null

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        val polyline = mMap.addPolyline(
//            PolylineOptions()
//                .add(
//                    LatLng(40.38428265640623, 71.7813047179948),
//                    LatLng(40.383130348487256, 71.78268873775045),
//                    LatLng(40.38294238149579, 71.78090775108814),
//                    LatLng(40.3840374861594, 71.78022110562797)
//                )
//                .clickable(true)
//        )

        val polygon = mMap.addPolygon(
            PolygonOptions()
                .fillColor(Color.YELLOW)
                .add(
                    LatLng(40.38428265640623, 71.7813047179948),
                    LatLng(40.383130348487256, 71.78268873775045),
                    LatLng(40.38294238149579, 71.78090775108814),
                    LatLng(40.3840374861594, 71.78022110562797)
                )
                .clickable(true)
        )


        // Add a marker in Sydney and move the camera
//        val markhamat = LatLng(40.383105831083334, 71.78261363590325)
//
//        marker = mMap.addMarker(MarkerOptions().position(markhamat))

//        mMap.setOnMapClickListener {

//            val list = polyline.points
//            list.add(it)
//            polyline.points = list
//
//        }

//        val cameraPosition = CameraPosition.Builder()
//            .target(markhamat) // Sets the center of the map to Mountain View
//            .zoom(18f)            // Sets the zoom
////                .bearing(90 f)         // Sets the orientation of the camera to east
////            .tilt(45f)            // Sets the tilt of the camera to 30 degrees
//            .build()              // Creates a CameraPosition from the builder
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


        mMap.setOnPolylineClickListener {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }

        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        val locationTask: Task<Location> = fusedLocationProviderClient.lastLocation
        locationTask.addOnSuccessListener { it: Location ->
            if (it != null) {

//                    binding.tv.text = "${it.latitude}, ${it.longitude}"
                Log.d(TAG, "getLastLocation: ${it.toString()}")
                Log.d(TAG, "getLastLocation: ${it.latitude}")
                Log.d(TAG, "getLastLocation: ${it.longitude}")

                val markhamat = LatLng(it.latitude, it.longitude)

                marker = mMap.addMarker(MarkerOptions().position(markhamat))

                val cameraPosition = CameraPosition.Builder()
                    .target(markhamat) // Sets the center of the map to Mountain View
                    .zoom(18f)            // Sets the zoom
//                .bearing(90 f)         // Sets the orientation of the camera to east
//            .tilt(45f)            // Sets the tilt of the camera to 30 degrees
                    .build()              // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            } else {
                Log.d(
                    TAG,
                    "getLastLocation: location was null,,,,,,,,,,,,,,,,,,,..............."
                )
            }
        }
        locationTask.addOnFailureListener {
            Log.d(TAG, "getLastLocation: ${it.message}")
        }

    }
}