package tech.inscripts.ins_armman.mMitra.homeactivity

import tech.inscripts.ins_armman.mMitra.utility.IMvpView

interface IMainActivity : IMvpView {
       fun showSnackBar(message: String)
       fun setUnsentFormsCount(count: Int)
       fun showProgressBar(label: String)
       fun hideProgressBar()
       fun showFormUpdateErrorDialog()
       fun updateAvailable(url : String)
}