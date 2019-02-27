package justdoit.servlet;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.FormatUtils;
import justdoit.common.ValidationBean;
import justdoit.exceptions.EntityAlreadyExistsException;
import justdoit.task.bean.CategoryBean;
import justdoit.task.bean.ToDoBean;
import justdoit.task.entitiy.Category;
import justdoit.task.entitiy.CategoryId;
import justdoit.task.entitiy.ToDo;
import justdoit.task.entitiy.ToDoPriority;
import justdoit.task.entitiy.ToDoStatus;
import justdoit.user.User;
import justdoit.user.UserBean;

@WebServlet(name = "CreateToDoServlet", urlPatterns = {"/view/todo/create/"})
public class CreateToDoServlet extends HttpServlet {

    @EJB
    CategoryBean categoryBean;

    @EJB
    ToDoBean toDoBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<User> users = this.userBean.findAll();
        session.setAttribute("users", users);

        List<Category> categories = this.categoryBean.findByUser(this.userBean.getCurrentUser());
        session.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/createToDo.jsp");
        dispatcher.forward(request, response);

        session.removeAttribute("todo_form");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> user = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        HttpSession session = request.getSession();

        User todoUser = this.userBean.findByUsername(request.getParameter("todo_user"));
        user.add(todoUser);

        CategoryId id = new CategoryId(request.getParameter("todo_user"), request.getParameter("todo_category"));
        Category todoCategory = this.categoryBean.findById(id);
        if (todoUser != this.userBean.getCurrentUser() && todoCategory == null) {
            try {
                this.categoryBean.saveNew(todoCategory, id);
            } catch (EJBException ex) {
                if(ex.getCausedByException() instanceof EntityAlreadyExistsException) {
                    errors.add("Das ToDo kann dem Benutzer $user nicht unter der Kategorie $category zugewiesen werden"
                               .replace("$user", todoUser.getUsername())
                               .replace("$category", todoCategory.getCategoryName()));
                }
            }
        }
        //Parse Date
        Date dueDate = FormatUtils.parseDate(request.getParameter("todo_due_date"));
        //Parse Time
        Time dueTime = FormatUtils.parseTime(request.getParameter("todo_due_time"));

        //Check ToDo
        ToDo todo = new ToDo(request.getParameter("todo_title"),
                todoCategory, //request.getParameter("todo_category"),
                request.getParameter("todo_description"),
                ToDoStatus.OPEN, ToDoPriority.URGENT,
                dueDate,
                dueTime,
                user);
        errors = this.validationBean.validate(todo, errors);

        if (!errors.isEmpty()) {
            Form form = new Form();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);
            session.setAttribute("todo_form", form);

            response.sendRedirect(request.getRequestURI());
        } else {
            this.toDoBean.saveNew(todo, todo.getId());
            response.sendRedirect(request.getContextPath() + "/view/dashboard/");
        }
    }
}
