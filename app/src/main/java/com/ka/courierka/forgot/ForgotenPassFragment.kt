package com.ka.courierka.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ka.courierka.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgotenPassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgotenPassFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var editTextEmail: EditText
    private lateinit var buttonResetPassword: Button
    private lateinit var viewModel: ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgoten_pass, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]
        InitViews(view)
    }

    private fun InitViews(view: View) {
        editTextEmail = view.findViewById(R.id.editTextEmail)
        buttonResetPassword = view.findViewById(R.id.buttonResetPassword)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        editTextEmail.setText(param1)
        buttonResetPassword.setOnClickListener {
            var email = editTextEmail.text.toString().trim()
            viewModel.ResetPassword(email)
        }
    }
    fun observeViewModel() {
        viewModel.getError().observe(viewLifecycleOwner) {

            if (it != null) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.isSuccess().observe(viewLifecycleOwner){
            if (it){
                Toast.makeText(activity, getString(R.string.reset_link), Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForgotenPassFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForgotenPassFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}