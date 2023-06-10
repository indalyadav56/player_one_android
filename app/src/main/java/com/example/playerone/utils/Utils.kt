package com.example.playerone.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.example.playerone.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


class Utils {

    fun requestPermission(activity: Activity, permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }

    fun fullscreen(activity: Activity) {
        activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        activity.supportActionBar?.hide()


    }

    fun showBottomNavigation(activity: Activity) {
        val bottomNavigation: BottomNavigationView = activity.findViewById(R.id.bottomNavigationView)
        bottomNavigation.visibility = View.VISIBLE
    }

    fun hideBottomNavigation(activity: Activity) {
        val bottomNavigation: BottomNavigationView = activity.findViewById(R.id.bottomNavigationView)
        bottomNavigation.visibility = View.GONE
    }

    fun hide(view: View) {
        view.visibility = View.GONE
    }

    fun removeAppBar(appBarLayout: AppBarLayout) {
        val parentView: ViewGroup = appBarLayout.parent as ViewGroup
        parentView.removeView(appBarLayout)
    }


}