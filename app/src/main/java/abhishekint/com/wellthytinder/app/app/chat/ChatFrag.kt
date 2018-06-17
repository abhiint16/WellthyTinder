package abhishekint.com.wellthytinder.app.chat

import abhishekint.com.wellthytinder.R
import abhishekint.com.wellthytinder.databinding.FragmentChatBinding
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by abhishek on 17-06-2018.
 */
class ChatFrag : Fragment()
{
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        return binding.root
    }
}