import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class AddStudentServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddStudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddStudentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");
        double fee = Double.parseDouble(request.getParameter("fee"));

        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentMS", "root", "Manvi756@");

        PreparedStatement ps = conn.prepareStatement(
          "INSERT INTO student(name, email, course, fee) VALUES (?, ?, ?, ?)");
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, course);
        ps.setDouble(4, fee);
        ps.executeUpdate();
        response.sendRedirect("ViewStudentServlet"); // or a success page
        conn.close();
        } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().println("Error: " + e.getMessage());
        }
    }
        @Override
        public String getServletInfo() {
            return "Short description";
        }// </editor-fold>

}
