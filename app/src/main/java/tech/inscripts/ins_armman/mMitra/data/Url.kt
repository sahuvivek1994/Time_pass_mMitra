package tech.inscripts.ins_armman.mMitra.data

interface Url {
    companion object {

        //API V2 added after data sync error message structure changed

        val BASE_URL = "http://arogyasakhi.armman.org/API/V2/"
        //    String BASE_URL = "http://192.168.0.101/ArogyaSakhi-API/V2/";

        const val AUTHENTICATE = "authenticate"
        const val DOWNLOAD_FORMS = "newjson"
        val DOWNLOAD_HELP_MANUAL = "gethelp"
        val RELEASE = "release"
        val SYNC_REGISTRATION_DATA = "registration"
        val SYNC_UPDATE_PHOTO_DATA = "update_image"
        val SYNC_FORM_DATA = "visits"
        val SYNC_REFERRAL_DATA = "update_referral"
        val SYNC_CHILD_GROWTH = "childgrowth"
        val GET_REGISTRATIONS = "getregistrations"
        val GET_VISITS = "getvisits"
        val GET_REFERRALS = "getreferrals"
        val GET_CHILD_GRWOTH = "getchildgrowth"
    }

}
