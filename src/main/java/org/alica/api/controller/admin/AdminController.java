package org.alica.api.controller.admin;


import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/")
@RolesAllowed("ADMIN")
public class AdminController {

   // to implements

}
