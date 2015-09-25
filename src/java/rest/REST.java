package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "REST", urlPatterns = {"/api/quote/*"})
public class REST extends HttpServlet {

    private Map<Integer, String> quotes = new HashMap() {
        {
            put(1, "Friends are kisses blown to us by angels");
            put(2, "Do not take life too seriously. You will never get out of it alive");
            put(3, "Behind every great man, is a woman rolling her eyes");
        }
    };

    String getParam(HttpServletRequest request) {
        String[] parts = request.getRequestURI().split("/");
        String parameter = null;
        if (parts.length == 5) {
            parameter = parts[4];
        }
        return parameter;
    }

    protected void makeResponse(String responseString, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(responseString);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idString = getParam(request);
        int id = Integer.parseInt(idString);
        String quote = quotes.get(id);
        JsonObject json = new JsonObject();
        json.addProperty("quote", quote);
        makeResponse(new Gson().toJson(json), response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Scanner jsonScanner = new Scanner(request.getInputStream());
        Scanner idScanner = new Scanner(request.getInputStream());
        String json = "";
        String id = "";
        while (jsonScanner.hasNext()) {
        json += jsonScanner.nextLine();
        }
        while (idScanner.hasNext()){
            id+=idScanner.nextLine();
        }
        quotes.put(Integer.parseInt(id), json);
                
 
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
