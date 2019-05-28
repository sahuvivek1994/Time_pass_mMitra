package tech.inscripts.ins_armman.mMitra.completeForms

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import tech.inscripts.ins_armman.mMitra.R

class CompleteFormActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_woman_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Complete Forms")
        setSupportActionBar(toolbar)

    }
}