package com.company.Location;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;

public class MainLocation {
    /**
     * Key
     */
    private static String KEY = "1137f7d2f7a524537f4bd8f10c87ac2d";

    public static String Location_URL = "https://restapi.amap.com/v3/ip?ip=%s&key=%s";
    public static String GD_URL = "https://restapi.amap.com/v3/geocode/geo?address=%s&key=%s";

    /**
     * 成功标识
     */
    private static String SUCCESS_FLAG="1";

//    /**
//     * 根据地址获取对应的经纬度信息
//     * @param address
//     * @return
//     */
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return "";
    }
    public static String getLonAndLatByAddress(String address){
        String location="";
        GD_URL = String.format(GD_URL, address, KEY);
        //高德接口返回的是JSON格式的字符串
        String queryResult = getResponse(GD_URL);
        JSONObject obj = JSONObject.parseObject(queryResult);
        if(String.valueOf(obj.get("status")).equals(SUCCESS_FLAG)){
            JSONObject jobJSON = JSONObject.parseObject(obj.get("geocodes").toString().substring(1, obj.get("geocodes").toString().length() - 1));
            location = String.valueOf(jobJSON.get("location"));
        }else{
            throw new RuntimeException("地址转换经纬度失败，错误码：" + obj.get("infocode"));
        }
        return location;
    }
    public static String getLocationAddress(String IP){
//        IP = "114.247.50.2";
        String location = "";
        Location_URL = String.format(Location_URL, IP, KEY);

        String queryResult = getResponse(Location_URL);
        JSONObject obj = JSONObject.parseObject(queryResult);
        if(String.valueOf(obj.get("status")).equals(SUCCESS_FLAG)){
            String province = String.valueOf(obj.get("province"));
            String city = String.valueOf(obj.get("city"));
            if (province.equals("局域网") || city.equals("[]")){
                throw new RuntimeException("IP转换地址失败1，错误码：" + obj.get("infocode"));
            }
            location = province + city;
        }
        else{
            throw new RuntimeException("IP转换地址失败，错误码：" + obj.get("infocode"));
        }
        return location;
    }
    /**
     * 发送请求
     *
     * @param serverUrl 请求地址
     */
    private static String getResponse(String serverUrl) {
        // 用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    public static void main(String[] args) {
        String IP = getIpAddress();
        System.out.println(IP);
        String address = getLocationAddress(IP);
        String location=getLonAndLatByAddress(address);// output: 经纬度
        System.out.println("经纬度：" + location);
    }
}


