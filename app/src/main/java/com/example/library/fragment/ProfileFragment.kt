package com.example.library.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.library.R
import com.example.library.util.ConnectionManger


class ProfileFragment : Fragment() {

    lateinit var btnCheckInternet : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        btnCheckInternet = view.findViewById(R.id.btnCheckInternet)
        btnCheckInternet.setOnClickListener(){
            if (ConnectionManger().checkConnectivity(activity as Context)){
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection Found")
                dialog.setPositiveButton("Ok"){text,listener ->
                    Toast.makeText(activity as Context,"Ok clicked", Toast.LENGTH_SHORT).show()
                }
                dialog.setNegativeButton("Cancel"){text,listener ->
                    Toast.makeText(activity as Context,"Cancel clicked",Toast.LENGTH_SHORT).show()
                }
                dialog.create()
                dialog.show()
            }else{
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection not Found")
                dialog.setPositiveButton("Ok"){text,listener ->
                    Toast.makeText(activity as Context,"Ok clicked", Toast.LENGTH_SHORT).show()
                }
                dialog.setNegativeButton("Cancel"){text,listener ->
                    Toast.makeText(activity as Context,"Cancel clicked",Toast.LENGTH_SHORT).show()
                }
                dialog.create()
                dialog.show()
            }
        }

        return view
    }

}