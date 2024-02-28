package com.example.EpamSpringBoot.interfaces;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService<T> {

	List<T> readAll();

	T readById(Long id);

	T create(T createRequest);

	T update(T updateRequest);

	boolean deleteById(Long id);

}
