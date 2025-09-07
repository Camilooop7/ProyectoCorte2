package co.edu.unbosque.proyectocorte2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectocorte2.service.AdminService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/admin" })
public class AdminController {
	@Autowired
	private AdminService adminService;
	

}
