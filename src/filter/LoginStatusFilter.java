package filter;
import java.io.IOException;

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


@WebFilter("/*")
public class LoginStatusFilter implements Filter{

	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

		String contextPath = req.getContextPath();
		String uri = req.getRequestURI();

		if (uri.equals(contextPath + "/login.jsp") || uri.equals(contextPath + "/login") || uri.matches(".*/css.*")) {
			chain.doFilter(request, response);
		}
		else{
			HttpSession session = req.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null){
				res.sendRedirect("login");
				return;
			}
			else{
				chain.doFilter(request, response);
			}
		}

	System.out.println("LoginStatusFilterFilter# chain.doFilterが実行されました。");
	}

	@Override
	public void init(FilterConfig filterConfig)    throws ServletException{

	}
}
