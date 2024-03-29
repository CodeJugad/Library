package com.example.library.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
//import android.widget.Toolbar
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.library.*
import com.example.library.fragment.AboutAppFragment
import com.example.library.fragment.DashboardFragment
import com.example.library.fragment.FavouritesFragment
import com.example.library.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toolbar:Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)

        openDashboard()

        setUpToolbar()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId){
                R.id.dashboard ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, DashboardFragment())
                        .addToBackStack("Dashboard")
                        .commit()
                    supportActionBar?.title = "Dashboard"
                    drawerLayout.closeDrawers()

                    Toast.makeText(this@MainActivity,"dashboard clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.favourites ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FavouritesFragment())
                        .commit()
                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                    // code
                    Toast.makeText(this@MainActivity,"favourites clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                    // code
                    Toast.makeText(this@MainActivity,"profile clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.aboutApp ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, AboutAppFragment())
                        .commit()
                    supportActionBar?.title = "About App"
                    drawerLayout.closeDrawers()
                    // code
                    Toast.makeText(this@MainActivity,"aboutApp clicked",Toast.LENGTH_SHORT).show()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }
    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dashboard"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)
        when(frag){
            !is DashboardFragment -> openDashboard()

            else -> super.onBackPressed()
        }

    }

    fun  openDashboard(){
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()

        supportActionBar?.title = "Dashboard"

        navigationView.setCheckedItem(R.id.dashboard)
    }

}