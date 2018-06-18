package abhishekint.com.wellthytinder.app.app.connect.ViewModel

import abhishekint.com.wellthytinder.app.app.connect.Repository.ConnectRepository
import android.databinding.BaseObservable
import java.util.ArrayList

/**
 * Created by abhishek on 17-06-2018.
 */
class ConnectViewModel : BaseObservable {
     var connectRepository: ConnectRepository

    constructor(connectRepository: ConnectRepository) {
        this.connectRepository=connectRepository
    }


    fun loadData() : ArrayList<String> {
        return connectRepository.getCardData()
    }
}