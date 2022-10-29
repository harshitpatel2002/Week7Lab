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
        try
        {
            showUsers(request, response);
            String action = request.getParameter("action");
            if (action != null && action.equals("delete"))
                doPost(request, response);
        } catch (Exception ex)
        {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Data not found in the database");
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
        
        String action = request.getParameter("action");
        
        try
        {
            switch (action)
            {
                case "add":
                    addUser(request, response, us, rs);
                    break;
                case "edit":
                    editUser(request, response, us, rs);
                    break;
                case "delete":
                    deleteUser(request, response, us, rs);
                    break;
                default:
                    break;
            }
        } catch (Exception ex)
        {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void showUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception
    {
        UserService us = new UserService();
        List<User> users = us.getAll();
        if (users.isEmpty())
            request.setAttribute("zero", "No users exist in the database");
        else
            request.setAttribute("users", users);
    }
    
    protected void addUser(HttpServletRequest request, HttpServletResponse response, UserService us, RoleService rs)
            throws ServletException, IOException, Exception
    {
        // Taking all the inputs and Adding the user
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String role_name = request.getParameter("role");
        
        if (email == null || firstname == null || lastname == null || password == null || role_name == null || email.equals("") || firstname.equals("") || lastname.equals("") || password.equals("") || role_name.equals(""))
        {
            request.setAttribute("emptyInput", "Please fill out all the fields");
            showUsers(request, response);
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
            .forward(request, response);
            return;
        }
        
        int role_id = rs.getRoleId(role_name);
        Role role2 = new Role(role_id, role_name);
        
        
        us.insert(email, firstname, lastname, password, role2);
        request.setAttribute("success", "User has been added successfully!");
        showUsers(request, response);
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
            request.setAttribute("emptyInput", "Please fill out all the fields");
            showUsers(request, response);
            return;
        }
        
        int role_id = rs.getRoleId(role);
        Role role2 = new Role(role_id, role);
       
        us.update("", firstname, lastname, password, role2);//Add email parameter
        request.setAttribute("action", "add");
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
    }
    
    protected void deleteUser(HttpServletRequest request, HttpServletResponse response, UserService us, RoleService rs)
            throws ServletException, IOException, Exception
    {
        String email = request.getParameter("email");
        email = email.replace(" ", "+");
        us.delete(email);
        request.setAttribute("success", "Deleted the user successfully!");
        showUsers(request, response);
    }
}
