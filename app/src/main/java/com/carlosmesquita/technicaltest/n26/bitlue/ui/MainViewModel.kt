package com.carlosmesquita.technicaltest.n26.bitlue.ui

import androidx.lifecycle.ViewModel
import com.carlosmesquita.technicaltest.n26.bitlue.repository.BitcoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: BitcoinRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
}
