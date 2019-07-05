package tech.inscripts.ins_armman.mMitra.completeFormList

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.json.JSONException
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.completeFormsDetails.CompleteFormDetailsActivity
import tech.inscripts.ins_armman.mMitra.data.model.CompleteFormQnA
import tech.inscripts.ins_armman.mMitra.utility.Utility
import java.util.ArrayList

class CompleteFormAdapter() : RecyclerView.Adapter<CompleteFormAdapter.ViewHolder>() {


    internal lateinit var mContext: Context
    internal lateinit var language: String
    internal lateinit var formName:String
    internal lateinit var participant_id:String
    internal var form_id: Int = 0
    var utility = Utility()

    internal var status = 0
    internal lateinit var uniqueId: String
    internal var name:String? = null
    internal var pos = 0
    private var mDetails: ArrayList<CompleteFormQnA>?=null
    private var clickListener: CompleteFormAdapter.ClickListener? = null

    constructor(mContext: Context, participant_id: String, form_id: Int, mDetails: ArrayList<CompleteFormQnA>?) : this() {
        this.mContext = mContext
        this.participant_id = participant_id
        this.form_id = form_id
        this.mDetails = mDetails
    }


    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    public fun setClickListener(clickListener : ClickListener){
        this.clickListener=clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CompleteFormAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.activity_form_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDetails!!.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val c = mDetails?.get(position)
        form_id = mDetails?.get(position)?.form_id!!
        try {
            val obj = JSONObject(c?.formName)
            language = utility.getLanguagePreferance(mContext)
            if (language.isEmpty()) {
                utility.setApplicationLocale(mContext, "en")
            } else {
                utility.setApplicationLocale(mContext, language)
            }
            formName = obj.getString(this.language)
            formName = formName.toUpperCase()
            val count = 0

            if (form_id >= 1 && form_id <= 5 || form_id == 10) {
                holder.formName?.setText(formName)
            }


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        var a = 0
        while (a < mDetails?.size!!) {
            println("formList$mDetails")
            a++
        }
    }

    public interface ClickListener{
        fun itemClicked(view : View, position : Int)
    }
    //
    inner public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var formName : TextView?=null

        constructor(itemView: View, formName: TextView?) : this(itemView) {
            this.formName = itemView.findViewById(R.id.tvFormName)
        }
        override fun onClick(v: View) {
            var intent = Intent(mContext, CompleteFormDetailsActivity::class.java)
            if (clickListener != null) {
                clickListener!!.itemClicked(v, position)
                val formId = mDetails?.get(position)?.getForm_id()
                // if (formId >= 1 && formId <= 9 || formId == 10) {
                uniqueId = participant_id
                //  }
                intent.putExtra("unique_id", uniqueId)
                intent.putExtra("form_id", formId)
                mContext.startActivity(intent)
            }
        }
    }
}