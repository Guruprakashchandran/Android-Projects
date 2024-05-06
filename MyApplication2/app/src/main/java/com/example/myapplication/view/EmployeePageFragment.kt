package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentModifyEmployeeDetailBinding

import com.example.myapplication.model.Employee
import com.example.myapplication.util.StringValidationUtil
import com.example.myapplication.viewmodel.EmployeeDetailViewModel
import com.example.myapplication2.EmployeeEntity
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class EmployeePageFragment : Fragment() {

    private val viewModel: EmployeeDetailViewModel by viewModels()
    private lateinit var context: Context
    private lateinit var employeeEntity: Employee
    private lateinit var employee: EmployeeEntity
    private lateinit var binding: FragmentModifyEmployeeDetailBinding
    private lateinit var view: View
    private var dob: String? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        if (container != null) {
            context = container.context
        }
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_modify_employee_detail,
            container,
            false
        )
        view = binding.root
        employeeEntity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("employeeData", Employee::class.java)
        } else {
            arguments?.getParcelable("employeeData")
        }!!

        departmentDropDownMenuSetup()
        designationDropDownMenuSetup()
        genderDropDownSetup()

        binding.dateOfBirth.setOnClickListener {

            openDatePickerDialog()
        }
        binding.dateOfBirthDatePicker.setOnClickListener {

            openDatePickerDialog()
        }
        if (employeeEntity.name?.isNotEmpty() == true) {

            updateContentInEmployeePage(employeeEntity)
        }
        binding.addDetailButton.setOnClickListener {
            employee = getAndCheckEmployeeDetails(view)
            if (employee.id > 0L) {
                Toast.makeText(context, "Update Data Successfully!!!", Toast.LENGTH_SHORT).show()
                viewModel.updateEmployeeDetail(employee)
                findNavController().navigateUp()
            } else if (employee.name.isNotEmpty() && employeeEntity.name?.isEmpty() == true) {
                Toast.makeText(context, "Add Data Successfully!!!", Toast.LENGTH_SHORT).show()
                viewModel.addEmployeeDetail(employee)
                findNavController().navigateUp()
            }

        }
        binding.backButtonEmployeePage.setOnClickListener {
            findNavController().navigateUp()
        }
        return view
    }

    private fun genderDropDownSetup() {

        val genderList = resources.getStringArray(R.array.gender)
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_textview, genderList)
        binding.genderCategory.setAdapter(genderAdapter)
    }

    @SuppressLint("SimpleDateFormat")
    private fun openDatePickerDialog() {

        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(parentFragmentManager, "DatePicker")

        datePicker.addOnPositiveButtonClickListener {

            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            dob = dateFormat.format(Date(it))
            binding.dateOfBirth.text = dob
        }
    }


    private fun designationDropDownMenuSetup() {
        val designationList = resources.getStringArray(R.array.designations)
        val designationAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_textview, designationList)
        binding.designationCategory.setAdapter(designationAdapter)
    }

    private fun departmentDropDownMenuSetup() {

        val departmentArray = resources.getStringArray(R.array.departments)
        val departmentAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_textview, departmentArray)
        binding.departmentCategory.setAdapter(departmentAdapter)
    }

    private fun updateContentInEmployeePage(employeeEntity: Employee?) {

        binding.apply {
            empId.editText?.setText(employeeEntity?.employeeId)
            name.editText?.setText(employeeEntity?.name)
            address.editText?.setText(employeeEntity?.address)
            emailId.editText?.setText(employeeEntity?.emailId)
            mobileNo.editText?.setText(employeeEntity?.mobileNo.toString())
            experience.editText?.setText(employeeEntity?.yearOfExperience.toString())
            salary.editText?.setText(employeeEntity?.salary.toString())
            dateOfBirth.text = employeeEntity?.dateOfBirth.toString()
            departmentCategory.setText(employeeEntity?.department,false)
            designationCategory.setText(employeeEntity?.designation,false)
            genderCategory.setText(employeeEntity?.gender,false)
            addDetailButton.text = resources.getString(R.string.updateContent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAndCheckEmployeeDetails(view: View): EmployeeEntity {

        val employeeData = EmployeeEntity(0, "", "", "", 0, 0, 0, "", "", "", "","")
        try {

            val empId = binding.empId.editText?.text.toString()
            if (!StringValidationUtil.checkEmpId { empId }) {

                Toast.makeText(context, "Please!!! Enter Employee id !!!", Toast.LENGTH_SHORT)
                    .show()
                return employeeData
            }
            val name = binding.name.editText?.text.toString()
            if (!StringValidationUtil.checkEmployeeName { name }) {
                Toast.makeText(context, "Wrong Name entered!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }

            val address = binding.address.editText?.text.toString()
            if (!StringValidationUtil.checkEmployeeAddress { address }) {
                Toast.makeText(context, "Wrong Address entered!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }

            val emailId = binding.emailId.editText?.text.toString()
            if (!StringValidationUtil.checkEmployeeMailId { emailId }) {
                Toast.makeText(context, "Wrong MailId entered!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }

            val mobileNo = binding.mobileNo.editText?.text.toString()
            if (mobileNo.isEmpty() || !StringValidationUtil.checkEmployeeMobileNo {
                    mobileNo.toLong()
                }) {
                Toast.makeText(context, "Wrong MobileNo entered!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }

            val experience = binding.experience.editText?.text.toString()
            if (experience.isEmpty() || !StringValidationUtil.checkEmployeeExperience {
                    experience.toLong()
                }) {
                Toast.makeText(context, "Wrong Experience entered!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }

            val gender = binding.gender.editText?.text
            if (gender == null) {
                Toast.makeText(context, "Please!! Select Gender!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }

            val salary = binding.salary.editText?.text.toString()
            if (salary.isEmpty() || !StringValidationUtil.checkEmployeeSalary { salary.toLong() }) {
                Toast.makeText(context, "Wrong Salary entered!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }
            val department = binding.department.editText?.text
            if (department == null) {
                Toast.makeText(context, "Please!! Select Department!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }

            val designation = binding.designation.editText?.text
            if (designation == null) {
                Toast.makeText(context, "Please!! Select Designation!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }
            dob = binding.dateOfBirth.text.toString()
            if (!StringValidationUtil.checkDob { dob }) {
                Toast.makeText(context, "Select Dob!!!", Toast.LENGTH_SHORT).show()
                return employeeData
            }
            var id = 0L
            if (employeeEntity.id != 0L) {
                id = employeeEntity.id
            }

            return EmployeeEntity(
                id,
                name,
                address,
                emailId,
                mobileNo.toLong(),
                experience.toLong(),
                salary.toLong(),
                department.toString(),
                designation.toString(),
                empId,
                dob!!,
                gender.toString()
            )
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }
        return employeeData
    }


}