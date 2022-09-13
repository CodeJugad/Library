package com.example.library.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.model.Book
import org.w3c.dom.Text
import java.util.ArrayList


class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Book>) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DashboardViewHolder,
        position: Int
    ) {
        val item = itemList[position]
        holder.txtBookName.text = item.bookName
        holder.txtBookAuthor.text = item.bookAuthor
        holder.txtBookPrice.text = item.bookPrice
        holder.txtBookRating.text = item.bookRating
        holder.imgBookImage.setImageResource(item.bookImage)

        holder.rlContent.setOnClickListener(){
            Toast.makeText(context,"clicked on ${holder.txtBookName.text}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val txtBookName : TextView = view.findViewById(R.id.txtBookName)
        val txtBookAuthor : TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice : TextView = view.findViewById(R.id.txtBookPrice)
        val txtBookRating : TextView = view.findViewById(R.id.txtBookRating)
        val imgBookImage : ImageView = view.findViewById(R.id.imgBookImage)

        val rlContent : RelativeLayout = view.findViewById(R.id.rlContent)
    }

}