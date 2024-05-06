package com.example.myapplication.design

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.view.EmployeeListFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
class BottomSheetDesign(activity: EmployeeListFragment) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener {
            when(it){


            }
        }
    }

    companion object{
        const val TAG = "ModalBottomSheet"
    }
}