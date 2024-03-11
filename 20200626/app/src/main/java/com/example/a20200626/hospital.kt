package com.example.a20200626

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_hospital.*

class hospital : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val IMF = LatLng(24.787745, 121.0001433)
        mMap.addMarker(MarkerOptions().position(IMF).title("NCTU IMF"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(IMF, 18F))

        mMap.addMarker(MarkerOptions().position(LatLng(24.782,121.021)).title("旺鑫寵物醫院"))
        mMap.addMarker(MarkerOptions().position(LatLng(24.802,120.986)).title("台大安欣動物醫院"))
        mMap.addMarker(MarkerOptions().position(LatLng(24.815,120.974)).title("築心動物醫院"))
        mMap.addMarker(MarkerOptions().position(LatLng(24.813,120.971)).title("信安動物醫院"))
        mMap.addMarker(MarkerOptions().position(LatLng(24.794,121.002)).title("懷生動物醫院"))
        mMap.addMarker(MarkerOptions().position(LatLng(24.781,121.024)).title("新方伴侶動物醫院"))

    }
}
