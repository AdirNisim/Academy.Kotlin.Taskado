package com.example.taskado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.taskado.databinding.ActivityMainBinding
import com.example.taskado.fragments.Users.ViewModels.UserPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserPageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.visibility = View.VISIBLE
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.entryPageFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.entryAnimationFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.loginPageFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.registerPageFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
            }
        }

        setupWithNavController(binding.bottomNavigation,navController)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val navController = findNavController(R.id.nav_host_fragment)
            when (item.itemId) {
                R.id.nav_home -> {
                    navController.popBackStack()
                    navController.navigate(R.id.userPageFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_addtask -> {
                    navController.popBackStack()
                    navController.navigate(R.id.addTaskFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_favorite -> {
                    navController.popBackStack()
                    navController.navigate(R.id.favoriteTasksPageFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    navController.popBackStack()
                    navController.navigate(R.id.updateProfileFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    runBlocking { viewModel.signOut() }
                    runBlocking { viewModel.CleanAllTasksInRooam() }
                    navController.popBackStack()
                    navController.popBackStack(R.id.entryAnimationFragment, true)
                    navController.navigate(R.id.entryAnimationFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }

}