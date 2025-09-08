package me.michelao.shorturl.tools

import org.springframework.core.convert.converter.Converter
import java.util.Calendar
import java.util.Date
import java.util.regex.Pattern

class DateConverter : Converter<String, Date> {
    private val DATE_PATTERN = Pattern.compile("(\\d+)([hms])")
    override fun convert(source: String): Date {
        val matcher = DATE_PATTERN.matcher(source)
        if (!matcher.matches()) {
            throw IllegalArgumentException("Invalid duration format. Expected: number followed by m (minutes), h (hours), or s (seconds).")
        }
        val amount = matcher.group(1).toInt()
        val unit = matcher.group(2)

        val calendar = Calendar.getInstance()

        when (unit) {
            "h" -> calendar.add(Calendar.HOUR_OF_DAY, amount)
            "m" -> calendar.add(Calendar.MINUTE, amount)
            "s" -> calendar.add(Calendar.SECOND, amount)
            else -> throw IllegalArgumentException("Unsupported time unit: $unit")
        }
        return calendar.time
    }
}
