package com.si.siassignment.repository;

import com.si.siassignment.models.ClipboardStats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClipboardStatsRepository extends CrudRepository<ClipboardStats, Integer> {

    @Query(value = "select s.* from clipboard_stats s join clipboard c on c.id = s.clipboard_id where c.tinyurl = ?1", nativeQuery = true)
    //@Query(value = "select s from clipboardstats s join clipboard c on c.id = s.clipboard_id where c.tinyurl = ?1")
    public ClipboardStats findStatsByTinyURL(String tinyURL);
}
