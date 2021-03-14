package com.carlosmesquita.technicaltest.n26.bitlue.ui.bitcoin_value

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.carlosmesquita.technicaltest.n26.bitlue.R
import com.carlosmesquita.technicaltest.n26.bitlue.databinding.FragmentBitcoinValueBinding
import com.carlosmesquita.technicaltest.n26.bitlue.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BitcoinValueFragment : Fragment(R.layout.fragment_bitcoin_value) {

    private val viewModel: MainViewModel by activityViewModels()

    private val binding: FragmentBitcoinValueBinding
        get() = _binding!!
    private var _binding: FragmentBitcoinValueBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBitcoinValueBinding.bind(view).apply {
            lifecycleOwner = this@BitcoinValueFragment.viewLifecycleOwner
            viewModel = this@BitcoinValueFragment.viewModel
        }

        viewModel.bitcoinValues.observe(viewLifecycleOwner) {
            binding.valuesChart.setBitcoinValues(it)
        }

        // TODO Hide for now until [FilterSettingsDialogFragment] is implemented
        binding.fab.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
