package tech.inscripts.ins_armman.mMitra.data.model

class Form {
    var formId:Int=0
    var fromDays: Int=0
    var toDays: Int=0
    var visitName:String=""
    var orderId:String=""

    constructor()
    constructor(formId: Int, fromDays: Int, toDays: Int, visitName: String, orderId: String) {
        this.formId = formId
        this.fromDays = fromDays
        this.toDays = toDays
        this.visitName = visitName
        this.orderId = orderId
    }


}