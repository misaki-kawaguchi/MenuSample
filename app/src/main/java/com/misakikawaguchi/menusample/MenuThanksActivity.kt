package com.misakikawaguchi.menusample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView

class MenuThanksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_thanks)

        val menuName = intent.getStringExtra("menuName")
        val menuPrice = intent.getStringExtra("menuPrice")

        val tvMenuName = findViewById<TextView>(R.id.tvMenuName)
        val tvMenuPrice = findViewById<TextView>(R.id.tvMenuPrice)

        tvMenuName.text = menuName
        tvMenuPrice.text = menuPrice

        // 戻るメニューを表示
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // 戻るメニューを押したときの処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // 戻るボタンを押した場合、アクティビティを終了
        if(item.itemId == android.R.id.home) {
            finish()
        }

        // 親クラスの同名メソッドを呼び出し、その戻り値を返却
        return super.onOptionsItemSelected(item)
    }


}