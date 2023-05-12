package com.example.apielib.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apielib.APIEPackage

import com.example.apielib.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_apie.*
import kotlinx.android.synthetic.main.fragment_station_a.*
import kotlinx.android.synthetic.main.fragment_station_p.*
import kotlinx.android.synthetic.main.fragment_station_p.btnEnd
import kotlinx.android.synthetic.main.fragment_station_p.txtNext


class StationPFragment : Fragment(R.layout.fragment_station_p) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ApieActivity).txtTitle.text = "Station P"
        viewPagerP.isUserInputEnabled = false
        viewPagerP.offscreenPageLimit = 3
        viewPagerP.adapter = PViewPagerAdapter(this)
        TabLayoutMediator(tabLayoutP, viewPagerP) { tab, position ->
            tab.text = when (position) {
                0 -> "Plan"
                else -> "Documentation"
            }
            viewPagerP.setCurrentItem(tab.position, true)
        }.attach()

        txtNext.setOnClickListener {
            with(activity as ApieActivity) {
                gotoNextStation(true)
            }
        }
        btnEnd.setOnClickListener { (activity as ApieActivity).endApie() }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            AlertDialog.Builder(requireActivity())
                .setMessage("Do you want to exit this APIE package?").setCancelable(false)
                .setPositiveButton("YES") { _, _ -> requireActivity().finish() }
                .setNegativeButton("NO") { _, _ -> }
                .create().show()
        }
    }



}

class PViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        0 -> PlanFragment()
        else -> DocumentationFragment()
    }
}
