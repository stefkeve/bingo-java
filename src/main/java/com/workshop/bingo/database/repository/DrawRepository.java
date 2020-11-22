package com.workshop.bingo.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import com.workshop.bingo.database.entity.Draw;

import java.util.List;



public interface DrawRepository extends CrudRepository<Draw, Integer> {
    @Query("SELECT d from Draw d where d.drawDt > CURRENT_TIMESTAMP() and d.status = 0 ORDER BY d.id ASC")
    List<Draw> findNextDraw();

    Draw findFirstByOrderByIdDesc();

    Draw findTop1ByStatusOrderByIdDesc(Integer status);
}