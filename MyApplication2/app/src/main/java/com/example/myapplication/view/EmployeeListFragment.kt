package com.example.myapplication.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.SwipeGestures
import com.example.myapplication.adapter.EmpDetailsAdapter
import com.example.myapplication.databinding.BottomsheetBinding
import com.example.myapplication.databinding.FragmentEmployeeDetailListBinding
import com.example.myapplication.listener.OnClickListener
import com.example.myapplication.model.Employee
import com.example.myapplication.viewmodel.EmployeeDetailViewModel
import com.example.myapplication2.EmployeeEntity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.Locale

@AndroidEntryPoint
class EmployeeListFragment : Fragment() {

    private val employeeDetailViewModel: EmployeeDetailViewModel by viewModels()
    private lateinit var context: Context
    private lateinit var adapter: EmpDetailsAdapter
    private var container: ViewGroup? = null
    private lateinit var listOfEmployees: List<EmployeeEntity>
    private lateinit var view: View
    var selectedItem: String? = null
    private lateinit var bottomSheetBinding: BottomsheetBinding
    private lateinit var view1: View
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var inflater: LayoutInflater
    private lateinit var binding: FragmentEmployeeDetailListBinding
    private lateinit var filteredList: List<EmployeeEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.inflater = inflater
        this.container = container

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_employee_detail_list,
            container,
            false
        )
        view = binding.root
        if (container == null) {
            return view
        }
        context = container.context

        addEmployeeDetails()
        filterImageSetUp()
        adapterSetup()
        searchEmployeeDetails()
        addSwipeActionToList()

        lifecycleScope.launch {
            employeeDetailViewModel.listOfEmployee.collect {
                listOfEmployees = it
                when (selectedItem) {
                    null -> adapter.updateData(listOfEmployees, emptyList(), emptyList())
                    else -> filterByDepartment()
                }
            }
        }
        return view
    }

    private fun addSwipeActionToList() {

        try {
            val swipeGestures = object : SwipeGestures(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    when (direction) {
                        ItemTouchHelper.LEFT -> {
                            deleteEmployeeDetail(viewHolder)
                        }

                        ItemTouchHelper.RIGHT ->
                            editEmployeeDetailPage(viewHolder)
                    }

                }

            }
            val touchHelper = ItemTouchHelper(swipeGestures)
            touchHelper.attachToRecyclerView(binding.recyclerView)
        } catch (e: Exception) {
            println(e.toString())
        }
    }

    private fun editEmployeeDetailPage(viewHolder: RecyclerView.ViewHolder) {

        val position = viewHolder.adapterPosition
        val item = adapter.getData()[position]

        val action = EmployeeListFragmentDirections.actionEmployeeListToModifyEmployeeDetail(
            setEmployeeContent(item)
        )
        findNavController().navigate(action)
    }


    private fun deleteEmployeeDetail(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        val item = adapter.getData()[position]
        var isUntoNotClicked = true;
        adapter.removeItem(position)
        val snackBar: Snackbar =
            Snackbar.make(binding.root, "Data was removed!!!", Snackbar.LENGTH_SHORT)
        snackBar.setAction("UNDO") {
            adapter.restore(position, item)
            isUntoNotClicked = false
        }
        snackBar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {

                if (isUntoNotClicked) {
                    deleteEmployeeData(item)
                }
            }
        })
        snackBar.show()

    }

    private fun searchEmployeeDetails() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val listOfStartingPoint = ArrayList<Int>()
                val listOfEndPoint = ArrayList<Int>()
                when (selectedItem) {
                    null ->{
                        if(s?.length != 0){

                            val listOfEmployeeDetails = listOfEmployees.filter {
                                val output = it.name.getSubString(s.toString(),true)
                                if(output.isEmpty()){
                                    false
                                }
                                else{
                                    listOfStartingPoint.add(output[0])
                                    listOfEndPoint.add(output[1])
                                    true
                                }
//                            !emptyArray<Int>().contentEquals(it.name.getSubString(s.toString(),false))
                            }
                            adapter.updateData(listOfEmployeeDetails,listOfStartingPoint,listOfEndPoint)
                        }
                        else{
                            adapter.updateData(listOfEmployees, emptyList(), emptyList())
                        }
                    }

                    else -> adapter.updateData(
                        filteredList.filter { it.name.contains(s.toString()) },
                        listOfStartingPoint,
                        listOfEndPoint
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun addEmployeeDetails() {

        binding.addFloatingButton.setOnClickListener {
            val employeeEntity =
                Employee(0, "", "", "", 0, 0, 0, "", "", "", "", "")
            val action =
                EmployeeListFragmentDirections.actionEmployeeListToModifyEmployeeDetail(
                    employeeEntity
                )
            findNavController().navigate(action)
        }
    }

    private fun adapterSetup() {


        employeeDetailViewModel.getAllEmployeeDetails()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = EmpDetailsAdapter(requireContext(), emptyList(), employeeDetailViewModel)
        binding.recyclerView.adapter = adapter

        adapter.setOnClickListener(
            object : OnClickListener {
                override fun onClick(
                    employeeEntity: EmployeeEntity,
                    transitionName: String,
                    squareProfileImage: ImageView,
                    transitionTextName: String,
                    empDetailName: TextView
                ) {
                    val employee: Employee = setEmployeeContent(employeeEntity)
                    val action =
                        EmployeeListFragmentDirections.actionEmployeeDetailListToEmployeeProfile(
                            employee,transitionName, transitionTextName
                        )
                    findNavController().navigate(action, FragmentNavigatorExtras(squareProfileImage to transitionName, empDetailName to transitionTextName))
                }

                override fun onDelete(employeeEntity: EmployeeEntity) {

                    deleteEmployeeData(employeeEntity)
                }

                override fun selectSalary(value: Long) {

                }

            }
        )
    }

    private fun getImageId(id: Long): Int {

        when (id % 5) {
            1L -> {
                return (R.drawable.people2)
            }

            2L -> {
                return (R.drawable.people7)
            }

            3L -> {
                return (R.drawable.people4)
            }

            4L -> {
                return (R.drawable.people8)
            }

            else -> {
                return (R.drawable.people6)
            }
        }
    }

    private fun setEmployeeContent(employeeEntity: EmployeeEntity): Employee {

        return Employee(
            employeeEntity.id,
            employeeEntity.name,
            employeeEntity.address,
            employeeEntity.mail_id,
            employeeEntity.mobile_no,
            employeeEntity.year_of_experience,
            employeeEntity.salary,
            employeeEntity.department,
            employeeEntity.designation,
            employeeEntity.employee_id,
            employeeEntity.date_of_birth,
            employeeEntity.gender
        )
    }

    private fun filterImageSetUp() {

        if (!::bottomSheetBinding.isInitialized) {

            bottomSheetBinding = BottomsheetBinding.inflate(inflater, container, false)
            view1 = bottomSheetBinding.root

            bottomSheetBinding.all.setOnClickListener {
                bottomSheetDialog.dismiss()
                selectedItem = null
                binding.filterItem = selectedItem
                adapter.updateData(listOfEmployees, emptyList<Int>(), emptyList<Int>())
            }
            bottomSheetBinding.trainee.setOnClickListener {
                bottomSheetDialog.dismiss()
                selectedItem = "Trainee"
                filterByDepartment()
            }
            bottomSheetBinding.employee.setOnClickListener {
                bottomSheetDialog.dismiss()
                selectedItem = "Employee"
                filterByDepartment()

            }
            bottomSheetBinding.teamLead.setOnClickListener {
                bottomSheetDialog.dismiss()
                selectedItem = "Team Leader"
                filterByDepartment()
            }
            bottomSheetBinding.manager.setOnClickListener {
                bottomSheetDialog.dismiss()
                selectedItem = "Manager"
                filterByDepartment()
            }
            bottomSheetBinding.admin.setOnClickListener {
                bottomSheetDialog.dismiss()
                selectedItem = "Admin"
                filterByDepartment()
            }
            bottomSheetBinding.superAdmin.setOnClickListener {
                bottomSheetDialog.dismiss()
                selectedItem = "Super Admin"
                filterByDepartment()
            }
            bottomSheetDialog = BottomSheetDialog(context)

        }
        binding.filterfloatingActionButton.setOnClickListener {

            val parent = view1.parent as? ViewGroup
            parent?.removeView(view1)
            bottomSheetBinding.check = selectedItem
            bottomSheetDialog.setCancelable(true)
            bottomSheetDialog.setContentView(view1)
            bottomSheetDialog.show()
        }
    }

    private fun filterByDepartment() {

        binding.filterItem = selectedItem
        filteredList = listOfEmployees.filter {
            it.designation == selectedItem
        }
        adapter.updateData(filteredList, emptyList(), emptyList())
    }

    private fun deleteEmployeeData(employeeEntity: EmployeeEntity) {

        lifecycleScope.launch {
            employeeDetailViewModel.deleteEmployeeDetails(employeeEntity)
        }
    }

    fun String.getSubString(newString: String, ignoreCase: Boolean): Array<Int> {

        var input = this
        var searchString = newString
        if (newString.isEmpty()) {
            return emptyArray()
        }
        if(ignoreCase){
            searchString = newString.lowercase()
            Log.i("lower",searchString)
            input = input.lowercase()
        }
        for (i in 0..input.length - searchString.length) {

            if (input.substring(i, i + searchString.length) == (searchString)) {

                return arrayOf(i, i + searchString.length)
            }
        }
        return emptyArray()
    }
}

//    private fun dropDownMenuSetup() {
//
//        dropDownMenu = view.findViewById(R.id.dropdown_contents)
//        val salaryCategory = resources.getStringArray(R.array.listItems)
//        val arrayAdapter =
//            ArrayAdapter(requireContext(), R.layout.drop_down_textview, salaryCategory)
//        dropDownMenu.setAdapter(arrayAdapter)
//
//        dropDownMenu.setOnItemClickListener { parent, _, position, _ ->
//            selectedItem = parent.getItemAtPosition(position).toString()
//            filterBySalary()
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//
//        val salaryCategory = resources.getStringArray(R.array.listItems)
//        val arrayAdapter =
//            ArrayAdapter(requireContext(), R.layout.drop_down_textview, salaryCategory)
//        dropDownMenu.setAdapter(arrayAdapter)
//
//        dropDownMenu.setOnItemClickListener { parent, _, position, _ ->
//            selectedItem = parent.getItemAtPosition(position).toString()
//
//            filterBySalary()
//        }
//    }

//ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val deleteDetail : EmployeeEntity = listOfEmployees[viewHolder.adapterPosition]
//                val pos = viewHolder.adapterPosition
//                val data = listOfEmployees.toMutableList()[pos]
//                listOfEmployees.toMutableList().removeAt(pos)
//                deleteEmployeeData(data)
//                println("employee $listOfEmployees")
//
//                println("employee $listOfEmployees")
//                adapter.notifyItemRemoved(listOfEmployees.size)
//            }
//
//        }).attachToRecyclerView(recyclerView)