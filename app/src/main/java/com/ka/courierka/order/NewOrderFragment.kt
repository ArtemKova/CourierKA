package com.ka.courierka.order

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ka.courierka.R
import com.ka.courierka.courier.UserFragment
import com.ka.courierka.helper.isCorrectDestinationNow
import com.ka.courierka.register.RegistrationViewModel
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewOrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var idCustomer=""
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
    private lateinit var viewModel: NewOrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            idCustomer = param1.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_order, container, false)
        requiredDestinationId = R.id.newOrderFragment
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewOrderViewModel::class.java]
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
        buttonDelivery = view.findViewById(R.id.buttonDelivery)

        editTextPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher("+7"));


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buttonDelivery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
               var newOrder = param1?.let {
                   Order(
                       "",
                       editNameCustomer.text.toString().trim(),
                       editTextPhone.text.toString().trim(),
                       editOrderReceiptAddress.text.toString().trim(),
                       editAddress.text.toString().trim(),
                       it,
                       editTime.text.toString().trim(),
                       editPay.text.toString().trim(),
                       "",
                       editCity.text.toString().trim(),
                       editTypeOrder.text.toString().trim(),
                       false
                   )
               }
                if (newOrder != null) {
                    viewModel.createOrder(newOrder)
                }
                param1?.let { goToUser(it) }
            }

        })
    }

    private fun goToUser(id:String) {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = UserFragment.newInstance(id,"")
            findNavController().navigate(R.id.action_newOrderFragment_to_userFragment,args.arguments)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewOrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewOrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}