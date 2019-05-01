package com.example.admin.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.admin.R

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this@StatsActivity, DashBoard::class.java)
        startActivity(i)
    }
}
