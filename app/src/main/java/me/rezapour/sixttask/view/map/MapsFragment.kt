package me.rezapour.sixttask.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.rezapour.sixttask.R
import me.rezapour.sixttask.databinding.FragmentMapsBinding
import me.rezapour.sixttask.model.Car
import me.rezapour.sixttask.utils.DataState
import me.rezapour.sixttask.utils.IconUtil.vectorBitmap
import me.rezapour.sixttask.viewmodel.CarListViewModel


@AndroidEntryPoint
class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CarListViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var btnAvailableCars: Button
    private lateinit var navController: NavController
    private lateinit var resolutionForResult: ActivityResultLauncher<IntentSenderRequest>

    private val callback = OnMapReadyCallback { googleMap ->
        setUp(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        navController = Navigation.findNavController(view)
    }

    private fun subscribeToViewModel() {
        viewModel.carDataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> onError(dataState.messageId)
                DataState.Loading -> onLoading(true)
                is DataState.Success -> onSuccess(dataState.data)
            }
        }
    }

    private fun setUi() {
        btnAvailableCars = binding.btnAvailableCars
        btnAvailableCars.setOnClickListener {
            navController.navigate(R.id.action_mapsFragment_to_carListFragment)
        }
    }

    private fun setUp(googleMap: GoogleMap) {
        map = googleMap
        setUi()
        subscribeToViewModel()
        viewModel.loadData()
        useMap()
    }

    private fun onSuccess(cars: List<Car>) {
        addLocationsToMap(cars)
    }

    private fun onError(messageId: Int) {
        snackBar(messageId) {
            viewModel.loadData()
        }
    }

    private fun onLoading(isLoading: Boolean) {

    }


    private fun addLocationsToMap(listcars: List<Car>) {
        for (car: Car in listcars) {
            val carsPoint = LatLng(car.latitude, car.longitude)
            addPointsOnMap(carsPoint, car.modelName)
        }
    }

    private fun addPointsOnMap(latlong: LatLng, name: String) {
        map.addMarker(
            MarkerOptions().position(latlong).title(name).icon(
                context?.let {
                    vectorBitmap(
                        it,
                        R.drawable.ic_baseline_directions_car_24,
                        ContextCompat.getColor(it, R.color.orange)
                    )
                }
            )
        )
    }

    private fun changeMapCameraPosition(latlong: LatLng) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 14f))
    }

    private fun snackBar(messageId: Int) {
        Snackbar.make(binding.coordinatorMaps, messageId, Snackbar.LENGTH_LONG).show();
    }

    private fun snackBar(messageId: Int, retryAction: () -> Unit) {
        Snackbar.make(binding.coordinatorMaps, messageId, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                retryAction()
            }.show();
    }

    private fun checkGpsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun turnOnInMyLocationEnable() {
        map.isMyLocationEnabled = true

    }

    private fun useMap() {
        map.uiSettings.isMyLocationButtonEnabled = true
        if (checkGpsPermission()) {
            checkGps()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun checkGps() {
        if (checkGpsStatus(requireActivity())) {
            turnOnInMyLocationEnable()
        } else {
            turnOnGps()
        }
    }

    private fun permissionResponse() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    checkGps()
                } else
                    snackBar(
                        R.string.error_gps_not_permitted
                    ) { useMap() }
            }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        permissionResponse()
        gpsResponse()
    }


    private fun checkGpsStatus(context: Context): Boolean {
        val locationManager =
            context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    private fun gpsResponse() {
        resolutionForResult =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
                if (activityResult.resultCode == -1)
                    useMap()
                else {
                    snackBar(R.string.error_gps_turn_on_response_no) { useMap() }
                }
            }
    }


    private fun turnOnGps() {
        val locationRegest = com.google.android.gms.location.LocationRequest.create().apply {
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 1000 / 2
        }
        val locationSettingRequestBuilder = LocationSettingsRequest.Builder().apply {
            addLocationRequest(locationRegest)
            setAlwaysShow(true)
        }
        val settingClient: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task = settingClient.checkLocationSettings(locationSettingRequestBuilder.build())
        task.addOnFailureListener { e ->
            try {
                if (e is ResolvableApiException) {
                    val intentSenderRequest = IntentSenderRequest.Builder(e.resolution).build()
                    resolutionForResult.launch(intentSenderRequest)
                } else
                    snackBar(R.string.error_setting_failed)
            } catch (e: Exception) {
                snackBar(R.string.error_setting_failed)
            }
        }
    }


}