package fortic.library.android
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object Permify {

    private var code = 2020
    private var activity:Activity?=null
    operator fun invoke(context:Activity): Permify {
        this.activity = context
        return this
    }

    operator fun get(vararg permissions: Permission): Boolean{
        if (activity == null) return except("Context Not initialize, 'context = null'",false)
        val gets = arrayListOf<String>()
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(activity!!, it.pack) == denied) {
                gets.add(it.pack)
            }
        }
        if (gets.isNotEmpty()){
            ActivityCompat.requestPermissions(activity!!, gets.toTypedArray(), code)
            return false
        }
        return true
    }

    private val granted get() = PackageManager.PERMISSION_GRANTED
    private val denied get() = PackageManager.PERMISSION_DENIED
    private val Permission.pack : String get() = "android.permission.$this"
    private fun <T> Any?.except(message:String?, result:T):T {
        var text = "=========================\n"
        if (this != null){
            text += "Class name : Permify\n"
            text += "Class path : fortic.library.android:Permify\n"
        }
        if (message != null && message.isNotEmpty())
            text += "Exception : $message"
        text += "\n========================="
        System.err.println(text)
        return result
    }

}