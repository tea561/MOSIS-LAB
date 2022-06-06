package elfak.mosis.myplaces

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
    lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var ctx: Context? = getActivity()?.getApplicationContext()
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        map = requireView().findViewById<MapView>(R.id.map)

        map.setMultiTouchControls(true)
        if(ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissionLauncher.launch(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            setMyLocationOverlay()
        }
        map.controller.setZoom(15.0)
        val startPoint = GeoPoint(43.3209, 21.8958)
        map.controller.setCenter(startPoint)
    }

    private fun setMyLocationOverlay() {
        var myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(activity), map)
        myLocationOverlay.enableMyLocation()
        map.overlays.add(myLocationOverlay)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if(isGranted){
                setMyLocationOverlay()
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_place -> {
                this.findNavController().navigate(R.id.action_MapFragment_to_EditFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        var item = menu.findItem(R.id.action_my_places_list)
        item.isVisible = false
        item = menu.findItem(R.id.action_show_map)
        item.isVisible = false
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}