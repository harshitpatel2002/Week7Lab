package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

/**
 *
 * @author hsp28
 */
public class UserServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
//        Whenever someone visits the page all the users in the database should be shown
        
        UserService us = new UserService();
        
        try
        {
            List<User> users = us.getAll();
            request.setAttribute("users", users);
        } catch (Exception ex)
        {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
            .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        UserService us = new UserService();
        RoleService rs = new RoleService();
        try
        {
            if (request.getParameter("action").equals("add"))
                addUser(request, response, us, rs);
            
            if (request.getParameter("action").equals("edit"))
                editUser(request, response, us, rs);
            
            if (request.getParameter("action").equals("delete"))
                deleteUser(request, response, us, rs);
        } catch (Exception ex)
        {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void addUser(HttpServletRequest request, HttpServletResponse response, UserService us, RoleService rs)
            throws ServletException, IOException, Exception
    {
        // Taking all the inputs and Adding the user
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        if (email.equals("") || firstname.equals("") || lastname.equals("") || password.equals("") || role.equals(""))
        {
            request.setAttribute("empty", "Please fill out all the fields");
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
            return;
        }
        
        Role role2 = rs.getRoleDB(role);
        
        us.insert(email, firstname, lastname, password, role2);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
    }
    
    protected void editUser(HttpServletRequest request, HttpServletResponse response, UserService us, RoleService rs)
            throws ServletException, IOException, Exception
    {
//        When the user clicks on edit I have to copy the pre added data and copy to the inputs
//        password
        
        
        
        
        
//       Taking all the inputs and Adding the edited information
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        if (firstname.equals("") || lastname.equals("") || password.equals("") || role.equals(""))
        {
            request.setAttribute("empty", "Please fill out all the fields");
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
            return;
        }
        
        Role role2 = rs.getRoleDB(role);
       
        us.update("", firstname, lastname, password, role2);//Add email parameter
        request.setAttribute("action", "add");
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
    }
    
    protected void deleteUser(HttpServletRequest request, HttpServletResponse response, UserService us, RoleService rs)
            throws ServletException, IOException, Exception
    {
        User user = new User();//Not proper
        us.delete(user);//Add email parameter
        request.setAttribute("action", "add");
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
    }
}
