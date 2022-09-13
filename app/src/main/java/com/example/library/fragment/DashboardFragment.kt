package com.example.library.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.adapter.DashboardRecyclerAdapter
import com.example.library.model.Book

class DashboardFragment : Fragment() {

    lateinit var recyclerDashboard : RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    val bookInfoList = arrayListOf<Book>(
        Book("Physics","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("Chemistry","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("Maths","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("History","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("Economics","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("Hindi","hc verma","rs.299","4.5",R.drawable.ic_profile),
        Book("English","hc verma","rs.299","4.5",R.drawable.ic_profile),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        layoutManager = LinearLayoutManager(activity)
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
        return view
    }

}