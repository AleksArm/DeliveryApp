package com.highestaim.deliveryapp.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.highestaim.deliveryapp.R
import com.highestaim.deliveryapp.enum.MapEnum
import com.highestaim.deliveryapp.service.MockDataService
import com.highestaim.deliveryapp.service.MockDataService.getOrders
import com.highestaim.deliveryapp.util.AppKee.OPEN_ENUM
import com.highestaim.deliveryapp.util.replaceTabFragment
import kotlinx.android.synthetic.main.orders_fragment.*

class OrdersFragment : BaseFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupTabLayout()
        bindTabsWithAnEvent()
    }

    override fun getLayoutId() = R.layout.orders_fragment


    private fun setupTabLayout() {
        val mapFragment = tabLayout?.newTab()
            ?.setText("${getString(R.string.stops)}   (${getOrders().size})")
        val stopsFragment = tabLayout?.newTab()?.setText(R.string.map)
        mapFragment?.let { tabLayout?.addTab(it, true) }
        stopsFragment?.let { tabLayout?.addTab(it) }

        replaceTabFragment(StopsTabFragment())
    }

    private fun bindTabsWithAnEvent() {
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                setCurrentTabFragment(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setCurrentTabFragment(tabPosition: Int) {
        when (tabPosition) {
            0 -> replaceTabFragment(StopsTabFragment())
            1 -> {
                val mapFragment = MapFragment()
                val bundle = Bundle()

                bundle.putSerializable(OPEN_ENUM, MapEnum.FROM_TAB)
                mapFragment.arguments = bundle
                replaceTabFragment(mapFragment, addBackStack = true)
            }
        }
    }

}