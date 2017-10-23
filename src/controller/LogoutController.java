package controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.util.ModelAndView;
import dao.member.MemberDAO;
import vo.member.Member;

public class LogoutController implements Controller {

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Member member = (Member)request.getSession().getAttribute("memberVO");
		String name = member.getName();
		
		request.getSession().removeAttribute("memberVO");
		request.getSession().invalidate();
		
		return new ModelAndView("index.jsp?name="+name);
	}

}