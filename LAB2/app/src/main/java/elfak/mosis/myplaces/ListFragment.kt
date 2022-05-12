package elfak.mosis.myplaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplaces.databinding.FragmentListBinding
import elfak.mosis.myplaces.model.MyPlacesViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private lateinit var listView: ListView
    private val myPlacesViewModel: MyPlacesViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPlacesViewModel.addPlace("Tvrdjava")
        myPlacesViewModel.addPlace("Cair")
        myPlacesViewModel.addPlace("Park Svetog Save")
        myPlacesViewModel.addPlace("Trg Kralja Milana")

        listView = requireView().findViewById(R.id.my_places_list)

        val adapter: ArrayAdapter<String> = ArrayAdapter(view.context,
            android.R.layout.simple_list_item_1, android.R.id.text1, myPlacesViewModel.myPlacesList)
        listView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}