package com.si.siassignment.repository;

import com.si.siassignment.models.Clipboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClipboardRepository extends CrudRepository<Clipboard, Integer> {

    public Clipboard findByTinyURL(String tinyURL);

    @Query(value = "select count(*) FROM clipboard;", nativeQuery = true)
    public Integer getCount();
}

