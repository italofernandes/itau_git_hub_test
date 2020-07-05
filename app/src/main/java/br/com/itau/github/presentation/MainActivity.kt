package br.com.itau.github.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.itau.github.R
import br.com.itau.github.di.loadListDependencies
import br.com.itau.github.di.loadPrListDependencies
import br.com.itau.github.domain.usecase.list.RepoListUseCase
import br.com.itau.github.domain.usecase.pr.RepoPrUseCase
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main)