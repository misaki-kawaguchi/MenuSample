package com.misakikawaguchi.menusample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private var _menuList : MutableList<MutableMap<String, Any>>? = null

    private val FROM = arrayOf("name", "price")
    private val TO = intArrayOf(R.id.tvMenuName, R.id.tvMenuPrice)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _menuList = createTeishoku()

        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        val adapter = SimpleAdapter(applicationContext, _menuList, R.layout.row, FROM, TO)
        lvMenu.adapter = adapter

        lvMenu.onItemClickListener = ListItemClickListener()

        // 長押しを検知する
        registerForContextMenu(lvMenu)

    }

    // 定食メニューを生成
    private fun createTeishoku() : MutableList<MutableMap<String, Any>> {
        val menuList : MutableList<MutableMap<String, Any>> = mutableListOf()

        var menu = mutableMapOf("name" to "から揚げ定食", "price" to 800, "desc" to "から揚げ定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ハンバーグ定食", "price" to 850, "desc" to "ハンバーグ定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "生姜焼き定食", "price" to 800, "desc" to "生姜焼き定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ステーキ定食", "price" to 1000, "desc" to "ステーキ定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "野菜炒め定食", "price" to 750, "desc" to "野菜炒め定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "とんかつ定食", "price" to 800, "desc" to "とんかつ定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ミンチカツ定食", "price" to 850, "desc" to "ミンチカツ定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "チキンカツ定食", "price" to 800, "desc" to "チキンカツ定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "コロッケ定食", "price" to 750, "desc" to "コロッケ定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "焼き魚定食", "price" to 800, "desc" to "焼き魚定食です")
        menuList.add(menu)
        menu = mutableMapOf("name" to "焼肉定食", "price" to 900, "desc" to "焼肉定食です")
        menuList.add(menu)

        return menuList
    }

    // カレーメニューを生成
    private fun createCurry() : MutableList<MutableMap<String, Any>> {
        val menuList : MutableList<MutableMap<String, Any>> = mutableListOf()

        var menu = mutableMapOf("name" to "ビーフカレー", "price" to 520, "desc" to "ビーフカレーです")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ポークカレー", "price" to 420, "desc" to "ポークカレーです")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ハンバーグカレー", "price" to 620, "desc" to "ハンバーグカレーです")
        menuList.add(menu)
        menu = mutableMapOf("name" to "チーズカレー", "price" to 560, "desc" to "チーズカレーです")
        menuList.add(menu)
        menu = mutableMapOf("name" to "カツカレー", "price" to 750, "desc" to "カツカレーです")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ビーフカツカレー", "price" to 890, "desc" to "ビーフカツカレーです")
        menuList.add(menu)
        menu = mutableMapOf("name" to "から揚げカレー", "price" to 540, "desc" to "から揚げカレーです")
        menuList.add(menu)

        return menuList
    }

    // オプションメニューを表示する
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_options_menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // オプションメニューをクリックしたときの処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menuListOptionTeishoku ->
                _menuList = createTeishoku()
            R.id.menuListOptionCurry ->
                _menuList = createCurry()
        }

        val lvMenu = findViewById<ListView>(R.id.lvMenu)

        val adapter = SimpleAdapter(applicationContext, _menuList, R.layout.row, FROM, TO)

        lvMenu.adapter = adapter

        return super.onOptionsItemSelected(item)
    }

    // コンテキストメニューを表示する
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.menu_context_menu_list, menu)
        menu.setHeaderTitle(R.string.menu_list_context_header)
    }

    // コンテキストメニューを選択したときの処理
    override fun onContextItemSelected(item: MenuItem): Boolean {

        // 長押しされたビューに関する情報が書くのされたオブジェクトを取得
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo

        // 長押しされたリストのポジションを取得
        val listPosition = info.position

        // ポジションから長押しされたメニュー情報Mapオブジェクトを取得
        val menu = _menuList!![listPosition]

        // 選択されたメニューのIDのR値による処理の分岐
        when(item.itemId) {
            // 「説明を表示」メニューが選択されたときの処理
            R.id.menuListContextDesc -> {
                // メニューの説明文字列を取得
                val desc = menu["desc"] as String
                // トーストを表示
                Toast.makeText(applicationContext, desc, Toast.LENGTH_LONG).show()
            }

            // 「ご注文」メニューが選択されたときの処理
            R.id.menuListContextOrder ->
                // 注文処理
            order(menu)
        }

        return super.onContextItemSelected(item)
    }

    // リストビューをクリックする
    private inner class ListItemClickListener() : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val item = parent.getItemAtPosition(position) as MutableMap<String, Any>
            order(item)
        }
    }

    // コンテキストメニューの「ご注文」とリストビューをクリックしたときの処理
    private fun order(menu: MutableMap<String, Any>) {

        val menuName = menu["name"] as String
        val menuPrice = menu["price"] as Int

        val intent = Intent(applicationContext, MenuThanksActivity::class.java)
        intent.putExtra("menuName", menuName)
        intent.putExtra("menuPrice", "${menuPrice}円")

        startActivity(intent)
    }
}