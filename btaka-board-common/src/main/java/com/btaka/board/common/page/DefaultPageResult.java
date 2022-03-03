package com.btaka.board.common.page;

import java.util.List;

public class DefaultPageResult<T> implements PageResult<T> {
	
	private int page = 0;
	private int size = 20;
	private int totalCount = 0;
	
	private List<T> list;
	
	public DefaultPageResult(List<T> list) {
		this.list = list;
		if( list != null ) {
			this.totalCount = list.size();
		}
	}
	
	public DefaultPageResult(List<T> list, int page, int size, int totalCount ) {
		this.list = list;
		this.page = page;
		this.size = size;
		this.totalCount = totalCount;
	}

	public static DefaultPageResult of(List list) {
		return new DefaultPageResult(list);
	}

	public static DefaultPageResult of(List list, int page, int size ) {
		return new DefaultPageResult(list, page, size, list.size());
	}

	public static DefaultPageResult of(List list, int page, int size, int totalCount ) {
		return new DefaultPageResult(list, page, size, totalCount);
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getTotalPages() {
		
		if( size < 1) {
			return 0;
		}
		
		int pageCount = totalCount/size;
		
		if( totalCount % size != 0) {
			pageCount++;
		}
		
		return pageCount;
	}

	@Override
	public long getTotalCount() {
		return totalCount;
	}

	@Override
	public List<T> getList() {
		return list;
	}

	@Override
	public boolean hasList() {
		return list != null && !list.isEmpty();
	}

}
