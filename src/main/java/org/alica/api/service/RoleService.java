package org.alica.api.service;

import org.alica.api.Dao.Role;

public interface RoleService {

    Role findByName(String name);
}
