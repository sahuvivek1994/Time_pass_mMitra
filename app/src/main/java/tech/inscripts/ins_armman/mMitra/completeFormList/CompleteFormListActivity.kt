package tech.inscripts.ins_armman.mMitra.completeFormList

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper
import tech.inscripts.ins_armman.mMitra.data.model.CompleteFormQnA
import java.util.ArrayList

class CompleteFormListActivity : AppCompatActivity(),ICompleteFormListView,FormListAdapter.ClickListener {

    internal var qnaList: ArrayList<CompleteFormQnA>? = null
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var db: DBHelper
    internal lateinit var adapter: FormListAdapter
    internal lateinit var presentor: CompleteFormListPresenter
    internal lateinit var id: String
    internal lateinit var name:String
    internal var form_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_forms_list)
        val toolbar = findViewById<Toolbar>(R.id.include)
        toolbar.setTitle("Filled Form List")
        setSupportActionBar(toolbar)
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
        adapter = FormListAdapter(this,id,form_id,formDetails)
        recyclerView.adapter = adapter
        adapter.setClickListener(this)
    }

    override fun itemClicked(view: View, position: Int) {
    }

    override fun getContext(): Context {
    return this
    }

    override fun onDestroy() {
        super.onDestroy()
        presentor.detachView()
    }
}