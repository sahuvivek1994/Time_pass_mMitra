package tech.inscripts.ins_armman.mMitra.completeFormsDetails

import android.content.Context
import tech.inscripts.ins_armman.mMitra.data.model.CompleteFormQnA
import java.util.ArrayList

interface ICompleteFormsDetailsView {
    abstract fun getContext(): Context
    abstract fun getFormdetails(formDetails: ArrayList<CompleteFormQnA>)
}