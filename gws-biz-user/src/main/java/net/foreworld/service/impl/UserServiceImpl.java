package net.foreworld.service.impl;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
import net.foreworld.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author huangxin
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseService<User> implements UserService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Override
	public ResultMap<String> logout(String user_id) {

		ResultMap<String> map = new ResultMap<String>();
		map.setSuccess(true);
		return map;
	}

}
