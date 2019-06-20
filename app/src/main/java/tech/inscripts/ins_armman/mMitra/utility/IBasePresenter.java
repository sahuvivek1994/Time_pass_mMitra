package tech.inscripts.ins_armman.mMitra.utility;

/**
 * This interface is used for attachedView and detch function to follow mvp structure
 * @author vivek & juilee on 2/05/2019
 */

public interface IBasePresenter<V> {

    void attachView(V view);

    void detachView();

}
