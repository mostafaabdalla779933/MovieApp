package com.example.movieapp.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.movieapp.R
import com.example.movieapp.databinding.SnackBarBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel>(
    private val inflate: Inflate<V>
) : Fragment() {



    private var snack: Snackbar? =null

    open lateinit var viewModel: VM

    private var _binding: V? = null
    val binding get() = _binding!!

    open lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(inflater, container, false)
        initViewModel()

        if (context != null) progressDialog = ProgressDialog(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.internalState.collect {
                when (it) {
                    is ErrorState -> {
                        showErrorMsg(it.message ?: "Something went wrong")
                    }
                    is Loader.Progress -> {
                        if (it.show) showLoading() else hideLoading()
                    }
                    else -> {
                        renderView(it)
                    }
                }
            }
        }
        onFragmentCreated()
    }

    abstract fun initViewModel()

    abstract fun onFragmentCreated()

    abstract fun renderView(viewState: BaseViewState?)

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    override fun onDestroyView() {
        hideLoading()
        snack?.dismiss()
        _binding = null
        super.onDestroyView()
    }

    fun returnError(error: String): Boolean {
        showErrorMsg(error)
        return false
    }

    fun showSuccessMsg(msg: String) {
        if (context != null)
            showCustomSnackBar(msg)
    }

    fun showErrorMsg(msg: String) {
        if (context != null)
            showCustomSnackBar(msg)
    }

    open fun showMessage(message: String){
        requireContext().showMessage(message)
    }

    private fun showCustomSnackBar(msg :String){
        snack = Snackbar.make(requireView(), "", Snackbar.LENGTH_LONG)
        SnackBarBinding.bind(View.inflate(requireContext(), R.layout.snack_bar, null)).let { snackBarBinding ->
            (snack?.view as ViewGroup).removeAllViews()
            (snack?.view as ViewGroup).addView(snackBarBinding.root)

            snack?.view?.setPadding(20, 10, 20, 30)
            snack?.view?.elevation = 0f
            snack?.setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.transparent
                )
            )
            snackBarBinding.text.text = msg
            snack?.show()
        }
    }
}