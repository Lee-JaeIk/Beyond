package com.example.lee.tmap;

import android.content.Context;
import android.util.Log;

import com.skp.Tmap.TMapGpsManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lee on 2016-11-10.
 */
public class UserException {

    public static final String TAG = "UserException";

    public static double STATIC_CURRENT_LONGITUDE = 0.0;
    public static double STATIC_CURRENT_LATITUDE = 0.0;
    public static boolean STATIC_CURRENT_GPS_CHECK = false;



    /*==========
        1. GuideActivity & PathInfoActivity
        2. 네비 소요시간 & 통행요금 & 거리정보
    ========== */

    // [ 도착시간 오전 & 오후 구분 위함 ]
    public String strArrival_time(int arrival_time){
        String result = "";

        // 시간 더하기 위해 Calendar 사용
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, arrival_time);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");

        if(cal.get(Calendar.AM_PM) == 1) result = "오후 " + sdfNow.format(cal.getTime()) + " 도착예정";
        else result = "오전 " + sdfNow.format(cal.getTime()) + " 도착예정";

        result = sdfNow.format(cal.getTime()) + " 도착예정";

        return result;
    }   // strArrival_time

    // 소요시간
    public String strTime(int totalTime){
        String result = "";
        int hour, minute;

        hour = totalTime / 3600;
        minute = totalTime % 3600 / 60;

        if( hour == 0 ) result = minute + "분";
        else result = hour+"시간 " + minute +"분";

        return result;
    }   // strTime

    // 통행요금
    public String strWon(int fare){
        String result = "";

        if(fare >= 1000) result = fare/1000 + "," + fare%1000 + " 원";
        else result = fare + " 원";

        return result;
    }   // strWon

    // 거리
    public String strDistance(int distance){
        String result = "";

        if( distance >= 1000 ) result = distance / 1000 + "." + (distance % 1000 ) / 100 + " km";
        else result = distance + " m";

        return result;
    }   // strDistance

    // 총 남은거리
    public String strRemainDistance(int total_distance, int remain_distance){
        String result = "";
        String strTotal = "";
        String strRemain = "";
        if( total_distance >= 1000 ) strTotal = total_distance / 1000 + "." + (total_distance % 1000 ) / 100;
        else strTotal = "0." + total_distance/100;

        if( remain_distance >= 1000 ) strRemain = remain_distance / 1000 + "." + (remain_distance % 1000 ) / 100;
        else if( remain_distance <= 0 ) strRemain = "0.0";
        else strRemain = "0." + remain_distance/100;

        return result = strRemain + " / " + strTotal +"km";
    }


    /*==========
        1. SearchDestinationActivity
        2. POI명칭이 길때 구리역중앙.... 처럼 표시하기 위함
    ==========*/
    public String strPOIName(String name){
        String result = "";
        if( name.length() > 15 ) result = name.substring(0, 15) + "..";
        else result = name;

        return result;
    }



    /*==========
        [ 경도 위도로 거리 구하기 ]
    ==========*/
    public double calDistance(double lat1, double lon1, double lat2, double lon2){

        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

        return dist;
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg){
        return (double)(deg * Math.PI / (double)180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad){
        return (double)(rad * (double)180d / Math.PI);
    }
}
