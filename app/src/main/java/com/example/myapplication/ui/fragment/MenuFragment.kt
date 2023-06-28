package com.example.myapplication.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapters.MenuItemAdapter
import com.example.myapplication.databinding.MenuFragmentBinding
import com.example.myapplication.data.entities.MenuItem
import com.example.myapplication.ui.viewmodel.MenuItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {
    private lateinit var binding: MenuFragmentBinding
    private val viewModel: MenuItemViewModel by viewModels()
    private lateinit var adapter: MenuItemAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MenuFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        observeMenuItems()
        fetchMenuItems()

        binding.applyButton.setOnClickListener {
            applyFilters()
        }
    }

    private fun initializeRecyclerView() {
        adapter = MenuItemAdapter()
        recyclerView = binding.menuItemsList
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = adapter
    }



    private fun observeMenuItems() {
        viewModel.menuItems.observe(viewLifecycleOwner) { menuItems: List<MenuItem> ->
            if (menuItems.isEmpty()) {
                showEmptyState()
            } else {
                showMenuItems(menuItems)
            }
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) { isLoading: Boolean ->
            if (isLoading) {
                showLoadingState()
            } else {
                hideLoadingState()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error: String? ->
            error?.let { showErrorState(it) }
        }
    }

    private fun showEmptyState() {
        binding.menuItemsList.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun showMenuItems(menuItems: List<MenuItem>) {
        binding.numberOfMenus.text = "Recipes: " + menuItems.size.toString()
        binding.menuItemsList.visibility = View.VISIBLE
        adapter.submitList(menuItems)
        adapter.notifyDataSetChanged()
    }

    private fun showLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.menuItemsList.visibility = View.GONE
    }

    private fun hideLoadingState() {
        binding.progressBar.visibility = View.GONE
        binding.menuItemsList.visibility = View.VISIBLE
    }

    private fun showErrorState(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.menuItemsList.visibility = View.GONE
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun applyFilters() {
        val maxValue = binding.rangeSlider.values[0]
        viewModel.fetchFilteredMenuItems(0f, maxValue)
    }

    private fun fetchMenuItems() {
        viewModel.fetchFilteredMenuItems(0f, 1000.0f)
    }
}
