package br.com.itau.github.presentation.repoList.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.itau.github.R
import br.com.itau.github.common.extentions.addEndScrollListener
import br.com.itau.github.di.loadListDependencies
import br.com.itau.github.presentation.repoList.view.adapter.RepoItemAdapter
import br.com.itau.github.presentation.repoList.viewmodel.RepoListViewModel
import br.com.itau.github.presentation.repoPrs.view.REPO_BUNDLE_EXTRA
import kotlinx.android.synthetic.main.fragment_repo_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoListFragment : Fragment() {
    private val viewModel: RepoListViewModel by viewModel()
    private lateinit var listAdapter: RepoItemAdapter
    private lateinit var navController: NavController

    init { loadListDependencies() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchAllRepo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        listAdapter = RepoItemAdapter {repo ->
            val bundle = bundleOf(REPO_BUNDLE_EXTRA to repo)
            navController.navigate(R.id.action_go_to_pr_view, bundle)
        }

        view.repoList.apply {
            adapter = listAdapter
            this.layoutManager = LinearLayoutManager(this@RepoListFragment.activity)
            this.addItemDecoration(DividerItemDecoration(this@RepoListFragment.activity,  LinearLayoutManager.VERTICAL))

            addEndScrollListener {
                viewModel.fetchAllRepo()
            }
        }

        observe(view)
    }

    private fun observe(view: View){
        viewModel.listRepo.observe(viewLifecycleOwner, Observer { list ->
            listAdapter.list = list
        })

        viewModel.errorMsgRes.observe(viewLifecycleOwner, Observer {msgRes ->
            Toast.makeText(activity, msgRes, Toast.LENGTH_LONG).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { show->
            view.loading.visibility = if (show) View.VISIBLE else View.GONE
        })
    }
}