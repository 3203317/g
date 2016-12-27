package net.foreworld.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.foreworld.model.Manager;
import net.foreworld.model.ResultMap;
import net.foreworld.service.ManagerService;

/**
 *
 * @author Administrator
 *
 */
@Controller
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	/**
	 * 验证令牌
	 *
	 * @param session
	 * @param token
	 * @return
	 */
	private ResultMap<Void> verifyToken(HttpSession session, String token) {

		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		Object verify = session.getAttribute("verify.token");

		if (null == verify) {
			map.setMsg("请刷新页面");
			return map;
		}

		if (verify.toString().equals(token)) {
			map.setSuccess(true);
			session.removeAttribute("verify.token");
			return map;
		}

		map.setMsg("请刷新页面");
		session.removeAttribute("verify.token");
		return map;
	}

	/**
	 * 生成令牌（4位数字）
	 *
	 * @param session
	 * @return
	 */
	private String genVerifyToken(HttpSession session) {
		int i = (int) ((Math.random() * 5 + 1) * 1000);
		session.setAttribute("verify.token", String.valueOf(i));
		return session.getAttribute("verify.token").toString();
	}

	/**
	 *
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping(value = { "/manage/user/login" }, method = RequestMethod.GET)
	public String _m_loginUI(HttpSession session, Map<String, Object> map) {
		map.put("verify_token", genVerifyToken(session));
		return "m/manager/login";
	}

	@ResponseBody
	@RequestMapping(value = { "/manage/user/login" }, method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> _m_login(HttpSession session,
			@RequestParam(required = true) String verify_token,
			@RequestParam(required = true) String user_name,
			@RequestParam(required = true) String user_pass) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);

		ResultMap<Void> verifyToken = verifyToken(session, verify_token);
		if (!verifyToken.getSuccess()) {
			result.put("msg", verifyToken.getMsg());
			return result;
		}

		ResultMap<Manager> map = managerService.login(user_name, user_pass);

		if (!map.getSuccess()) {
			result.put("msg", map.getMsg());
			return result;
		}

		// 获取用户对象
		Manager manager = map.getData();

		session.setAttribute("session.user", manager);
		session.setAttribute("session.user.id", manager.getId());
		session.setAttribute("session.user.lv", 1);
		session.setAttribute("session.time", (new Date()).toString());

		result.put("success", true);
		return result;
	}

	/**
	 * 退出
	 *
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/manage/user/logout" }, method = RequestMethod.GET)
	public String _m_logoutUI(HttpSession session) {
		session.invalidate();
		return "redirect:/manage/user/login";
	}

	/**
	 * 修改密码
	 *
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping(value = { "/manage/user/changePwd" }, method = RequestMethod.GET)
	public String _m_changePwdUI(HttpSession session, Map<String, Object> map) {
		map.put("verify_token", genVerifyToken(session));
		map.put("session_user", session.getAttribute("session.user"));
		map.put("nav_choose", ",03,0302,");
		return "m/manager/changePwd";
	}

	@ResponseBody
	@RequestMapping(value = { "/manage/user/changePwd" }, method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> _m_changePwd(HttpSession session,
			@RequestParam(required = true) String verify_token,
			@RequestParam(required = true) String old_pass,
			@RequestParam(required = true) String new_pass) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);

		ResultMap<Void> verifyToken = verifyToken(session, verify_token);
		if (!verifyToken.getSuccess()) {
			result.put("msg", verifyToken.getMsg());
			return result;
		}

		ResultMap<Void> changePwd = managerService
				.changePwd(session.getAttribute("session.user.id").toString(),
						old_pass, new_pass);

		if (!changePwd.getSuccess()) {
			result.put("msg", changePwd.getMsg());
			return result;
		}

		result.put("success", true);
		return result;
	}

}