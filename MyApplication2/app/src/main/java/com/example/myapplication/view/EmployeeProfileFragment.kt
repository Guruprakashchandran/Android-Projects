package com.example.myapplication.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEmployeeProfileBinding
import com.example.myapplication.model.Employee
import com.example.myapplication.viewmodel.EmployeeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeProfileFragment : Fragment() {

    private val employeeViewModel : EmployeeDetailViewModel by viewModels()
    private lateinit var view: View
    private lateinit var employeeEntity: Employee
    private lateinit var cancelButton: ImageButton
    private lateinit var binding: FragmentEmployeeProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_employee_profile,
            container,
            false
        )

        view = binding.root
        employeeEntity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("Data", Employee::class.java)
        } else {
            arguments?.getParcelable("Data")
        }!!
        getAnimationTransition()
        cancelButtonSetup()

        employeeViewModel.setProfileImage(binding.profileImage,employeeEntity.id)

        binding.employee = employeeEntity
        return view
    }

    private fun getAnimationTransition() {

        val transitionImageName = arguments?.getString("transitionImageName")
        val transitionTextName = arguments?.getString("transitionTextName")
        ViewCompat.setTransitionName(binding.profileImage,transitionImageName)
        ViewCompat.setTransitionName(binding.empNameText,transitionTextName)
        binding.profileImage.doOnPreDraw { startPostponedEnterTransition() }
        val transition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)

        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    private fun cancelButtonSetup() {

        cancelButton = view.findViewById(R.id.profileCancelButton)
        cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}

