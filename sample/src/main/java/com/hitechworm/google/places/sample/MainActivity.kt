package com.hitechworm.google.places.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hitechworm.rxplaces.adapter.SimplePlaceSuggestAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edt_input.setAdapter(SimplePlaceSuggestAdapter("YOUR API KEY"))
    }
}
