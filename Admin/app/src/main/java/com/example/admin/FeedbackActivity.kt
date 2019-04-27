package com.example.admin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_feedback.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.admin.adapters.FeedbackAdapter
import com.example.admin.networking.APIClient
import com.example.admin.networking.RetrofitService
import com.example.admin.objects.Feedback
import retrofit2.Call
import retrofit2.Response


class FeedbackActivity : AppCompatActivity() {

    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val ADMIN_MESS = "admin_mess"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        Log.e("kdh","aaya")

        feedbackProgress.visibility=View.VISIBLE
        recyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager


        val sharedPref = this@FeedbackActivity.getSharedPreferences("name", Context.MODE_PRIVATE) ?: return
        val mess = sharedPref.getString(ADMIN_MESS,"NO_MESS")

        val apiService = APIClient.getSOSClient().create<RetrofitService>(RetrofitService::class.java)
        val call = apiService.getFeedback(mess)

        var mDataset = ArrayList<Feedback>()

        call.enqueue(object : retrofit2.Callback<ArrayList<Feedback>> {

            override fun onResponse(call: Call<ArrayList<Feedback>>, response: Response<ArrayList<Feedback>>) {

                Log.e("log", call.request().url().toString())
                try {

                    val list = response.body()

                    for (l in list) {
                        Log.e("date"," : "+l.timeSlot)
                        mDataset.add(l)
                    }

                    val mAdapter = FeedbackAdapter(mDataset,this@FeedbackActivity)
                    recyclerView.adapter = mAdapter
                    feedbackProgress.visibility=View.GONE

                } catch (e: Exception) {
                    e.printStackTrace()

                }

            }
            override fun onFailure(call: Call<ArrayList<Feedback>>, t: Throwable) {
                Log.e("log", call.request().url().toString())
                Log.e("ERROR2", t.toString())
            }
        })

        }
}
