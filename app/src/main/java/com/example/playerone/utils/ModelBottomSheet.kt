package com.example.playerone.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playerone.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView

class ModalBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.video_options, container, false)
        // Inflate the menu
        val menu = view.findViewById<NavigationView>(R.id.navigation_view)

        menu.inflateMenu(R.menu.video_options_menu)
        return view

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}
