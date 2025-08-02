
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ViewStudentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head><meta charset='UTF-8'>");
        out.println("<title>View Students</title>");
        out.println("<style>");
        out.println("header { background-color: #0020ff;padding: 30px; color: white;text-align: center;}");
        out.println("body { font-family: Arial; margin: 40px; background: #f2f2f2; }");
        out.println("table { width: 90%; margin: auto; border-collapse: collapse; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println("th, td { padding: 12px 15px; text-align: center; border-bottom: 1px solid #ddd; }");
        out.println("th { background-color: #007bff; color: white; }");
        out.println("tr:hover { background-color: #f1f1f1; }");
        
        out.println("</style></head><body>");
        
        out.println("<header>");
        out.println("<h2>Students List</h2>");
        out.println("<div style='text-align:center; margin-bottom: 20px;'>");
out.println("<a href='index.html' style='padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px;'>Back to Dashboard</a>");
out.println("</div>");

        out.println("</header>");
        out.println("<br><br>");
        out.println("<table><thead><tr><th>Roll No</th><th>Name</th><th>Email</th><th>Course</th></tr></thead><tbody>");
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/StudentMS", "root", "Manvi756@"
            );

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String course = rs.getString("course");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + course + "</td>");
                out.println("</tr>");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace(out);
        }

        out.println("</tbody></table></body></html>");
    }
}
