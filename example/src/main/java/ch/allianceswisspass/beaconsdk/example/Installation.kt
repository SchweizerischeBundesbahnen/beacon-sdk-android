package ch.allianceswisspass.beaconsdk.example

import android.content.Context
import timber.log.Timber
import java.io.File
import java.util.*


object Installation {

    private var id: String? = null

    @Synchronized
    fun id(context: Context): String {
        if (id != null) {
            return id!!
        }
        val file = File(context.filesDir, "installation")
        if (file.exists()) {
            id = file.readText()
        } else {
            Timber.d("Create installation file")
            id = UUID.randomUUID().toString()
            file.writeText(id!!)
        }
        return id!!
    }
}
