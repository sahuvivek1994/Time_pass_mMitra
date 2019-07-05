package tech.inscripts.ins_armman.mMitra.completeFormList

import android.content.Context
import tech.inscripts.ins_armman.mMitra.data.model.CompleteFormQnA
import java.util.ArrayList

interface ICompleteFormListView {
     fun getContext(): Context
     fun getData(formDetails: ArrayList<CompleteFormQnA>)
}