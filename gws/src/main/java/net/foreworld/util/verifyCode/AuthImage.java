package net.foreworld.util.verifyCode;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 *
 */
public class AuthImage extends HttpServlet implements Servlet {

	private static final long serialVersionUID = -7552720670363550231L;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 生成随机字串，4位
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
		// 存入会话session
		HttpSession session = request.getSession(true);
		session.setAttribute("verify.imgCode", verifyCode.toLowerCase());
		// 生成图片
		int w = 200, h = 80;
		VerifyCodeUtil
				.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
}