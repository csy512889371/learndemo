package com.ctoedu.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/10/9.
 * 获取客户端信息的工具类
 */
public final class NetworkUtil {

   private final static Logger logger = LoggerFactory.getLogger(NetworkUtil.class);

    /**
     * 获取请求主机IP地址，如果通过代理进来，则透过防火墙获取真实IP地址
     * @param request
     * @return
     */
    public final static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (logger.isInfoEnabled()) {
            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0;index < ip.length();index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return  ip;
    }

    public static String getOS(HttpServletRequest request) {
        String browserDetails = request.getHeader("User-Agent");
        String userAgent = browserDetails;
        String userAgentLower = userAgent.toLowerCase();
        StringBuffer os = new StringBuffer();
        if (userAgentLower.indexOf("windows") >= 0) {
            os.append("Windows");
        }else if (userAgentLower.indexOf("mac") >= 0) {
            os.append("Mac");
        }else if (userAgentLower.indexOf("windows") >= 0) {
            os.append("Windows");
        }else if (userAgentLower.indexOf("x11") >= 0) {
            os.append("Unix");
        }else if (userAgentLower.indexOf("android") >= 0) {
            os.append("Android");
        }else if (userAgentLower.indexOf("iphone") >= 0) {
            os.append("Iphone");
        }else {
            os.append("Unknown,More-Info:").append(userAgent);
        }
        return os.toString();
    }

    public static String getBrowser(HttpServletRequest request) {
        String browserDetails = request.getHeader("User-Agent");
        String userAgent = browserDetails;
        String userAgentLower = userAgent.toLowerCase();
        StringBuffer browser = new StringBuffer();
        if (userAgentLower.contains("edge")) {
            browser.append((userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-"));
        }else if (userAgentLower.contains("msie")) {
            browser.append((userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("MSIE", "IE"))
                   .append("-").append((userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[1]));
        }else if (userAgentLower.contains("safari") && userAgentLower.contains("version")) {
            browser.append((userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0])
                   .append("-").append((userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1]);
        }else if (userAgentLower.contains("opr") || userAgentLower.contains("opera")) {
            if (userAgentLower.contains("opr")) {
                browser.append((userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0])
                       .append("-").append((userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1]);
            }else if (userAgentLower.contains("opera")) {
                browser.append((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-").replace("OPR", "Opera"));
            }
        }else if (userAgentLower.contains("chrome")) {
            browser.append((userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-"));
        }else if (userAgentLower.contains("firefox")) {
            browser.append((userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-"));
        }else if (userAgentLower.contains("rv")) {
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            browser.append("IE").append(IEVersion.substring(0, IEVersion.length() - 1));
        }else {
            browser.append("Unknown,More-Info:").append(userAgent);
        }

        return browser.toString();
    }
}
