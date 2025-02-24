package com.ioffeivan.videoplayer.core.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.ioffeivan.videoplayer.R
import com.ioffeivan.videoplayer.core.utils.network.NetworkState
import com.ioffeivan.videoplayer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    private var snackbar: Snackbar? = null

    private var orientation: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orientation = resources.configuration.orientation

        applyOrientationSettings()

        createSnackbar()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.networkState.collect {
                    when (it) {
                        NetworkState.AVAILABLE -> snackbar?.dismiss()

                        NetworkState.LOST, NetworkState.UNAVAILABLE -> snackbar?.show()

                        NetworkState.INITIAL -> {}
                    }
                }
            }
        }
    }

    private fun applyOrientationSettings() {
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                setFullscreen()
                hideSystemUi()
            }
        }
    }

    private fun setFullscreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun hideSystemUi() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    private fun createSnackbar() {
        snackbar = Snackbar.make(
            binding.root,
            getString(R.string.no_network_message),
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            setAction("Ясно") {
                dismiss()
            }
        }
    }
}