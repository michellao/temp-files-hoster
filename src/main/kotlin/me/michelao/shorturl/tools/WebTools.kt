package me.michelao.shorturl.tools

import jakarta.servlet.http.HttpServletRequest

class WebTools {
    companion object {
        fun readRealIp(request: HttpServletRequest): String {
            var ip: String? = request.getHeader("x-forwarded-for")
            if (ip == null) {
                ip = request.getHeader("x-real-ip")
            }
            if (ip == null) {
                ip = request.remoteAddr
            }
            return ip
        }
    }
}
