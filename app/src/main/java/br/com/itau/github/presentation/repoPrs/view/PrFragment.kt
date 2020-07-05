package br.com.itau.github.presentation.repoPrs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.itau.github.R
import br.com.itau.github.di.loadPrListDependencies
import br.com.itau.github.domain.entity.RepoEntity
import br.com.itau.github.presentation.repoPrs.view.adapter.PrItemAdapter
import br.com.itau.github.presentation.repoPrs.viewmodel.PrViewModel
import kotlinx.android.synthetic.main.fragment_pr.view.*
import kotlinx.android.synthetic.main.tool_bar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


const val REPO_BUNDLE_EXTRA = "repo_extra"

class PrFragment : Fragment() {

    private val viewModel: PrViewModel by viewModel()
    private lateinit var prAdapter: PrItemAdapter

    init {
        loadPrListDependencies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<RepoEntity>(REPO_BUNDLE_EXTRA)?.let {repo ->
            viewModel.loadPrs(repo.author, repo.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pr, container, false)
    }

    override fun onResume() {
        super.onResume()
        view?.prList?.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prAdapter = PrItemAdapter()

        view.prList.apply {
            this.adapter = prAdapter
            this.layoutManager = LinearLayoutManager(this@PrFragment.activity)
            this.addItemDecoration(DividerItemDecoration(this@PrFragment.activity,  LinearLayoutManager.VERTICAL))
        }

        observe(view)
    }

    private fun observe(view: View){

        viewModel.repoName.observe(viewLifecycleOwner, Observer { repoName->
            (activity as AppCompatActivity?)?.apply {
                val toolbar = view.toolbar
                setSupportActionBar(view.toolbar)
                title = repoName
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.contentDescription = getString(R.string.pr_toolbar_accessibility_repo_title, repoName)
            }
        })

        viewModel.prList.observe(viewLifecycleOwner, Observer { list ->
            prAdapter.list = list
        })

        viewModel.errorMsgRes.observe(viewLifecycleOwner, Observer { msgRes ->
            Toast.makeText(activity, msgRes, Toast.LENGTH_LONG).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { show ->
            view.loading.visibility = if (show) View.VISIBLE else View.GONE
        })

        viewModel.emptyList.observe(viewLifecycleOwner, Observer { show ->
            view.prEmptyListMsg.visibility = if (show) View.VISIBLE else View.GONE
        })
    }
}