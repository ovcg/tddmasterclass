package com.example.tddmasterclass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.tddmasterclass.utils.TestIdlingResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TestIdlingResource.increment()

        val finalHost = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, finalHost)
            .setPrimaryNavigationFragment(finalHost)
            .commit()

        TestIdlingResource.decrement()
    }
}