package com.molitics.molitician.ui.dashboard.register.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentRegisterBinding
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.register.location.LocationFragment
import com.molitics.molitician.ui.dashboard.register.register.viewModel.RegisterViewModel
import com.molitics.molitician.util.Constant
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*
import javax.inject.Inject

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(),
    RegisterNavigation {

    private var handler = Handler(Looper.getMainLooper() /*UI thread*/)
    private lateinit var workRunnable: Runnable
    private val myCalendar = Calendar.getInstance()

    // Initializing other items
    // from layout file


    private val datePicker: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            registerViewModel.dateOfBirth.set("$year-$monthOfYear-$dayOfMonth")
        }

    @Inject
    lateinit var factory: MyViewModelFactory<RegisterViewModel>

    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[RegisterViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.registerViewModel

    override fun getLayoutId(): Int = R.layout.fragment_register

    override fun getViewModel(): RegisterViewModel = registerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel.navigator = this

        genderRadioListener()
        setDatePicker()
        handleUi()
        setRadioCheck()
        ivBackCreateAccount.setOnClickListener {
            val fm = parentFragmentManager;
            if (fm.backStackEntryCount > 0) {
                fm.popBackStack()
            }
        }
    }

    private fun handleUi() {
        uniqNameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(search: CharSequence?, start: Int, before: Int, count: Int) {
                if (search?.length!! > 3) {
                    if (::workRunnable.isInitialized) {
                        handler.removeCallbacks(workRunnable)
                    }
                    workRunnable = Runnable {
                        registerViewModel.checkUserName(search.toString())
                    }
                    handler.postDelayed(workRunnable, 1000 /*delay*/)
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun genderRadioListener() {
        gender_radio_group.setOnCheckedChangeListener { group, checkedId ->
            val radioButtonID = group.checkedRadioButtonId
            val radioButton = group.findViewById<View>(radioButtonID)
            val idx = group.indexOfChild(radioButton)
            registerViewModel.gender = idx.plus(1)
        }
    }

    private fun setDatePicker() {
        dobET.setOnClickListener {

            val dialog = DatePickerDialog(
                requireContext(), datePicker, myCalendar
                    .get(Calendar.YEAR) - 10, myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = getCalculatedDate(-10)

            dialog.show()
        }
    }

    private  fun setRadioCheck(){
        val genderButton = gender_radio_group.getChildAt(registerViewModel.gender - 1) as RadioButton?
        if (genderButton != null) genderButton.isChecked = true
    }
    fun getCalculatedDate(year: Int): Long {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, year)
        return cal.timeInMillis
    }

    companion object {
        fun getInstance(): RegisterFragment {
            val instance = RegisterFragment()
            val mBundle = Bundle()
            instance.arguments = mBundle
            return instance
        }
    }

    override fun navigateToStateLocation() {
        (activity as ActivityFragment).replaceFragment(
            LocationFragment.getInstance(Constant.From.STATE_LOCATION),
            null,
            true,
            false
        )
    }
}