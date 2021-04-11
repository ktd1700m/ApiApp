package jp.techacademy.takeshi.kataoka.apiapp

interface FragmentCallback {
    // Itemを押したときの処理
    fun onClickItem(shop: Shop)
    fun onClickItem(shop: FavoriteShop)
    // お気に入り追加時の処理
    fun onAddFavorite(shop: Shop)
    // お気に入り削除時の処理
    fun onDeleteFavorite(id: String)
}