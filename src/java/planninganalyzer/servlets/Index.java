/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planninganalyzer.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import planninganalyzer.model.Event;

/**
 *
 * @author Nicolas
 */
public class Index extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet Index</title>");            
        out.println("</head>");
        out.println("<body>");
        
        try {
        
            String applicationPath = request.getServletContext().getRealPath("");

            BufferedReader EdT_SI = new BufferedReader(new FileReader(applicationPath + File.separator + "EdT_PENDON_NICOLAS.ics"));

            String line;
            float count_hours = 0;
            while((line = EdT_SI.readLine()) != null) {
                // go to next event
                if(!line.startsWith("BEGIN:VEVENT")) {
                    continue;
                }
                
                Event event = new Event();
                int index1 = 0;
                
                
                while(!line.startsWith("UID")) {
                    line = EdT_SI.readLine();
                }
                
                if(line.contains("Férié")) {
                    continue;
                }


                // go to its date
                while(!line.startsWith("DTSTART")) {
                    line = EdT_SI.readLine();
                }

                index1 = line.indexOf(":") + 1;
                String str = line.substring(index1, index1 + 8);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

                event.date = formatter.parse(str);
                System.out.println(event.date);

                Date dateD, dateF;
                formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                
                index1 = line.indexOf(":") + 1;
                if(index1 > 10) {
                    System.out.println(line);
                }
                dateD = formatter.parse(line.substring(index1, index1 + 15));
                
                line = EdT_SI.readLine();
                index1 = line.indexOf(":") + 1;
                dateF = formatter.parse(line.substring(index1, index1 + 15));
                long diff = dateF.getTime() - dateD.getTime();
                
                event.hours = diff / 1000 / 3600;
                
                // go to its summary
                while(!line.startsWith("SUMMARY")) {
                    line = EdT_SI.readLine();
                }
                
                index1 = line.indexOf(":") + 1;
                line = line.substring(index1);
                
                index1 = line.indexOf("-") - 1;
                event.libelle = line.substring(0, index1);
                
                if(event.libelle.contains("hors cursus")) {
                    System.out.println(event.libelle);
                    continue;
                }
                
                line = line.substring(index1 + 2);
                
                index1 = line.indexOf("-") - 1;
                if(index1 >= 0) {
                    event.prof = line.substring(0, index1);
                }
                line = line.substring(index1 + 2);
                
                event.type = line;
                
                
                out.println(event);
                out.println("<BR>");
                //break;
            }
        
        } catch (ParseException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
