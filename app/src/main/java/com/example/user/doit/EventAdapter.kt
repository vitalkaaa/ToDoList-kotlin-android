package com.example.user.doit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class EventAdapter(ctx: Context, private var events: MutableList<Event>): BaseAdapter() {
    private val  lInflater: LayoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount() = events.size

    override fun getItemId(id: Int) = id.toLong()

    override fun getItem(pos: Int) = events[pos]

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View? {
        var view: View? = convertView
        if (view == null) {
            view = lInflater.inflate(R.layout.row_bg, parent, false)
        }

        val event = events[pos]
        view?.findViewById<TextView>(R.id.txtTime)?.text = event.getTimeString()//case.date.toString()
        view?.findViewById<TextView>(R.id.txtDate)?.text = event.getDateString()
        view?.findViewById<TextView>(R.id.txtTitle)?.text = event.title
        view?.findViewById<TextView>(R.id.txtContent)?.text = event.content

        return view
    }
}