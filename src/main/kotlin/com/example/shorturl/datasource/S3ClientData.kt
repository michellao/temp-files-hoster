package com.example.shorturl.datasource

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse
import java.io.InputStream

@Component
class S3ClientData(private val s3Client: S3Client) {
    val bucketName = "shorturl"

    fun readData(k: String): ResponseEntity<ByteArray> {
        val response = s3Client.getObject { request ->
            request.bucket(this.bucketName).key(k)
        }
        val objectMetadata = response.response()
        val contentType = MediaType.valueOf(objectMetadata.contentType() ?: MediaType.APPLICATION_OCTET_STREAM_VALUE)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$k")
            .contentType(contentType)
            .body(response.readAllBytes())
    }

    fun writeData(k: String, inputStream: InputStream, contentType: String): PutObjectResponse? {
        val putObjectRequest = PutObjectRequest.builder().bucket(this.bucketName).key(k).contentType(contentType).build()
        val requestBody = RequestBody.fromBytes(inputStream.readBytes())
        val response = s3Client.putObject(putObjectRequest, requestBody)
        return response
    }
}