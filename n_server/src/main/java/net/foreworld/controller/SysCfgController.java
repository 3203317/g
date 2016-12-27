package net.foreworld.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.foreworld.model.ResultMap;
import net.foreworld.model.SysCfg;
import net.foreworld.service.SysCfgService;

/**
 *
 * @author Administrator
 *
 */
@Controller
public class SysCfgController {

	@Autowired
	private SysCfgService roleService;

	@RequestMapping(value = { "/manage/settings/" }, method = RequestMethod.GET)
	public String _m_indexUI(HttpSession session, Map<String, Object> map) {

		List<SysCfg> list = roleService
				.findBySysCfg(null, 1, Integer.MAX_VALUE);
		map.put("data_list", list);

		map.put("session_user", session.getAttribute("session.user"));
		map.put("nav_choose", ",08,0801,");
		return "m/settings/index";
	}

	@ResponseBody
	@RequestMapping(value = { "/manage/settings/edit" }, method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> _i_edit(HttpSession session, SysCfg sysCfg) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);

		ResultMap<Void> edit = roleService.editInfo(sysCfg);

		if (!edit.getSuccess()) {
			result.put("msg", edit.getMsg());
			return result;
		}

		result.put("success", true);
		return result;
	}
}