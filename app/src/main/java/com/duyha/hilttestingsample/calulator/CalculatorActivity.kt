package com.duyha.hilttestingsample.calulator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.duyha.hilttestingsample.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_calculator.*
import javax.inject.Inject

@AndroidEntryPoint
class CalculatorActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: CalculatorViewModelFactory
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        viewModel = ViewModelProvider(this, viewModelFactory)[CalculatorViewModel::class.java]

        viewModel.sum.observe(this) {
            tvSum.text = it.toString()
        }
        viewModel.msg.observe(this) { event ->
            event.getContentIfNotHandledOrNull()?.let { msg ->
                showMessage(msg)
            }
        }

    }

    private fun showMessage(@StringRes msgId: Int) {
          Snackbar.make(rootView, msgId, Snackbar.LENGTH_LONG).show()
    }

    fun onSumClick(view: View) {
        hideKeyboard()
        viewModel.onSumClick(edtA.text.toString().trim(), edtB.text.toString().trim())
    }

    private fun hideKeyboard() {
        currentFocus?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}
