package com.lk.biocadproject

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tester.componentsoftester.DatePickerFragment
import com.lk.biocadproject.ui.dashboard.DashboardFragment

class MainActivity : AppCompatActivity(), DashboardFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onCreateDatePicker(listener: DatePickerDialog.OnDateSetListener) {
        showDatePickerDialog(listener)
    }

    private fun showDatePickerDialog(listener: DatePickerDialog.OnDateSetListener){
        val newFragment = DatePickerFragment(listener)
        newFragment.show(supportFragmentManager, "datePicker")
    }
}
