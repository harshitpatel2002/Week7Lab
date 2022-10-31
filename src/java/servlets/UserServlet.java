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
    public User updateUserIns = null;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            showUsers(request, response);
            String action = request.getParameter("action");
            request.setAttribute("action", action);
            if (action == null)
                request.setAttribute("action", "add");
            else if (action.equals("delete") || action.equals("edit"))
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
                case "Update":
                    updateUser(request, response, us, rs);
                    break;
                case "Cancel":
                    cancelUser(request, response, us, rs);
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
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String role_name = request.getParameter("role");
        
        if (email == null || firstname == null || lastname == null || password == null || role_name == null || email.equals("") || firstname.equals("") || lastname.equals("") || password.equals("") || role_name.equals(""))
        {
            request.setAttribute("emptyInput", "Please fill out all the fields");
            request.setAttribute("action", "add");
            showUsers(request, response);
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
            .forward(request, response);
            return;
        }
        
        int role_id = rs.getRoleId(role_name);
        Role role2 = new Role(role_id, role_name);
        
        
        us.insert(email, firstname, lastname, password, role2);
        request.setAttribute("success", "User has been added successfully!");
        request.setAttribute("action", "add");
        showUsers(request, response);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
            .forward(request, response);
    }
    
    protected void editUser(HttpServletRequest request, HttpServletResponse response, UserService us, RoleService rs)
            throws ServletException, IOException, Exception
    {
        String email = request.getParameter("email");
        email = email.replace(" ", "+");
        User user = us.get(email);
        
        request.setAttribute("editUser", user);
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
    }
    
    protected void updateUser(HttpServletRequest request, HttpServletResponse response, UserService us, RoleService rs)
            throws ServletException, IOException, Exception
    {
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String role_name = request.getParameter("role");
        
        if (email == null || firstname == null || lastname == null || password == null || role_name == null || email.equals("") || firstname.equals("") || lastname.equals("") || password.equals("") || role_name.equals(""))
        {
            request.setAttribute("emptyInput", "Please fill out all the fields");
            request.setAttribute("action", "edit");
            showUsers(request, response);
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
            .forward(request, response);
            return;
        }
        
        int role_id = rs.getRoleId(role_name);
        Role role2 = new Role(role_id, role_name);
        
        updateUserIns = new User(email, firstname, lastname, password, role2);
        
        // IMP: Program will only update the user if the password is correct
        String dbPassword = us.getPassword(email);
        
        if (!password.equals(dbPassword)){
            request.setAttribute("emptyInput", "Please enter the correct password");
            request.setAttribute("action", "edit");
            showUsers(request, response);
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
            .forward(request, response);
            return;
        }
        
        us.update(updateUserIns);
        request.setAttribute("success", "Updated the user information having email " +updateUserIns.getEmail());
        request.setAttribute("action", "add");
        showUsers(request, response);
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
        request.setAttribute("action", "add");
        showUsers(request, response);
    }
    
        protected void cancelUser(HttpServletRequest request, HttpServletResponse response, UserService us, RoleService rs)
            throws ServletException, IOException, Exception
    {
        request.setAttribute("action", "add");
        showUsers(request, response);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
    }
}
