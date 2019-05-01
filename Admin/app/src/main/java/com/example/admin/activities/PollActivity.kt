package com.example.admin.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.admin.R
import kotlinx.android.synthetic.main.activity_poll.*
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.EditText
import android.widget.Toast
import com.example.admin.adapters.RebateAdapter
import com.example.admin.networking.APIClient
import com.example.admin.networking.RetrofitService
import com.example.admin.objects.Poll
import com.example.admin.objects.Rebate
import kotlinx.android.synthetic.main.activity_mess_rebate.*
import retrofit2.Call
import retrofit2.Response




class PollActivity : AppCompatActivity() {

    var number = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poll)

        val allEds = ArrayList<EditText>()

        add_option.setOnClickListener {

            val et = EditText(this)
            val p = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            et.layoutParams = p
            et.hint = "Option $number"
            et.tag = "option" + 1
            ll_options.addView(et)
            allEds.add(et)
            number++
        }
        make_pole.setOnClickListener {

            Log.e("log","Clicked");
            val sharedPref = this@PollActivity.getSharedPreferences("name", Context.MODE_PRIVATE)
            val mess = sharedPref.getString("admin_mess","NO_MESS")

            var strings = o1.text.toString() + ","

            Log.e("size","bajddj+ "+allEds.size)
            var i=0
            while(i<allEds.size) {
                Log.e("js","js"+allEds[i].text.toString())
                strings = strings + allEds[i].text.toString() + ","
                i++
            }

            Log.e("test","str : "+strings)
            val apiService = APIClient.getSOSClient().create<RetrofitService>(RetrofitService::class.java)
            var p = Poll(poll_id.text.toString(),poll_question.text.toString(),strings)
            val call = apiService.makePoll(p, mess)


            call.enqueue(object : retrofit2.Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {

                    Log.e("log", call.request().url().toString())
                    try {

                        val list = response.body()
                        Toast.makeText(this@PollActivity,"Poll Created Successfully",Toast.LENGTH_LONG).show()

                    } catch (e: Exception) {
                        e.printStackTrace()

                    }

                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("log", call.request().url().toString())
                    Log.e("ERROR2", t.toString())
                }
            })
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this@PollActivity, DashBoard::class.java)
        startActivity(i)
    }
}
