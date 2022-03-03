package com.btaka.board.common.page;

import java.util.List;


public interface PageResult<T> {

	int getPage();

	int getSize();

	int getTotalPages();

	long getTotalCount();

	List<T> getList();

	boolean hasList();

}

