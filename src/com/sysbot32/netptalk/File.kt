package com.sysbot32.netptalk

import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

data class ChatFile(val filename: String, val data: ByteArray) {
    constructor(jsonObject: JSONObject) : this(
            jsonObject.getString("filename"),
            Base64.getDecoder().decode(jsonObject.getString("data"))
    )

    constructor(file: File) : this(
            file.name,
            readFile(file)
    )

    fun write() {
        writeFile(File(filename), data)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChatFile

        if (filename != other.filename) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = filename.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}

fun readFile(file: File): ByteArray {
    val fileInputStream = FileInputStream(file)
    val buf: ByteArray = ByteArray(fileInputStream.available())
    fileInputStream.read(buf)
    fileInputStream.close()
    return buf
}

fun writeFile(file: File, data: ByteArray) {
    val fileOutputStream = FileOutputStream(file)
    fileOutputStream.write(data)
    fileOutputStream.close()
}
