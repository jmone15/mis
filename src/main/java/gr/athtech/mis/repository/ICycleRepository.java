/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.athtech.mis.repository;

import gr.athtech.mis.model.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jmone
 */
@Repository("iCycleRepository")
public interface ICycleRepository extends JpaRepository<Cycle, Long>{
    
    @Query("SELECT c FROM Cycle as c WHERE CURRENT_DATE >= c.startDate AND CURRENT_DATE <= c.endDate")
    Cycle getCurrentCycle();
}
