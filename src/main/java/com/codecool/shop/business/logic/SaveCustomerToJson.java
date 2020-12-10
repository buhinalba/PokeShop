package com.codecool.shop.business.logic;

import com.codecool.shop.model.Customer;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class SaveCustomerToJson {
    private Customer customer;

    public SaveCustomerToJson(HttpServletRequest req){
        this.setUpCustomerDetails(req);
    }

    private void setUpCustomerDetails(HttpServletRequest req){
        Customer customer = new Customer(req.getParameter("fullname"));
        customer.setEmail(req.getParameter("email"));
        customer.setAddress(req.getParameter("address"));
        customer.setCity(req.getParameter("city"));
        customer.setState(req.getParameter("state"));
        customer.setZip(Integer.parseInt(req.getParameter("zip")));

        customer.setCardName(req.getParameter("cardname"));
        customer.setCardNumber(Integer.parseInt(req.getParameter("cardnumber")));
        customer.setExpDate(Date.valueOf(req.getParameter("expdate")));
        customer.setCvv(Integer.parseInt(req.getParameter("cvv")));
    }

    public Customer getCustomer() {
        return customer;
    }

}
