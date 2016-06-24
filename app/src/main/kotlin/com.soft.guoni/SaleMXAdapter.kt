package com.soft.guoni

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.DecimalFormat
import java.util.*

/**
 * Created by yuan on 2016/6/20.
 */
class SaleMXAdapter(context: Context, sqlite: SQLiteDatabase, start: Date, end: Date) : DataAdapter(context, sqlite, start, end) {
    override fun initData() {
        val s = start?.toString(MainActivity.formatString)
        val e = end?.toString(MainActivity.formatString)
        val c = db.rawQuery("select tm,sl,zq,je from sale_mx where date(rq)>='$s' and date(rq)<='$e'", null)
        var sl = 0
        var je = 0
        var id = 0
        var sum_sl = 0
        var sum_je = 0
        val formatter = DecimalFormat("#,###.00")
        while (c.moveToNext()) {
            sl = c.getInt(1)
            je = c.getInt(3)
            val m = HashMap<String, String>()
            m["id"] = (++id).toString()
            m["tm"] = c.getString(0) + ".00"
            m["sl"] = sl.toString()
            m["zq"] = formatter.format(c.getFloat(2))
            m["je"] = formatter.format(je)
            sum_sl += sl
            sum_je += je
            mData.add(m)
        }
        var map = HashMap<String, String>()
        map["id"] = "合计"
        map["tm"] = ""
        map["sl"] = sum_sl.toString()
        map["zq"] = ""
        map["je"] = sum_je.toString() + ".00"
        mData.add(map)
        c.close()
    }

    override fun compute() {
        throw UnsupportedOperationException()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var v: View
        var vh: ViewHolder
        if (convertView == null) {
            v = mInflater.inflate(R.layout.sale_mx_item, null)
            vh = ViewHolder(v)
            v.tag = vh
        } else {
            v = convertView
            vh = v.tag as ViewHolder
        }
        var m = mData[position]
        vh.id.text = m["id"]
        vh.tm.text = m["tm"]
        vh.sl.text = m["sl"]
        vh.zq.text = m["zq"]
        vh.je.text = m["je"]
        return v
    }

    private class ViewHolder(v: View) {
        val id = v.findViewById(R.id.sale_mx_id) as TextView
        var tm = v.findViewById(R.id.sale_mx_tm) as TextView
        var sl = v.findViewById(R.id.sale_mx_sl) as TextView
        var zq = v.findViewById(R.id.sale_mx_zq) as TextView
        var je = v.findViewById(R.id.sale_mx_je) as TextView
    }
}