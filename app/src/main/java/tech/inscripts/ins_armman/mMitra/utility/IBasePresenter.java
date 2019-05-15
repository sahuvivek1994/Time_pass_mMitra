package tech.inscripts.ins_armman.mMitra.utility;

/**
 * Created by Amol on 11/10/17.
 */

public interface IBasePresenter<V> {

    void attachView(V view);

    void detachView();

}
