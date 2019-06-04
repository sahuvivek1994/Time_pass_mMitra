package tech.inscripts.ins_armman.mMitra.mainMenu

import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.settingActivity.Settings

class MainActivity : AppCompatActivity() {
    private var mSyncDrawable: LayerDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_activity, menu)
        val item = menu?.findItem(R.id.action_sync)
        mSyncDrawable = item?.icon as LayerDrawable
        /*Utility.setBadgeCount(this, mSyncDrawable, 0)
        mPresenter.fetchUnsentFormsCount()
*/
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.getItemId()) {
                R.id.action_settings -> startActivity(Intent(this@MainActivity, Settings::class.java))

                // R.id.action_sync -> mPresenter.fetchRegistrationData()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}