package elfak.mosis.myplaces

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import elfak.mosis.myplaces.databinding.ActivityMyPlacesListBinding


class MyPlacesList : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMyPlacesListBinding

    private lateinit var listView: ListView
    private lateinit var listItems: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPlacesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        listView = findViewById(R.id.my_places_list)
        listItems = resources.getStringArray(R.array.myplaces)

        val adapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, listItems)
        listView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_my_places_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show_map -> Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show()
            R.id.action_new_place -> Toast.makeText(this, "New Place!", Toast.LENGTH_SHORT).show()
            R.id.action_about -> {
                val i: Intent = Intent(this, About::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}