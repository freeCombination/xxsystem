package com.xx.system.common.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.SessionImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xx.system.common.dao.IBaseDao;

/**
 * 通用DAO之hibernate实现
 * 
 * @version V1.20,2013-12-5 下午5:53:20
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@Repository("baseDao")
@SuppressWarnings({"rawtypes", "unchecked"})
@Transactional(propagation = Propagation.REQUIRED)
public class BaseDaoHibernateImpl extends HibernateDaoSupport implements
    IBaseDao, Serializable {
    /** @Fields serialVersionUID : serialVersionUID */
    private static final long serialVersionUID = -1326060851595011233L;
    
    /**
     * @Title setSessionFactoryTemp
     * @author wanglc
     * @Description: setSessionFactoryTemp
     * @date 2013-12-6
     * @param sessionFactory
     */
    @Autowired
    public void setSessionFactoryTemp(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
    
    /**
     * 添加对象
     * 
     * @Title addEntity
     * @author wanglc
     * @date 2013-12-5
     * @param entity 实体对象
     */
    @Override
    public void addEntity(Object entity) {
        getHibernateTemplate().save(entity);
    }
    
    /**
     * 修改对象
     * 
     * @Title updateEntity
     * @author wanglc
     * @date 2013-12-5
     * @param entity 实体对象
     */
    @Override
    public void updateEntity(Object entity) {
        getHibernateTemplate().update(entity);
    }
    
    /**
     * @Title deleteEntity
     * @author wanglc
     * @Description: 删除对象，物理删除
     * @date 2013-12-5
     * @param entity 实体对象
     */
    @Override
    public void deleteEntity(Object entity) {
        getHibernateTemplate().delete(entity);
    }
    
    /**
     * @Title deleteEntityById
     * @author wanglc
     * @Description: 通过id删除对象，物理删除
     * @date 2013-12-5
     * @param clazz 实体类
     * @param id 实体对象主键ID
     */
    @Override
    public void deleteEntityById(Class clazz, Serializable id) {
        getHibernateTemplate().delete(queryEntityById(clazz, id));
    }
    
    /**
     * @Title queryEntityById
     * @author wanglc
     * @Description: 通过Id查询对象
     * @date 2013-12-5
     * @param clazz 实体类
     * @param id 实体对象主键ID
     * @return 实体对象
     */
    @Override
    public Object queryEntityById(Class clazz, Serializable id) {
        return getHibernateTemplate().get(clazz, id);
    }
    
    /**
     * @Title queryEntitys
     * @author wanglc
     * @Description: 通过条件查询对象集合
     * @date 2013-12-5
     * @param queryString 查询条件
     * @param values 查询条件对应的值
     * @return List 实体集合
     */
    @Override
    public List queryEntitys(String queryString, Object[] values) {
        return getHibernateTemplate().find(queryString, values);
    }
    
    /**
     * @Title queryEntityByExample
     * @author wanglc
     * @Description: 通过Example查询对象集合
     * @date 2013-12-5
     * @param exampleEntity
     * @return List
     */
    @Override
    public List queryEntityByExample(Object exampleEntity) {
        return getHibernateTemplate().findByExample(exampleEntity);
    }
    
    /**
     * 通过Criteria查询对象集合
     * 
     * @Title queryEntityByCriteria
     * @author wanglc
     * @date 2013-12-5
     * @param criteria
     * @return List
     */
    @Override
    public List queryEntityByCriteria(final Criteria criteria) {
        
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session)
                throws HibernateException, SQLException {
                
                CriteriaImpl countCriteria = (CriteriaImpl)criteria;
                countCriteria.setSession((SessionImpl)session.getSession(EntityMode.POJO));
                countCriteria.setFirstResult(0);
                
                return countCriteria.setResultTransformer(Criteria.ROOT_ENTITY)
                    .list();
            }
            
        });
        
    }
    
    /**
     * 获得HibernateTemplate
     * 
     * @Title getHibernateTemp
     * @author wanglc
     * @date 2013-12-5
     * @return List
     */
    @Override
    public List queryEntitysByMap(final String hql,
        final Map<String, Object> map) {
        List result =
            (List)this.getHibernateTemplate().execute(new HibernateCallback() {
                @Override
                public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                    Query query = session.createQuery(hql);
                    Iterator it = map.keySet().iterator();
                    while (it.hasNext()) {
                        Object key = it.next();
                        query.setParameter(key.toString(), map.get(key));
                    }
                    return query.list();
                }
            });
        return result;
        
    }
    
    /**
     * 根据HSQL，取得结果列表
     * 
     * @Title queryEntitys
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @return List
     */
    @Override
    public List queryEntitys(String hql) {
        List result = new ArrayList<Object>();
        result = this.getHibernateTemplate().find(hql);
        return result;
    }
    
    /**
     * 根据查询条件查询记录数的个数
     * 
     * @Title queryTotalCount
     * @author wanglc
     * @date 2013-12-5
     * @param hql hql查询语句
     * @param map 用map封装查询条件
     * @return int 数量
     */
    @Override
    public int queryTotalCount(String hql, Map<String, Object> map) {
        Query query = this.getSession().createQuery(hql);
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            query.setParameter(key.toString(), map.get(key));
        }
        int beginPos = hql.toLowerCase().indexOf("from");
        String hql4Count = " select count(*)  " + hql.substring(beginPos);
        List countlist = this.queryEntitysByMap(hql4Count, map);
        int totalCount = ((Long)countlist.get(0)).intValue();
        return totalCount;
    }
    
    /**
     * 分页显示符合所有的记录数 hql， Query查询
     * 
     * @Title queryEntitysByPage
     * @author wanglc
     * @date 2013-12-5
     * @param start 当前起始记录数
     * @param limit 每页显示的条数
     * @param hql hql 查询语句
     * @param map 用map封装查询条件
     * @return List
     */
    @Override
    public List queryEntitysByPage(int start, int limit, String hql,
        Map<String, Object> map) {
        List result = new ArrayList<Object>();
        Query query = this.getSession().createQuery(hql);
        if(map!=null && map.size()>0){
	        Iterator it = map.keySet().iterator();
	        while (it.hasNext()) {
	            Object key = it.next();
	            query.setParameter(key.toString(), map.get(key));
	        }
        }
        query.setFirstResult(start);
        query.setMaxResults(limit);
        result = query.list();
        return result;
    }
    
    /**
     * 执行hql
     * 
     * @Title executeHql
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @return int
     */
    @Override
    public int executeHql(String hql) {
    	return executeHql(hql,null);
        
    }
    /**
     * 执行hql
     * 
     * @Title executeHql
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @param param
     * @return int
     */
    @Override
    public int executeHql(String hql,Map<String,Object> param) {
        final String tempSql = hql;
        final Map<String,Object> map = param;
        return (Integer)this.getHibernateTemplate()
            .execute(new HibernateCallback() {
                @Override
                public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                	Query  q = session.createQuery(tempSql);
                	 if(map!=null && map.size()>0){
             	        Iterator it = map.keySet().iterator();
             	        while (it.hasNext()) {
             	            Object key = it.next();
             	            q.setParameter(key.toString(), map.get(key));
             	        }
                     }
                    return q.executeUpdate();
                }
                
            });
        
    }
    /**
     * 通过native sql 取得记录列表
     * 
     * @Title queryEntitysByNavtiveSql
     * @author wanglc
     * @date 2013-12-5
     * @param sql
     * @return List
     */
    @Override
    public List queryEntitysByNavtiveSql(final String sql) {
        List list =
            this.getHibernateTemplate().executeFind(new HibernateCallback() {
                @Override
                public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                    List list = session.createSQLQuery(sql).list();
                    return list;
                }
            });
        return list;
    }
    
    /**
     * 获得HibernateTemplate
     * 
     * @Title getHibernateTemp
     * @author wanglc
     * @date 2013-12-5
     * @return List
     */
    @Override
    public HibernateTemplate getHibernateTemp() {
        return getHibernateTemplate();
    }
    
    /**
     * 持久化一个对象，该对象类型为T。
     * 
     * @Title save
     * @author wanglc
     * @date 2013-12-5
     * @param newInstance 需要持久化的对象，使用JPA标注。
     * @return
     */
    @Override
    public Object save(Object newInstance) {
        this.getHibernateTemplate().save(newInstance);
        return newInstance;
    }
    
    /**
     * 删除实体
     * 
     * @Title deleteEntities
     * @author wanglc
     * @date 2013-12-5
     * @param entities
     */
    @Override
    public void deleteEntities(List<?> entities) {
        this.getHibernateTemplate().deleteAll(entities);
    }
    
    /**
     * 持久化一个对象，该对象类型为T。
     * 
     * @Title add
     * @author wanglc
     * @date 2013-12-5
     * @param newInstance 需要持久化的对象，使用JPA标注。
     */
    @Override
    public void add(Object newInstance) {
        this.getHibernateTemplate().save(newInstance);
    }
    
    /**
     * 更新一个对象，主要用于更新一个在persistenceContext之外的一个对象。
     * 
     * @Title update
     * @author wanglc
     * @date 2013-12-5
     * @param transientObject transientObject 需要更新的对象，该对象不需要在persistenceContext中。
     */
    @Override
    public void update(Object transientObject) {
        this.getHibernateTemplate().update(transientObject);
    }
    
    /**
     * 保存或者更新
     * 
     * @Title saveOrUpdate
     * @author wanglc
     * @date 2013-12-5
     * @param transientObject
     */
    @Override
    public void saveOrUpdate(Object transientObject) {
        this.getHibernateTemplate().saveOrUpdate(transientObject);
    }
    
    /**
     * 调用命名查询，可通过此方法调用xml中配置的存储过程，存储过程必须有返回值
     * 
     * @Title executeNamedQuery
     * @author wanglc
     * @date 2013-12-5
     * @param queryName
     * @param params
     * @return List
     */
    @Override
    public List executeNamedQuery(String queryName, Object[] params) {
        Query q = this.getSession().createQuery(queryName);
        for (int i = 0; i < params.length; i++) {
            q.setParameter(i, params);
        }
        return q.list();
    }
    
    /**
     * 调用无返回值的存储过程，params是输入参数索引和值，outParamType是输出参数索引和类型
     * 
     * @Title callProc
     * @author wanglc
     * @date 2013-12-5
     * @param procSql
     * @param inParams
     * @param outParamType
     * @return List<Map>
     * @throws SQLException
     */
    @Override
    public List<Map> callProc(String procSql, Map<Integer, Object> inParams,
        Map<Integer, Integer> outParamType)
        throws SQLException {
        Connection conn =
            SessionFactoryUtils.getDataSource(getSessionFactory())
                .getConnection();
        ;
        CallableStatement call = conn.prepareCall(procSql);
        Iterator<Integer> it = inParams.keySet().iterator();
        while (it.hasNext()) {
            Integer index = it.next();
            Object param = inParams.get(index);
            call.setInt(index, (Integer)param);
        }
        
        Iterator<Integer> itOut = outParamType.keySet().iterator();
        while (itOut.hasNext()) {
            Integer index = itOut.next();
            call.registerOutParameter(index, outParamType.get(index));
        }
        call.execute();
        List<Map> mapList = new ArrayList<Map>();
        ResultSet rs = null;
        rs = (ResultSet)call.getObject(5);
        Map map = null;
        while (rs.next()) {
            map = new HashMap();
            map.put("RUSULT", rs.getString("RUSULT"));
            map.put("V_CURR_AMOUNT", rs.getFloat("V_CURR_AMOUNT"));
            map.put("V_PREVIOUS_PERIOD_AMOUNT",
                rs.getFloat("V_PREVIOUS_PERIOD_AMOUNT"));
            map.put("V_PREVIOUS_CURR_AMOUNT",
                rs.getFloat("V_PREVIOUS_CURR_AMOUNT"));
            mapList.add(map);
            
        }
        rs.close();
        call.close();
        return mapList;
    }
    
    /**
     * @Title getType
     * @author wanglc
     * @Description: 取数据类型
     * @date 2013-12-5
     * @param obj
     * @return
     */
    public static String getType(Object obj) {
        if (obj instanceof Integer) {
            return "int";
        }
        else if (obj instanceof Long) {
            return "long";
        }
        else if (obj instanceof Double) {
            return "double";
        }
        else if (obj instanceof Float) {
            return "float";
        }
        else if (obj instanceof Boolean) {
            return "bool";
        }
        else if (obj instanceof String) {
            return "string";
        }
        else if (obj instanceof Date) {
            return "date";
        }
        else if (obj instanceof BigDecimal) {
            return "bigDecimal";
        }
        else {
            return null;
        }
    }
    
    /**
     * 根据一个查询条件及其参数，还有开始查找的位置和查找的个数来查找任意类型的对象。
     * 
     * @Title executeNamedQuery
     * @author wanglc
     * @date 2013-12-5
     * @param queryName 命名查询的名字
     * @param params 查询条件中的参数的值。使用Object数组，要求顺序和查询条件中的参数位置一致。
     * @param begin 开始查询的位置
     * @param max 需要查询的对象的个数
     * @return 一个任意对象的List对象，如果没有查到任何数据，返回一个空的List对象。
     */
    @Override
    public List executeNamedQuery(String queryName, Object[] params, int begin,
        int max) {
        Query q = this.getSession().createQuery(queryName);
        for (int i = 0; i < params.length; i++) {
            q.setParameter(i, params);
        }
        return q.list();
    }
    
    /**
     * 根据一个查询条件及其参数，还有开始查找的位置和查找的个数来查找类型为T的对象
     * 
     * @Title find
     * @author wanglc
     * @date 2013-12-5
     * @param query 查询的条件，使用位置参数，对象名统一为obj，查询条件从where后开始。比如：obj.name = ?1 and obj.properties = ?2
     * @param params 查询条件中的参数的值。使用Object数组，要求顺序和查询条件中的参数位置一致。
     * @param begin 开始查询的位置
     * @param max 需要查询的对象的个数
     * @return 一个该类型对象的List对象，如果没有查到任何数据，返回一个空的List对象。
     */
    @Override
    public List<Object> find(String queryStr, Object[] params, int begin,
        int max) {
        Query query = this.getSession().createQuery(queryStr);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        query.setMaxResults(max);
        query.setFirstResult(begin);
        List<Object> ret = query.list();
        return ret;
    }
    
    /**
     * 根据一个查询条件及其参数，还有开始查找的位置和查找的个数来查找任意类型的对象。
     * 
     * @Title query
     * @author wanglc
     * @date 2013-12-5
     * @param hql 完整的查询语句，使用位置参数。比如：select user from User user where user.name = ?1 and
     *            user.properties = ?2
     * @param params 查询条件中的参数的值。使用Object数组，要求顺序和查询条件中的参数位置一致。
     * @param begin 开始查询的位置
     * @param max 需要查询的对象的个数
     * @return 一个任意对象的List对象，如果没有查到任何数据，返回一个空的List对象。
     */
    @Override
    public List query(String hql, Object[] params, int begin, int max) {
        Query query = this.getSession().createQuery(hql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
        query.setMaxResults(max);
        query.setFirstResult(begin);
        List list = query.list();
        return list;
    }
    
    /**
     * 查询符合条件的总条目
     * 
     * @Title totalCountHql
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @param params
     * @return int 总条目
     */
    @Override
    public int totalCountHql(String hql, Object[] params) {
        int count = 0;
        Query q = this.getSession().createQuery(hql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i, params[i]);
            }
        }
        if (q.uniqueResult() != null) {
            count = ((Long)(q.uniqueResult())).intValue();
        }
        return count;
    }
    
    /**
     * @Title totalCount
     * @author wanglc
     * @Description: 取总条数
     * @date 2013-12-5
     * @param hql
     * @return
     */
    public int totalCount(String hql) {
        Query query = this.getSession().createQuery(hql);
        return ((Long)(query.uniqueResult())).intValue();
    }
    
    /**
     * 查询符合条件的所有记录
     * 
     * @Title query
     * @author wanglc
     * @date 2013-12-5
     * @param hql 查询语句
     * @param params 参数列表
     * @return 返回符合条件的数据对象,如果没有数据对象则返回null
     */
    @Override
    public List query(String hql, Object[] params) {
        return query(hql, params, 0, -1);
    }
    
    /**
     * 通过DAO接口查询任意对象
     * 
     * @Title query
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @return 返回符合条件的所有记录集,如果没有查到数据,则返回null
     */
    @Override
    public List query(String hql) {
        return query(hql, null);
    }
    
    /**
     * 根据hql语句执行批量数据更新等操作
     * 
     * @Title batchUpdate
     * @author wanglc
     * @date 2013-12-5
     * @param jpql 需要执行jpql语句
     * @param params 语句中附带的参数
     * @return int
     */
    @Override
    public int batchUpdate(String jpql, Object[] params) {
        return 0;
    }
    
    /**
     * 执行SQL语句查询
     * 
     * @Title executeNativeNamedQuery
     * @author wanglc
     * @date 2013-12-5
     * @param nnq
     * @return List
     */
    @Override
    public List executeNativeNamedQuery(String nnq) {
        return null;
    }
    
    /**
     * 执行原生sql
     * 
     * @Title executeNativeNamedQuery
     * @author wanglc
     * @date 2013-12-5
     * @param nnq
     * @param params
     * @return List
     */
    @Override
    public List executeNativeNamedQuery(String name, Object[] params) {
        return null;
    }
    
    /**
     * 执行原生sql
     * 
     * @Title executeNativeQuery
     * @author wanglc
     * @date 2013-12-5
     * @param nnq
     * @param params
     * @param begin
     * @param max
     * @return List
     */
    @Override
    public List executeNativeQuery(String sql, Object[] params, int begin,
        int max) {
        Query q = this.getSession().createSQLQuery(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i, params[i]);
            }
        }
        q.setFirstResult(begin);
        q.setMaxResults(max);
        return q.list();
    }
    
    /**
     * @Title executeNativeQuery
     * @author wanglc
     * @Description: 执行原生sql
     * @date 2013-12-5
     * @param nnq
     * @param clazz
     * @param params
     * @param begin
     * @param max
     * @return List
     */
    @Override
    public List executeNativeQuery(String sql, Class clazz, Object[] params,
        int begin, int max) {
        Query q = this.getSession().createSQLQuery(sql).addEntity(clazz);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i, params[i]);
            }
        }
        q.setFirstResult(begin);
        q.setMaxResults(max);
        return q.list();
    }
    
    /**
     * @Title getTotalCountNativeQuery
     * @author wanglc
     * @Description: 原生SQL取得总记录数
     * @date 2013-12-5
     * @param sql
     * @param params
     * @return int
     */
    @Override
    public int getTotalCountNativeQuery(String sql, Object[] params) {
        int count = 0;
        Query q = this.getSession().createSQLQuery(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i, params[i]);
            }
        }
        if (q.uniqueResult() != null) {
        	BigInteger b = (BigInteger)q.uniqueResult();
            count = b.intValue();
        }
        return count;
    }
    
    /**
     * @Title executeNativeQuery
     * @author wanglc
     * @Description: 执行完整原生sql
     * @date 2013-12-5
     * @param sql
     * @return List
     */
    @Override
    public List executeNativeQuery(String sql) {
        Query q = this.getSession().createSQLQuery(sql);
        return q.list();
    }
    
    /**
     * sql查询，转换为hibernate实体
     * 
     * @Title executeNativeSQLForBean
     * @author wanglc
     * @date 2014-2-25
     * @param sql
     * @return
     */
    @Override
    public List executeNativeSQLForBean(int start, int pageSize, String sql,
        Class className) {
        Query q = this.getSession().createSQLQuery(sql);
        q.setFirstResult(start);
        q.setMaxResults(pageSize);
        q.setResultTransformer(Transformers.aliasToBean(className));
        return q.list();
    }
    
    /**
     * @Title executeNativeSQL
     * @author wanglc
     * @Description: 执行SQL语句
     * @date 2013-12-5
     * @param nnq
     * @return int
     */
    @Override
    public int executeNativeSQL(String nnq) {
        return this.getSession().createSQLQuery(nnq).executeUpdate();
    }
    
    /**
     * 执行SQL语句
     * 
     * @Title executeNativeSQL
     * @author wanglc
     * @date 2013-12-5
     * @param nnq paramValues
     * @return int
     */
    @Override
    public int executeNativeSQL(String nnq, Object[] paramValues) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery(nnq);
        if (paramValues != null && paramValues.length > 0) {
            for (int i = 0; i < paramValues.length; i++) {
                sqlQuery.setParameter(i, paramValues[i]);
            }
        }
        return sqlQuery.executeUpdate();
    }
    
    /**
     * 执行SQL语句查询，返回单个查询结果
     * 
     * @Title getSingleResult
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @param params
     * @return Object
     */
    @Override
    public Object getSingleResult(String jpql, Object[] params) {
        List list = this.query(jpql, params, 0, 1);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        else {
            return null;
        }
    }
    
    /**
     * 组装自己的SQL进行查询返回相应的MAP
     * 
     * @Title querySQLForMap
     * @author wanglc
     * @date 2013-12-5
     * @param sql
     * @param begin
     * @param max
     * @return List<Map>
     */
    @Override
    public List<Map> querySQLForMap(String sql, int begin, int max) {
        List<Map> mapList = null;
        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        sqlQuery.setMaxResults(max);
        sqlQuery.setFirstResult(begin);
        mapList = sqlQuery.list();
        return mapList;
    }
    
    /**
     * 执行SQL查询并返回Map集合
     * 
     * @Title querySQLForMap
     * @author wanglc
     * @date 2013-12-5
     * @param sql
     * @return List<Map>
     */
    @Override
    public List<Map> querySQLForMap(String sql) {
        List<Map> mapList = null;
        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        
        mapList = sqlQuery.list();
        return mapList;
    }
    
    /**
     * 执行SQL查询并返回List集合
     * 
     * @Title querySQLForList
     * @author wanglc
     * @date 2013-12-5
     * @param sql
     * @return List
     */
    @Override
    public List querySQLForList(String sql) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
        sqlQuery.setResultTransformer(Transformers.TO_LIST);
        return sqlQuery.list();
    }
    
    /**
     * 组装自己的HQL进行查询返回相应的MAP，通常用于多表关联查询，返回结果无法映射到一个entity中
     * 
     * @Title queryHQLForMap
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @param begin
     * @param max
     * @return List<Map<String, ?>>
     */
    @Override
    public List<Map<String, ?>> queryHQLForMap(String hql, int begin, int max) {
        List<Map<String, ?>> mapList = null;
        Query query = this.getSession().createQuery(hql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setMaxResults(max);
        query.setFirstResult(begin);
        mapList = query.list();
        return mapList;
    }
    
    /**
     * 删除数据 或者启用
     * 
     * @Title delete
     * @author wanglc
     * @date 2013-12-5
     * @param ids
     * @param className
     * @param pkName
     * @param state
     */
    @Override
    public void delete(final String ids, String className, String pkName,
        String state) {
        String hql =
            "update " + className + " a SET a.flage = '1' where a." + pkName
                + " in (" + ids + ")";
        this.getHibernateTemplate().bulkUpdate(hql);
    }
    
    /**
     * 逻辑删除
     * 
     * @Title delete
     * @author wanglc
     * @date 2013-12-5
     * @param conditionValues where条件值
     * @param className 该更对象名（表）
     * @param targetColmName 要更改的列名
     * @param conditionName where 条件名
     * @param state 更改后的值
     */
    @Override
    public void delete(final String conditionValues, String className,
        String targetColmName, String conditionName, String state) {
        String hql =
            "update " + className + " a SET a." + targetColmName + " = '"
                + state + "' where a." + conditionName + " in ("
                + conditionValues + ")";
        this.getHibernateTemplate().bulkUpdate(hql);
    }
    
    /**
     * querySQLPrcForMap
     * 
     * @Title querySQLPrcForMap
     * @author wanglc
     * @date 2013-12-5
     * @param sql
     * @param params
     * @return List<Map>
     */
    @Override
    public List<Map> querySQLPrcForMap(String sql, Object[] params) {
        List<Map> mapList = null;
        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
        for (int i = 0; i < params.length; i++) {
            sqlQuery.setParameter(i, params);
        }
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        mapList = sqlQuery.list();
        return mapList;
    }
    
    /**
     * getAllObjects
     * 
     * @Title getAllObjects
     * @author wanglc
     * @date 2013-12-5
     * @param c
     * @return List<?>
     */
    @Override
    public List<?> getAllObjects(Class<?> c) {
        List<?> list = new ArrayList<Object>();
        list =
            this.getHibernateTemplate().find("select o from " + c.getName()
                + " o order by o.id asc ");
        return list;
    }
    
    /**
     * 保存或更新
     * 
     * @Title saveOrUpdate
     * @author wanglc
     * @date 2013-12-5
     * @param entityList
     * @return
     */
    @Override
    public boolean saveOrUpdate(List<?> entityList) {
        Session session = this.getSession();
        int i = 0;
        for (i = 0; i < entityList.size(); i++) {
            session.saveOrUpdate(entityList.get(i));
        }
        return i == entityList.size();
    }
    
    /**
     * 全部保存
     * 
     * @Title saveAll
     * @author wanglc
     * @date 2013-12-5
     * @param entityList
     * @return
     */
    @Override
    public boolean saveAll(List<?> entityList) {
        Session session = this.getSession();
        int i = 0;
        for (i = 0; i < entityList.size(); i++) {
            session.save(entityList.get(i));
        }
        return i == entityList.size();
    }
    
    /**
     * 根据HQL删除
     * 
     * @Title removeByHql
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     */
    @Override
    public void removeByHql(String hql) {
        Session session = this.getSession();
        Query query = session.createQuery(hql);
        query.executeUpdate();
    }
    
    /**
     * 特殊业务保存
     * 
     * @Title saveOrUpdate
     * @author wanglc
     * @date 2013-12-5
     * @param entityList
     * @param entityTowList
     * @return
     */
    @Override
    public boolean saveOrUpdate(List<?> entityList, List<?> entityTowList) {
        Session session = this.getSession();
        Transaction tx = session.beginTransaction();
        int i = 0;
        for (i = 0; i < entityList.size(); i++) {
            session.saveOrUpdate(entityList.get(i));
            if (i % 50 == 0) {
                session.flush();
                session.clear();
            }
        }
        for (i = 0; i < entityTowList.size(); i++) {
            session.saveOrUpdate(entityTowList.get(i));
            if (i % 50 == 0) {
                session.flush();
                session.clear();
            }
        }
        // 提交事务
        tx.commit();
        session.close();
        return i == entityTowList.size();
    }
    
    /**
     * SQL查询记录数
     * 
     * @Title queryCountSQL
     * @author wanglc
     * @date 2013-12-5
     * @param sql
     * @return
     */
    @Override
    public int queryCountSQL(String sql) {
        List<Map> mapList = null;
        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        mapList = sqlQuery.list();
        if (mapList.size() > 0) {
            Iterator it = mapList.get(0).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                Object value = entry.getValue();
                return Integer.parseInt(value.toString());
            }
        }
        return 0;
    }
    
    /**
     * 自定义逻辑删除
     * 
     * @Title falseDelete
     * @author wanglc
     * @date 2013-12-5
     * @param classType
     * @param ids
     * @param idField
     * @param statusField
     */
    @Override
    public void falseDelete(Class<?> classType, String ids, String idField,
        String statusField) {
        String hql =
            "update " + classType.getName() + " a SET a." + statusField
                + " = 1 where a." + idField + " in (" + ids + ")";
        this.getHibernateTemplate().bulkUpdate(hql);
    }
    
    /**
     * 分页查询
     * 
     * @Title findPageByQuery
     * @author wanglc
     * @date 2013-12-5
     * @param start
     * @param pageSize
     * @param hql
     * @param map
     * @return List<?>
     */
    @Override
    public List<?> findPageByQuery(int start, int pageSize, String hql,
        Map<?, ?> map) {
        List<?> result = new ArrayList<Object>();
        Query query = this.getSession().createQuery(hql);
        Iterator<?> it = map.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            query.setParameter(key.toString(), map.get(key));
        }
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        result = query.list();
        return result;
    }
    
    /**
     * 取总条数
     * 
     * @Title getTotalCount
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @param map
     * @return int 取总条数
     */
    @Override
    public int getTotalCount(String hql, Map<?, ?> map) {
        int result = 0;
        try {
            Query query = this.getSession().createQuery(hql);
            if(map!=null){
	            Iterator<?> it = map.keySet().iterator();
	            while (it.hasNext()) {
	                Object key = it.next();
	                query.setParameter(key.toString(), map.get(key));
	            }
            }
            Long i = (Long)query.list().get(0);
            
            result = NumberUtils.toInt(i.toString());
        }
        catch (RuntimeException re) {
    
        }
        return result;
    }
    
    /**
     * 分类取总条数
     * 
     * @Title getTotalCountByCategory
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @return int总条数
     */
    @Override
    public int getTotalCountByCategory(String hql) {
        return 0;
    }
    
    /**
     * 从Sequnce取主键
     * 
     * @Title getIdFromSequence
     * @author wanglc
     * @date 2013-12-5
     * @param sequenceName
     * @return int
     */
    @Override
    public int getIdFromSequence(final String sequenceName) {
    	List<Map> mapList = null;
        SQLQuery sqlQuery = this.getSession().createSQLQuery("select " + sequenceName
                + ".nextval from dual");
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        mapList = sqlQuery.list();
        if (mapList.size() > 0) {
            Iterator it = mapList.get(0).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                Object value = entry.getValue();
                return Integer.parseInt(value.toString());
            }
        }
        return 0;
    }
    
    /**
     * 执行批量数据操作（包括插入，删除，和更新）native sql
     * 
     * @Title batchUpdate
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @return int[]
     */
    @Override
    public int[] batchUpdate(final String[] queryString) {
        int[] num = null;
        try {
            num =
                (int[])this.getHibernateTemplate()
                    .execute(new HibernateCallback() {
                        @SuppressWarnings("deprecation")
                        @Override
                        public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                            Connection connect = session.connection();
                            Statement statement = connect.createStatement();
                            for (int i = 0; i < queryString.length; i++) {
                                statement.addBatch(queryString[i]);
                            }
                            return statement.executeBatch();
                        }
                    });
        }
        catch (DataAccessException e) {
            
        }
        return num;
    }
    
    /**
     * 保存集合
     * 
     * @Title saveAllByList
     * @author wanglc
     * @date 2013-12-5
     * @param entities
     */
    @Override
    public void saveAllByList(List<?> entities) {
        this.getHibernateTemplate().saveOrUpdateAll(entities);
    }
    
    protected void getHqlCondition(StringBuffer hql, Map paramMap,
        Map conditionMap) {
        for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry)it.next();
            String key = (String)entry.getKey();
            conditionMap.put(key.replaceAll("\\.", ""), entry.getValue());
            hql.append(" and ")
                .append(key)
                .append("=:")
                .append(key.replaceAll("\\.", ""));
        }
    }
    
    /**
     * 取总条数
     * 
     * @Title getCountByNativeSQL
     * @author wanglc
     * @date 2014-3-4
     * @param string
     * @param className
     * @return
     */
    @Override
    public int getCountByNativeSQL(String sql, Class className) {
        Query q = this.getSession().createSQLQuery(sql);
        q.setResultTransformer(Transformers.aliasToBean(className));
        return q.list().size();
    }
}
