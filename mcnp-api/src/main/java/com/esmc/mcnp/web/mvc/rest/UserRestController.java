package com.esmc.mcnp.web.mvc.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esmc.mcnp.model.security.EuUser;
import com.esmc.mcnp.services.security.UserService;
import com.esmc.mcnp.web.mvc.utils.BaseRestController;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController extends BaseRestController{

	@Inject
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public List<EuUser> fetchUsers() {
		return userService.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createUser(
			@RequestBody EuUser user) {
		try {
			EuUser s_user = userService.saveUser(user,
					user.getHashedPassword());
			Map<String, Object> map = new HashMap<>();
			map.put("user", s_user);
			return new ResponseEntity<>(map,
					HttpStatus.CREATED);
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<>();
			map.put("error", e.getMessage());
			return new ResponseEntity<>(map,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="/{userId}", method=RequestMethod.GET)
	public EuUser getUser(@PathVariable("userId") int userId) {
		return userService.findById((long) userId);
	}

	@RequestMapping(method=RequestMethod.PUT)
	public EuUser updateUser(@RequestBody EuUser user) {
		return userService.updateUser(user);
	}

	@RequestMapping(value="/{userId}", method=RequestMethod.DELETE)
	public void deleteUser(@PathVariable("userId") int userId) {
		userService.removeById((long) userId);
	}

}
