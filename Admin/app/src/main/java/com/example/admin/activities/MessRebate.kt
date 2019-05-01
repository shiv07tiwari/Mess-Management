package com.example.admin.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.admin.R
import com.example.admin.adapters.RebateAdapter
import com.example.admin.networking.APIClient
import com.example.admin.networking.RetrofitService
import com.example.admin.objects.Rebate
import kotlinx.android.synthetic.main.activity_mess_rebate.*
import retrofit2.Call
import retrofit2.Response

class MessRebate : AppCompatActivity() {


    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val ADMIN_MESS = "admin_mess"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mess_rebate)

        recyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager

        val sharedPref = this@MessRebate.getSharedPreferences("name", Context.MODE_PRIVATE) ?: return
        val mess = sharedPref.getString(ADMIN_MESS,"NO_MESS")

        val apiService = APIClient.getSOSClient().create<RetrofitService>(RetrofitService::class.java)
        val call = apiService.getRebate(mess)

        var mDataset = ArrayList<Rebate>()

        call.enqueue(object : retrofit2.Callback<ArrayList<Rebate>> {

            override fun onResponse(call: Call<ArrayList<Rebate>>, response: Response<ArrayList<Rebate>>) {

                Log.e("log", call.request().url().toString())
                try {

                    val list = response.body()

                    for (l in list) {
                        Log.e("date"," : "+l.fromDate)
                        mDataset.add(l)
                    }

                    val mAdapter = RebateAdapter(mDataset,this@MessRebate)
                    recyclerView.adapter = mAdapter

                } catch (e: Exception) {
                    e.printStackTrace()

                }

            }
            override fun onFailure(call: Call<ArrayList<Rebate>>, t: Throwable) {
                Log.e("log", call.request().url().toString())
                Log.e("ERROR2", t.toString())
            }
        })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this@MessRebate, DashBoard::class.java)
        startActivity(i)
    }
}
