package com.example.apielib.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.example.apielib.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_apie.*
import kotlinx.android.synthetic.main.fragment_station_i.*
import kotlinx.android.synthetic.main.fragment_station_i.btnEnd
import kotlinx.android.synthetic.main.fragment_station_i.txtNext

class StationIFragment : Fragment() {

    var showInstruction = true

    var videoFragment = DrugVideoFragment()
    var recordFragment = RecordDrugFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_station_i, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ApieActivity).txtTitle.text = "Station I"
        viewPagerDrug.isUserInputEnabled = false
        viewPagerDrug.adapter = DrugViewPagerAdapter(this)
        viewPagerDrug.offscreenPageLimit = 4
        tabLayoutDrug.addTab(tabLayoutDrug.newTab().setText("Video"))
        tabLayoutDrug.addTab(tabLayoutDrug.newTab().setText("Chart"))
        tabLayoutDrug.addTab(tabLayoutDrug.newTab().setText("Record"))
        tabLayoutDrug.addTab(tabLayoutDrug.newTab().setText("Documentation"))
//        TabLayoutMediator(tabLayoutDrug, viewPagerDrug) { tab, position ->
//            tab.text = when (position) {
//                0 -> "Video"
//                1 -> "Chart"
//                2 -> "Record"
//                else -> "Documentation"
//            }
//
////            viewPagerDrug.setCurrentItem(tab.position, true)
//        }.attach()

        tabLayoutDrug.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(showInstruction){
                    showInstruction = false
                    videoFragment.showInstruction()
                    tabLayoutDrug.selectTab(tabLayoutDrug.getTabAt(0))
                }else{
                    when (tab?.position) {
                        0 ->  viewPagerDrug.setCurrentItem(
                            0,
                            true
                        )
                        1 ->  viewPagerDrug.setCurrentItem(
                            1,
                            true
                        )
                        2 ->  viewPagerDrug.setCurrentItem(
                            2,
                            true
                        )
                        3 ->  viewPagerDrug.setCurrentItem(
                            3,
                            true
                        )

                    }
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewPagerDrug.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayoutDrug.selectTab(tabLayoutDrug.getTabAt(position))
            }
        })


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

    inner class DrugViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = 4

        override fun createFragment(position: Int) = when (position) {
            0 -> videoFragment
            1 -> DrugChartFragment()
            2 -> recordFragment
            else -> DocumentationFragment()
        }


    }

}






