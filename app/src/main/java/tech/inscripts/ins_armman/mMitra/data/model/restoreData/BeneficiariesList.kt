package tech.inscripts.ins_armman.mMitra.data.model.restoreData

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

 class BeneficiariesList {
     @SerializedName("unique_id")
     var uniqueId : String=""
     @SerializedName("visits")
     val visitsList : ArrayList<VisitsList>? = null

     override fun toString(): String {
         return "BeneficiariesList(uniqueId=$uniqueId, visitsList=$visitsList)"
     }


 }