package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.material3.ModalBottomSheet
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.view.EmployeeListFragment
import com.example.myapplication.viewmodel.EmployeeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
//    val bottomSheetDesign = BottomSheetDesign()

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        var fragment = EmployeeListFragment()

//        bottomSheetDesign.show(supportFragmentManager, BottomSheetDesign.TAG)
        navController = navHostFragment.navController
    }

}