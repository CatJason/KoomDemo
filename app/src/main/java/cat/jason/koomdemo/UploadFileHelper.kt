package cat.jason.koomdemo

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

fun uploadFile(url: String, file: File) {
    val client = OkHttpClient()
    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("uploadFile", file.name, RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file))
        .build()
    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()
    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            println(response.body?.string())
        }
    }
}
