package me.michelao.shorturl.datasource

import io.awspring.cloud.autoconfigure.s3.properties.S3Properties
import me.michelao.shorturl.configuration.EncryptionManagement
import me.michelao.shorturl.configuration.properties.MyAppProperties
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.ResponseInputStream
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse
import software.amazon.encryption.s3.S3EncryptionClient
import software.amazon.encryption.s3.S3EncryptionClientException
import java.io.InputStream
import java.util.logging.Logger

@Component
class S3ClientData(
    appProperties: MyAppProperties,
    s3Properties: S3Properties,
    private val s3Client: S3Client
) {
    private val logger = Logger.getLogger(this.javaClass.name)
    private val s3ClientEncryption: S3Client
    init {
        val encryptionManagement = EncryptionManagement(appProperties)
        encryptionManagement.autoSetup()
        val aesKey = encryptionManagement.getSecretKey()
        val region = Region.of(s3Properties.region)
        s3ClientEncryption = S3EncryptionClient.builder()
            .region(region)
            .endpointOverride(s3Properties.endpoint)
            .forcePathStyle(s3Properties.pathStyleAccessEnabled)
            .aesKey(aesKey)
            .build()
    }

    private val bucketName: String = appProperties.bucketName

    private fun getS3Object(key: String): Pair<ResponseInputStream<GetObjectResponse>, Boolean> {
        return try {
            val response = s3ClientEncryption.getObject { request ->
                request.bucket(bucketName).key(key)
            }
            response to true
        } catch (e: S3EncryptionClientException) {
            logger.info("Read an unencrypted data")
            val response = s3Client.getObject { request ->
                request.bucket(bucketName).key(key)
            }
            response to false
        }
    }

    fun readData(url: Url): ResponseEntity<InputStreamResource> {
        var contentLength = 0L
        val (response, isEncrypted) = getS3Object(url.urlPath)
        val objectMetadata = response.response()
        val contentType = MediaType.valueOf(url.contentType.toString())
        if (isEncrypted) {
            contentLength = url.sizeBytes
        } else {
            contentLength = objectMetadata.contentLength()
        }
        val data = InputStreamResource(response)
        val responseEntity = ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
            .contentType(contentType)
            .contentLength(contentLength)
        if (contentType.type == MediaType.APPLICATION_OCTET_STREAM_VALUE) {
            responseEntity
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${url.originalFilename}")
        }
        return responseEntity.body(data)
    }

    fun writeData(k: String, inputStream: InputStream, contentLength: Long, contentType: String): PutObjectResponse? {
        val putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(k).contentType(contentType).build()
        val requestBody = RequestBody.fromInputStream(inputStream, contentLength)
        val response = s3ClientEncryption.putObject(putObjectRequest, requestBody)
        return response
    }

    fun deleteData(k: String) {
        try {
            s3ClientEncryption.deleteObject { request ->
                request.bucket(bucketName).key(k)
            }
        } catch (e: S3EncryptionClientException) {
            logger.info("Delete an unencrypted data")
            s3Client.deleteObject { request ->
                request.bucket(bucketName).key(k)
            }
        }

    }
}
