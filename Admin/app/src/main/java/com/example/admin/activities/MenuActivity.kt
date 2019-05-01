package com.example.admin.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.util.Log
import com.example.admin.fragments.BreakfastFragment
import com.example.admin.fragments.DinnerFragment
import com.example.admin.fragments.LunchFragment
import com.example.admin.R
import com.example.admin.networking.APIClient
import com.example.admin.networking.RetrofitService
import com.example.admin.objects.MessMenu
import retrofit2.Call
import retrofit2.Response

class MenuActivity : AppCompatActivity() {


     var questions: ArrayList<MessMenu> = ArrayList()
    var mBreakfast: ArrayList<MessMenu> = ArrayList()
    var mLunches: ArrayList<MessMenu> = ArrayList()
    var mDinner: ArrayList<MessMenu> = ArrayList()
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        toolbar = supportActionBar!!

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val sharedPref = this@MenuActivity.getSharedPreferences("name", Context.MODE_PRIVATE) ?: return
        val mess = sharedPref.getString("admin_mess","NO_MESS")

        val apiService = APIClient.getSOSClient().create<RetrofitService>(RetrofitService::class.java)
        val call = apiService.getMenu(mess)


        call.enqueue(object : retrofit2.Callback<ArrayList<MessMenu>> {

            override fun onResponse(call: Call<ArrayList<MessMenu>>, response: Response<ArrayList<MessMenu>>) {

                Log.e("log", call.request().url().toString())
                try {

                    val list = response.body()

                    for (l in list) {
                        questions.add(l)
                    }
                    for (i in questions) {
                        Log.e("lofhnfb", i.timeSlot.toString())
                        if (i.timeSlot == 1) {
                            mBreakfast.add(i)
                        }
                        if (i.timeSlot == 2) {
                            mLunches.add(i)
                        }
                        if (i.timeSlot == 3) {
                            mDinner.add(i)
                        }
                        toolbar.title = "Breakfast MessMenu"
                        val breakfastFragment = BreakfastFragment.newInstance()
                        val bundle = Bundle()
                        bundle.putSerializable("menu", mBreakfast)
                        breakfastFragment.arguments = bundle
                        openFragment(breakfastFragment)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("bkadbjda", e.toString())
                }

            }
            override fun onFailure(call: Call<ArrayList<MessMenu>>, t: Throwable) {
                Log.e("log", call.request().url().toString())
                Log.e("ERROR2", t.toString())
            }
        })

    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_breakfast -> {

                toolbar.title = "Breakfast MessMenu"
                val breakfastFragment = BreakfastFragment.newInstance()
                val bundle = Bundle()
                bundle.putSerializable("menu", mBreakfast)
                breakfastFragment.arguments = bundle
                openFragment(breakfastFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_lunch -> {

                toolbar.title = "Lunch MessMenu"
                val lunchFragment = LunchFragment.newInstance()
                val bundle = Bundle()
                bundle.putSerializable("menu", mLunches)
                lunchFragment.arguments = bundle
                openFragment(lunchFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dinner -> {

                toolbar.title = "Dinner MessMenu"
                val dinnerFragment = DinnerFragment.newInstance()
                val bundle = Bundle()
                bundle.putSerializable("menu", mDinner)
                dinnerFragment.arguments = bundle
                openFragment(dinnerFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
