package justdoit.todo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import justdoit.common.ejb.UserBean;
import justdoit.common.jpa.User;
import justdoit.todo.ejb.ToDoBean;
import justdoit.todo.jpa.ToDo;

@WebServlet(name = "DetailToDoServlet", urlPatterns = {"/view/todo/detail/*"})
public class DetailToDoServlet extends HttpServlet {

    @EJB
    ToDoBean toDoBean;

    @EJB
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Angeforderter ToDo ermitteln
        long id = -1;
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1]);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }
        ToDo todo = toDoBean.findById(id);
        List<User> users = userBean.getUsers(id);
        /* Zurück auf ToDo Übersicht seite wenn es keinen ToDo dieser ID gibt
         if (todo == null) {
            response.sendRedirect(request.getContextPath() + HIERÜBERSICHTSERVLET.URL);
            return;
        }
         */

        request.setAttribute("todo", todo);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/view/detailToDo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equals("edit")) {
            this.editToDo(request, response);
        } else if (action.equals("delete")) {
            this.deleteToDo(request, response);
        }
        //response.sendRedirect(request.getContextPath() + "/view/dashboard/");
    }

    private void deleteToDo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = -1;
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1]);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }

        ToDo todo = toDoBean.findById(id);
        this.toDoBean.delete(todo);
        response.sendRedirect(request.getContextPath() + "/view/dashboard/");
    }

    private void editToDo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = -1;
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1]);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }
        response.sendRedirect(request.getContextPath() + "/view/todo/edit/" + id);
        // Code folgt, der Edit jsp aufruft
    }
}
