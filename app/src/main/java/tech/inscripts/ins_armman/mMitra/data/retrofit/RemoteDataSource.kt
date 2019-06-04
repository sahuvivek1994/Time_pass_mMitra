package tech.inscripts.ins_armman.mMitra.data.retrofit

import retrofit2.Retrofit

class RemoteDataSource {
    private var mRemoteDataSource: RemoteDataSource? = null
    private var mRestClient : Retrofit?=null

    constructor(mRestClient: Retrofit?) {
        this.mRestClient = mRestClient
    }

    fun getInstance() : RemoteDataSource? {
       if(mRemoteDataSource == null){
           mRemoteDataSource= RemoteDataSource(RestClient.getClient())
       }
        return mRemoteDataSource
    }

}