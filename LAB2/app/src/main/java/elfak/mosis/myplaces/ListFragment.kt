package elfak.mosis.myplaces

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplaces.data.MyPlaces
import elfak.mosis.myplaces.databinding.FragmentListBinding
import elfak.mosis.myplaces.model.MyPlacesViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private lateinit var listView: ListView
    //private val myPlacesViewModel: MyPlacesViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val myPlacesViewModel: MyPlacesViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_place -> {
                this.findNavController().navigate(R.id.action_ListFragment_to_EditFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_my_places_list)
        item.isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = requireView().findViewById(R.id.my_places_list)

        listView.adapter = ArrayAdapter<MyPlaces>(view.context, android.R.layout.simple_list_item_1, myPlacesViewModel.myPlacesList)
        listView.setOnItemClickListener(object: AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var myPlace:MyPlaces = p0?.adapter?.getItem(p2) as MyPlaces
                myPlacesViewModel.selected = myPlace
                view.findNavController().navigate(R.id.action_ListFragment_to_ViewFragment)
            }
        })

        listView.setOnCreateContextMenuListener(object: View.OnCreateContextMenuListener {
            override fun onCreateContextMenu(
                menu: ContextMenu,
                v: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
            ) {
                val info = menuInfo as AdapterView.AdapterContextMenuInfo
                val myPlace: MyPlaces = myPlacesViewModel.myPlacesList[info.position]
                menu.setHeaderTitle(myPlace.name)
                menu.add(0, 1, 1, "View Place")
                menu.add(0, 2, 2, "Edit Place")
                menu.add(0, 3, 3, "Delete Place")
            }
        })
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        if (item.itemId == 1) {
            myPlacesViewModel.selected = myPlacesViewModel.myPlacesList[info.position]
            this.findNavController().navigate(R.id.action_ListFragment_to_ViewFragment)
        } else if (item.itemId == 2) {
            myPlacesViewModel.selected = myPlacesViewModel.myPlacesList[info.position]
            this.findNavController().navigate(R.id.action_ListFragment_to_EditFragment)
        } else if (item.itemId == 3) {
            myPlacesViewModel.myPlacesList.removeAt(info.position)
            val myPlacesList: ListView = requireView().findViewById<ListView>(R.id.my_places_list)
            myPlacesList.adapter = this@ListFragment.context?.let { ArrayAdapter<MyPlaces>(it, android.R.layout.simple_list_item_1, myPlacesViewModel.myPlacesList)}
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}