package com.soft.guoni

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Toast

class MainActivity : Activity() {
    val db: SQLiteDatabase by lazy {
        DbHelper(this).writableDatabase
    }
    val email: Email by lazy {
        Email(this, db)
    }
    val mainLayout: LinearLayout by lazy {
        findViewById(R.id.mainLayout) as LinearLayout
    }
    var listLayout: View? = null
    var listView: ListView? = null

    companion object {
        var isRunning = false
    }

    var isRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        createListLayout(R.layout.goods, R.id.listView_goods, GoodsAdapter(this, db))
    }

    fun createListLayout(layoutId: Int, listViewId: Int, adapter: DataAdapter) {
        if (listLayout != null) mainLayout.removeView(listLayout)
        listLayout = layoutInflater.inflate(layoutId, null)
        listView = listLayout?.findViewById(listViewId) as ListView
        listView?.adapter = adapter
        mainLayout.addView(listLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sp -> createListLayout(R.layout.goods, R.id.listView_goods, GoodsAdapter(this, db))
            R.id.fs -> createListLayout(R.layout.sale_fs, R.id.listView_sale_fs, SaleFSAdapter(this, db))
            R.id.mx -> toast("Play")
            R.id.db -> showPopupMenu(listLayout!!)
            R.id.refresh -> {
                if (!isRunning) {
                    isRunning = true
                    Thread(Runnable { email.receive() }).start()
                }
            }
            R.id.exit -> finish()
            else -> return false
        }
        return true
    }

    fun showPopupMenu(v: View) {
        val menu = PopupMenu(this, v)
        menuInflater.inflate(R.menu.main, menu.menu)
        menu.show()
    }
    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}