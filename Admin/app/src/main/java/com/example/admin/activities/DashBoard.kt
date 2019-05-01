package com.example.admin.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_dash_board.*
import android.view.Menu
import android.view.MenuItem
import com.example.admin.*
import com.example.admin.networking.APIClient
import com.example.admin.networking.RetrofitService
import retrofit2.Call
import retrofit2.Response


class DashBoard : AppCompatActivity() {

    val ADMIN_NAME = "admin_name"
    val ADMIN_EMAIL = "admin_email"
    val ADMIN_MESS = "admin_mess"
    val IS_LOGGEDIN = "is_logged_in"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        val sharedPref = this@DashBoard.getSharedPreferences("name",Context.MODE_PRIVATE) ?: return
        val name = sharedPref.getString(ADMIN_NAME,"NO_Name")
        val email = sharedPref.getString(ADMIN_EMAIL,"NO_EMAIL")
        val mess = sharedPref.getString(ADMIN_MESS,"NO_MESS")

        dashName.setText("Welcome $name")
        dashMess.setText("Mess : $mess")

        feedbackButton.setOnClickListener {
            val i = Intent(this@DashBoard, FeedbackActivity::class.java)
            startActivity(i)
        }

        verifyButton.setOnClickListener {
            val i = Intent(this@DashBoard, UnverifiedStudents::class.java)
            startActivity(i)
        }
        rebateButton.setOnClickListener {
            val i = Intent(this@DashBoard, MessRebate::class.java)
            startActivity(i)
        }
        menuButton.setOnClickListener {
            val i = Intent(this@DashBoard, MenuActivity::class.java)
            startActivity(i)
        }
        surveyButton.setOnClickListener {
            val i = Intent(this@DashBoard, PollActivity::class.java)
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuLogOut -> {
                val sharedPref = this@DashBoard.getSharedPreferences("name",Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putBoolean(IS_LOGGEDIN,false)
                    apply()
                }
                val i = Intent(this@DashBoard, SignIn::class.java)
                startActivity(i)
            }
        }
        return true
    }
}