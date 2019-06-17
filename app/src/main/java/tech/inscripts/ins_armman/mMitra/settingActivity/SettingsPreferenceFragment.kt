package tech.inscripts.ins_armman.mMitra.settingActivity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.support.design.widget.Snackbar
import android.support.v14.preference.PreferenceFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.utility.Utility

class SettingsPreferenceFragment:PreferenceFragment(),ISettingsView{


    var mListPreference:ListPreference?=null
    var mSettingsPresentor : SettingsPresentor?=null
    var mSettingsInteractor : SettingsInteractor?=null
    var mProgressBar:ProgressBar?=null
    var mPreferencesyncForm: Preference?=null
    var mPreferenceVersion : Preference?=null
    var mPreferenceCheckUpdate : Preference?=null
    var mPreferenceHelpManual : Preference?=null
    var mPreferenceRestoreData : Preference?=null
    var mProgressDialog:AlertDialog?=null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
     setPreferencesFromResource(R.xml.pref_settings,rootKey)
        linkViewsId()
        mSettingsPresentor = SettingsPresentor()
        mSettingsPresentor!!.attachView(this)
    }
    fun linkViewsId(){
        mListPreference=findPreference("language") as ListPreference
        mListPreference!!.setOnPreferenceChangeListener(Preference.OnPreferenceChangeListener { preference, value ->
            mSettingsPresentor?.changeLanguage(activity.applicationContext, value.toString())
            activity.recreate()
            true
        })

        mPreferencesyncForm= findPreference("sync_form") as Preference
        mPreferencesyncForm!!.setOnPreferenceClickListener(Preference.OnPreferenceClickListener {
            mSettingsPresentor?.downloadForms()
            false
        })

        mPreferenceCheckUpdate=findPreference("check_update") as Preference
        mPreferenceCheckUpdate!!.setOnPreferenceClickListener(Preference.OnPreferenceClickListener {
            mSettingsPresentor?.checkUpdate()
            false
        })

        mPreferenceHelpManual=findPreference("help_manual") as Preference
        mPreferenceHelpManual!!.setOnPreferenceClickListener(Preference.OnPreferenceClickListener {
            mSettingsPresentor?.downloadHelpManual()
            false
        })

        mPreferenceCheckUpdate = findPreference("logout") as Preference
        mPreferenceCheckUpdate!!.setOnPreferenceClickListener(Preference.OnPreferenceClickListener {
            mSettingsPresentor?.logout()
            false
        })
        mPreferenceRestoreData = findPreference("restore_data") as Preference
        mPreferenceRestoreData?.setOnPreferenceClickListener(Preference.OnPreferenceClickListener {
            val builder = android.support.v7.app.AlertDialog.Builder(context)
            builder.setTitle(R.string.restore_warning_text)
                .setMessage(R.string.dialog_msg_loss_data_warning)
                .setPositiveButton(
                    R.string.ok
                ) { dialog, which -> mSettingsPresentor!!.restoreData() }.setNegativeButton(
                    R.string.cancel
                ) { dialogInterface, i -> }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
            false
        })
        mPreferenceVersion = findPreference("version") as Preference
        mPreferenceVersion!!.setSummary(Utility.getAppVersionName(activity))
    }

    override fun showProgressBar(label: String) {
      var inflater : LayoutInflater= activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var dialogView : View=inflater.inflate(R.layout.progress_dialog_layout,null)
        var textView: TextView=dialogView.findViewById(R.id.textView_label)
        textView.setText(label)
        var mAlertDialogBuilder : AlertDialog.Builder= AlertDialog.Builder(activity)
        mAlertDialogBuilder.setView(dialogView)
        mAlertDialogBuilder.setCancelable(false)
        mProgressDialog = mAlertDialogBuilder.create()
        mProgressDialog!!.show()
    }

    override fun hideProgressBar() {
        if (mProgressDialog != null) mProgressDialog!!.dismiss()
    }

    override fun showDialog(title: String, message: String) {
        var builder:AlertDialog.Builder
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            builder= AlertDialog.Builder(activity,android.R.style.ThemeOverlay_Material_Dialog_Alert)
        }
        else
            builder=AlertDialog.Builder(activity)
    builder.setTitle(title)
        .setPositiveButton(android.R.string.yes,DialogInterface.OnClickListener { dialog, which ->  })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show()
    }

    override fun updateAvailable(url: String) {
        android.support.v7.app.AlertDialog.Builder(context).setMessage(getString(R.string.dialog_update_available))
            .setPositiveButton(
                getString(R.string.dialog_install_text)
            ) { dialogInterface, i -> mSettingsPresentor!!.downloadApk(url) }
            .setNegativeButton(
                getString(R.string.cancel)
            ) { dialogInterface, i -> }
            .show()
    }

    override fun showApkDownloadProgress() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.progress_dialog_layout, null)
        val textView = dialogView.findViewById<TextView>(R.id.textView_label)
        mProgressBar = dialogView.findViewById<ProgressBar>(R.id.progressBar)
        textView.setText(R.string.apk_download_progress_title)
       // mProgressBar.setIndeterminate(false)
       var mAlertDialogBuilder : AlertDialog.Builder=AlertDialog.Builder(context)
        mAlertDialogBuilder.setView(dialogView)
        mAlertDialogBuilder.setCancelable(false)
        mProgressDialog=mAlertDialogBuilder.create()
        mProgressDialog!!.show()
    }

    override fun updateApkDownloadProgress(progress: Int) {
        mProgressBar?.setProgress(progress)
    }
    override fun dissmissApkDownloadProgress() {
        if (mProgressDialog != null) mProgressDialog!!.dismiss()
    }

    override fun showSnackBar(message: String) {
        val snackbar = Snackbar
            .make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)

        snackbar.show()
    }

    override fun getContext(): Context {
        return activity
    }
}