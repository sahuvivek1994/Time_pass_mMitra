package tech.inscripts.ins_armman.mMitra.completeformsdetails

import android.content.Context
import tech.inscripts.ins_armman.mMitra.data.model.CompleteFormQnA
import java.util.ArrayList

interface ICompleteFormsDetailsView {
     fun getContext(): Context
     fun getFormdetails(formDetails: ArrayList<CompleteFormQnA>)
}