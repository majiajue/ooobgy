package com.ooobgy.ifnote.dbctrler.dao;

import com.ooobgy.ifnote.entity.St_Fund;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public interface St_FundDao extends TDataDao<St_Fund, Integer> {
	public St_Fund findWithCode(Integer code);
}
