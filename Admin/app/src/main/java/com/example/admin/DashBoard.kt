package com.example.admin

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dash_board.*
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem


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

        textAll.text = "Name : $name Email : $email MESS : $mess"
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
                val i = Intent(this@DashBoard,SignIn::class.java)
                startActivity(i)
            }
        }
        return true
    }
}
