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
import justdoit.comment.ejb.CommentBean;
import justdoit.comment.jpa.Comment;
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

    @EJB
    CommentBean commentBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        User currentUser = this.userBean.getCurrentUser();

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
        List<User> users = todo.getUser();
        List<Comment> comments = commentBean.findByToDoId(id);
        // Zurück auf ToDo Übersicht seite wenn es keinen ToDo dieser ID gibt
        if (todo == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }
        //Wenn der aktuelle User nicht in den Benutzer des ToDos vorkommt, hat er keine Anzeigerechte
        if (!users.contains(currentUser)) {
            response.sendRedirect(request.getContextPath() + "/403");
            return;
        }

        request.setAttribute("todo", todo);
        request.setAttribute("users", users);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("/WEB-INF/view/detailToDo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action.equals("edit")) {
            this.editToDo(request, response);
        } else if (action.equals("delete")) {
            this.deleteToDo(request, response);
        } else if (action.equals("comment")) {
            this.addComment(request, response);
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
    }

    private void addComment(HttpServletRequest request, HttpServletResponse response)
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
        User currentUser = this.userBean.getCurrentUser();
        ToDo todo = toDoBean.findById(id);
        String text = request.getParameter("todo_comment");
        Comment comment = new Comment(currentUser, todo, text);

        this.commentBean.saveNew(comment, id);
        response.sendRedirect(request.getRequestURI());
    }
}
