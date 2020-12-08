package com.sysbot32.netptalk

import org.json.JSONObject
import java.nio.file.Files
import java.nio.file.Paths

data class ChatFile(val filename: String, val data: String) {
    constructor(jsonObject: JSONObject) : this(
            jsonObject.getString("filename"),
            jsonObject.getString("data")
    )

    constructor(filename: String) : this(
            filename,
            Files.readString(Paths.get(filename))
    )

    fun write() {
        Files.writeString(Paths.get(filename), data)
    }

    fun toJSONObject(): JSONObject = JSONObject()
            .put("filename", filename)
            .put("data", data)
}
