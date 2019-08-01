package tech.inscripts.ins_armman.mMitra.completeformsdetails

import tech.inscripts.ins_armman.mMitra.data.model.CompleteFormQnA
import java.util.ArrayList

class CompleteFormsDetailsPresenter : ICompleteFormsDetailsPresenter<CompleteFormDetailsActivity> {
    internal var completeFormView: ICompleteFormsDetailsView? = null
    internal var interactor: CompleteFormsDetailsInteractor?=null
    internal var formDetails = ArrayList<CompleteFormQnA>()
    internal var childFormId = ArrayList<CompleteFormQnA>()


    override fun attachView(view: CompleteFormDetailsActivity) {
        this.completeFormView = view
        this.interactor = CompleteFormsDetailsInteractor(view?.getContext()!!)
    }

    override fun detachView() {
        completeFormView = null
    }

    override fun displayFIlledForm(unique_id: String, form_id: Int) {
        val cur = interactor?.displayFormDetails(unique_id, form_id)
            if (cur != null && cur.moveToFirst()) {
                do {
                    val completeFormQnA = CompleteFormQnA()
                    var que: String? = cur.getString(cur.getColumnIndex("question_label"))
                    if (que == null) {
                        que = cur.getString(cur.getColumnIndex("question_keyword"))
                        completeFormQnA.question = que
                    } else {
                        completeFormQnA.question = cur.getString(cur.getColumnIndex("question_label"))
                    }
                    var ans: String? = cur.getString(cur.getColumnIndex("option_label"))
                    if (ans == null) {
                        ans = cur.getString(cur.getColumnIndex("answer_keyword"))
                        completeFormQnA.answer = ans
                    } else {
                        completeFormQnA.answer = cur.getString(cur.getColumnIndex("option_label"))
                    }
                    formDetails.add(completeFormQnA)
                    println("question :$que\nanswer :$ans")
                } while (cur.moveToNext())
            }

        if (!formDetails.isEmpty()) {
            completeFormView?.getFormdetails(formDetails)
        }
    }
}