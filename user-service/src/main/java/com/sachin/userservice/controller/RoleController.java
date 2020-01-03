package com.sachin.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.userservice.model.RoleModel;
import com.sachin.userservice.service.RoleService;

@RestController
@RequestMapping(value = "v1/role")
public class RoleController {

	@Autowired
	RoleService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public RoleModel getRole(@PathVariable("id") String roleId) {
		return service.getRole(Long.parseLong(roleId));
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<RoleModel> getAllRoles() {
		return service.getAllRoles();
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public RoleModel saveRole(@RequestBody RoleModel role) {
		return service.saveRole(role);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean deleteRole(@PathVariable("id") String roleId) {
		return service.deleteRole(Long.parseLong(roleId));
	}
}