package id.shobrun.ukmmobile.ui.myevents.detail

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerFragment
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.databinding.FragmentEventDetailBinding
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailActivity.Companion.EXTRA_EVENT
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailActivity.Companion.currentEventId
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailActivity.Companion.isNewEvent
import id.shobrun.ukmmobile.ui.myevents.scanner.ScannerActivity
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject

class EventDetailFragment : DaggerFragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    companion object {
        fun newInstance() = EventDetailFragment()
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        const val TAG = "EventDetailFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EventDetailViewModel by viewModels { viewModelFactory }
    private val viewModelMain: EventDetailMainViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentEventDetailBinding
    private var event: Event? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_event_detail, container, false)
            event= arguments?.getParcelable(EXTRA_EVENT)

        with(binding) {
            lifecycleOwner = this@EventDetailFragment
            vm = viewModel
        }
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        viewModel.snackbarText.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.root.snackbar(it).show()
                viewModel.postSnackbarText("")
            }
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    isNewEvent = false
                    currentEventId = viewModel.eventIdNew.value
                }
            }

        })
        viewModel.isSuccessLoad.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    val location =
                        LatLng(viewModel.eventLatitude.value!!, viewModel.eventLongitude.value!!)
                    val markerOptions = MarkerOptions().position(location)
                    cameraLocation(markerOptions, location)
                }

            }
        })

        viewModel.isUpdateLocation.observe(viewLifecycleOwner, Observer {
            it?.let {
                /**
                 * When Ready
                 */
                if (it && !isNewEvent) {
                    setUpMap()
                }
            }
        })
        viewModel.postEventId(event?.event_id ?: currentEventId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_event_detail, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.scan -> {
                if (isNewEvent) {
                    binding.root.snackbar(getString(R.string.seo_info_has_create_event))
                    return true
                }
                val scan = intentFor<ScannerActivity>(
                    EXTRA_EVENT to event
                )
                startActivity(scan)
                true
            }
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }

            else -> true
        }
    }


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location


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
        viewModel.postMap(googleMap)

        viewModel.map.value?.uiSettings?.isZoomControlsEnabled = true
        viewModel.map.value?.setOnMarkerClickListener(this)

        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?) = false

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        viewModel.map.value?.isMyLocationEnabled = true
        viewModel.map.value?.mapType = GoogleMap.MAP_TYPE_NORMAL

        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
            }
        }
    }

    private fun placeMarkerOnMap(location: LatLng) {
        /**
         * Location Set Up
         */
        var markerOptions: MarkerOptions
        if (EventDetailActivity.isNewEvent || viewModel.isUpdateLocation.value ?: false) {
            viewModel.eventLatitude.value = location.latitude
            viewModel.eventLongitude.value = location.longitude
            markerOptions = MarkerOptions().position(location)
            cameraLocation(markerOptions, location)
        }
    }

    private fun cameraLocation(markerOptions: MarkerOptions, location: LatLng) {
        val titleStr: String? = null
        markerOptions.title(titleStr ?: "Event Location")
        viewModel.map.value?.clear()
        viewModel.map.value?.addMarker(markerOptions)
        viewModel.map.value?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
