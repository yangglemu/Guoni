package com.soft.guoni

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

/**
 * Created by yuan on 2016/6/20.
 */
class SaleMXAdapter(context: Context, sqlite: SQLiteDatabase) : DataAdapter(context, sqlite) {
    override fun initData() {
        var formatString = "yyyy-MM-dd"
        var c = db.rawQuery("select tm,sl,zq,je from sale_mx where date(rq)='${Date().toString(formatString)}'", null)
        while (c.moveToNext()) {
            var m = HashMap<String, String>()
            m["tm"] = c.getString(0)
            m["sl"] = c.getString(1)
            m["zq"] = c.getString(2)
            m["je"] = c.getString(3) + ".00"
            mData.add(m)
        }
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
        vh.tm.text = m["tm"]
        vh.sl.text = m["sl"]
        vh.zq.text = m["zq"]
        vh.je.text = m["je"]
        return v
    }

    private class ViewHolder(v: View) {
        var tm = v.findViewById(R.id.sale_mx_tm) as TextView
        var sl = v.findViewById(R.id.sale_mx_sl) as TextView
        var zq = v.findViewById(R.id.sale_mx_zq) as TextView
        var je = v.findViewById(R.id.sale_mx_je) as TextView
    }
}