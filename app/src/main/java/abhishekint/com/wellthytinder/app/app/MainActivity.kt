package abhishekint.com.wellthytinder.app.app

import abhishekint.com.wellthytinder.R
import abhishekint.com.wellthytinder.databinding.ActivityMainBinding
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by abhishek on 17-06-2018.
 */
class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}