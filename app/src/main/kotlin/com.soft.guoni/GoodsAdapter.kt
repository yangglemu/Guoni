package com.soft.guoni

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.DecimalFormat
import java.util.*

/**
 * Created by yuan on 2016/6/17.
 */
class GoodsAdapter(context: Context, sqlite: SQLiteDatabase) : DataAdapter(context, sqlite) {

    override fun initData() {
        val cursor = db.rawQuery("select tm,sl from goods where sl>0 order by sj asc", null)
        var id = 0
        var sl: Int
        var sj: Int
        var je: Int
        var sum_sl = 0
        var sum_je = 0
        val formatter = DecimalFormat("#,###.00")
        while (cursor.moveToNext()) {
            val map: HashMap<String, String> = HashMap()
            sl = cursor.getInt(1)
            sj = cursor.getInt(0)
            map.put("id", (++id).toString())
            map.put("tm", sj.toString() + ".00")
            map.put("sl", sl.toString())
            map.put("zq", "1.00")
            je = sl * sj
            map.put("je", formatter.format(je))
            sum_sl += sl
            sum_je += je
            mData.add(map)
        }
        val map = HashMap<String, String>()
        map["id"] = "合计"
        map["tm"] = ""
        map["sl"] = sum_sl.toString()
        map["zq"] = ""
        map["je"] = formatter.format(sum_je)
        mData.add(map)
        cursor.close()
    }

    override fun compute() {
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var holder: ViewHolder
        var v: View
        if (convertView == null) {
            v = mInflater.inflate(R.layout.goods_item, null)
            holder = ViewHolder(v)
            v.tag = holder
        } else {
            v = convertView
            holder = v.tag as ViewHolder
        }
        var map = mData[position]
        holder.id.text = map["id"]
        holder.tm.text = map["tm"]
        holder.sl.text = map["sl"]
        holder.zq.text = map["zq"]
        holder.je.text = map["je"]
        return v
    }

    private class ViewHolder(var v: View) {
        var id: TextView = v.findViewById(R.id.goods_id) as TextView
        var tm: TextView = v.findViewById(R.id.goods_tm) as TextView
        var sl: TextView = v.findViewById(R.id.goods_sl) as TextView
        var zq: TextView = v.findViewById(R.id.goods_zq) as TextView
        var je: TextView = v.findViewById(R.id.goods_je) as TextView
    }
}