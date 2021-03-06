/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.athtech.mis.repository;

import gr.athtech.mis.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author JurgenPC
 */
@Repository("iRoleRepository")
public interface IRoleRepository extends JpaRepository<Role, Long>{
    
    public Role findByName(String name);
}
