package br.com.itau.github.presentation.repoList.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.itau.github.R
import br.com.itau.github.common.extentions.loadUrlImage
import br.com.itau.github.domain.entity.RepoEntity
import kotlinx.android.synthetic.main.repo_list_item.view.*

class RepoItemAdapter(
    private var repoClickListener: ((RepoEntity)->Unit)?
): RecyclerView.Adapter<RepoItemAdapter.RepoItemViewHolder>() {

    var list: List<RepoEntity> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, null)
        return RepoItemViewHolder(root)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        val repo = list[position]
        holder.bind(repo)
    }

    inner class RepoItemViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {

        fun bind(repo: RepoEntity) {
            view.repoNameLabel.text = repo.name
            view.repoDesc.text = repo.description
            view.forkText.text = repo.forkNumber.toString()
            view.favText.text = repo.starsNumber.toString()
            view.authorName.text = repo.author
            view.authorImg.loadUrlImage(repo.authorImage)

            view.setOnClickListener {
                repoClickListener?.invoke(repo)
            }

            configureAccessibility(view)
        }

        private fun configureAccessibility(view: View){
            view.repoNameLabel.contentDescription = view.context.getString(R.string.repo_item_accessibility_repo_title,  view.repoNameLabel.text)
            view.repoDesc.contentDescription = view.context.getString(R.string.repo_item_accessibility_repo_summary, view.repoDesc.text)
            view.forkText.contentDescription = view.context.getString(R.string.repo_item_accessibility_repo_forks, view.forkText.text)
            view.favText.contentDescription = view.context.getString(R.string.repo_item_accessibility_repo_star, view.favText.text)
            view.authorName.contentDescription = view.context.getString(R.string.repo_item_accessibility_repo_author, view.authorName.text)
        }
    }
}


