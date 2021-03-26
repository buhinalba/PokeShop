package com.codecool.shop.business.logic;

import com.codecool.shop.model.Customer;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveCustomerToJson {
    private Customer customer;
    private static final Logger logger = LoggerFactory.getLogger(SaveCustomerToJson.class);
    public SaveCustomerToJson(HttpServletRequest req) throws IOException {
        this.setUpCustomerDetails(req);
    }

    public void save() throws IOException {
        Gson gson = new Gson();
        String fileName = customer.getSaveName();
        FileOutputStream fileOut = new FileOutputStream("src/main/saves/" + fileName + ".json", false);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(gson.toJson(customer));
        out.flush();
        out.close();
        logger.info("Serialized data is saved in " + fileName);
    }

    private void setUpCustomerDetails(HttpServletRequest req){
        this.customer = new Customer(req.getParameter("fullname"));
        customer.setEmail(req.getParameter("email"));
        customer.setAddress(req.getParameter("address"));
        customer.setCity(req.getParameter("city"));
        customer.setState(req.getParameter("state"));
        customer.setZip(Integer.parseInt(req.getParameter("zip")));

        customer.setCardName(req.getParameter("cardname"));
        customer.setCardNumber(req.getParameter("cardnumber"));
        customer.setExpDate(req.getParameter("expdate"));
        customer.setCvv(Integer.parseInt(req.getParameter("cvv")));
        logger.info("New customer details set up.");
    }

    public Customer getCustomer() {
        return customer;
    }

}
