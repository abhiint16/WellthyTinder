package abhishekint.com.wellthytinder.app.app.connect

import abhishekint.com.wellthytinder.databinding.CardStackItemBinding
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

/**
 * Created by abhishek on 18-06-2018.
 */
class TinderCardArrayAdapter(context: Context, resource: Int) : ArrayAdapter<String>(context, 0) {
    private lateinit var binding: CardStackItemBinding

    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {
        //supply the layout for your card
        binding= DataBindingUtil.bind(contentView!!)!!
        //val v = contentView!!.findViewById<TextView>(R.id.helloText)
        val v=binding.helloText
        v.text = getItem(position)
        return contentView
    }

}