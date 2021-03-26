package com.codecool.shop.controller;

import com.codecool.shop.business.logic.EmailHandler;
import com.codecool.shop.business.logic.SaveCustomerToJson;
import com.codecool.shop.dao.UtilDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Customer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/valid-checkout"})
public class ValidCheckOutController extends HttpServlet implements UtilDao {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        EmailHandler emailHandler = new EmailHandler();

        SaveCustomerToJson saveCustomerToJson = new SaveCustomerToJson(req);
        saveCustomerToJson.save();

        Customer customer = saveCustomerToJson.getCustomer();

        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        StringBuilder sb = new StringBuilder();

        sb.append("Dear "+ customer.getName() +", <br>"+
                "<p>Here you can see your order details: </p><br><br>" +
                "<table>" +
                    "<th>Name</th>" +
                    "<th>Categories</th>" +
                    "<th>Price</th>" +
                    "<th>Image</th>" +
                    "<th>Quantity</th>");

        cartDaoMem.getAll().forEach((pokemon, integer) -> sb.append(
                "<tr>" +
                    "<td>"+pokemon.getName() + "</td>" +
                    "<td>"+pokemon.getPokemonCategoryString() + "</td>" +
                    "<td>"+pokemon.getPrice() + "</td>" +
                    "<td><img src='"+pokemon.getSpriteImageUrl() + "'/></td>" +
                    "<td>"+ integer + "</td>" +
                "</tr>"));

        sb.append("</table><br><br>"+
                "<p><strong>Total price: " + cartDaoMem.getTotalPrice() + " $ </strong><br>"+
                "<p><strong>Estimated delivery: "+ emailHandler.getEstimatedDelivery() +"</strong></p><br>"+
                "<p><i>If you have any problem with your order, please contact: customers@pokeshop.com </i></p>" +
                "<p>Kind regards, pokeStaff </p><br>");

        emailHandler.sendMail(customer.getEmail(), sb.toString());
        resp.sendRedirect(req.getContextPath() + "/");
    }

}
