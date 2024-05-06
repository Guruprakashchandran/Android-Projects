package com.example.myapplication.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.listener.OnClickListener
import com.example.myapplication2.EmployeeEntity
import com.example.myapplication.databinding.EmployeeBasicDetailBinding
import com.example.myapplication.viewmodel.EmployeeDetailViewModel


class EmpDetailsAdapter(
    private var context: Context,
    private var list: List<EmployeeEntity>,
    private var employeeDetailViewModel: EmployeeDetailViewModel
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var startingPoints: List<Int>
    private lateinit var endingPoints: List<Int>
    private lateinit var onClickListener: OnClickListener
    private lateinit var view: View
    private lateinit var parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        this.parent = parent
        val binding: EmployeeBasicDetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.employee_basic_detail,
            parent,
            false
        )
        view = binding.root
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as CustomViewHolder).bind(list[position],position)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    inner class CustomViewHolder(val binding: EmployeeBasicDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(employeeEntity: EmployeeEntity, position: Int) {

            binding.employee = employeeEntity
            if (startingPoints.isNotEmpty() && endingPoints.isNotEmpty()) {

                val textView = binding.empDetailName.also {

                    it.setTextColor(context.getColor(R.color.black_with_alpha80))
                }

                val text = employeeEntity.name

                val spannableString = SpannableString(text)
                val color = Color.BLACK
                spannableString.setSpan(
                    ForegroundColorSpan(color),
                    startingPoints[position], endingPoints[position],
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                textView.text = spannableString
            }
            else{
                binding.empDetailName.setTextColor(context.getColor(R.color.black))
            }
            val transitionImageName = "shared_image_${employeeEntity.id}"
            val transitionTextName = "shared_name_${employeeEntity.id}"
            binding.squareProfileImage.transitionName = transitionImageName
            employeeDetailViewModel.setProfileImage(binding.squareProfileImage, employeeEntity.id)

            ViewCompat.setTransitionName(binding.squareProfileImage, transitionImageName)
            ViewCompat.setTransitionName(binding.empDetailName, transitionTextName)
            binding.root.setOnClickListener {

                onClickListener.onClick(
                    employeeEntity,
                    transitionImageName,
                    binding.squareProfileImage,
                    transitionTextName,
                    binding.empDetailName
                )
            }
        }
    }

    override fun getItemCount() = list.size

    fun updateData(
        employees: List<EmployeeEntity>,
        listOfStartingPoint: List<Int>,
        listOfEndPoint: List<Int>
    ) {

        startingPoints = listOfStartingPoint
        endingPoints = listOfEndPoint
        list = employees
        notifyDataSetChanged()
    }

    fun getData() = list
    fun removeItem(position: Int) {

//        list.toMutableList().removeAt(position)
        val mutableList = list.toMutableList()
        mutableList.removeAt(position)
        list = mutableList.toList()
        notifyItemRemoved(position)
    }

    fun restore(position: Int, item: EmployeeEntity) {

//        list.toMutableList().add(position,item)

        val mutableList = list.toMutableList()
        mutableList.add(position, item)
        list = mutableList.toList()
        notifyItemInserted(position)
    }
}


//        init {
//            setName = view.findViewById(R.id.empDetailName)
//            setDepartment = view.findViewById(R.id.departmentText)
//            setDesignation = view.findViewById(R.id.designationText)
//            menuIcon = view.findViewById(R.id.menuIcon)

//            menuIcon.setOnClickListener {
//                popupMenu(it)
//            }
//        }

//        private fun popupMenu(view: View) {
//
//            if(list.isEmpty()){
//                Toast.makeText(context,"List Empty!!!",Toast.LENGTH_LONG).show()
//                return
//            }
//            val employeeEntity = list[adapterPosition]
//            val popupMenus = PopupMenu(context, view)
//            popupMenus.inflate(R.menu.menu)
//            popupMenus.setOnMenuItemClickListener {
//                when (it.itemId) {
//                    R.id.editButton -> {
//                        onClickListener.onClick(employeeEntity)
//                        true
//                    }
//
//                    R.id.deleteButton -> {
//                        list.toMutableList().removeAt(adapterPosition)
//                        notifyItemRemoved(adapterPosition)
//                        onClickListener.onDelete(employeeEntity)
//                        true
//                    }
//
//                    else -> true
//                }
//            }
//            popupMenus.show()
//        }