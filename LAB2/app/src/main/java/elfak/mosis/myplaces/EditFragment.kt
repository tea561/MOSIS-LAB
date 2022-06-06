package elfak.mosis.myplaces

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplaces.data.MyPlaces
import elfak.mosis.myplaces.model.MyPlacesViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {
    private val myPlacesViewModel: MyPlacesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (myPlacesViewModel.selected != null) {
            (activity as AppCompatActivity).supportActionBar?.title = "Edit Place"
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editName: EditText = requireView().findViewById<EditText>(R.id.editmyplace_name_edit)
        val editDesc: EditText = requireView().findViewById<EditText>(R.id.editmyplace_desc_edit)
        val editLongitude: EditText = requireView().findViewById<EditText>(R.id.editmyplace_longitude_edit)
        val editLatitude: EditText = requireView().findViewById<EditText>(R.id.editmyplace_latitude_edit)

        if (myPlacesViewModel.selected != null) {
            editName.setText(myPlacesViewModel.selected?.name)
            editDesc.setText(myPlacesViewModel.selected?.description)
        }

        val addButton: Button = requireView().findViewById<Button>(R.id.editmyplace_finished_button)
        addButton.isEnabled = false

        if (myPlacesViewModel.selected != null) {
            addButton.setText(R.string.editmyplace_save_label)
        }
        editName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addButton.isEnabled = (editName.text.length > 0)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        addButton.setOnClickListener{
            val name: String = editName.text.toString()
            val editDesc: EditText = requireView().findViewById<EditText>(R.id.editmyplace_desc_edit)
            val desc: String = editDesc.text.toString()
            val longitude: String = editLongitude.text.toString()
            val latitude: String = editLatitude.text.toString()
            if (myPlacesViewModel.selected != null) {
                myPlacesViewModel.selected?.name = name
                myPlacesViewModel.selected?.description = desc
                myPlacesViewModel.selected?.longitude = longitude
                myPlacesViewModel.selected?.latitude = latitude
            } else {
                myPlacesViewModel.addPlace(MyPlaces(name, desc, longitude, latitude))
            }
            findNavController().popBackStack()
        }
       val cancelButton: Button = requireView().findViewById<Button>(R.id.editmyplace_cancel_button)
       cancelButton.setOnClickListener{
           findNavController().popBackStack()
       }
    }

    override fun onDestroyView() {
         super.onDestroyView()
        myPlacesViewModel.selected = null
    }
}