package com.example.admin.activities

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager

import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ArrayAdapter
import android.util.Log
import android.widget.Toast
import com.example.admin.R
import com.example.admin.networking.APIClient
import com.example.admin.networking.RetrofitService
import com.example.admin.objects.Admin
import retrofit2.Call
import retrofit2.Response


class SignIn : AppCompatActivity() {

    private var messType: String? = null
    val ADMIN_NAME = "admin_name"
    val ADMIN_EMAIL = "admin_email"
    val ADMIN_MESS = "admin_mess"
    val IS_LOGGEDIN = "is_logged_in"


    override fun onStart() {
        super.onStart()
        val sharedPref = this@SignIn.getSharedPreferences("name",Context.MODE_PRIVATE) ?: return
        val loginStatus = sharedPref.getBoolean(IS_LOGGEDIN,false)
        if (loginStatus) {
            val i = Intent(this@SignIn, DashBoard::class.java)
            startActivity(i)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        object : CountDownTimer(2000, 1000) {
            override fun onFinish() {
                bookITextView.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
                rootView.setBackgroundColor(ContextCompat.getColor(this@SignIn, R.color.white))
                bookIconImageView.setImageResource(R.drawable.logo_black)
                startAnimation()
            }
            override fun onTick(p0: Long) {}
        }.start()

        ArrayAdapter.createFromResource(
            this,
            R.array.planets_array,
            R.layout.sppinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMess.adapter = adapter
        }

        notRegistered.setOnClickListener{
            signInLinearLayout.visibility = View.GONE
            signUpLinearLayout.visibility = View.VISIBLE
        }
        notRegisteredSignUp.setOnClickListener{
            signInLinearLayout.visibility = View.VISIBLE
            signUpLinearLayout.visibility = View.GONE
        }

        signUpButton.setOnClickListener{

            signUpButton.visibility=View.GONE
            signUpProgressBar.visibility = View.VISIBLE
            val name = nameEditTextSignUp.text.toString()
            val email = emailEditTextSignUp.text.toString()
            val password = passwordEditTextSignUp.text.toString()
            messType = spinnerMess.selectedItem.toString()
            //Toast.makeText(this, "$name $email $password $messType", Toast.LENGTH_LONG).show()

//            if (name=="" || email=="" || !email.endsWith("@iiita.ac.in") || password.length<6) {
//                Toast.makeText(this, "Invalid Details", Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }


            val apiService = APIClient.getSOSClient().create<RetrofitService>(RetrofitService::class.java)

            val a = Admin(name, email, password, messType)
            val call = apiService.addAdmin(a)

            call.enqueue(object : retrofit2.Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {

                    Log.e("log", call.request().url().toString())
                    try {
                        val list = response.body()
                        Log.e("log",list)

                        val sharedPref = this@SignIn.getSharedPreferences("name",Context.MODE_PRIVATE) ?: return
                        with (sharedPref.edit()) {
                            Log.e("test",name+" "+email+" "+messType)
                            putString(ADMIN_NAME, name)
                            putString(ADMIN_EMAIL,email)
                            putString(ADMIN_MESS, messType)
                            putBoolean(IS_LOGGEDIN,true)
                            apply()
                        }
                        val i = Intent(this@SignIn, DashBoard::class.java)
                        startActivity(i)

                    } catch (e: Exception) {
                        e.printStackTrace()
                        signUpButton.visibility=View.VISIBLE
                        signUpProgressBar.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    signUpButton.visibility=View.VISIBLE
                    signUpProgressBar.visibility = View.GONE
                    Log.e("log", call.request().url().toString())
                    Log.e("ERROR2", t.toString())
                    Toast.makeText(this@SignIn,"SignUp Failed",Toast.LENGTH_LONG).show()
                }
            })

        }


        loginButton.setOnClickListener{
            loginButton.visibility=View.GONE
            loginProgressBar.visibility = View.VISIBLE
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val apiService = APIClient.getSOSClient().create<RetrofitService>(RetrofitService::class.java)

            val call = apiService.login(email,password);

            call.enqueue(object : retrofit2.Callback<Admin> {

                override fun onResponse(call: Call<Admin>, response: Response<Admin>) {

                    Log.e("log", call.request().url().toString())
                    try {

                        if (response.body().name == null) {
                            loginButton.visibility=View.VISIBLE
                            loginProgressBar.visibility = View.GONE
                            Toast.makeText(this@SignIn,"Incorrect Credentials",Toast.LENGTH_LONG).show()
                        } else { val sharedPref = this@SignIn.getSharedPreferences("name",Context.MODE_PRIVATE) ?: return
                            with (sharedPref.edit()) {
                                putString(ADMIN_NAME, response.body().name)
                                putString(ADMIN_EMAIL,response.body().email)
                                putString(ADMIN_MESS, response.body().mess)
                                putBoolean(IS_LOGGEDIN,true)
                                apply()
                            }
                            val i = Intent(this@SignIn, DashBoard::class.java)
                            startActivity(i)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this@SignIn,"Login Failed",Toast.LENGTH_LONG).show()
                        loginButton.visibility=View.VISIBLE
                        loginProgressBar.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<Admin>, t: Throwable) {
                    Toast.makeText(this@SignIn,"Login Failed",Toast.LENGTH_LONG).show()
                    loginButton.visibility=View.VISIBLE
                    loginProgressBar.visibility = View.GONE
                    Log.e("log", call.request().url().toString())
                    Log.e("ERROR2", t.toString())
                }
            })
        }
    }

    private fun startAnimation() {
        bookIconImageView.animate().apply {
            x(380f)
            y(200f)
            duration = 1000
        }.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                afterAnimationView.visibility = VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        })
    }
}
