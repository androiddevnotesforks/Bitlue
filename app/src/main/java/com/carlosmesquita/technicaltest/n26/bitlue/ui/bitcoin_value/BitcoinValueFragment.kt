package com.carlosmesquita.technicaltest.n26.bitlue.ui.bitcoin_value

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.carlosmesquita.technicaltest.n26.bitlue.BuildConfig
import com.carlosmesquita.technicaltest.n26.bitlue.R
import com.carlosmesquita.technicaltest.n26.bitlue.databinding.FragmentBitcoinValueBinding
import com.carlosmesquita.technicaltest.n26.bitlue.ui.MainViewModel
import com.carlosmesquita.technicaltest.n26.bitlue.ui.actions.MainEvents.BitcoinValueEvents
import com.carlosmesquita.technicaltest.n26.bitlue.ui.actions.MainStates.BitcoinValueStates
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
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

        collectUIStates()

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showErrorDialog(it ?: return@observe)
        }

        viewModel.bitcoinValues.observe(viewLifecycleOwner) {
            binding.valuesChart.show(it)
        }

        binding.themeToggle.setOnClickListener {
            sendEvent(BitcoinValueEvents.OnThemeToggleClicked)
        }

        binding.fab.setOnClickListener {
            sendEvent(BitcoinValueEvents.OnFabClicked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun collectUIStates() = lifecycleScope.launchWhenStarted {
        viewModel.states.collect {
            Timber.i("State received => ${it::class.java.name}")

            if (it !is BitcoinValueStates) {
                return@collect
            }

            when (it) {
                BitcoinValueStates.OpenFilterSettings -> {
                    findNavController().navigate(R.id.to_filterSettingsDialogFragment)
                }

                BitcoinValueStates.ShowLoading -> {
                    binding.loadingBar.isVisible = true
                }

                BitcoinValueStates.HideLoading -> {
                    binding.loadingBar.isVisible = false
                }

                BitcoinValueStates.ShowFAB -> {
                    binding.fab.show()
                }

                BitcoinValueStates.HideFAB -> {
                    binding.fab.hide()
                }

                else -> {
                    if (BuildConfig.DEBUG) {
                        throw IllegalStateException(
                            "Unknown BitcoinValueStates instance: ${it::class.java.simpleName}"
                        )
                    }
                }
            }
        }
    }

    private fun sendEvent(event: BitcoinValueEvents) = lifecycleScope.launch {
        viewModel.eventsChannel.send(event)
    }

    private fun showErrorDialog(detailsMessage: String) {
        MaterialAlertDialogBuilder(context ?: return)
            .setTitle(R.string.title_unexpected_error)
            .setMessage(getString(R.string.message_unexpected_error, detailsMessage))
            .setPositiveButton(R.string.action_ok, null)
            .show()
    }
}
