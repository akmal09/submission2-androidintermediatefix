package com.example.submission2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.submission2.api.ListStory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.submission2.databinding.ActivityMapsBinding
import com.example.submission2.model.LoginSession
import com.example.submission2.viewmodel.HomeViewModel
import com.example.submission2.viewmodel.MapsViewModel
import com.example.submission2.viewmodel.ViewModelFactory
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object{
        const val TOKEN = "token"
    }

    private lateinit var mHomeViewModel: HomeViewModel
//    private var mMap =  ArrayList<GoogleMap>()
    private lateinit var mMap: GoogleMap
//    private var mMaps = ArrayList<GoogleMap>()
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mSession: SessionPreference
    private var getToken: String? = ""
    private var listLat = ArrayList<Double>()
    private var listLon = ArrayList<Double>()
//    private lateinit var loginSessionLocal : Login

//    private val mMapsViewModel: MapsViewModel by viewModels{
//        ViewModelFactory(this)
//    }
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val mMapsViewModel : MapsViewModel by viewModels{
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mSession = SessionPreference(this)
        mMap = googleMap

        mMapsViewModel.getMapsListStory(mSession.getSession()).observe(this,{
            var latlngarr = ArrayList<LatLng>()
            for (listStory in it) {
                val latLng = LatLng(listStory.lat,listStory.lon)
                latlngarr.add(latLng)
                mMap.addMarker(
                    MarkerOptions().position(latLng)
                        .title("Posisi ${listStory.name}")
                        .snippet("Posisi ${listStory.name}")
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            }
            Log.d("kumpulan lat lng","$latlngarr")
        })
//        mMapsViewModel.getListStory().observe(this,{
//            var latlngarr = ArrayList<LatLng>()
//            for (listStory in it) {
//                val latLng = LatLng(listStory.lat,listStory.lon)
//                latlngarr.add(latLng)
//                mMap.addMarker(
//                    MarkerOptions().position(latLng)
//                        .title("Posisi ${listStory.name}")
//                        .snippet("Posisi ${listStory.name}")
//                )
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//            }
//            Log.d("kumpulan lat lng","$latlngarr")
//        })

    }

}