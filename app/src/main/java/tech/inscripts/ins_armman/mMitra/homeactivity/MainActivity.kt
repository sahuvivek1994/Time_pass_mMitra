package tech.inscripts.ins_armman.mMitra.homeactivity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_homeactivity.*
import kotlinx.android.synthetic.main.content_homeactivity.*
import kotlinx.android.synthetic.main.reg_alert_layout.view.*
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.completeforms.CompleteFormActivity
import tech.inscripts.ins_armman.mMitra.displayform.displayForm
import tech.inscripts.ins_armman.mMitra.forms.EnrollmentQuestions
import tech.inscripts.ins_armman.mMitra.incompleteforms.IncompleteFormActivity
import tech.inscripts.ins_armman.mMitra.settingactivity.Settings
import tech.inscripts.ins_armman.mMitra.settingactivity.SettingsActivity
import tech.inscripts.ins_armman.mMitra.settingactivity.SettingsPresentor
import tech.inscripts.ins_armman.mMitra.userprofile.UserProfileActivity
import tech.inscripts.ins_armman.mMitra.utility.Constants
import tech.inscripts.ins_armman.mMitra.utility.Utility
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,IMainActivity,View.OnClickListener {

    var settingsPresentor :SettingsPresentor?=null
    var mPresenter: MainActivityPresentor?=null
    var utility : Utility = Utility()
    var mSyncDrawable: LayerDrawable? = null
    var mProgressDialog: android.support.v7.app.AlertDialog? = null
    var userDetails = ArrayList<String>()
    private var name : String=""
    private var phone : String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homeactivity)
        val toolbar: android.support.v7.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.home)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_navigation_icon)
        setSupportActionBar(toolbar)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            openForms()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        btnRegistration.setOnClickListener(this)
        btnIncompleteForm.setOnClickListener(this)
        btnCompleteForm.setOnClickListener(this)
        btnUserProfile.setOnClickListener(this)
        mPresenter = MainActivityPresentor()
        mPresenter?.attachView(this)
        mPresenter?.getLoginDetail(userDetails)
        val name = userDetails.get(0)
        var navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView?.setNavigationItemSelectedListener(this)
        var header : View= navigationView!!.getHeaderView(0)
        var textUserName : TextView = header.findViewById(R.id.navUserName)
        textUserName.text=name
        var drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        var mDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name)
        mDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE)
        drawerLayout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_activity, menu)
        val item = menu?.findItem(R.id.action_sync)
        mSyncDrawable = item?.icon as LayerDrawable
        utility.setBadgeCount(this, mSyncDrawable!!, 0)
        mPresenter?.fetchUnsentFormsCount()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.fetchUnsentFormsCount()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.getItemId()) {
                R.id.action_settings -> startActivity(Intent(this@MainActivity, Settings::class.java))

                R.id.action_sync -> mPresenter?.fetchRegistrationData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_registration -> { openForms() }
            R.id.nav_complete -> {
                startActivity(Intent(this,CompleteFormActivity::class.java))
            }
            R.id.nav_incomplete -> {
                startActivity(Intent(this,IncompleteFormActivity::class.java))
            }
            R.id.nav_updateForms -> {
                mPresenter?.downloadForms()
            }
            R.id.nav_restoreData -> {
                val builder = AlertDialog.Builder(getContext())
                builder.setTitle(R.string.restore_data)
               // builder.setTitle("NOTICE")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(R.string.dialog_msg_loss_data_warning)
                    //.setMessage("This Functionality in in progress..")
                    .setCancelable(false)
                    .setPositiveButton(
                        R.string.ok
                    ) { dialog, which -> mPresenter?.restoreData()
                    }
                    .setNegativeButton(
                        R.string.cancel
                    ) { dialog, which -> dialog.cancel() }
                    .show()

            }

            R.id.nav_checkUpdate -> {
                mPresenter?.checkUpdate()
                          }

            R.id.nav_logout -> {
                val builder = AlertDialog.Builder(getContext())
                builder.setTitle(R.string.logout)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(R.string.logout_message)
                    .setCancelable(false)
                    .setPositiveButton(
                        R.string.ok
                    ) { dialog, which -> mPresenter?.logout() }
                    .setNegativeButton(
                        R.string.cancel
                    ) { dialog, which -> dialog.cancel() }
                    .show()

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun showSnackBar(message: String) {
        val snackbar = Snackbar
            .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)

        snackbar.show()
    }

    override fun setUnsentFormsCount(count: Int) {
        if (mSyncDrawable != null) utility.setBadgeCount(this, mSyncDrawable!!, count)
    }

    override fun showProgressBar(label: String) {
        var inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var dialogView = inflater.inflate(R.layout.progress_dialog_layout, null)
        var textView = dialogView.findViewById<TextView>(R.id.textView_label)
        textView.text = label
        var mAlertDialogBuilder = AlertDialog.Builder(this)
        mAlertDialogBuilder.setView(dialogView)
        mAlertDialogBuilder.setCancelable(false)
        mProgressDialog = mAlertDialogBuilder.create()
        mProgressDialog?.show()
       }

    override fun hideProgressBar() {
        if (mProgressDialog != null) mProgressDialog?.dismiss()
    }

    override fun showFormUpdateErrorDialog() {
        val builder = AlertDialog.Builder(getContext())
        builder.setTitle(R.string.restore_warning_text)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage(R.string.update_forms_message)
            .setCancelable(false)
            .setPositiveButton(
                R.string.ok
            ) { dialog, which -> startActivity(Intent(this, SettingsActivity::class.java)) }
            .show()
    }

    override fun getContext(): Context {
        return this    }

    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.btnRegistration -> openForms()//startActivity(Intent(this, EnrollmentQuestions::class.java))

            R.id.btnIncompleteForm -> startActivity(Intent(this, IncompleteFormActivity::class.java))

            R.id.btnCompleteForm -> startActivity(Intent(this, CompleteFormActivity::class.java))

            R.id.btnUserProfile -> startActivity(Intent(this, UserProfileActivity::class.java))
        }
    }

    /**
     * this method is used for asking user whether the first form is being filled or from 2nd form the form filling will be donw
     */
    private fun openForms(){
        val builder = AlertDialog.Builder(getContext())
        builder.setTitle(this.getString(R.string.confirm_dialog))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage(this.getString(R.string.confirm_Reg))
            .setPositiveButton(this.getString(R.string.confirm_Reg_option1)) {
                    dialog, which ->
                val intent = Intent(this@MainActivity, EnrollmentQuestions::class.java)
                intent.putExtra("NormalRegFlag", 100)
                startActivity(intent)
            }
            .setNegativeButton(this.getString(R.string.confirm_Reg_option2)){
                dialog,which->
                askWomanName()
            }
            .show()
    }

    /**
     * if woman's 2nd form is being filled first then to keep her track this method is called where woman's name is asked
     * and her name and unique id will be stored.
     */
    private fun askWomanName() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.reg_alert_layout, null)
        var etName: EditText = mDialogView.findViewById(R.id.dialogNameEt)
        var etPhone: EditText = mDialogView.findViewById(R.id.dialogPhoneEt)
        val builder = AlertDialog.Builder(getContext())
            .setView(mDialogView)
        val mAlertDialog = builder.show()
        mDialogView.btnContinue.setOnClickListener {
            name = etName.text.toString()
            phone = etPhone.text.toString()
            if (name != null && phone != null) {
                if (phone.length ==10) {
                    mAlertDialog.dismiss()
                    val intent = Intent(this@MainActivity, displayForm::class.java)
                    intent.putExtra(Constants.UNIQUE_ID, "0")
                    intent.putExtra(Constants.FORM_ID, "2")
                    intent.putExtra(Constants.NAME, name)
                    intent.putExtra("phone", phone)
                    startActivity(intent)
                } else {
                    etPhone.error = this.getString(R.string.valid_phone)
                }
            }else if(name.isEmpty() && !phone.isEmpty() ){
                etName.error = this.getString(R.string.valid_name)
            }
            else if(phone.isEmpty() && !name.isEmpty()){
                etPhone.error = this.getString(R.string.valid_phone)
            }
            else{
                etName.error = this.getString(R.string.valid_name)
                etPhone.error = this.getString(R.string.valid_phone)
            }
        }
        mDialogView.btnCancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
    override fun updateAvailable(url: String) {
        android.app.AlertDialog.Builder(this)
            .setMessage(getString(R.string.dialog_update_available))
            .setPositiveButton(getString(R.string.dialog_install_text), DialogInterface.OnClickListener { dialog, which ->
                settingsPresentor!!.downloadApk(url)
            })
            .setNegativeButton(getString(R.string.cancel),DialogInterface.OnClickListener { dialog, which ->  })
            .show()
    }

    override fun alertForRegistration() {
        val builder = AlertDialog.Builder(getContext())
        builder.setTitle("REGISTRAtION ALERT")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("You haven't filled registration form of this woman. To sync the data on server please fill registration form first.")
            .setCancelable(false)
            .setPositiveButton(
                R.string.ok
            ) { dialog, which -> }
            .show()

    }
}
