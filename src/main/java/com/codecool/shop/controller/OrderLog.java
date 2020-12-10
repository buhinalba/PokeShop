package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Pokemon;
import com.google.gson.Gson;
import org.json.simple.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderLog {
    //(date, log)
    private HashMap<String, String> orderLog = new HashMap<String, String>();
    private Integer orderId;

    public OrderLog(Integer orderId) {
        this.orderId = orderId;
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


    public void writeLog(String message, Pokemon pokemon) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        JSONObject log = new JSONObject();
        log.put("message", message);
        log.put("date", dtf.format(now));
        log.put("cartId", orderId);
        log.put("pokemonName", pokemon.getName());

        String fileName = "log_" + orderId + "_" + (dtf.format(now)) + ".json";
        try (FileWriter file = new FileWriter(fileName, true)) {
            file.write(log.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveFile() {
        JSONObject newLogRow = new JSONObject();
        //Write JSON file
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String fileName = "log_" + orderId + "_" + (dtf.format(now)) + ".json";

        for (Map.Entry<String, String> entry : orderLog.entrySet()) {
            newLogRow = new JSONObject();
            newLogRow.put("date", entry.getKey());
            newLogRow.put("action", entry.getValue());

            try (FileWriter file = new FileWriter(fileName, true)) {
                file.write(newLogRow.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
