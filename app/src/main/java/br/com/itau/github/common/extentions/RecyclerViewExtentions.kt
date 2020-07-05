package br.com.itau.github.common.extentions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

internal fun RecyclerView.addEndScrollListener(action: () -> Unit) {

    addOnScrollListener(object : RecyclerView.OnScrollListener() {

        private var load = false

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager?.childCount ?: 0
            val totalItemCount = layoutManager?.itemCount ?: 0
            val firstVisibleItemPosition: Int =
                (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

            takeIf {
                !load && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition > 0
            }?.apply {
                action()
                load = true
            } ?: kotlin.run {
                load = false
            }
        }
    })
}