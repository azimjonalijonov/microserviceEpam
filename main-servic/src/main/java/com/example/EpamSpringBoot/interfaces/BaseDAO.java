package com.example.EpamSpringBoot.interfaces;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseDAO<T> {

	List<T> readAll();

	T readById(Long id);

	T createOrUpdate(T entity);

	boolean deleteById(Long id);

	boolean existById(Long id);

}
