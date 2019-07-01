package tech.inscripts.ins_armman.mMitra.incompleteForm;

import com.inscripts.ins_armman.npdsf.utility.IBasePresenter;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public interface IncompletePresenter<v> extends IBasePresenter<v> {


    public void getListInCompleteForm();

    public void getUniqueIdFormId(String uniqueId);

}
