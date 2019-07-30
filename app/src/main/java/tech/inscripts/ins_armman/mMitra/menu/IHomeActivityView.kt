package tech.inscripts.ins_armman.mMitra.menu

import tech.inscripts.ins_armman.mMitra.data.model.ArogyasakhiInfoModel
import tech.inscripts.ins_armman.mMitra.utility.IMvpView

interface IHomeActivityView : IMvpView {
       fun showSnackBar(message: String)
       fun setUnsentFormsCount(count: Int)
       fun showProgressBar(label: String)
       fun hideProgressBar()
       //fun setUnregisteredCount(count: Int)

     //  fun setArogyasakhiName(name: String)

      // fun setArogyasakhiInfo(arogyasakhiInfo: ArogyasakhiInfoModel)

       fun showFormUpdateErrorDialog()
}