package com.lb.books.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.lb.books.model.BookTag;

@Repository
public interface BookTagRepository extends CrudRepository<BookTag, Long> {

	public BookTag findByPhrase(String phrase);
}