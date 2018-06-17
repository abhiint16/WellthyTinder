package abhishekint.com.wellthytinder.app.discover

import abhishekint.com.wellthytinder.R
import abhishekint.com.wellthytinder.databinding.FragmentDiscoverBinding
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by abhishek on 17-06-2018.
 */
class DiscoverFrag : Fragment()
{
    private lateinit var binding: FragmentDiscoverBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false)
        return binding.root
    }
}