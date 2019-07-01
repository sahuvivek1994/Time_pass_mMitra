package tech.inscripts.ins_armman.mMitra.completedFormList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.CompleteFormQnA;
import com.inscripts.ins_armman.npdsf.database.DBHelper;
import tech.inscripts.ins_armman.mMitra.R;
import tech.inscripts.ins_armman.mMitra.data.model.syncing.CompleteFormQnA;

import java.util.ArrayList;

/**
 * This activity is to get the completed form list for mother and her child.
 */
public class CompletedFormsList extends AppCompatActivity implements ICompletedFormList, FormListAdapter.ClickListener {
    ArrayList<CompleteFormQnA> qnaList;
    RecyclerView recyclerView;
    DBHelper db;
    FormListAdapter adapter;
    CompletedFormsListPresentor presentor;
    ArrayList<CompleteFormQnA> childNoList = new ArrayList<CompleteFormQnA>();
    String id, name;
    int form_id;
    String childId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_forms_list);
        setTitle("Filled Form List");
        recyclerView = findViewById(R.id.recyclerView);
        db = new DBHelper(this);
        presentor = new CompletedFormsListPresentor();
        presentor.attachView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CompletedFormsList.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        form_id = getIntent().getIntExtra("form_id", 0);
        presentor.getCompleteFormList(id);


    }

    @Override
    public Context getContext() {
        return null;
    }


    @Override
    public void getData(ArrayList<CompleteFormQnA> formDetails, ArrayList<CompleteFormQnA> childNo) {
        adapter = new FormListAdapter(formDetails, childNo, this, id, form_id);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    @Override
    public void itemClicked(View view, int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presentor.detch();
    }
}
