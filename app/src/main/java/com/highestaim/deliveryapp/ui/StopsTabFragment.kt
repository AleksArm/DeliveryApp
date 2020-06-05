package com.highestaim.deliveryapp.ui

import android.os.Bundle
import android.view.View
import com.highestaim.deliveryapp.R
import com.highestaim.deliveryapp.enum.MapEnum
import com.highestaim.deliveryapp.ui.adapter.OrderAdapter
import com.highestaim.deliveryapp.util.AppKee.OPEN_ENUM
import com.highestaim.deliveryapp.util.AppKee.ORDER
import com.highestaim.deliveryapp.util.MockDataService.getOrders
import com.highestaim.deliveryapp.util.initRecyclerView
import com.highestaim.deliveryapp.util.replaceFragment
import kotlinx.android.synthetic.main.stops_tab_fragment_layout.*

class StopsTabFragment : BaseFragment() {

    private val ordersAdapter = OrderAdapter()

    override fun getLayoutId() = R.layout.stops_tab_fragment_layout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initOrderRecyclerView()
        setOrders()
        navigateClickListener()
    }

    private fun initOrderRecyclerView() {
        ordersRecyclerView?.initRecyclerView(context, ordersAdapter)
    }

    private fun setOrders() {
        ordersAdapter.submitList(getOrders())
    }

    private fun navigateClickListener() {
        ordersAdapter.onNavigateClick = {
            val mapFragment = MapFragment()
            val bundle = Bundle()

            bundle.putSerializable(OPEN_ENUM, MapEnum.FROM_NAVIGATE)
            bundle.putSerializable(ORDER,it)

            mapFragment.arguments = bundle

            replaceFragment(mapFragment, true)
        }
    }
}