package me.michelao.shorturl.configuration

import me.michelao.shorturl.datasource.MimeType
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration

@Configuration
class MimeTypeConverter : AbstractJdbcConfiguration() {
    override fun userConverters(): List<*> = listOf(
        MimeTypeToStringConverter(),
        StringToMimeTypeConverter()
    )
}

@WritingConverter
class MimeTypeToStringConverter : Converter<MimeType, String> {
    override fun convert(source: MimeType): String = source.data
}

@ReadingConverter
class StringToMimeTypeConverter : Converter<String, MimeType> {
    override fun convert(source: String): MimeType = MimeType.fromValue(source)
}
