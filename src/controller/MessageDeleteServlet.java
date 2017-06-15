package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MessageService;

@WebServlet("/messagedelete")
public class MessageDeleteServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		 new MessageService().messageDelete(Integer.parseInt(request.getParameter("id")));
		 request.getRequestDispatcher("./").forward(request, response);

	}
}
