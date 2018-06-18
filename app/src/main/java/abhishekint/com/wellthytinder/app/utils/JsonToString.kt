package abhishekint.com.wellthytinder.app.utils

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

/**
 * Created by abhishek on 18-06-2018.
 */
class JsonToString(internal var context: Context) {

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val `is` = context.assets.open("card_item.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

}
