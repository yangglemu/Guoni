package com.soft.guoni

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.widget.BaseAdapter
import java.util.*

/**
 * Created by yuan on 2016/6/16.
 */
    abstract class DataAdapter(context: Context, sqlite:SQLiteDatabase) : BaseAdapter() {
    val mContext = context
    val mInflater = LayoutInflater.from(mContext)
    val mData = ArrayList<HashMap<String, String>>()
    val db = sqlite
    init {
        initData()
    }

    protected abstract  fun initData()
    abstract fun compute()

    override fun getCount(): Int = mData.size

    override fun getItem(position: Int): Any? = mData[position]

    override fun getItemId(position: Int): Long = position.toLong()
}