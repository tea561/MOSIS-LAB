package elfak.mosis.myplaces
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button

class About: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        val ok:Button = findViewById(R.id.about_ok)
        ok.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                finish()
            }
        })
    }
}