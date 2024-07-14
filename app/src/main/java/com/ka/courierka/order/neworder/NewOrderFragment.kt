package com.ka.courierka.order.neworder


import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ka.courierka.R
import com.ka.courierka.dependencyinjection.repo.TypeViewModel
import com.ka.courierka.courier.UserFragment
import com.ka.courierka.helper.isCorrectDestinationNow
import com.ka.courierka.order.Order
import dagger.hilt.android.AndroidEntryPoint

import java.util.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class NewOrderFragment : Fragment() {
//    override val scope: Scope by fragmentScope()
    private val viewModel1: TypeViewModel by viewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2 = arrayListOf<String>()
    private var idCustomer = ""
    private var requiredDestinationId = 0
    private lateinit var editNameCustomer: EditText
    private lateinit var editOrderReceiptAddress: EditText
    private lateinit var editAddress: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTime: EditText
    private lateinit var editPay: EditText
    private lateinit var editCity: EditText
    private lateinit var editTypeOrder: EditText
    private lateinit var buttonDelivery: Button
    private lateinit var spinnerTypeOrder: Spinner
    private lateinit var viewModel: NewOrderViewModel
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getStringArrayList(ARG_PARAM2)!!
            idCustomer = param1.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            context = container.getContext()
        }
        val view = inflater.inflate(R.layout.fragment_new_order, container, false)
        requiredDestinationId = R.id.newOrderFragment
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewOrderViewModel::class.java]
        viewModel1.doNetworkCall()

        InitViews(view)
    }

    private fun InitViews(view: View) {
        editNameCustomer = view.findViewById(R.id.editNameCustomer)
        editOrderReceiptAddress = view.findViewById(R.id.editOrderReceiptAddress)
        editAddress = view.findViewById(R.id.editAddress)
        editTextPhone = view.findViewById(R.id.editTextPhone)
        editTime = view.findViewById(R.id.editTime)
        editPay = view.findViewById(R.id.editPay)
        editCity = view.findViewById(R.id.editCity)
        editTypeOrder = view.findViewById(R.id.editTypeOrder)
        editTypeOrder.visibility = View.GONE
        spinnerTypeOrder = view.findViewById(R.id.spinnerTypeOrder)
        buttonDelivery = view.findViewById(R.id.buttonDelivery)


        editTextPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher("+7"));
//        spinnerTypeOrder.setOnItemSelectedListener(this)

        val array_adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, param2)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTypeOrder.adapter = array_adapter
        spinnerTypeOrder.setSelection(1)
//        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
//            this,typeOrders,
//            android.R.layout.simple_spinner_item
//        )
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var selected = spinnerTypeOrder.selectedItem
        if (selected != null) {
            if( selected.equals("other")) {
                editTypeOrder.visibility = View.VISIBLE
            }
        }

        buttonDelivery.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var selected = spinnerTypeOrder.selectedItem.toString()
                if (selected.equals("other")) {
                    if (editTypeOrder.text.toString().trim().equals("")) {
                        selected = "OTHERS"
                    } else {
                        selected = editTypeOrder.text.toString().trim()
                    }
                }
                val id = viewModel.setOrderId()
                if (id != null) {
                var newOrder = param1?.let {

                        Order(
                            id,
                            editNameCustomer.text.toString().trim(),
                            editTextPhone.text.toString().trim(),
                            editOrderReceiptAddress.text.toString().trim(),
                            editAddress.text.toString().trim(),
                            it,
                            editTime.text.toString().trim(),
                            editPay.text.toString().trim(),
                            "",
                            editCity.text.toString().trim(),
                            selected,
                            false
                        )
                    }

                if (newOrder != null) {

                    viewModel.createOrder(newOrder)
//                    viewModel.setOrderId(newOrder)
                } }
                param1?.let { goToUser(it) }
            }

        })
    }

    private fun goToUser(id: String) {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = UserFragment.newInstance(id)
            findNavController().navigate(
                R.id.action_newOrderFragment_to_userFragment,
                args.arguments
            )
        }

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: ArrayList<String>) =
            NewOrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putStringArrayList(ARG_PARAM2, param2)
                }
            }
    }


}