package tech.inscripts.ins_armman.mMitra.completeforms

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.completeformList.CompleteFormListActivity
import tech.inscripts.ins_armman.mMitra.data.model.completeFilledForm

class CompleteFormsAdapter() : RecyclerView.Adapter<CompleteFormsAdapter.ViewHolder>() {

    var mContext: Context? = null
    var mWomenList: List<completeFilledForm>? = null
    var clickListener: ClickListener? = null
  //  var obj = ViewHolder()

    constructor(mContext: Context?, mWomenList: List<completeFilledForm>?) : this() {
        this.mContext = mContext
        this.mWomenList = mWomenList
    }


    fun setOnClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_incomplete_list, parent, false))
    }

    override fun getItemCount(): Int {
        return mWomenList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.bindData(mWomenList?.get(i)!!)
    }

    fun swapDataList(womenList : List<completeFilledForm>){
        this.mWomenList=womenList
    }

    public interface ClickListener {
        fun itemClicked(v: View, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var textViewName: TextView=itemView.findViewById(R.id.textview_name)
        var constraintLayout: ConstraintLayout= itemView.findViewById(R.id.constraint_layout_root)

        init {
            itemView.setOnClickListener(this)
        }

        fun bindData(listModel: completeFilledForm?) {
            if (listModel != null) {
                textViewName.text = listModel!!.name
            }
        }

         override fun onClick(v: View) {
             val intent = Intent(mContext, CompleteFormListActivity::class.java)
             if (clickListener != null) {
                 clickListener?.itemClicked(v, position)
                 val i = mWomenList?.size
                 intent.putExtra("id", mWomenList?.get(position)?.unique_id)
                 intent.putExtra("firstName", mWomenList?.get(position)?.name)
             }
             mContext?.startActivity(intent)
         }
    }
}