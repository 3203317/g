package net.foreworld.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.foreworld.model.Menu;
import net.foreworld.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Administrator
 *
 */
@Controller
public class MenuController {

	@Autowired
	private MenuService menuService;

	@RequestMapping(value = { "/manage/menu/" }, method = RequestMethod.GET)
	public String _m_indexUI(HttpSession session, Map<String, Object> map) {

		List<Menu> list = menuService.findByMenu(null, 1, Integer.MAX_VALUE);
		map.put("data_list", list);

		map.put("session_user", session.getAttribute("session.user"));
		map.put("nav_choose", ",08,0803,");
		return "m/Menu/index";
	}
}