package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Branch;
import beans.Department;
import beans.User;
import service.BranchService;
import service.DepartmentService;
import service.UserService;

@WebServlet(urlPatterns = { "/administer" })
public class AdministerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<User> users = new UserService().getUsers();
		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();


		request.setAttribute("branches", branches );
		request.setAttribute("departments", departments );
		request.setAttribute("users", users);
		request.getRequestDispatcher("administer.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		if(request.getParameter("decision") == null){
			messages.add("処理が選択されていません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("administer");
			return;
		}
		UserService userService = new UserService();
		userService.Decision(Integer.parseInt(request.getParameter("id")),Integer.parseInt(request.getParameter("decision")));

		List<User> users = new UserService().getUsers();
		request.setAttribute("users", users);
		response.sendRedirect("administer");
	}
}