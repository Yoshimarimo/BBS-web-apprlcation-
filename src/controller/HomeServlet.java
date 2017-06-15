package controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import beans.Message;
import beans.UserComment;
import beans.UserMessage;
import service.CommentService;
import service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		List<UserMessage> usermessages;
		List<UserComment> comments;
		List<Message> categoris;

		String min = null;
		String max = null;


		String stratnarrow = request.getParameter("stratnarrow");
		String finishnarrow = request.getParameter("finishnarrow");
		String category = request.getParameter("category");

		usermessages = new MessageService().getUserMessages();

		if(!StringUtils.isEmpty(stratnarrow) || !StringUtils.isEmpty(finishnarrow) || !StringUtils.isEmpty(category)){
			if(!StringUtils.isEmpty(stratnarrow) && !StringUtils.isEmpty(finishnarrow)){
				if((stratnarrow).compareTo(finishnarrow) == -1){
					min = stratnarrow;
					max = finishnarrow;
				}
				else{
					min = finishnarrow;
					max = stratnarrow;
				}
				request.setAttribute("min", min);
				request.setAttribute("max", max);
			}
			else if(StringUtils.isEmpty(stratnarrow) && StringUtils.isEmpty(finishnarrow)){
				String createdAt = new MessageService().getMostOldDate();
				min = String.valueOf(createdAt);
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				max = sdf.format(c.getTime());
			}
			else if(StringUtils.isEmpty(stratnarrow)){
				String createdAt = new MessageService().getMostOldDate();
				min = String.valueOf(createdAt);
				max = finishnarrow;
				request.setAttribute("max", max);

			}
			else if(StringUtils.isEmpty(finishnarrow)){
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				max = sdf.format(c.getTime());
				min = stratnarrow;
				request.setAttribute("min", min);
			}

			if(StringUtils.isEmpty(category)){
				category = null;
			}else{
				request.setAttribute("isCategory", category);
			}

			max += " 23:59:59";
			usermessages = new MessageService().getNarrowMessage(min, max, category);
		}

		comments = new CommentService().getUserComment();
		categoris = new MessageService().getCategory();
		request.setAttribute("comments", comments);
		request.setAttribute("usermessages", usermessages);
		request.setAttribute("categoris", categoris);
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}
}
