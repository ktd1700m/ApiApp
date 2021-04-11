package jp.techacademy.takeshi.kataoka.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    // 一覧画面から登録するときのコールバック（FavoriteFragmentへ通知するメソッド）
    var onClickAddFavorite: ((String) -> Unit)? = null
    // 一覧画面から削除するときのコールバック（ApiFragmentへ通知するメソッド）
    var onClickDeleteFavorite:((String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL_FAVORITE).toString())

        fab_favorite.apply {
            val id = intent.getStringExtra(KEY_ID)
            val isFavorite = FavoriteShop.findBy(id.toString()) != null
            setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
            setOnClickListener {
                if (isFavorite) {
                    if (id != null) {
                        onClickDeleteFavorite?.invoke(id)
                    }
                } else {
                    if (id != null) {
                        onClickAddFavorite?.invoke(id)
                    }
                }
            }
        }
    }

    companion object {
        private const val KEY_URL = "key_url"
        private const val KEY_URL_FAVORITE = "key_url_favorite"
        private const val KEY_ID = "id"
        fun start(activity: Activity, shop: Shop) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java)
                .putExtra(KEY_URL, shop.couponUrls.toString())
                .putExtra(KEY_ID, shop.id)
            )
        }
        fun start(activity: Activity, favoriteShop: FavoriteShop) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java)
                .putExtra(KEY_URL_FAVORITE, favoriteShop.url)
                .putExtra(KEY_ID, favoriteShop.id)
            )
        }
    }
}