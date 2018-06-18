package abhishekint.com.wellthytinder.app.app.connect.ViewModel

import abhishekint.com.wellthytinder.app.app.connect.Repository.ConnectRepository
import java.util.ArrayList

/**
 * Created by abhishek on 17-06-2018.
 */
class ConnectViewModel {
    lateinit var cardItem: String
    lateinit var connectRepository: ConnectRepository

    constructor(
            connectRepository: ConnectRepository
    )

    constructor(cardItem: String) {
        this.cardItem = cardItem
    }


    fun loadData() : ArrayList<ConnectViewModel> {
        return connectRepository.getCardData()
    }
}