package com.endeavour.clink.ui.form

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.endeavour.clink.Injection
import com.endeavour.clink.R
import com.endeavour.clink.model.Gate
import com.endeavour.clink.ui.main.MainViewModel
import kotlinx.android.synthetic.main.form_fragment.*

class FormFragment : Fragment() {


    private lateinit var viewModel: MainViewModel

    private var first = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(context!!))
                .get(MainViewModel::class.java)


        val code = arguments?.let {
            val safeArgs = FormFragmentArgs.fromBundle(it)
            safeArgs.edit
        } ?: ""

        if(code.isEmpty()){
            setupCreateClick()
        }else{
            setupEdit(code)

        }
    }

    private fun setupEdit(code : String) {
        viewModel.getGateByCode(code).observe(this, Observer { gate->

            if (first) {
                first = false

                gate_name_input.text = Editable.Factory.getInstance().newEditable(gate.name)
                gate_code_input.text = Editable.Factory.getInstance().newEditable(gate.code)

                create_gate_btn.text = getString(R.string.edit_gate)
                create_gate_btn.setOnClickListener { viewModel.editGate(Gate(gate.id, gate_name_input.text.toString(), gate_code_input.text.toString())) }

                setupDelete(gate)
            }

        })
    }

    private fun setupDelete(gate : Gate) {

        delete_gate_btn.visibility = View.VISIBLE
        delete_gate_btn.setOnClickListener {

            viewModel.deleteGate(gate)
        }
    }

    private fun setupCreateClick() {
        create_gate_btn.setOnClickListener { _ ->

            if(validFields()) viewModel.saveGate(Gate(0, gate_name_input.text.toString(), gate_code_input.text.toString()))
        }
    }

    private fun validFields() : Boolean {
        return (gate_name_input.text.toString().isNotEmpty() && gate_code_input.text.toString().isNotEmpty())
    }
}
