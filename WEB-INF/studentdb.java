import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
public class studentdb extends HttpServlet{
    int en; String pass="";
    public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
        en = Integer.parseInt(req.getParameter("enroll"));
        pass = req.getParameter("pass");
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/student_feedback", "root", "");
            PreparedStatement ps = conn.prepareStatement("SELECT password from students where roll_no=?");
            ps.setInt(1, en);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                if(pass.equals(rs.getString("password")))
                {
                    pw.println("<h2>Login Successful</h2>");
                    HttpSession hs = req.getSession();  //now student session begins
                    hs.setAttribute("enroll", en);
                    res.sendRedirect("fb.html");
                }    
                else
                {
                    pw.println("<html><head>");
                    pw.println("<meta http-equiv='refresh' content='3;URL=login.html'> </head>");
                    pw.println("<h2>Password Incorrect! Login Unsuccessful</h2>");
                }       
            }
            else
                pw.println("<h2>No student found with this enrollment number.</h2>");
            conn.close();
        }
        catch(Exception e)
        {
            pw.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }

}
