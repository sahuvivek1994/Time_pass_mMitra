package tech.inscripts.ins_armman.mMitra.completeForms

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.completeFilledForm

class CompleteFormActivity : AppCompatActivity(),ICompleteFormView,CompleteFormsAdapter.ClickListener{
    internal lateinit var completedPresenter: CompleteFormPresenter
    internal lateinit var mcompleteFormAdapter: CompleteFormsAdapter
    internal lateinit var mProgressBar: ProgressBar
    internal lateinit var emptyLayout: RelativeLayout
    private var mRecyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_woman_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Complete Forms")
        setSupportActionBar(toolbar)
        mProgressBar = findViewById(R.id.child_list_progress_bar)
        emptyLayout=findViewById(R.id.empty_layout)
        mRecyclerView=findViewById(R.id.recycler_view)

        val layoutManager = LinearLayoutManager(this@CompleteFormActivity)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mRecyclerView?.addItemDecoration(itemDecoration)
        mRecyclerView?.setLayoutManager(layoutManager)

        completedPresenter = CompleteFormPresenter()
        completedPresenter.attachView(this)    }

    override fun setAdapter(mWomenList: List<completeFilledForm>) {
        mProgressBar.visibility = View.GONE
        if(mWomenList==null || mWomenList.size < 1){
            emptyLayout.visibility=View.VISIBLE
            val emptyTextView = findViewById<TextView>(R.id.text_empty_list)
            emptyTextView.setText(R.string.Reg_women_com)
            return
        }
        if(mWomenList!=null){
            mcompleteFormAdapter = CompleteFormsAdapter(getContext(),mWomenList)
            mRecyclerView?.setAdapter(mcompleteFormAdapter)
            mcompleteFormAdapter.setOnClickListener(this)
        }
        else{
            mcompleteFormAdapter.swapDataList(mWomenList)
            mcompleteFormAdapter.notifyDataSetChanged()
        }
    }

    override fun getContext(): Context {
    return this
    }

    override fun itemClicked(v: View, position: Int) {
    }

    override fun onResume() {
        super.onResume()
        completedPresenter.getListCompleteForm()
    }

    override fun onDestroy() {
        super.onDestroy()
        completedPresenter.attachView(this)

    }
}