package com.codecool.shop.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrderLog {
    //(date, log)
    private HashMap<String, String> orderLog = new HashMap<String, String>();
    private Integer orderId;

    public OrderLog(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderLog(Integer orderId, String date, String log) {
        this.orderId = orderId;
        orderLog.put(date, log);
    }

    public HashMap<String, String> getOrderLog() {
        return orderLog;
    }

    public void setOrderLog(HashMap<String, String> orderLog) {
        this.orderLog = orderLog;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void addLog(String date, String log) {
        orderLog.put(date, log);
    }

    public void saveFile() {
        JSONObject newLogRow = new JSONObject();
        JSONArray newLogRows = new JSONArray();
        Iterator<Map.Entry<String, String>> mapIterator = orderLog.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<String, String> entry = mapIterator.next();

            newLogRow.put("date", entry.getKey());
            newLogRow.put("action", entry.getValue());
            newLogRows.add(newLogRow);
            newLogRow = new JSONObject();
        }

        //Write JSON file
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String fileName = orderId + "_" + (dtf.format(now)) + ".json";

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(newLogRows.toJSONString()); //append kéne
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
