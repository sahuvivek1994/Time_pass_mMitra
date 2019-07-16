package tech.inscripts.ins_armman.mMitra.incompleteForms

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_woman_list.*
import kotlinx.android.synthetic.main.empty_list_layout.*
import kotlinx.android.synthetic.main.splash_screen.*
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.completeFormList.CompleteFormListActivity
import tech.inscripts.ins_armman.mMitra.data.model.IncompleteFilledForm
import tech.inscripts.ins_armman.mMitra.displayForm.displayForm
import tech.inscripts.ins_armman.mMitra.utility.Constants.FORM_ID
import tech.inscripts.ins_armman.mMitra.utility.Constants.UNIQUE_ID

class IncompleteFormActivity : AppCompatActivity(),IIncompleteFormView{


    var presenter : IncomleteFormPresenter?=null
    var adapter : IncompleteFormsAdapter ?=null
    var recyclerView :RecyclerView ?=null
     var mProgressBar: ProgressBar?=null
      var emptyLayout: RelativeLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_woman_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Incomplete Forms")
        mProgressBar = findViewById(R.id.child_list_progress_bar)
        emptyLayout = findViewById(R.id.empty_list_layout)
recyclerView=findViewById(R.id.recycler_view)
        var layoutManager = LinearLayoutManager(this@IncompleteFormActivity)
        var itemDecoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        recyclerView?.apply {
            addItemDecoration(itemDecoration)
            setLayoutManager(layoutManager)
        }

        presenter = IncomleteFormPresenter()
        presenter?.attachView(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }

    override fun onResume() {
        super.onResume()
        presenter?.getIncompleteFormList()
    }

    override fun getContext(): Context {
        return this
    }

    override fun setAdapter(mWomenList: ArrayList<IncompleteFilledForm>) {
        mProgressBar?.visibility= View.GONE
        if(mWomenList == null || mWomenList.size<1){
            empty_list_layout.visibility=View.VISIBLE
            text_empty_list.text= R.string.Reg_women.toString()
            return
        }

        if (adapter == null) {
            adapter = IncompleteFormsAdapter(
                this@IncompleteFormActivity, mWomenList,object : IncompleteFormsAdapter.onItemClickListener {
                    override fun onItemClick(uniqueId: String, form_id: Int) {
                            val unique_id = uniqueId
                            val builder = android.app.AlertDialog.Builder(this@IncompleteFormActivity)
                            builder
                                .setTitle("Form Details")
                                .setMessage("do you want to continue filling the incomplete forms ?")
                                .setPositiveButton(this@IncompleteFormActivity.getString(R.string.continue_filling_forms)
                                ) { dialog, which ->
                                    openActivity(unique_id, form_id)
                                }
                                .setNegativeButton(this@IncompleteFormActivity.getString(R.string.view_filled_form)
                                ) { dialog, which ->
                                    val intent =
                                        Intent(this@IncompleteFormActivity, CompleteFormListActivity::class.java)
                                    val uniqueId = unique_id
                                    intent.putExtra("id", unique_id)
                                    intent.putExtra("form_id", form_id)
                                    startActivity(intent)
                                }.show()
                    }
                })
            recyclerView?.adapter = adapter
        } else {
            adapter?.swapDataList(mWomenList)
            adapter?.notifyDataSetChanged()
        }
        }

    override fun openActivity(uniqueId: String, formId: Int) {
        val intent2 = Intent(this@IncompleteFormActivity, displayForm::class.java)
        intent2.putExtra(UNIQUE_ID, uniqueId)
        intent2.putExtra(FORM_ID, formId.toString())
        //intent2.putExtra("child", noOfChild)
        //intent2.putExtra("childcounter", childCounter)
        startActivity(intent2)
        finish()
    }


}