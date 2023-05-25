package com.carefer.core.base.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import com.carefer.core.base.dialog.BaseNetworkingDialog
import com.carefer.core.utils.network.ConnectivityProvider
import com.carefer.core.utils.network.NetworkUtils
import javax.inject.Inject

open class NetworkBaseFragment : Fragment(), ConnectivityProvider.ConnectivityStateListener {

    private val TAG = NetworkBaseFragment::class.java.simpleName

    @Inject
    lateinit var baseNetworkingDialog: BaseNetworkingDialog

    companion object {
        var isNetworkConnected = true
    }

    private val provider: ConnectivityProvider by lazy {
        ConnectivityProvider.createProvider(
            requireContext()
        )
    }


    override fun onResume() {
        super.onResume()
        provider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        if (baseNetworkingDialog.isShown) baseNetworkingDialog.dismiss()
        provider.removeListener(this)
    }

    override fun onNetworkStateChange(state: ConnectivityProvider.NetworkState) {
        val hasInternet = NetworkUtils.isNetworkConnected(state)
        if (!hasInternet) {
            baseNetworkingDialog.showDialog(
                requireContext(),
                com.carefer.core.domain.entities.base.ErrorStatus.NO_CONNECTION
            )
            isNetworkConnected = false
        } else {
            if (baseNetworkingDialog.isShown) baseNetworkingDialog.dismiss()
            isNetworkConnected = true
        }
        Log.d(TAG, "Is Network connected: $hasInternet")
    }
}