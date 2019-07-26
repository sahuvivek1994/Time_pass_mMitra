package tech.inscripts.ins_armman.mMitra

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import tech.inscripts.ins_armman.mMitra.completeForms.CompleteFormActivity
import tech.inscripts.ins_armman.mMitra.forms.EnrollmentQuestions
import tech.inscripts.ins_armman.mMitra.incompleteForms.IncompleteFormActivity
import tech.inscripts.ins_armman.mMitra.settingActivity.Settings
import android.support.v4.os.HandlerCompat.postDelayed
import android.support.v7.app.AlertDialog
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.widget.*
import tech.inscripts.ins_armman.mMitra.data.model.ArogyasakhiInfoModel
import tech.inscripts.ins_armman.mMitra.menu.HomeActivityPresentor
import tech.inscripts.ins_armman.mMitra.menu.IHomeActivityView
import tech.inscripts.ins_armman.mMitra.settingActivity.SettingsActivity
import tech.inscripts.ins_armman.mMitra.utility.Utility


class HomeActivity : AppCompatActivity(),IHomeActivityView,View.OnClickListener {



    var mPresenter: HomeActivityPresentor ?=null
    var utility : Utility= Utility()
      var mSyncDrawable: LayerDrawable? = null
      var mProgressDialog: AlertDialog? = null
      var sheetBehavior: BottomSheetBehavior<*>? = null
      var toggleView: ImageView? = null
      var bottomSheet: ConstraintLayout? = null
      var swipeUpHint: TextView? = null
      var womenCountTextView: TextView? = null
      var childCountTextView: TextView? = null
      var totalVisitCountTextView: TextView? = null
      var inCompleteVisitCountTextView: TextView? = null
      var dueVisitCountTextView: TextView? = null
      var overdueVisitCountTextView: TextView? = null
      var referralCountTextView: TextView? = null
      var notReferredCountTextView: TextView? = null


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
        mPresenter = HomeActivityPresentor()
        mPresenter?.attachView(this)

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
        utility.setBadgeCount(this, mSyncDrawable!!, 0)
        mPresenter?.fetchUnsentFormsCount()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.getItemId()) {
                R.id.action_settings -> startActivity(Intent(this@HomeActivity, Settings::class.java))

                 R.id.action_sync -> mPresenter?.fetchRegistrationData()
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

    /*override fun setUnregisteredCount(count: Int) {
        val childCareTitle = getString(R.string.child)
        if (count > 0) {
            val spannableString = SpannableString(String.format("%s - %s", childCareTitle, count))
            spannableString.setSpan(
                ForegroundColorSpan(Color.RED),
                childCareTitle.length,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            mButtonChildCare.setText(spannableString)
        } else {
            mButtonChildCare.setText(childCareTitle)
        }
    }*/



    override fun showFormUpdateErrorDialog() {
        val builder = AlertDialog.Builder(getContext())
        builder.setTitle(R.string.restore_warning_text)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage(R.string.update_forms_message)
            .setCancelable(false)
            .setPositiveButton(
                R.string.ok
            ) { dialog, which -> startActivity(Intent(this@HomeActivity, SettingsActivity::class.java)) }
            .show()
    }
    override fun getContext(): Context {
        return this    }
    }
