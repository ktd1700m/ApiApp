package jp.techacademy.takeshi.kataoka.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val shopInfo = FavoriteShop()
        when (intent.getSerializableExtra(KEY_FROM_VIEW).toString()) {
            "shop" -> {
                webView.loadUrl(intent.getStringExtra(KEY_URL).toString())
                shopInfo.id = intent.getStringExtra(KEY_ID).toString()
                shopInfo.imageUrl = intent.getStringExtra(KEY_IMAGEURL).toString()
                shopInfo.name = intent.getStringExtra(KEY_NAME).toString()
                shopInfo.address = intent.getStringExtra(KEY_ADDRESS).toString()
                shopInfo.url = intent.getStringExtra(KEY_URL).toString()
            }
            "favorite_shop" -> {
                webView.loadUrl(intent.getStringExtra(KEY_URL_FAVORITE).toString())
                shopInfo.id = intent.getStringExtra(KEY_ID_FAVORITE).toString()
                shopInfo.imageUrl = intent.getStringExtra(KEY_IMAGEURL_FAVORITE).toString()
                shopInfo.name = intent.getStringExtra(KEY_NAME_FAVORITE).toString()
                shopInfo.address = intent.getStringExtra(KEY_ADDRESS_FAVORITE).toString()
                shopInfo.url = intent.getStringExtra(KEY_URL_FAVORITE).toString()
            }
        }

        var isFavorite = FavoriteShop.findBy(shopInfo.id) != null
        fab_favorite.setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)

        fab_favorite.setOnClickListener {
            isFavorite = FavoriteShop.findBy(shopInfo.id) != null
            fab_favorite.setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
            if (isFavorite) {
                FavoriteShop.delete(shopInfo.id)
                fab_favorite.setImageResource(R.drawable.ic_star_border)
            } else {
                FavoriteShop.insert(shopInfo)
                fab_favorite.setImageResource(R.drawable.ic_star)
            }
        }
    }

    companion object {
        private const val KEY_FROM_VIEW = "key_from_view"
        private const val KEY_ID = "key_id"
        private const val KEY_IMAGEURL = "key_imageUrl"
        private const val KEY_NAME = "key_name"
        private const val KEY_ADDRESS = "key_address"
        private const val KEY_URL = "key_url"
        private const val KEY_ID_FAVORITE = "key_id_favorite"
        private const val KEY_IMAGEURL_FAVORITE = "key_imageUrl_favorite"
        private const val KEY_NAME_FAVORITE = "key_name_favorite"
        private const val KEY_ADDRESS_FAVORITE = "key_address_favorite"
        private const val KEY_URL_FAVORITE = "key_url_favorite"

        fun start(activity: Activity, shop: Shop) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java)
                .putExtra(KEY_FROM_VIEW, "shop")
                .putExtra(KEY_ID, shop.id)
                .putExtra(KEY_IMAGEURL, shop.logoImage)
                .putExtra(KEY_NAME, shop.name)
                .putExtra(KEY_ADDRESS, shop.address)
                .putExtra(KEY_URL, if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc)
            )
        }
        fun start(activity: Activity, favoriteShop: FavoriteShop) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java)
                .putExtra(KEY_FROM_VIEW, "favorite_shop")
                .putExtra(KEY_ID_FAVORITE, favoriteShop.id)
                .putExtra(KEY_IMAGEURL_FAVORITE, favoriteShop.imageUrl)
                .putExtra(KEY_NAME_FAVORITE, favoriteShop.name)
                .putExtra(KEY_ADDRESS_FAVORITE, favoriteShop.address)
                .putExtra(KEY_URL_FAVORITE, favoriteShop.url)
            )
        }
    }
}