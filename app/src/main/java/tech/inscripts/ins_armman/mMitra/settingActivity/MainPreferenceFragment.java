package tech.inscripts.ins_armman.mMitra.settingActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import tech.inscripts.ins_armman.mMitra.R;

/**
 * Created by lenovo on 24/10/17.
 */

public class MainPreferenceFragment extends PreferenceFragment  {


    private ListPreference mListPreference;
    private Preference mPreferenceSyncForm, mPreferenceVersion;
    private Preference mPreferenceCheckUpdate, mPreferenceHelpManual, mPreferenceRestoreData;
    private ProgressBar mProgressBar;
    private AlertDialog mProgressDialog;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);

        linkViewsId();



    }

    void linkViewsId() {
        mListPreference = (ListPreference) findPreference("language");
        mListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {
              //  mSettingsPresentor.changeLanguage(getActivity().getApplicationContext(), value.toString());
                getActivity().recreate();
                return true;
            }
        });

        mPreferenceSyncForm = findPreference("sync_form");
        mPreferenceSyncForm.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
              //  mSettingsPresentor.downloadForms();
                return false;
            }
        });

        mPreferenceCheckUpdate = findPreference("check_update");
        mPreferenceCheckUpdate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
               // mSettingsPresentor.checkUpdate();
                return false;
            }
        });

        mPreferenceHelpManual = findPreference("help_manual");
        mPreferenceHelpManual.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //mSettingsPresentor.downloadHelpManual();
                return false;
            }
        });

        mPreferenceCheckUpdate = findPreference("logout");
        mPreferenceCheckUpdate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
               // mSettingsPresentor.logout();
                return false;
            }
        });

        mPreferenceRestoreData = findPreference("restore_data");
        mPreferenceRestoreData.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.restore_warning_text)
                        .setMessage(R.string.dialog_msg_loss_data_warning)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            //    mSettingsPresentor.restoreData();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                return false;
            }
        });

        mPreferenceVersion = findPreference("version");
      //  mPreferenceVersion.setSummary(Utility.getAppVersionName(getActivity()));
    }

    @Override
    public Context getContext() {
        return getActivity();
    }


    public void showProgressBar(String label) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
        TextView textView = dialogView.findViewById(R.id.textView_label);
        textView.setText(label);
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(getActivity());
        mAlertDialogBuilder.setView(dialogView);
        mAlertDialogBuilder.setCancelable(false);
        mProgressDialog = mAlertDialogBuilder.create();
        mProgressDialog.show();
    }


    public void hideProgressBar() {
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }


    public void showDialog(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void updateAvailable(final String url) {
        new android.support.v7.app.AlertDialog.Builder(getContext()).
                setMessage(getString(R.string.dialog_update_available))
                .setPositiveButton(getString(R.string.dialog_install_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //mSettingsPresentor.downloadApk(url);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }


    public void showApkDownloadProgress() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
        TextView textView = dialogView.findViewById(R.id.textView_label);
        mProgressBar = dialogView.findViewById(R.id.progressBar);
        textView.setText(R.string.apk_download_progress_title);
        mProgressBar.setIndeterminate(false);
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(getContext());
        mAlertDialogBuilder.setView(dialogView);
        mAlertDialogBuilder.setCancelable(false);
        mProgressDialog = mAlertDialogBuilder.create();
        mProgressDialog.show();
    }


    public void updateApkDownloadProgress(int progress) {
        mProgressBar.setProgress(progress);
    }


    public void dismissApkDownloadProgress() {
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }


    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);

        snackbar.show();
    }
}
