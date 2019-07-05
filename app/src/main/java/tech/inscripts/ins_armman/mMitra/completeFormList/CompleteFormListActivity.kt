package tech.inscripts.ins_armman.mMitra.completeFormList

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper
import tech.inscripts.ins_armman.mMitra.data.model.CompleteFormQnA
import java.util.ArrayList

class CompleteFormListActivity : AppCompatActivity(),ICompleteFormListView,CompleteFormAdapter.ClickListener {

    internal var qnaList: ArrayList<CompleteFormQnA>? = null
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var db: DBHelper
    internal lateinit var adapter: CompleteFormAdapter
    internal lateinit var presentor: CompleteFormListPresenter
    internal var childNoList = ArrayList<CompleteFormQnA>()
    internal lateinit var id: String
    internal lateinit var name:String

    internal var form_id: Int = 0
    internal var childId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_forms_list)
        setTitle("Filled Form List")
        recyclerView= findViewById(R.id.recyclerView)
        db = DBHelper(this)
        presentor= CompleteFormListPresenter()
        presentor.attachView(this)
        var linearLayoutManager = LinearLayoutManager(this@CompleteFormListActivity)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        id = intent.getStringExtra("id")
        name = intent.getStringExtra("name")
        form_id = intent.getIntExtra("form_id", 0)
        presentor.getCompleteFormList(id)

    }


    override fun getData(formDetails: ArrayList<CompleteFormQnA>) {
        adapter = CompleteFormAdapter(this,id,form_id,formDetails)
        recyclerView.adapter = adapter
        adapter.setClickListener(this)
    }

    override fun itemClicked(view: View, position: Int) {
    }

    override fun getContext(): Context {
    return null!!
    }

    override fun onDestroy() {
        super.onDestroy()
        presentor.detachView()
    }
}