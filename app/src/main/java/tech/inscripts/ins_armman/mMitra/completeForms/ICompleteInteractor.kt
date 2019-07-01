package tech.inscripts.ins_armman.mMitra.completeForms

import android.database.Cursor

interface ICompleteInteractor {
fun fetchListCompleteForm() : Cursor
}