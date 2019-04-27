package com.example.admin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.admin.adapters.FeedbackAdapter
import com.example.admin.adapters.UnverifiedAdapter
import com.example.admin.networking.APIClient
import com.example.admin.networking.RetrofitService
import com.example.admin.objects.Feedback
import com.example.admin.objects.Student
import kotlinx.android.synthetic.main.activity_feedback.*
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
}
