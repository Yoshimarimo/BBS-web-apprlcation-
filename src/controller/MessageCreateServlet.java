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

import beans.Message;
import beans.User;
import service.MessageService;

@WebServlet(urlPatterns = { "/messagecreate" })
public class MessageCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Message> categoris = new MessageService().getCategory();
		request.setAttribute("categoris", categoris);

		request.getRequestDispatcher("messagecreate.jsp").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		List<Message> categoris = new MessageService().getCategory();

		User user = (User) session.getAttribute("loginUser");
		Message isMessage = new Message();

		isMessage.setSubject(request.getParameter("subject"));
		isMessage.setText(request.getParameter("text"));
		isMessage.setCategory(request.getParameter("category"));
		isMessage.setUserId(user.getId());

		if (isValid(request, messages) == true) {

			new MessageService().register(isMessage);
			response.sendRedirect("./");
		}
		else{
			session.setAttribute("errorMessages", messages);
			request.setAttribute("isMessage", isMessage);
			request.setAttribute("categoris", categoris);
			request.getRequestDispatcher("messagecreate.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String subject = request.getParameter("subject");
		String text = request.getParameter("text");
		String category = request.getParameter("category");

		if (StringUtils.isEmpty(subject) == true) {
			messages.add("件名を入力してください");
		}
		else if (StringUtils.isBlank(subject)){
			messages.add("件名：空白のみの入力はできません");
		}
		if (StringUtils.isEmpty(text) == true) {
			messages.add("本文を入力してください");
		}
		else if (StringUtils.isBlank(text)){
			messages.add("本文：空白のみの入力はできません");
		}
		if (StringUtils.isEmpty(category) == true) {
			messages.add("カテゴリーを入力してください");
		}
		else if (StringUtils.isBlank(category)){
			messages.add("カテゴリー：空白のみの入力はできません");
		}
		if (50 < subject.length()) {
			messages.add("件名は50文字以下で入力してください");
		}
		if (1000 < text.length()) {
			messages.add("本文は1000文字以下で入力してください");
		}
		if (10 < category.length()) {
			messages.add("カテゴリーは10文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
