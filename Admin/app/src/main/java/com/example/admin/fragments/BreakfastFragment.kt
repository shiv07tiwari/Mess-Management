package com.example.admin.fragments

import android.app.ActionBar
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*

import com.example.admin.R
import com.example.admin.objects.MessMenu
import kotlinx.android.synthetic.main.fragment_breakfast.view.*
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.admin.adapters.RebateAdapter
import com.example.admin.networking.APIClient
import com.example.admin.networking.RetrofitService
import com.example.admin.objects.Rebate
import kotlinx.android.synthetic.main.activity_mess_rebate.*
import kotlinx.android.synthetic.main.edit_menu_dialog.*
import retrofit2.Call
import retrofit2.Response


class BreakfastFragment : Fragment() {

    var strtext : ArrayList<MessMenu> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_breakfast, container, false)
        strtext  = arguments!!.getSerializable("menu") as ArrayList<MessMenu>

        rootView.b_everyday.text = strtext[0].fixedMenu
        rootView.b_monday.text = strtext[0].mondayMenu
        rootView.b_tuesday.text = strtext[0].tuesdayMenu
        rootView.b_wednesday.text = strtext[0].wednesdayMenu
        rootView.b_thursday.text = strtext[0].thursdayMenu
        rootView.b_friday.text = strtext[0].fridayMenu
        rootView.b_saturday.text = strtext[0].saturdayMenu
        rootView.b_sunday.text = strtext[0].sundayMenu

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() = BreakfastFragment()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when(item.itemId) {
            R.id.menuEditWeekly -> {
                val dialog = Dialog(context)
                val metrics = resources.displayMetrics
                val width = metrics.widthPixels
                dialog.setContentView(R.layout.edit_menu_dialog)

                dialog.window.setLayout((6 * width)/7, ActionBar.LayoutParams.WRAP_CONTENT)

                dialog.update_monday.setText(strtext[0].mondayMenu)
                dialog.update_tuesday.setText(strtext[0].tuesdayMenu)
                dialog.update_wednesday.setText(strtext[0].wednesdayMenu)
                dialog.update_thursday.setText(strtext[0].thursdayMenu)
                dialog.update_friday.setText(strtext[0].fridayMenu)
                dialog.update_saturday.setText(strtext[0].saturdayMenu)

                dialog.update_sunday.setText(strtext[0].sundayMenu)
                dialog.update_everyday.setText(strtext[0].fixedMenu)

                dialog.dialogButton_menu.setOnClickListener {

                    val sharedPref = this@BreakfastFragment.activity?.getSharedPreferences("name", Context.MODE_PRIVATE)
                    val mess = sharedPref?.getString("admin_mess","NO_MESS")

                    val apiService = APIClient.getSOSClient().create<RetrofitService>(RetrofitService::class.java)
                    var m = MessMenu(1,dialog.update_everyday.text.toString(),dialog.update_monday.text.toString(),
                        dialog.update_tuesday.text.toString(),dialog.update_wednesday.text.toString()
                    ,dialog.update_thursday.text.toString(),dialog.update_friday.text.toString(),dialog.update_saturday.text.toString()
                        ,dialog.update_sunday.text.toString())
                    val call = apiService.updateMenu(m,mess)


                    call.enqueue(object : retrofit2.Callback<String> {

                        override fun onResponse(call: Call<String>, response: Response<String>) {

                            Log.e("log", call.request().url().toString())
                            try {

                                val list = response.body()
                                Toast.makeText(this@BreakfastFragment.activity,"Updated Successfully !",
                                    Toast.LENGTH_LONG).show()

                                dialog.dismiss()

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

                dialog.setCancelable(false)
                dialog.show()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        })
    }
}
