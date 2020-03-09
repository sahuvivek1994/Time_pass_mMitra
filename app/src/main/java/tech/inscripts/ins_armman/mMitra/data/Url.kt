package tech.inscripts.ins_armman.mMitra.data

interface Url {
    companion object {

        //API V2 added after dataSource sync error message structure changed

        var BASE_URL = "http://app.mmitra.org/m-mitraCohortSion/API/"
        //var BASE_URL = "http://mcts.armman.org/API/"
        const val AUTHENTICATE = "authenticate"
        const val DOWNLOAD_FORMS = "newjson"
        const val DOWNLOAD_HELP_MANUAL = "gethelp"
        const val RELEASE = "release"
        const val SYNC_REGISTRATION_DATA = "registration"
        const val SYNC_UPDATE_PHOTO_DATA = "update_image"
        const val SYNC_FORM_DATA = "visits"
        const val SYNC_REFERRAL_DATA = "update_referral"
        val SYNC_CHILD_GROWTH = "childgrowth"
        const val GET_REGISTRATIONS = "getregistrations"
        const val GET_VISITS = "getvisits"
        const val GET_REFERRALS = "getreferrals"
        val GET_CHILD_GRWOTH = "getchildgrowth"
    }

}
