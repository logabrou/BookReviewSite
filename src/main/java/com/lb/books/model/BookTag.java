package com.lb.books.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookTag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bookTag_id")
	private Long id;

	private String phrase;

	@ManyToMany(mappedBy = "bookTags")
	private List<Book> books;
}