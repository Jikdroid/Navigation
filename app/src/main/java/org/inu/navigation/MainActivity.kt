package org.inu.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        // Create an AppBarConfiguration with the correct top-level destinations
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.home_dest, R.id.deeplink_dest),drawerLayout)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        setupActionBar(navController, appBarConfiguration)
        setupBottomNavMenu(navController)
        setupNavigationMenu(navController)
    }

    // destination 의 label 에 따라 title 변경,
    // By using appBarConfig, it will also determine whether to
    // show the up arrow or drawer menu icon  ( Have NavigationUI handle what your ActionBar displays )
    private fun setupActionBar(navController: NavController, appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController,appBarConfig)
    }

    // bottom nav 설정 (Use NavigationUI to set up Bottom Nav)
    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    // optionMenu 생성
    // The NavigationView already has these same navigation items, so we only add
    // navigation items to the menu here if there isn't a NavigationView
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val retValue =  super.onCreateOptionsMenu(menu)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        if (navigationView == null) {
            menuInflater.inflate(R.menu.overflow_menu, menu)
            return true
        }
        return retValue
    }

    // optionMenu 선택 설정 (Have Navigation UI Handle the item selection - make sure to delete
    // the old return statement above)
    // Have the NavigationUI look for an action or destination matching the menu
    // item id and navigate there if found.
    // Otherwise, bubble up to the parent.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
//                || return super.onOptionsItemSelected(item)
    }


    // navigationMenu 선택에 따른 뷰 전환 (Use NavigationUI to set up a Navigation View)
    private fun setupNavigationMenu(navController: NavController) {
//        // In split screen mode, you can drag this view out from the left
//        // This does NOT modify the actionbar
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
    }

    // navigationMenu 표시 (Have NavigationUI handle up behavior in the ActionBar)
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }
}