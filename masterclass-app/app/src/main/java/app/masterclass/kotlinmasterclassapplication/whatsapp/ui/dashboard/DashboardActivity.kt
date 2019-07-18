package app.masterclass.kotlinmasterclassapplication.whatsapp.ui.dashboard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.whatsapp.adapters.SectionPagerAdapter
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.MainActivity
import app.masterclass.kotlinmasterclassapplication.whatsapp.ui.settings.SettingsActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_whatsapp_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_dashboard)

        setTitle()
        setupViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.whatsapp_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)

        if (item != null) {
            when (item.itemId) {
                R.id.settings ->
                    startActivity(Intent(this, SettingsActivity::class.java))
                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        return true
    }

    private fun setTitle() {
        supportActionBar!!.title = getString(R.string.whatsapp)
    }

    private fun setupViewPager() {
        dashboardViewPager.adapter = SectionPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(dashboardViewPager)
    }
}
