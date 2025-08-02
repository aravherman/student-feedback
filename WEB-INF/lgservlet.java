import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class lgservlet extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
            HttpSession hs = req.getSession(false);
            hs.invalidate();
            res.sendRedirect("login.html");
    }
}
