package com.bookexchange.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookexchange.app.model.Books;


@Repository
public interface BookRepository extends JpaRepository<Books, Long>{

}
