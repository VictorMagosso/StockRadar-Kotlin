package com.victor.stockradar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.victor.stockradar.R
import com.victor.stockradar.model.Stock

class AdapterStock(
    private val list: MutableList<Stock>
) :
    RecyclerView.Adapter<AdapterStock.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.adapter_stock, parent, false
            )
        return MyViewHolder(view)
    }

    fun updateList(stocks: List<Stock>) {
        list.clear()
        list.addAll(stocks)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val stock = list[position]
        holder.let { v ->
            v.symbol?.text = stock.symbol
            v.percent?.text = stock.change_percent
            v.title?.text = stock.name
            v.description?.text = stock.description
            v.price?.text = stock.price

            val isExpanded: Boolean = list[position].isExpanded
            v.expandableContent?.visibility = if (!isExpanded) View.GONE else View.VISIBLE

            if (isExpanded) {
                v.arrow?.setImageResource(R.drawable.ic_arrowup)
            } else {
                v.arrow?.setImageResource(R.drawable.ic_dropdowncustom)
            }

            v.adapterTitle?.setOnClickListener {
                val stock = list[position]
                stock.isExpanded = !stock.isExpanded
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var symbol: TextView? = null
        var percent: TextView? = null
        var title: TextView? = null
        var description: TextView? = null
        var price: TextView? = null
        var arrow: ImageView? = null
        var expandableContent: RelativeLayout? = null
        var adapterTitle: ConstraintLayout? = null

        init {
            symbol = itemView.findViewById(R.id.txtSymbolExpandable)
            percent = itemView.findViewById(R.id.txtPercentExpandable)
            title = itemView.findViewById(R.id.txtNameExpandable)
            description = itemView.findViewById(R.id.txtDescriptionExpandable)
            price = itemView.findViewById(R.id.txtPriceExpandable)
            arrow = itemView.findViewById(R.id.imgArrowExpandable)
            expandableContent = itemView.findViewById(R.id.expandableContent)
            adapterTitle = itemView.findViewById(R.id.adapterTitle)
        }
    }
}