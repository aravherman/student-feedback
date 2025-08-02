import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
public class feedbackservlet extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
        HttpSession hs = req.getSession(false);
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        if(hs== null || hs.getAttribute("enroll")==null)
        {
            res.sendRedirect("login.html");
            return;
        }
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/student_feedback", "root", "");
            //select roll_no from feedback where roll_no=? and faculty_name='';
            PreparedStatement ps = conn.prepareStatement("select roll_no from feedback where roll_no=? and faculty_name='" +  req.getParameter("faculty_name") +"'");
            ps.setInt(1, (Integer)hs.getAttribute("enroll"));
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                pw.println("<html><head>");
                pw.println("<meta http-equiv='refresh' content='3;URL=login.html'> </head><body>");
                pw.println("<h2>Feedback already submitted.</h2></body></html>");
                rs.close();
                ps.close();
                return;
            }
            else
            {
                String faculty = req.getParameter("faculty_name");
                String course = req.getParameter("course");
                int rating = Integer.parseInt(req.getParameter("rating"));
                String comments = req.getParameter("comments");

                PreparedStatement p = conn.prepareStatement("insert into feedback (roll_no,faculty_name,course,rating,comments) values(?,?,?,?,?)");
                p.setInt(1, (Integer)hs.getAttribute("enroll"));
                p.setString(2, faculty);
                p.setString(3, course);
                p.setInt(4, rating);
                p.setString(5, comments);

                int i = p.executeUpdate();
                if(i>0)
                {
                    pw.println("<html><h2>Feedback submitted successfully!</h2>");
                    pw.println("<head>");
                    pw.println("<meta http-equiv='refresh' content='1;URL=lg.html'></head></html>");
                }    
                else 
                    pw.println("<h2>Submission failed. Please try again.</h2>");
                p.close();
                conn.close();
            }
        }
        catch(Exception e){
            pw.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}
