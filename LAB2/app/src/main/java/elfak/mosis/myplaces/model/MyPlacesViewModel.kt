package elfak.mosis.myplaces.model

import androidx.lifecycle.ViewModel
import elfak.mosis.myplaces.data.MyPlaces

class MyPlacesViewModel : ViewModel() {
    var myPlacesList: ArrayList<MyPlaces> = ArrayList<MyPlaces>()
    fun addPlace(place: MyPlaces) {
        myPlacesList.add(place)
    }
    var selected: MyPlaces? = null
}