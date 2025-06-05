package com.example.shorturl.datasource

import com.example.shorturl.configuration.properties.MyAppProperties
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
class S3ClientData(
    appProperties: MyAppProperties,
    private val s3Client: S3Client)
{
    private val bucketName: String = appProperties.bucketName

    fun readData(url: Url): ResponseEntity<ByteArray> {
        val response = s3Client.getObject { request ->
            request.bucket(bucketName).key(url.urlPath)
        }
        val objectMetadata = response.response()
        val contentType = MediaType.valueOf(objectMetadata.contentType() ?: MediaType.APPLICATION_OCTET_STREAM_VALUE)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${url.originalFilename}")
            .contentType(contentType)
            .contentLength(objectMetadata.contentLength())
            .body(response.readAllBytes())
    }

    fun writeData(k: String, inputStream: InputStream, contentType: String): PutObjectResponse? {
        val putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(k).contentType(contentType).build()
        val requestBody = RequestBody.fromBytes(inputStream.readBytes())
        val response = s3Client.putObject(putObjectRequest, requestBody)
        return response
    }

    fun deleteData(k: String) {
        s3Client.deleteObject { request ->
            request.bucket(bucketName).key(k)
        }
    }
}