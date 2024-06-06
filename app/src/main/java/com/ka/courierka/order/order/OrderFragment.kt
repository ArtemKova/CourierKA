package com.ka.courierka.order.order

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ka.courierka.R
import com.ka.courierka.courier.UserFragment
import com.ka.courierka.helper.isCorrectDestinationNow

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"
private const val ARG_PARAM6 = "param6"
private const val ARG_PARAM7 = "param7"
private const val ARG_PARAM8 = "param8"
private const val ARG_PARAM9 = "param9"
private const val ARG_PARAM10 = "param10"
private const val ARG_PARAM11 = "param11"
private const val ARG_PARAM12 = "param12"
private const val ARG_PARAM13 = "param13"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null
    private var param6: String? = null
    private var param7: String? = null
    private var param8: String? = null
    private var param9: String? = null
    private var param10: String? = null
    private var param11: String? = null
    private var param12: Boolean = false
    private var param13: String? = null
    private var requiredDestinationId = 0
    private lateinit var textNameCustomer: TextView
    private lateinit var textOrderReceiptAddress: TextView
    private lateinit var textAddress: TextView
    private lateinit var textPhone: TextView
    private lateinit var textTime: TextView
    private lateinit var textPay: TextView
    private lateinit var textCity: TextView
    private lateinit var textTypeOrder: TextView
    private lateinit var buttonGetDelivery: Button
    private lateinit var viewModel: OrderViewModel
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)       //id
            param2 = it.getString(ARG_PARAM2)       //name
            param3 = it.getString(ARG_PARAM3)       //phone
            param4 = it.getString(ARG_PARAM4)       //adress
            param5 = it.getString(ARG_PARAM5)       //recadress
            param6 = it.getString(ARG_PARAM6)       //customer_id
            param7 = it.getString(ARG_PARAM7)       //time
            param8 = it.getString(ARG_PARAM8)       //isPay
            param9 = it.getString(ARG_PARAM9)       //courier
            param10 = it.getString(ARG_PARAM10)     //city
            param11 = it.getString(ARG_PARAM11)     //typeOrder
            param12 = it.getBoolean(ARG_PARAM12)    //delivered
            param13 = it.getString(ARG_PARAM13)     //courier_Id
        }
        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        id = param1.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requiredDestinationId = R.id.orderFragment
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this)[NewOrderViewModel::class.java]
        initViews(view)
    }

    private fun initViews(view: View) {
        textNameCustomer = view.findViewById(R.id.textNameCustomer)
        textOrderReceiptAddress = view.findViewById(R.id.textOrderReceiptAddress)
        textAddress = view.findViewById(R.id.textAddress)
        textPhone = view.findViewById(R.id.textPhone)
        textTime = view.findViewById(R.id.textTime)
        textPay = view.findViewById(R.id.textPay)
        textCity = view.findViewById(R.id.textCity)
        textTypeOrder = view.findViewById(R.id.textTypeOrder)
        buttonGetDelivery = view.findViewById(R.id.buttonGetDelivery)
        textPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher("+7"))

        textNameCustomer.text = param2
        textOrderReceiptAddress.text = param5
        textAddress.text = param4
        textPhone.text = param3
        textTime.text = param7
        textPay.text = param8
        textCity.text = param10
        textTypeOrder.text = param11

        if (param9 != "") {
            buttonGetDelivery.isEnabled = false
            buttonGetDelivery.text = getString(R.string.taken_order)
        } else {
            buttonGetDelivery.isEnabled = true
            buttonGetDelivery.text = getString(R.string.get_order)
        }


        buttonGetDelivery.setOnClickListener {
            param13?.let { it1 ->
                viewModel.setGetOrder(id, it1)
                goToUser(it1)
            }


        }
    }

    private fun goToUser(id: String) {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = UserFragment.newInstance(id, "")
            findNavController().navigate(
                R.id.action_orderFragment_to_userFragment,
                args.arguments
            )
        }

    }

    fun observeViewModel() {
        viewModel.getOrders(id).observe(viewLifecycleOwner) {

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(
            param1: String,
            param2: String,
            param3: String,
            param4: String,
            param5: String,
            param6: String,
            param7: String,
            param8: String,
            param9: String,
            param10: String,
            param11: String,
            param12: Boolean,
            param13: String
        ) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAM4, param4)
                    putString(ARG_PARAM5, param5)
                    putString(ARG_PARAM6, param6)
                    putString(ARG_PARAM7, param7)
                    putString(ARG_PARAM8, param8)
                    putString(ARG_PARAM9, param9)
                    putString(ARG_PARAM10, param10)
                    putString(ARG_PARAM11, param11)
                    putBoolean(ARG_PARAM12, param12)
                    putString(ARG_PARAM13, param13)
                }
            }
    }
}