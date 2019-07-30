package tech.inscripts.ins_armman.mMitra.settingActivity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.utility.Utility

class MainPreferenceFragment : PreferenceFragment(),ISettingsView {
val utility= Utility()
     var settingsPresentor = SettingsPresentor()
    var listPreference : ListPreference? = null
    var preferenceSyncForm : Preference? = null
    var preferenceCheckUpdate : Preference? = null
    var preferenceHelpManual : Preference? = null
    var preferenceRestoreData : Preference? = null
    var preferenceVersion : Preference? = null
    lateinit var mProgressBar : ProgressBar
    lateinit var alertDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_settings)
        initId()
       settingsPresentor = SettingsPresentor()
        settingsPresentor.attachView(this)
    }
@SuppressLint("NewApi")
fun initId() {

    listPreference = findPreference("language") as ListPreference
    listPreference!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
        settingsPresentor?.changeLanguage(activity.applicationContext, value.toString())
        activity.recreate()
        true
    }

    preferenceSyncForm = findPreference("sync_form") as Preference
    preferenceSyncForm!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
        settingsPresentor?.downloadForms()
        false
    }

    preferenceCheckUpdate = findPreference("check_update") as Preference
    preferenceCheckUpdate!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
        settingsPresentor?.checkUpdate()
        false
    }

    preferenceCheckUpdate = findPreference("logout") as Preference
    preferenceCheckUpdate!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
        settingsPresentor?.logout()
        false
    }
    preferenceRestoreData=findPreference("restore_data") as Preference
    preferenceRestoreData?.onPreferenceClickListener=Preference.OnPreferenceClickListener {
    var builder : AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.restore_warning_text)
            .setMessage(R.string.dialog_msg_loss_data_warning)
            .setPositiveButton(R.string.ok) {
                    dialog, which -> settingsPresentor!!.restoreData()
            }
            .setNegativeButton(R.string.cancel) {
                    dialogInterface, i ->

            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
        false
    }

    preferenceVersion = findPreference("version")
    preferenceVersion!!.summary = utility.getAppVersionName(activity)
}

    override fun getContext(): Context {
        return activity
    }

    override fun showProgressBar(label: String) {
        var inflater : LayoutInflater=activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var dialogview : View = inflater.inflate(R.layout.progress_dialog_layout,null)
        var textView : TextView = dialogview.findViewById(R.id.textView_label)
        var alertDialogBuilder : AlertDialog.Builder = AlertDialog.Builder(activity)
        alertDialogBuilder.setView(dialogview)
        alertDialogBuilder.setCancelable(false)
        alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun hideProgressBar() {
    if(alertDialog!=null)
        alertDialog.dismiss()
    }

    override fun showDialog(title: String, message: String) {
    var builder : AlertDialog.Builder?=null
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            builder= AlertDialog.Builder(context,android.R.style.ThemeOverlay_Material_Dialog_Alert)
        }
        else{
            builder= AlertDialog.Builder(context)
        }
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.yes,DialogInterface.OnClickListener { dialog, which ->  })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun updateAvailable(url: String) {
    AlertDialog.Builder(context)
        .setMessage(getString(R.string.dialog_update_available))
        .setPositiveButton(getString(R.string.dialog_install_text), DialogInterface.OnClickListener { dialog, which ->
            settingsPresentor!!.downloadApk(url)
        })
        .setNegativeButton(getString(R.string.cancel),DialogInterface.OnClickListener { dialog, which ->  })
        .show()
    }

    override fun showApkDownloadProgress() {
        var inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.progress_dialog_layout, null)
        val textView = dialogView.findViewById<TextView>(R.id.textView_label)
        mProgressBar = dialogView.findViewById<ProgressBar>(R.id.progressBar)
        textView.setText(R.string.apk_download_progress_title)
        mProgressBar.isIndeterminate = false
        val mAlertDialogBuilder =AlertDialog.Builder(context)
        mAlertDialogBuilder.setView(dialogView)
        mAlertDialogBuilder.setCancelable(false)
        alertDialog = mAlertDialogBuilder.create()
        alertDialog.show()
    }

    override fun updateApkDownloadProgress(progress: Int) {
        mProgressBar.progress = progress
    }

    override fun dissmissApkDownloadProgress() {
    if(alertDialog!=null)
        alertDialog.dismiss()
    }

    override fun showSnackBar(message: String) {
        val snackbar = Snackbar
            .make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)

        snackbar.show()
    }
}