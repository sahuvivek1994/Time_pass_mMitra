package tech.inscripts.ins_armman.mMitra.completedFormDetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.CompleteFormQnA;
import com.inscripts.ins_armman.npdsf.database.DBHelper;
import tech.inscripts.ins_armman.mMitra.R;
import tech.inscripts.ins_armman.mMitra.data.model.syncing.CompleteFormQnA;

import java.util.ArrayList;

/**
 * This activity is to get the details of complete form of mother and child.
 */
public class CompletedFormDetails extends AppCompatActivity implements ICompleteFormDetails {
    String unique_id;
    int form_id;
    RecyclerView recyclerView;
    DetailsAdapter adapter;
    DBHelper db;
    CompleteFormDetailsPresentor presentor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_forms_details);
        presentor = new CompleteFormDetailsPresentor();
        presentor.attachView(this);
        db = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CompletedFormDetails.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        unique_id = getIntent().getStringExtra("unique_id");
        form_id = getIntent().getIntExtra("form_id", 0);
        presentor.displayFIlledForm(unique_id, form_id);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getFormdetails(ArrayList<CompleteFormQnA> formDetails) {
        adapter = new DetailsAdapter(formDetails, this, form_id);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presentor.detch();
    }
}
