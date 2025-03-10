package com.rooted.checker.ui.fragments

import android.graphics.Color
import android.view.View
import com.rooted.checker.R
import com.rooted.checker.app_base.AppBaseFragment
import com.rooted.checker.app_utils.AppUtil
import com.rooted.checker.databinding.FragmentRootCheckerBinding
import com.scottyab.rootbeer.RootBeer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RootCheckerFragment : AppBaseFragment() {

    lateinit var binding: FragmentRootCheckerBinding

    override fun layoutRes(): View {
        binding = FragmentRootCheckerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initComponents() {
        checkForRoot()
        binding.deviceNameTv.text = AppUtil.DEVICE_NAME
        binding.androidVersionTv.text = AppUtil.ANDROID_VERSION

        binding.checkForRootedBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                binding.rootStatusTv.text = getString(R.string.checking)
                delay(5000)
                checkForRoot()
            }
        }
    }

    private fun checkForRoot() {
        try {
            if (RootBeer(requireContext()).isRooted) {
                binding.rootStatusTv.text = getString(R.string.device_is_rooted)
                binding.rootStatusTv.setTextColor(Color.RED)
            } else {
                binding.rootStatusTv.text = getString(R.string.device_is_not_rooted)
                binding.rootStatusTv.setTextColor(Color.GREEN)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}