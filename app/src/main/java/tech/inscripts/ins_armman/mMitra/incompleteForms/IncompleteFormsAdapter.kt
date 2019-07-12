package tech.inscripts.ins_armman.mMitra.incompleteForms

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.IncompleteFilledForm

class IncompleteFormsAdapter() : RecyclerView.Adapter<IncompleteFormsAdapter.ViewHolder>() {

    var mContext : Context?=null
    private var mWomenList: List<IncompleteFilledForm>? = null
    private lateinit var mOnItemClickListener:onItemClickListener
   // var obj = ViewHolder()

    constructor(mContext: Context?, mWomenList: List<IncompleteFilledForm>?,mOnItemClickListener :onItemClickListener) : this() {
        this.mContext = mContext
        this.mWomenList = mWomenList
        this.mOnItemClickListener = mOnItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_incomplete_list,parent,false))
    }

    override fun getItemCount(): Int {
        return mWomenList!!.size
    }

    fun swapDataList(womenList : List<IncompleteFilledForm>){
        this.mWomenList=womenList
    }
    override fun onBindViewHolder(holder: IncompleteFormsAdapter.ViewHolder, i: Int) {
        holder.bindData(mWomenList!!.get(i))

    }

    interface  onItemClickListener{
        fun onItemClick(uniqueId: String, form_id: Int)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //constructor()

         var textViewName: TextView = itemView.findViewById(R.id.textview_name)
         var incompleteVisitlabel: TextView? = null
         var constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraint_layout_root)

        fun bindData(listModel: IncompleteFilledForm?) {
            if (listModel != null) {
                textViewName.text = listModel!!.name

                constraintLayout.setOnClickListener {
                    var formIdToOpen = Integer.parseInt(listModel!!.formId)
                    formIdToOpen += 1
                    mOnItemClickListener?.onItemClick(listModel!!.uniqueId, formIdToOpen)
                }
            }
        }
    }
}