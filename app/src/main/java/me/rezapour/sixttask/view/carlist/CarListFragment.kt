package me.rezapour.sixttask.view.carlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.rezapour.sixttask.R
import me.rezapour.sixttask.databinding.FragmentCarListBinding
import me.rezapour.sixttask.model.Car
import me.rezapour.sixttask.utils.DataState
import me.rezapour.sixttask.viewmodel.CarListViewModel

@AndroidEntryPoint
class CarListFragment : Fragment() {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CarListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarListAdapter
    private lateinit var swiper: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUi()
        subscribeToViewMode()
        viewModel.loadData()
    }

    private fun subscribeToViewMode() {
        viewModel.carDataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> onError(dataState.message)
                DataState.Loading -> loading(true)
                is DataState.Success -> onSuccess(dataState.data)
            }
        }
    }


    private fun setUi() {
        recyclerView = binding.carListRecyclerView
        adapter = CarListAdapter(ArrayList<Car>())
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        swiper = binding.swiperLayout
        swiper.setOnRefreshListener {
            viewModel.loadData()
        }
    }

    private fun onSuccess(posts: List<Car>) {
        loading(false)
        adapter.addItem(posts)
        adapter.notifyDataSetChanged()


    }

    private fun onError(message: String) {
        loading(false)
        snackBar(message)
    }


    private fun loading(isLoading: Boolean) {
        swiper.isRefreshing = isLoading
    }

    private fun snackBar(message: String) {
        Snackbar.make(binding.coordinatorlayoutCarList, message, Snackbar.LENGTH_LONG).show();
    }


}