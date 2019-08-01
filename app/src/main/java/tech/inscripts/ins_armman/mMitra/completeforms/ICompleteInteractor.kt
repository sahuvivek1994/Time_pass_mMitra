package tech.inscripts.ins_armman.mMitra.completeforms

import android.database.Cursor

interface ICompleteInteractor {
fun fetchListCompleteForm() : Cursor?
}