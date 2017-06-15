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

import org.apache.commons.lang.StringUtils;

import beans.Branch;
import beans.Department;
import beans.User;
import service.BranchService;
import service.DepartmentService;
import service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();

		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();

		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();

		User user = new User();
		user.setLoginId(request.getParameter("loginId"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		user.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));

		HttpSession session = request.getSession();
		if (isValid(request, messages) == true) {

			new UserService().register(user);
			response.sendRedirect("administer");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("branches", branches);
			request.setAttribute("departments", departments);
			request.setAttribute("user", user);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}
	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String passwordConfirmation = request.getParameter("passwordConfirmation");
		String name = request.getParameter("name");
		int branchId = (Integer.parseInt(request.getParameter("branchId")));
		int departmentId = (Integer.parseInt(request.getParameter("departmentId")));


		User isLoginId = new UserService().getLoginId(loginId);

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}else if (!loginId.matches("\\w*") == true){
			messages.add("ログインIDは半角英数字[0～9,a～z,A～Z]で入力してください");
		}else if (loginId.length() < 6 || 20 < loginId.length()){
				messages.add("ログインIDは6文字以上,20文字以下で設定してください");
		}else if (isLoginId != null){
			messages.add("指定されたログインIDは既に使用されています");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}else if (!passwordConfirmation.equals(password)){
			messages.add("パスワード（確認用）が正しくありません");
		}else if (password.length() < 6 || password.length() > 255){
			messages.add("パスワードは6文字以上,255文字以下で設定してください");
		}
		if(StringUtils.isEmpty(name) == true){
			messages.add("名前を入力してください");
		}
		if (branchId == 1 && departmentId == 3){
			messages.add("支店と役職が正しくありません");
		}
		if (branchId == 1 && departmentId == 4){
			messages.add("支店と役職が正しくありません");
		}
		if (branchId != 1 && departmentId == 1){
			messages.add("支店と役職が正しくありません");
		}
		if (branchId != 1 && departmentId == 2){
			messages.add("支店と役職が正しくありません");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
