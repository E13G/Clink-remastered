package com.endeavour.clink.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.endeavour.clink.Injection
import com.endeavour.clink.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var first = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.main_fragment, container, false)

        view.activation_button.setOnClickListener {


            activation_button.isEnabled = false

            requestGateOpen()
            loadingRotation()

        }

        return view
    }

    private fun requestGateOpen(){


        val queue = Volley.newRequestQueue(context)
        val url = "https://api.thingspeak.com/update?api_key=" + viewModel.getCode() +
                "&field1=1&field2=1"

        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { _ ->

                    openGate()

                },
                Response.ErrorListener {
                    requestGateFinished()
                    Toast.makeText(context,getString(R.string.response_error),Toast.LENGTH_LONG).show()
                })

        queue.add(stringRequest)
    }

    private  fun loadingRotation(){

        val rotation = if(first) 2500F else 0F
        first = !first
        outer_button.animate().rotation(rotation)
                .setInterpolator(LinearInterpolator()).duration = 5000
    }

    private fun openGate(){

        activation_button.setImageState(intArrayOf(-R.attr.state_key, R.attr.state_opened, R.attr.state_closed),true)

        closeGate()
    }

    private fun closeGate() {

        Handler().postDelayed({

            activation_button.setImageState(intArrayOf(-R.attr.state_key, -R.attr.state_opened, -R.attr.state_closed),true)
            requestGateFinished()

        },5000)
    }

    private fun requestGateFinished(){

        activation_button.isEnabled = true
        outer_button.animate().cancel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(context!!))
                .get(MainViewModel::class.java)

    }



}
