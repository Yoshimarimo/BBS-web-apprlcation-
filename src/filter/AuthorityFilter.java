package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;

@WebFilter({"/administer", "/useredit", "/signup"} )
public class AuthorityFilter implements Filter{
	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

		HttpSession session = req.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int branchId = 0;
		int depertmentId = 0;
		if(loginUser == null){
			res.sendRedirect("login");
			return;
		}
		branchId = loginUser.getBranchId();
		depertmentId = loginUser.getDepartmentId();
		if (branchId > 1){
			messages.add("このページは存在しません");
			session.setAttribute("errorMessages", messages);
			res.sendRedirect("./");
			return;
		}
		else if(depertmentId > 1){
			messages.add("このページは存在しません");
			session.setAttribute("errorMessages", messages);
			res.sendRedirect("./");
			return;
		}

		chain.doFilter(request, response);

		System.out.println("AuthorityFilter# chain.doFilterが実行されました。");
	}

	@Override
	public void init(FilterConfig filterConfig)    throws ServletException{

	}
}
