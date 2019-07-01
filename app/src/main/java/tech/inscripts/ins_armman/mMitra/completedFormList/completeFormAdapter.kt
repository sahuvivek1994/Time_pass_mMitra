package tech.inscripts.ins_armman.mMitra.completedFormList

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.completedForm.completedFormAdapter
import tech.inscripts.ins_armman.mMitra.completedForm.completedFormAdapter.ViewHolder
import tech.inscripts.ins_armman.mMitra.data.model.syncing.completeFiledForm

class completeFormAdapter : RecyclerView.Adapter<ViewHolder>() {

    var mContext : Context?=null
    var mWomenList : List<completeFiledForm>?=null
    var clickListener :ClickListener?=null
    var viewHolder : completedFormAdapter.ViewHolder()

    constructor(mContext: Context?, mWomenList: List<completeFiledForm>?) {
        this.mContext = mContext
        this.mWomenList = mWomenList
    }


    fun setClickListener(clickListener:ClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_incomplete_list, parent, false))
    }

    override fun getItemCount(): Int {
        return mWomenList?.size!!
    }

    override fun onBindViewHolder(holder: completedFormAdapter.ViewHolder , i: Int) {
        holder.bindData(mWomenList?.get(i)!!)
    }

    public interface ClickListener{
        fun itemClicked(v : View, position :Int)
    }

   inner class ViewHolder: RecyclerView.ViewHolder,View.OnClickListener{

       var textViewName : TextView?=null
       var constraintLayout : ConstraintLayout

        constructor(itemView: View) : super(itemView){
            itemView.setOnClickListener(this)
            textViewName = itemView.findViewById(R.id.textview_name)
            constraintLayout = itemView.findViewById(R.id.constraint_layout_root)
        }

         fun bindData(listModel : completeFiledForm){
            if(listModel!=null){
                textViewName?.setText(listModel.name)
            }
        }

        override fun onClick(v: View) {
            val intent = Intent(mContext, CompletedFormsList::class.java)
        if(clickListener !=null){
            clickListener?.itemClicked(v,position)
            var i = mWomenList?.size
            intent.putExtra("id", mWomenList?.get(position)?.unique_id)
            intent.putExtra("name", mWomenList?.get(position)?.name)
        }
        }

    }

}