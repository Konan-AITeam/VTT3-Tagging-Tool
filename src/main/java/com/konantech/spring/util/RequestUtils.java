package com.konantech.spring.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RequestUtils {

    private static final String DATETIME_FORMAT = "yyyyMMddHHmmss";
    private static final String DATE_FORMAT = "yyyyMMdd";

    private RequestUtils() {
    }

    public static String getParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                String contentType = request.getContentType();
                if (contentType != null && contentType.length() > 0
                        && contentType.startsWith("multipart/form-data")) {
                    // If POST (with enctype multipart/form-data), do not
                    // decode.
                    return value;
                } else {
                    String encoding = request.getCharacterEncoding();
                    if (!"UTF-8".equals(encoding)) {
                        // If the request.getCharacterEncoding() is null, the
                        // default charset of the WAS is ISO-8859-1.
                        return new String(
                                value.getBytes((null == encoding) ? "ISO-8859-1"
                                        : encoding), "UTF-8");
                    }
                }
            } catch (Exception ex) {
            }
        }
        return value;
    }

    public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = getParameter(request, name);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    public static String getRedirectUrl(HttpServletRequest request, String path) {
        String port = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String url = "http://" + request.getServerName() + port + request.getContextPath();
        return url + path;
    }

    public static String getRequiredParameter(HttpServletRequest request,
                                              String name) throws ServletException {
        String value = getParameter(request, name);
        if (value == null) {
            throw new ServletException("Required " + String.class.getName()
                    + " parameter '" + name + "' is not present");
        }
        return value;
    }

    public static String[] getParameterValues(HttpServletRequest request,
                                              String name) {
        String[] values = request.getParameterValues(name);
        if (values != null) {
            try {
                String encoding = request.getCharacterEncoding();
                if (!"UTF-8".equals(encoding)) {
                    for (int i = 0; i < values.length; i++) {
                        values[i] = (null == values[i]) ? null
                                : new String(
                                values[i].getBytes((null == encoding) ? "ISO-8859-1"
                                        : encoding), "UTF-8");
                    }
                }
            } catch (Exception ex) {
            }
        }
        return values;
    }

    public static String[] getParameterValues(HttpServletRequest request,
                                              String name, char separatorChar) {
        String value = getParameter(request, name);
        return StringUtils.split(value, separatorChar);
    }

    public static boolean getParameterBool(HttpServletRequest request,
                                           String name) {
        String value = getParameter(request, name);
        return Boolean.valueOf(value).booleanValue();
    }

    public static boolean getParameterBool(HttpServletRequest request,
                                           String name, boolean defaultValue) {
        String value = getParameter(request, name, Boolean.toString(defaultValue));
        return Boolean.valueOf(value).booleanValue();
    }

    public static int getParameterInt(HttpServletRequest request, String name)
            throws ServletException {
        String value = getParameter(request, name);
        if (value != null) {
            return toInt(value.trim());
        } else {
            throw new ServletException("Required " + Integer.class.getName()
                    + " parameter '" + name + "' is not present");
        }
    }

    public static int getParameterInt(HttpServletRequest request, String name,
                                      int defaultValue) {
        try {
            return getParameterInt(request, name);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static int[] getParameterInts(HttpServletRequest request, String name)
            throws ServletException {
        String[] values = request.getParameterValues(name);
        return (values != null) ? toInts(values) : null;
    }

    public static Date parseDate(String value, String format)
            throws ServletException {
        if (value != null) {
            try {
                return new SimpleDateFormat(format).parse(value);
            } catch (ParseException e) {
                throw new ServletException("Failed to input string '" + value
                        + "' to requied type [" + Date.class.getName() + "]");
            }
        }
        return null;
    }

    public static String getParameterDateTimeString(HttpServletRequest request, String name) throws ServletException {
        String value = request.getParameter(name);
        if (StringUtils.isNotEmpty(value)) {
            value = deleteNonNumeric(value);
            if (value.length() == 8) {
                return value + "235959";
            } else if (value.length() == 14) {
                return value;
            } else {
                return null;
            }
        }
        return null;
    }


    public static Date getParameterDate(HttpServletRequest request,
                                        String name, String format) throws ServletException {
        String value = request.getParameter(name);
        if (value != null) {
            return parseDate(value, format);
        }
        return null;
    }

    public static Date getParameterDate(HttpServletRequest request,
                                        String name, boolean ceil) throws ServletException {
        String value = request.getParameter(name);
        if (StringUtils.isNotEmpty(value)) {
            value = deleteNonNumeric(value);
            if (value.length() == 8) {
                return (ceil)
                        ? parseDate(value + "235959", DATETIME_FORMAT)
                        : parseDate(value, DATE_FORMAT);
            } else { // if (value.length() == 14) {
                return parseDate(value, DATETIME_FORMAT);
            }
        }
        return null;
    }

    public static Date getParameterDate(HttpServletRequest request,
                                        String name, boolean ceil, Date defaultValue)
            throws ServletException {
        Date value = getParameterDate(request, name, ceil);
        if (null == value) {
            return (ceil)
                    ? DateUtils.addSeconds(DateUtils.ceiling(defaultValue, Calendar.DATE), -1)
                    : DateUtils.truncate(defaultValue, Calendar.DATE);
        }
        return value;
    }

    public static int toInt(String number) throws ServletException {
        try {
            return Integer.parseInt(number);
        } catch (Exception ex) {
            throw new ServletException("Failed to input string '" + number
                    + "' to requied type [" + Integer.class.getName() + "]");
        }
    }

    public static int[] toInts(String[] values) throws ServletException {
        int[] numbers = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            numbers[i] = toInt(values[i].trim());
        }
        return numbers;
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String[] headerNames = {"X-Forwarded-For", "Proxy-Client-IP",
                "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        for (String headerName : headerNames) {
            String value = request.getHeader(headerName);
            if (StringUtils.isNotEmpty(value)
                    && !"unknown".equalsIgnoreCase(value)) {
                return value;
            }
        }
        return request.getRemoteAddr();
    }

    private static String deleteNonNumeric(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }

    public static void setStartAndEnd(Map<String, Object> queryMap, int fromDay, int toDay) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //날짜
        if (queryMap.get("start") == null || StringUtils.length(MapUtils.getString(queryMap, "start")) != 10) {
            if (fromDay == 0) {
                queryMap.put("start", simpleDateFormat.format(new Date()));
            } else {
                queryMap.put("start", simpleDateFormat.format(DateUtil.addDate(new Date(), Calendar.DATE, fromDay)));
            }
        }
        if (queryMap.get("end") == null || StringUtils.length(MapUtils.getString(queryMap, "end")) != 10) {
            if (toDay == 0) {
                queryMap.put("end", simpleDateFormat.format(new Date()));
            } else {
                queryMap.put("end", simpleDateFormat.format(DateUtil.addDate(new Date(), Calendar.DATE, toDay)));
            }
        }
    }

    public static String getQueryString(HttpServletRequest request) {
        String queryString = "";
        Enumeration<?> names = request.getParameterNames();
        if (names != null) {
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String[] values = request.getParameterValues(name);
                queryString += name + "=" + values[0] + "&";
            }
        }
        return queryString;
    }

    public static HashMap getParameterMap(HttpServletRequest request) {
        HashMap map = new HashMap();
        Enumeration enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = (String) enums.nextElement();
            String[] parameters = request.getParameterValues(paramName);

            if (parameters.length > 1) {
                map.put(paramName, parameters);
            } else {
                map.put(paramName, parameters[0]);
            }
        }

        return map;
    }
}
