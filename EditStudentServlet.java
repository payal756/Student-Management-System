/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
public class EditStudentServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditStudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditStudentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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

    String idOrEmail = request.getParameter("identifier");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentMS", "root", "Manvi756@");

      String query = idOrEmail.matches("\\d+") ?
          "SELECT * FROM student WHERE id=?" :
          "SELECT * FROM student WHERE email=?";

      PreparedStatement ps = conn.prepareStatement(query);
      ps.setString(1, idOrEmail);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        out.println("<h2 style='text-align:center;'>Edit Student</h2>");
        out.println("<form method='post' action='EditStudentServlet' style='max-width:400px; margin:auto;'>");
        out.println("<input type='hidden' name='id' value='" + rs.getInt("id") + "'>");
        out.println("Name: <input type='text' name='name' value='" + rs.getString("name") + "' required><br><br>");
        out.println("Email: <input type='email' name='email' value='" + rs.getString("email") + "' required><br><br>");
        out.println("Course: <input type='text' name='course' value='" + rs.getString("course") + "' required><br><br>");
        out.println("Fee: <input type='number' name='fee' value='" + rs.getDouble("fee") + "' required><br><br>");
        out.println("<input type='submit' value='Update Student'>");
        out.println("</form>");
      } else {
        out.println("<p>Student not found.</p>");
      }

      conn.close();
    } catch (Exception e) {
      out.println("<p>Error: " + e.getMessage() + "</p>");
    }
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

    int id = Integer.parseInt(request.getParameter("id"));
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String course = request.getParameter("course");
    double fee = Double.parseDouble(request.getParameter("fee"));

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentMS", "root", "Manvi756@");

      PreparedStatement ps = conn.prepareStatement(
          "UPDATE student SET name=?, email=?, course=?, fee=? WHERE id=?");
      ps.setString(1, name);
      ps.setString(2, email);
      ps.setString(3, course);
      ps.setDouble(4, fee);
      ps.setInt(5, id);
      ps.executeUpdate();

      response.sendRedirect("ViewStudentServlet");
      conn.close();
    } catch (Exception e) {
      response.getWriter().println("Error: " + e.getMessage());
    }
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
