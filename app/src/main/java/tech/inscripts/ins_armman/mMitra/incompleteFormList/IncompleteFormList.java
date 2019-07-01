package tech.inscripts.ins_armman.mMitra.incompleteFormList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.CompleteFormQnA;
import com.inscripts.ins_armman.npdsf.database.DBHelper;

import java.util.ArrayList;

/**
 * This activity is to display the form list completed from total forms of mother and her child.
 */
public class IncompleteFormList extends AppCompatActivity implements IIncompleteFormList,IncompleteFormListAdapter.ClickListener {
    RecyclerView recyclerView;
    DBHelper db;
    IncompleteFormListPresentor presentor;
    IncompleteFormListAdapter adapter;
    String id, name;
    int form_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_forms_list);
        setTitle("Filled Form List");
        recyclerView = findViewById(R.id.recyclerView);
        db = new DBHelper(this);
        presentor = new IncompleteFormListPresentor();
        presentor.attachView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(IncompleteFormList.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        id = getIntent().getStringExtra("unique_id");
        form_id = getIntent().getIntExtra("form_id", 0);
        presentor.getCompleteFormList(id);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getData(ArrayList<CompleteFormQnA> formDetails, ArrayList<CompleteFormQnA> childNo) {
        adapter = new IncompleteFormListAdapter(formDetails, childNo, this, id, form_id);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    @Override
    public void itemClicked(View view, int position) {

    }
}
