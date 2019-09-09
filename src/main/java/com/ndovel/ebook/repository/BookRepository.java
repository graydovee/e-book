package com.ndovel.ebook.repository;

import com.ndovel.ebook.model.entity.Book;
import com.ndovel.ebook.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends BaseRepository<Book, Integer>, JpaSpecificationExecutor<Integer> {

}
