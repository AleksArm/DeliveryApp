package com.highestaim.deliveryapp.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.highestaim.deliveryapp.R
import com.highestaim.deliveryapp.enum.MapEnum
import com.highestaim.deliveryapp.service.PreferenceService
import com.highestaim.deliveryapp.ui.adapter.OrderAdapter
import com.highestaim.deliveryapp.util.AppKee.OPEN_ENUM
import com.highestaim.deliveryapp.util.AppKee.ORDER
import com.highestaim.deliveryapp.util.initRecyclerView
import com.highestaim.deliveryapp.util.replaceFragment
import com.highestaim.viewmodel.OrdersViewModel
import kotlinx.android.synthetic.main.stops_tab_fragment_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

class StopsTabFragment : BaseFragment() {

    private val ordersAdapter = OrderAdapter()

    private val ordersViewModel: OrdersViewModel by viewModel()

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
        ordersViewModel.getOrders().observe(viewLifecycleOwner, Observer {
            ordersAdapter.submitList(it)
            PreferenceService.get().saveRestsNative(it)
        })
    }

    private fun navigateClickListener() {
        ordersAdapter.onNavigateClick = {
            val mapFragment = MapFragment()
            val bundle = Bundle()

            bundle.putSerializable(OPEN_ENUM, MapEnum.FROM_NAVIGATE)
            bundle.putSerializable(ORDER, it)

            mapFragment.arguments = bundle

            replaceFragment(mapFragment, true)
        }
    }
}