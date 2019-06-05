package tech.inscripts.ins_armman.mMitra.data.retrofit

import retrofit2.Retrofit
import tech.inscripts.ins_armman.mMitra.data.service.AuthService
import tech.inscripts.ins_armman.mMitra.data.service.FormDownloadService
import tech.inscripts.ins_armman.mMitra.data.service.LoginServiceAPI

class RemoteDataSource {
    private var mRemoteDataSource: RemoteDataSource? = null
    private var mRestClient : Retrofit?=null

    constructor(mRestClient: Retrofit?) {
        this.mRestClient = mRestClient
    }

    fun getInstance(): RemoteDataSource {
        if (mRemoteDataSource == null) {
            mRemoteDataSource = RemoteDataSource(RestClient.getClient())
        }
        return mRemoteDataSource as RemoteDataSource
    }
fun <T> createApiService(apiInterface : Class<T>) : T? {
        return mRestClient?.create(apiInterface)
    }

    fun getAuthService(): AuthService {
        return AuthService(createApiService(LoginServiceAPI::class.java))
    }

    fun downloadFormService(): FormDownloadService {

    }

}