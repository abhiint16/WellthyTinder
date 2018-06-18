package abhishekint.com.wellthytinder.app.connect

import abhishekint.com.wellthytinder.R
import abhishekint.com.wellthytinder.app.app.connect.Adapter.TinderCardArrayAdapter
import abhishekint.com.wellthytinder.app.app.connect.Repository.ConnectRepository
import abhishekint.com.wellthytinder.app.app.connect.ViewModel.ConnectViewModel
import abhishekint.com.wellthytinder.app.tinderview.DefaultStackEventListener
import abhishekint.com.wellthytinder.app.utils.JsonToString
import abhishekint.com.wellthytinder.databinding.FragmentConnectBinding
import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject


/**
 * Created by abhishek on 17-06-2018.
 */
class ConnectFrag : Fragment()
{
    lateinit var viewModel : ConnectViewModel

    private lateinit var binding: FragmentConnectBinding

    lateinit var adapterData : ArrayList<String>
    internal lateinit var tinderCardArrayAdapter: TinderCardArrayAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_connect, container, false)

        initViewModel()
        loadAdapterData()
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        tinderCardArrayAdapter= TinderCardArrayAdapter(activity!!.applicationContext,0)
        tinderCardArrayAdapter.addAll(adapterData)
        binding.frame.setContentResource(R.layout.card_stack_item)
        binding.frame.setAdapter(tinderCardArrayAdapter)
        binding.frame.setListener(DefaultStackEventListener(300))
    }

    private fun loadAdapterData() {
        adapterData=viewModel.loadData()
    }

    private fun initViewModel() {
        viewModel=ConnectViewModel(ConnectRepository(JsonToString(activity!!.applicationContext)))
    }
}
