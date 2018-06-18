package abhishekint.com.wellthytinder.app.app.connect.Repository

import abhishekint.com.wellthytinder.app.app.connect.ViewModel.ConnectViewModel
import abhishekint.com.wellthytinder.app.utils.JsonToString
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by abhishek on 18-06-2018.
 */
class ConnectRepository
{
    internal lateinit var jsonToString: JsonToString

    constructor(jsonToString: JsonToString) {
        this.jsonToString = jsonToString
    }


    fun getCardData(): ArrayList<ConnectViewModel> {
        val dataString = jsonToString.loadJSONFromAsset()
        val connectViewModelList = ArrayList<ConnectViewModel>()
        try {
            val obj = JSONObject(dataString)
            val jArray = obj.getJSONArray("Item")

            for (i in 0 until jArray.length()) {
                val jo_inside = jArray.getJSONObject(i)
                val cardName = jo_inside.getString("item")

                val connectViewModel = ConnectViewModel(cardName)
                connectViewModelList.add(connectViewModel)
            }
            return connectViewModelList
        } catch (e: JSONException) {
            e.printStackTrace()
            return connectViewModelList
        }

    }
}