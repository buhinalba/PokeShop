package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Pokemon;
import com.google.gson.Gson;
import org.json.simple.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderLog {
    private static final Logger logger = LoggerFactory.getLogger(OrderLog.class);
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
            logger.info("Log saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("Cannot save log file.");
        }
    }

}
