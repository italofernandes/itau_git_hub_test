package br.com.itau.github.common.extentions

import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.repo_list_item.view.*

fun ImageView.loadUrlImage(url: String){
    Glide.with(this.context)
        .load(url)
        .circleCrop()
        .into(this);
}