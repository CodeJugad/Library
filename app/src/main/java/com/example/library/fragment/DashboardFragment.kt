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

    val bookInfoList = arrayListOf<Book>(
        Book("1","Physics","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("1","Chemistry","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("1","Maths","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("1","History","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("1","Economics","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("1","Hindi","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("1","English","hc verma","rs.299","4.5",R.drawable.ic_profile),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

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
//        val url = "http://13.235.250.119/vl/book/fetch_books/"
        val url = "https://gist.githubusercontent.com/nanotaboada/6396437/raw/855dd84436be2c86e192abae2ac605743fc3a127/books.json"
        if (ConnectionManger().checkConnectivity(activity as Context)){
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {
                println("Response is $it")
//            val success = it.getBoolean("success")
                val success = true
                if (success){
                    val data = it.getJSONArray("books")
                    for (i in 0 until data.length()){
                        val bookJsonObject = data.getJSONObject(i)
                        val bookObject = Book(
                            bookJsonObject.getString("isbn"),
                            bookJsonObject.getString("title"),
                            bookJsonObject.getString("author"),
                            bookJsonObject.getString("pages"),
                            bookJsonObject.getString("pages"),
                            R.drawable.ic_profile
                        )
                        bookInfoList.add(bookObject)
                        var recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)

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

                }else{
                    Toast.makeText(activity as Context,"Some Error Occurred!!!",Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener {
                println("Error is $it")
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Content-type"] = "application/json"
//                headers["token"] = "6f6a30929c3919"
                    return headers
                }
            }

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