package elfak.mosis.myplaces.model

import androidx.lifecycle.ViewModel

class MyPlacesViewModel : ViewModel() {
    var myPlacesList: ArrayList<String> = ArrayList<String>()
    fun addPlace(place: String) {
        myPlacesList.add(place)
    }
}