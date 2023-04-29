package com.example.library.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.library.R
import com.example.library.adapter.DashboardRecyclerAdapter
import com.example.library.model.Book
import com.example.library.util.ConnectionManger

class DashboardFragment : Fragment() {

    lateinit var recyclerDashboard : RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    lateinit var progressLayout : RelativeLayout
    lateinit var progressBar : ProgressBar

    val bookInfoList = arrayListOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        layoutManager = LinearLayoutManager(activity)
//        var recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)
//
//        recyclerDashboard.adapter = recyclerAdapter
//        recyclerDashboard.layoutManager = layoutManager
//
//        // adding divider line
//        recyclerDashboard.addItemDecoration(
//            DividerItemDecoration(
//                recyclerDashboard.context,
//                (layoutManager as LinearLayoutManager).orientation
//            )
//        )

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/book/fetch_books/"
//        val url = "https://gist.githubusercontent.com/nanotaboada/6396437/raw/855dd84436be2c86e192abae2ac605743fc3a127/books.json"
        if (ConnectionManger().checkConnectivity(activity as Context)){
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {
                println("Response is $it")
                try {
                    progressLayout.visibility = View.GONE
                    val success = it.getBoolean("success")
                    if (success) {
                        val data = it.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val bookJsonObject = data.getJSONObject(i)
                            val bookObject = Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image")
                            )
                            bookInfoList.add(bookObject)
                            var recyclerAdapter =
                                DashboardRecyclerAdapter(activity as Context, bookInfoList)

                            recyclerDashboard.adapter = recyclerAdapter
                            recyclerDashboard.layoutManager = layoutManager

                            // adding divider line
                            recyclerDashboard.addItemDecoration(
                                DividerItemDecoration(
                                    recyclerDashboard.context,
                                    (layoutManager as LinearLayoutManager).orientation
                                )
                            )
                        }

                    } else {
                        Toast.makeText(
                            activity as Context,
                            "Some Error Occurred!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }catch (e: Exception){
                    Toast.makeText(
                        activity as Context,
                        "Some Unexpected Error Occurred!!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, Response.ErrorListener {
//                println("Error is $it")
                Toast.makeText(
                    activity as Context,
                    "Volley Error Occurred!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "6f6a30929c3919"
                    return headers
                }
            }
            // why this line
            queue.add(jsonObjectRequest)

        }else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection not Found")
            dialog.setPositiveButton("Ok"){text,listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){text,listener ->
                ActivityCompat.finishAffinity((activity as Activity))
            }
            dialog.create()
            dialog.show()
        }


        return view
    }

}