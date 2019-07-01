package tech.inscripts.ins_armman.mMitra.incompleteForm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.IncompleteFiledForm;
import com.inscripts.ins_armman.npdsf.displayForms.displayForm;
import com.inscripts.ins_armman.npdsf.incompleteFormList.IncompleteFormList;

import java.util.List;

import static com.inscripts.ins_armman.npdsf.utility.Constants.FORM_ID;
import static com.inscripts.ins_armman.npdsf.utility.Constants.UNIQUE_ID;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class IncompleteForm extends AppCompatActivity implements IncompleteView {

    IncompletePresenter incompletePresenter;
    ProgressBar mProgressBar;
    RelativeLayout emptyLayout;
    IncompleteFormAdapter mIncompleteFormAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomplete_form);
        setTitle(R.string.incompleteformtitle);
        mProgressBar = findViewById(R.id.child_list_progress_bar);
        emptyLayout = findViewById(R.id.empty_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(IncompleteForm.this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        incompletePresenter = new IncompleteFormPresenter();
        incompletePresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        incompletePresenter.detch();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setAdapter(List<IncompleteFiledForm> womenList) {
        mProgressBar.setVisibility(View.GONE);

        if (womenList == null || womenList.size() < 1) {
            emptyLayout.setVisibility(View.VISIBLE);
            TextView emptyTextView = findViewById(R.id.text_empty_list);
            emptyTextView.setText(R.string.Reg_women);
            return;
        }


        if (mIncompleteFormAdapter == null) {
            mIncompleteFormAdapter = new IncompleteFormAdapter(IncompleteForm.this, womenList, new IncompleteFormAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String uniqueId, int form_id) {
                    final int formId=form_id;
                    final String unique_id=uniqueId;
                    AlertDialog.Builder builder = new AlertDialog.Builder(IncompleteForm.this);
                    builder
                            .setTitle("Form Details")
                            .setMessage("do you want to continue filling the incomplete forms ?")
                            .setPositiveButton(IncompleteForm.this.getString(R.string.continue_filling_forms), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if ((formId > 1 && formId <= 5) || (formId == 10)) {
                                        openActivity(unique_id, formId, "0", "1");
                                    } else {
                                        incompletePresenter.getUniqueIdFormId(unique_id);
                                    }
                                }
                            })
                            .setNegativeButton(IncompleteForm.this.getString(R.string.view_filled_form), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(IncompleteForm.this, IncompleteFormList.class);
                                    String uniqueId=unique_id;
                                    intent.putExtra("unique_id", unique_id);
                                    intent.putExtra("form_id", formId);
                                    startActivity(intent);
                                    }
                            }).show();
                }
            });
            mRecyclerView.setAdapter(mIncompleteFormAdapter);
        } else {
            mIncompleteFormAdapter.swapDataList(womenList);
            mIncompleteFormAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        incompletePresenter.getListInCompleteForm();
    }

    @Override
    public void openActivity(String uniqueId, int form_id, String noOfChild, String childCounter) {
        Intent intent2 = new Intent(IncompleteForm.this, displayForm.class);
        intent2.putExtra(UNIQUE_ID, uniqueId);
        intent2.putExtra(FORM_ID, String.valueOf(form_id));
        intent2.putExtra("child", noOfChild);
        intent2.putExtra("childcounter", childCounter);
        startActivity(intent2);
        finish();
    }
}
