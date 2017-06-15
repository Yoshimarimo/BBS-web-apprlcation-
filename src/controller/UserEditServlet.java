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

@WebServlet("/useredit")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		String que = request.getQueryString();

		if(!que.matches("id=\\d*") || que.matches("") ){
			messages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("administer");
			return;
		}
		if(request.getParameter("id") == ""){
			messages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("administer");
			return;
		}
		if(UserService.userExistence(Integer.parseInt(request.getParameter("id"))) != true){
			messages.add("指定されたIDに該当する情報はありません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("administer");
			return;
		}

		User user = new UserService().getPersonalDatas(Integer.parseInt(request.getParameter("id")));


		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();


		request.setAttribute("user", user);
		request.setAttribute("branches", branches );
		request.setAttribute("departments", departments );

		request.getRequestDispatcher("useredit.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		User user = new User();
		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();


		user.setId(Integer.parseInt(request.getParameter("id")));
		user.setLoginId(request.getParameter("loginId"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		user.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
		if(StringUtils.isEmpty(request.getParameter("password")) != true ){
			user.setPassword(request.getParameter("password"));
		}

		if (isValid(request, messages) == true) {

			new UserService().update(user);
			response.sendRedirect("administer");
			return;
		}
		else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("user", user);
			request.setAttribute("branches", branches );
			request.setAttribute("departments", departments );
			request.getRequestDispatcher("useredit.jsp").forward(request, response);
			return;
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		String passwordConfirmation = request.getParameter("passwordConfirmation");
		int branchId = (Integer.parseInt(request.getParameter("branchId")));
		int departmentId = (Integer.parseInt(request.getParameter("departmentId")));


		User isLoginId = new UserService().getLoginId(loginId);
		System.out.println(id);

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}else if (!loginId.matches("\\w*") == true){
			messages.add("ログインIDは半角英数字[0～9,a～z,A～Z]で入力してください");
		}else if (loginId.length() < 6 || 20 < loginId.length()){
				messages.add("ログインIDは6文字以上,20文字以下で設定してください");
		}else if (isLoginId != null && isLoginId.getId() != id ){
			messages.add("指定されたログインIDは既に使用されています");
		}

		if (0 < password.length() && password.length() < 6){
			messages.add("パスワードは6文字以上で入力してください");
		}else if (password.length() > 255){
			messages.add("パスワードは255文字以下でお願いします");
		}else if (!passwordConfirmation.equals(password)){
			messages.add("パスワード（確認用）が正しくありません");
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
