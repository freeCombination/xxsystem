package com.xx.grade.personal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xx.grade.personal.entity.IndexTypeRoleWeight;
import com.xx.grade.personal.entity.PersonalWeight;
import com.xx.grade.personal.service.IPersonalWeightService;
import com.xx.grade.personal.vo.IndexTypeRoleWeightVo;
import com.xx.grade.personal.vo.PersonalWeightVo;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.vo.ListVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.role.entity.Role;

/**
 * 个人评分权重 接口实现类
 * 
 * @author wujialing
 */
@Service("personalWeightService")
public class PersonalWeightServiceImpl implements IPersonalWeightService {
	
	@Autowired
	@Qualifier("baseDao")
	private IBaseDao baseDao ;

	@Override
	@SuppressWarnings("unchecked")
	public ListVo<PersonalWeightVo> getPersonalWeightList(
			Map<String, String> paramMap) {
		ListVo<PersonalWeightVo> result = new ListVo<PersonalWeightVo>();
		List<PersonalWeightVo> list = new ArrayList<PersonalWeightVo>(); 
		int totalSize = 0 ;
        int start = NumberUtils.toInt(paramMap.get("start"));
        int limit = NumberUtils.toInt(paramMap.get("limit"));
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		hql.append(" From PersonalWeight pg where 1=1  ");
		counthql.append(" select count(*) From PersonalWeight pg where 1=1 ");
		totalSize =  baseDao.getTotalCount(counthql.toString(), new HashMap<String, Object>());
		List<PersonalWeight> personalWeightLists =  (List<PersonalWeight>)baseDao.queryEntitysByPage(start, limit, hql.toString(),new HashMap<String, Object>());
		for (PersonalWeight weight : personalWeightLists) {
			PersonalWeightVo vo = new PersonalWeightVo();
			buildEntityToVo(weight,vo);
			list.add(vo);
		}
		result.setList(list);
		result.setTotalSize(totalSize);
		return result;
	}


	@Override
	public void addPersonalWeigh(PersonalWeightVo vo) {
		if (vo != null) {
			PersonalWeight weight = new PersonalWeight();
			buildVoToEntity(vo,weight);
			baseDao.save(weight);
			//角色与评分指标关系
			if (vo.getRwList() != null && vo.getRwList().size() > 0) {
				List<IndexTypeRoleWeightVo> list = vo.getRwList();
				for (IndexTypeRoleWeightVo indexTypeRoleWeightVo : list) {
					IndexTypeRoleWeight roleWeight = new IndexTypeRoleWeight();
					roleWeight.setPercentage(indexTypeRoleWeightVo.getPercentage());
					roleWeight.setPersonalWeight(weight);
					if (indexTypeRoleWeightVo.getRoleId() > 0) {
						Role role = (Role)baseDao.queryEntityById(Role.class, indexTypeRoleWeightVo.getRoleId());
						roleWeight.setRole(role);
					}
					baseDao.save(roleWeight);
				}
			}
		}
	}

	@Override
	public void updatePersonalWeigh(PersonalWeightVo vo) {
		if (vo != null) {
			PersonalWeight weight = (PersonalWeight)baseDao.queryEntityById(PersonalWeight.class, vo.getId());
			if (weight != null) {
				buildVoToEntity(vo,weight);
			}
			baseDao.update(weight);
			//角色与评分指标关系
			//先删除所有数据
			StringBuffer delSql = new StringBuffer();
			delSql.append(" delete from T_INDEXTYPEROLE_WEIGHT where PERSONAL_WEIGHT_ID = "+vo.getId());
			baseDao.executeNativeQuery(delSql.toString());
			if (vo.getRwList() != null && vo.getRwList().size() > 0) {
				List<IndexTypeRoleWeightVo> list = vo.getRwList();
				for (IndexTypeRoleWeightVo indexTypeRoleWeightVo : list) {
					IndexTypeRoleWeight roleWeight = new IndexTypeRoleWeight();
					roleWeight.setPercentage(indexTypeRoleWeightVo.getPercentage());
					roleWeight.setPersonalWeight(weight);
					if (indexTypeRoleWeightVo.getRoleId() > 0) {
						Role role = (Role)baseDao.queryEntityById(Role.class, indexTypeRoleWeightVo.getRoleId());
						roleWeight.setRole(role);
					}
					baseDao.save(roleWeight);
				}
			}
		}
	}

	@Override
	public void deletePersonalWeigh(String ids) {
		//先删除辅表 再删除主表
        StringBuffer delHql =
                new StringBuffer(" delete from IndexTypeRoleWeight ws where ws.personalWeight.id in ("
                    + ids + ")");
        StringBuffer delHql1 =
                new StringBuffer(" delete from PersonalWeight ws where id in ("
                    + ids + ")");
        baseDao.executeHql(delHql.toString());
        baseDao.executeHql(delHql1.toString());
	}
	
	/**
	 * 数据转化
	 * 
	 * @param grade
	 * @param vo
	 */
	private void buildEntityToVo(PersonalWeight weight, PersonalWeightVo vo) {
		vo.setId(weight.getId());
		vo.setPercentage(weight.getPercentage());
		vo.setRemark(weight.getRemark());
		if (weight.getClassification() != null) {
			vo.setClassificationId(weight.getClassification().getPkDictionaryId());
			vo.setClassificationName(weight.getClassification().getDictionaryName());
		}
		if (weight.getIndexType() != null) {
			vo.setIndexTypeId(weight.getIndexType().getPkDictionaryId());
			vo.setIndexTypeName(weight.getIndexType().getDictionaryName());
		}
		
		if (weight.getIndexTypeRoles() != null) {
			List<IndexTypeRoleWeightVo> list = new ArrayList<IndexTypeRoleWeightVo>();
			Iterator<IndexTypeRoleWeight> it = weight.getIndexTypeRoles().iterator();
			while (it.hasNext()) {
				IndexTypeRoleWeight roleWeight = it.next();
				IndexTypeRoleWeightVo weightVo = new IndexTypeRoleWeightVo();
				weightVo.setId(roleWeight.getId());
				weightVo.setPercentage(roleWeight.getPercentage());
				if (roleWeight.getRole() != null) {
					weightVo.setRoleId(roleWeight.getRole().getRoleId());
					weightVo.setRoleName(roleWeight.getRole().getRoleName());
				}
				list.add(weightVo);
			}
			vo.setRwList(list);
		}
	}
	

	/**
	 * vo转实体
	 * 
	 * @param vo
	 * @param weight
	 */
	private void buildVoToEntity(PersonalWeightVo vo, PersonalWeight weight) {
		weight.setPercentage(vo.getPercentage());
		weight.setRemark(vo.getRemark());
		// 分类
		if (vo.getClassificationId() > 0) {
			Dictionary dict = (Dictionary)baseDao.queryEntityById(Dictionary.class, vo.getClassificationId());
			if (dict != null) {
				weight.setClassification(dict);
			}
		}
		// 指标
		if (vo.getIndexTypeId() > 0) {
			Dictionary dict = (Dictionary)baseDao.queryEntityById(Dictionary.class, vo.getIndexTypeId());
			if (dict != null) {
				weight.setIndexType(dict);
			}
		}
	}

}
