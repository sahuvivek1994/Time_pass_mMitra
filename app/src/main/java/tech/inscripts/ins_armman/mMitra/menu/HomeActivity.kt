package tech.inscripts.ins_armman.mMitra

import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import tech.inscripts.ins_armman.mMitra.completeForms.CompleteFormActivity
import tech.inscripts.ins_armman.mMitra.forms.EnrollmentQuestions
import tech.inscripts.ins_armman.mMitra.incompleteForms.IncompleteFormActivity
import tech.inscripts.ins_armman.mMitra.settingActivity.Settings
import android.widget.Toast
import android.support.v4.os.HandlerCompat.postDelayed





class HomeActivity : AppCompatActivity(),View.OnClickListener {

    private var mSyncDrawable: LayerDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val button_registration =findViewById<ImageButton>(R.id.btnRegistration)
        val button_incomplete_form =findViewById<ImageButton>(R.id.btnIncompleteForm)
        val button_complete_form =findViewById<ImageButton>(R.id.btnCompleteForm)
        val button_user_profile =findViewById<ImageButton>(R.id.btnUserProfile)
        button_registration.setOnClickListener(this)
        button_incomplete_form.setOnClickListener(this)
        button_complete_form.setOnClickListener(this)
        button_user_profile.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.btnRegistration -> startActivity(Intent(this@HomeActivity, EnrollmentQuestions::class.java))

            R.id.btnIncompleteForm -> startActivity(Intent(this@HomeActivity, IncompleteFormActivity::class.java))

            R.id.btnCompleteForm -> startActivity(Intent(this@HomeActivity, CompleteFormActivity::class.java))

            R.id.btnUserProfile -> startActivity(Intent(this@HomeActivity, UserProfileActivity::class.java))
        }
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
                R.id.action_settings -> startActivity(Intent(this@HomeActivity, Settings::class.java))

                // R.id.action_sync -> mPresenter.fetchRegistrationData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

    }
}