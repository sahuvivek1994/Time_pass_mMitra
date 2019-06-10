package tech.inscripts.ins_armman.mMitra.data.retrofit

import retrofit2.Retrofit
import tech.inscripts.ins_armman.mMitra.data.service.*

class RemoteDataSource {
    private var mRemoteDataSource: RemoteDataSource? = null
    private var mRestClient : Retrofit?=null
    var rest = RestClient()


    constructor(mRestClient: Retrofit?) {
        this.mRestClient = mRestClient
    }

    fun getInstance(): RemoteDataSource {
        if (mRemoteDataSource == null) {
            mRemoteDataSource = RemoteDataSource(rest.getClient())
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
return FormDownloadService(createApiService(FormDownloadServiceAPI::class.java))
    }

    fun getCheckUpdateService() : CheckUpdateService {
        return CheckUpdateService(createApiService(CheckUpdateApi::class.java))
    }

    fun syncRegistrationService() :SyncRegistrationService{
        return SyncRegistrationService(createApiService(SyncRegistrationServiceApi::class.java))

    }
    fun syncFormService(): SyncFormService {
        return SyncFormService(createApiService(SyncFormServiceApi::class.java))
    }

    fun helpManualDownloadService() : HelpManualDownloadService{
        return HelpManualDownloadService(createApiService(HelpManualDownloadServiceApi::class.java))
    }

    fun syncReferralService(): SyncReferralService {
        return SyncReferralService(createApiService(SyncReferralServiceApi::class.java))
    }

    fun restoreRegistrationService(): RestoreRegistrationService {
        return RestoreRegistrationService(createApiService(RestoreRegistrationServiceApi::class.java))
    }

    fun restoreVisitsService(): RestoreVisitsService {
        return RestoreVisitsService(createApiService(RestoreVisitsServiceAPI::class.java))
    }

    fun restoreReferralService(): RestoreReferralService {
        return RestoreReferralService(createApiService(RestoreReferralServiceAPI::class.java))
    }

    fun syncUpdatePhotoService(): SyncUpdatePhotoService {
        return SyncUpdatePhotoService(createApiService(SyncUpdatePhotoServiceApi::class.java))
    }

}