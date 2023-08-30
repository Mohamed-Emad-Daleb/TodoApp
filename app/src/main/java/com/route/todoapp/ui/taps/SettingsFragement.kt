package com.route.todoapp.ui.taps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.route.todoapp.databinding.FragementSettingsBinding

class SettingsFragement:Fragment() {
    lateinit var viewBiniding:FragementSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBiniding=FragementSettingsBinding.inflate(inflater,container,false)
        return viewBiniding.root
    }
}