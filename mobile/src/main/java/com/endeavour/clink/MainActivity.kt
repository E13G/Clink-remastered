package com.endeavour.clink

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.endeavour.clink.model.Gate
import com.endeavour.clink.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity() {

    private var isEmpty = true

    private lateinit var headerView: View
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
                .get(MainViewModel::class.java)

        viewModel.gate.observe(this, Observer { gates ->

            isEmpty = gates.isEmpty()

            if (isEmpty) navigateToForm("") else{
                findNavController(R.id.nav_host_fragment).popBackStack(R.id.mainFragment, false)
                setupSpinner(gates)
            }
        })

        headerView = nav_view.getHeaderView(0)

        setUpDrawerNavigation()

    }

    private fun setupSpinner(gates: List<Gate>) {

        val spinner: Spinner = headerView.spinner
        ArrayAdapter(
                this,
                R.layout.simple_spinner_item,
                gates
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                viewModel.setCode(gates[position].code)
            }
        }
    }

    private fun setUpDrawerNavigation() {

        headerView.nav_new.setOnClickListener {

            navigateToForm("")
            drawer_layout.closeDrawers()
        }

        headerView.nav_edit.setOnClickListener {

            navigateToForm(viewModel.getCode())
            drawer_layout.closeDrawers()
        }
    }

    private fun navigateToForm(code : String){
        val bundle = Bundle()
        bundle.putString("edit", code)
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.formFragment, bundle)
    }

    override fun onSupportNavigateUp()
            = findNavController(R.id.nav_host_fragment).navigateUp()

    override fun onBackPressed() {

        if (isEmpty) finish() else super.onBackPressed()
    }
}
