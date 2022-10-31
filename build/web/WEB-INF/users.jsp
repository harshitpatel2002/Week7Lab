<%-- 
    Document   : users
    Created on : 21-Oct-2022, 10:07:51 AM
    Author     : hsp28
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User page</title>
    </head>
    <body>
        <h1>Manage Users</h1>
        <p style="color: green">${success}</p>
        <c:if test="${message eq 'error'}"><b>Problem in finding the users</b></c:if>
        <c:if test="${zero ne null}"><b>No users found. Add below</b></c:if>
        <c:if test="${zero eq null}">
            <table border="1">
                <tr>
                    <td>Email</td>
                    <td>First Name</td>
                    <td>Last Name</td>
                    <td>Role</td>
                    <td></td>
                    <td></td>
                </tr>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.email}</td>
                        <td>${user.firstname}</td>
                        <td>${user.lastname}</td>
                        <td>${user.getRole().getName()}</td>
                        <td><a href="users?action=edit&amp;email=${user.email}">Edit</a></td>
                        <td><a href="users?action=delete&amp;email=${user.email}">Delete</a></td>
                    </tr>
                </c:forEach> 
            </table>
        </c:if>
        
        <c:if test="${action eq 'add'}">
            <h1>Add User</h1>
            <form action="users" method="post">
                Email: <input type="text" value="" name="email"><br><br>
                First name: <input type="text" value="" name="firstname"><br><br>
                Last name: <input type="text" value="" name="lastname"><br><br>
                Password: <input type="password" value="" name="password"><br><br>
                Role: 
                <select name="role" id="role">
                    <option value="system admin">system admin</option>
                    <option value="regular user">regular user</option>
                </select> 
                <br><br>
                <input type="hidden" name="action" value="add">
                <input type="submit" class="button" value="Add user">
            </form>
        </c:if>


        <c:if test="${action eq 'edit'}">
            <h1>Edit User</h1>
            <form action="users" method="post">
                Email: <input type="text" name="email" value="${editUser.email}" readonly=""><br><br>
                First name: <input type="text" name="firstname" value="${editUser.firstname}"><br><br>
                Last name: <input type="text" name="lastname" value="${editUser.lastname}"><br><br>
                Password: <input type="password" name="password" value=""><br><br>
                Role: 
                <select name="role" id="role">
                    <c:if test="${editUser.role.name eq 'system admin'}">
                        <option value="system admin" selected="selected">system admin</option>
                        <option value="regular user">regular user</option>
                    </c:if>
                    <c:if test="${editUser.role.name eq 'regular user'}">
                        <option value="regular user" selected="selected">regular user</option>
                        <option value="system admin">system admin</option>
                    </c:if>
                </select> 
                <br><br>
                <input type="submit" class="button" value="Update" name="action">
                <input type="submit" class="button" value="Cancel" name="action">
            </form>
        </c:if>
        <p style="color: red">${emptyInput}</p>
    </body>
</html>
