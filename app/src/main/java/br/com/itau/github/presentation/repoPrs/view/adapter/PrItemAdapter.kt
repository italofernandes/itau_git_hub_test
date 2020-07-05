package br.com.itau.github.presentation.repoPrs.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.itau.github.R
import br.com.itau.github.common.extentions.loadUrlImage
import br.com.itau.github.domain.entity.PrEntity
import kotlinx.android.synthetic.main.pr_list_item.view.*

class PrItemAdapter: RecyclerView.Adapter<PrItemAdapter.PrItemViewHolder>() {

    var list: List<PrEntity> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrItemViewHolder {
        val  root = LayoutInflater.from(parent.context).inflate(R.layout.pr_list_item, null)
        return PrItemViewHolder(root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PrItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class PrItemViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view){
        fun bind(pr: PrEntity){

            view.prTitle.text = pr.title
            view.prSummary.text = pr.body
            view.prAuthorName.text = pr.authorName
            view.prAuthorImage.loadUrlImage(pr.authorImage)

            configureAccessibility(view)
        }

        private fun configureAccessibility(view: View){
            view.prTitle.contentDescription = view.context.getString(R.string.pr_item_accessibility_repo_title,  view.prTitle.text)
            view.prSummary.contentDescription = view.context.getString(R.string.pr_item_accessibility_repo_summary, view.prSummary.text)
            view.prAuthorName.contentDescription = view.context.getString(R.string.pr_item_accessibility_repo_author, view.prAuthorName.text)
        }
    }
}