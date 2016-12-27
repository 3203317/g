package net.foreworld.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.foreworld.model.Role;
import net.foreworld.service.RoleService;

/**
 *
 * @author Administrator
 *
 */
@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = { "/manage/role/" }, method = RequestMethod.GET)
	public String _m_indexUI(HttpSession session, Map<String, Object> map) {

		List<Role> list = roleService.findByRole(null, 1, Integer.MAX_VALUE);
		map.put("data_list", list);

		map.put("session_user", session.getAttribute("session.user"));
		map.put("nav_choose", ",08,0802,");
		return "m/role/index";
	}
}