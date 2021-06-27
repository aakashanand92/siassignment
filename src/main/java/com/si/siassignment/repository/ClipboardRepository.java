package com.si.siassignment.repository;

import com.si.siassignment.models.Clipboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClipboardRepository extends CrudRepository<Clipboard, Integer> {

    @Query(value = "select * from clipboard where tinyurl = ?1 and expires_at >= CURDATE();", nativeQuery = true)
    public Clipboard findByTinyURL(String tinyURL);

    @Query(value = "select count(*) FROM clipboard;", nativeQuery = true)
    public Integer getCount();
}

