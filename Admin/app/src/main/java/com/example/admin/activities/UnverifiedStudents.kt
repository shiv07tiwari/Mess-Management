package com.example.admin.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.admin.R
import com.example.admin.adapters.UnverifiedAdapter
import com.example.admin.networking.APIClient
import com.example.admin.networking.RetrofitService
import com.example.admin.objects.Student
import kotlinx.android.synthetic.main.activity_unverified_students.*
import retrofit2.Call
import retrofit2.Response

class UnverifiedStudents : AppCompatActivity() {


    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val ADMIN_MESS = "admin_mess"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unverified_students)

        recyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.visibility = View.GONE
        progressBar.visibility=View.VISIBLE


        val sharedPref = this@UnverifiedStudents.getSharedPreferences("name", Context.MODE_PRIVATE) ?: return
        val mess = sharedPref.getString(ADMIN_MESS,"NO_MESS")

        val apiService = APIClient.getSOSClient().create<RetrofitService>(RetrofitService::class.java)
        val call = apiService.getUnverifiedStudents(mess)

        var mDataset = ArrayList<Student>()

        call.enqueue(object : retrofit2.Callback<ArrayList<Student>> {

            override fun onResponse(call: Call<ArrayList<Student>>, response: Response<ArrayList<Student>>) {

                Log.e("log", call.request().url().toString())
                try {

                    val list = response.body()

                    for (l in list) {
                        //Log.e("date"," : "+l.timeSlot)
                        mDataset.add(l)
                    }

                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility=View.GONE
                    val mAdapter = UnverifiedAdapter(mDataset,this@UnverifiedStudents)
                    recyclerView.adapter = mAdapter

                } catch (e: Exception) {
                    e.printStackTrace()

                }

            }
            override fun onFailure(call: Call<ArrayList<Student>>, t: Throwable) {
                Log.e("log", call.request().url().toString())
                Log.e("ERROR2", t.toString())
            }
        })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this@UnverifiedStudents, DashBoard::class.java)
        startActivity(i)
    }
}
