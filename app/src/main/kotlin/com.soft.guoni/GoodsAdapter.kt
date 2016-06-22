package com.soft.guoni

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

/**
 * Created by yuan on 2016/6/17.
 */
class GoodsAdapter(context: Context, sqlite: SQLiteDatabase) : DataAdapter(context, sqlite) {

    override fun initData() {
        val cursor = db.rawQuery("select tm,sj,sl from goods", null)
        while (cursor.moveToNext()) {
            var map: HashMap<String, String> = HashMap()
            map.put("tm", cursor.getString(0))
            map.put("sj", cursor.getString(1) + ".00")
            map.put("zq", "1.00")
            map.put("sl", cursor.getString(2))

            mData.add(map)
        }
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
        var map: HashMap<String, String> = mData[position]
        holder.tm.text = map["tm"]
        holder.sj.text = map["sj"]
        holder.zq.text = map["zq"]
        holder.sl.text = map["sl"]
        return v
    }

    private class ViewHolder(var v: View) {
        var tm: TextView = v.findViewById(R.id.goods_tm) as TextView
        var sj: TextView = v.findViewById(R.id.goods_sj) as TextView
        var zq: TextView = v.findViewById(R.id.goods_zq) as TextView
        var sl: TextView = v.findViewById(R.id.goods_sl) as TextView
    }
}