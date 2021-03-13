package com.carlosmesquita.technicaltest.n26.bitlue.ui.settings.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.carlosmesquita.technicaltest.n26.bitlue.databinding.FragmentDialogFilterSettingsBinding
import com.carlosmesquita.technicaltest.n26.bitlue.ui.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterSettingsDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private val binding: FragmentDialogFilterSettingsBinding
        get() = _binding!!
    private var _binding: FragmentDialogFilterSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogFilterSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
