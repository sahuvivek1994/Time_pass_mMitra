package tech.inscripts.ins_armman.mMitra.utility

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import java.lang.Exception
import java.lang.reflect.Method
import java.util.*

class TelephonyInfo {
    private val TAG = "TelephonyInfo"

     var telephonyInfo: TelephonyInfo? = null
     var imsiSIM1: String = ""
     var imsiSIM2: String = ""
     var isSIM1Ready: Boolean = false
     var isSIM2Ready: Boolean = false


    constructor()

    fun isDualSIM() : Boolean{
        return imsiSIM1!=null
    }

    @SuppressLint("MissingPermission")
    fun getInstance(context : Context) : TelephonyInfo{
        if (telephonyInfo == null) {

            telephonyInfo = TelephonyInfo()

            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            telephonyInfo?.imsiSIM1 = "869432026925037"//telephonyManager.deviceId

            Log.d(TAG, "getInstance: " + telephonyInfo?.imsiSIM1)

            telephonyInfo?.imsiSIM2 = ""

            try {
                telephonyInfo?.imsiSIM1 = getDeviceIdBySlot(context, "getDeviceIdGemini", 0)
                telephonyInfo?.imsiSIM2 = getDeviceIdBySlot(context, "getDeviceIdGemini", 1)
            } catch (e: GeminiMethodNotFoundException) {
                e.printStackTrace()

                try {
                    telephonyInfo?.imsiSIM1 =  getDeviceIdBySlot(context, "getDeviceId", 0)
                    telephonyInfo?.imsiSIM2 = getDeviceIdBySlot(context, "getDeviceId", 1)
                } catch (e1: GeminiMethodNotFoundException) {
                    //Call here for next manufacturer's predicted method name if you wish
                    e1.printStackTrace()
                }
            }

            telephonyInfo?.isSIM1Ready = telephonyManager.simState == TelephonyManager.SIM_STATE_READY
            telephonyInfo?.isSIM2Ready = false

            try {
                telephonyInfo?.isSIM1Ready = getSIMStateBySlot(context, "getSimStateGemini", 0)
                telephonyInfo?.isSIM2Ready = getSIMStateBySlot(context, "getSimStateGemini", 1)
            } catch (e: GeminiMethodNotFoundException) {

                e.printStackTrace()

                try {
                    telephonyInfo?.isSIM1Ready = getSIMStateBySlot(context, "getSimState", 0)
                    telephonyInfo?.isSIM2Ready = getSIMStateBySlot(context, "getSimState", 1)
                } catch (e1: GeminiMethodNotFoundException) {
                    //Call here for next manufacturer's predicted method name if you wish
                    e1.printStackTrace()
                }
            }
        }
        return telephonyInfo!!
    }

    @SuppressLint("NewApi")
    @Throws(GeminiMethodNotFoundException::class)
    private fun getDeviceIdBySlot(context: Context, predictedMethodName: String, slotID: Int): String {
        var imsi : String = ""
        var telephony : TelephonyManager= context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try{
        val telephonyClass = Class.forName(telephony.javaClass.name)
        val parameter = arrayOfNulls<Class<*>>(1)
        parameter[0]= Int.javaClass
        var getSimID : Method = telephonyClass.getMethod(predictedMethodName, *parameter)
        var objParameter=  arrayOfNulls<Any>(1)
        objParameter[0] = slotID
        var ob_phone : Objects = getSimID.invoke(telephony,objParameter) as Objects
        if(ob_phone!=null) {
            imsi = ob_phone.toString()
        }
        } catch (e : Exception){
            e.printStackTrace()
            throw GeminiMethodNotFoundException(predictedMethodName)
        }
        return imsi
    }
@Throws(GeminiMethodNotFoundException::class)
    private fun getSIMStateBySlot(context: Context,predictedMethodName: String,slotID: Int) : Boolean{
    var isReady = false

    val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    try {

        val telephonyClass = Class.forName(telephony.javaClass.name)

        val parameter = arrayOfNulls<Class<*>>(1)
        parameter[0] = Int::class.javaPrimitiveType
        val getSimStateGemini = telephonyClass.getMethod(predictedMethodName, *parameter)

        val obParameter = arrayOfNulls<Any>(1)
        obParameter[0] = slotID
        val ob_phone = getSimStateGemini.invoke(telephony, *obParameter)

        if (ob_phone != null) {
            val simState = Integer.parseInt(ob_phone.toString())
            if (simState == TelephonyManager.SIM_STATE_READY) {
                isReady = true
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        throw GeminiMethodNotFoundException(predictedMethodName)
    }


    return isReady
}
    private class GeminiMethodNotFoundException(info: String) : Exception(info) {
        companion object {

            private val serialVersionUID = -996812356902545308L
        }
    }
}