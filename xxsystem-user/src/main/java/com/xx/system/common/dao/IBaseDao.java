package com.xx.system.common.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 通用dao，包括基本的CRUD方法
 * 
 * @version V1.20,2013-12-5 下午5:31:35
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings("rawtypes")
public interface IBaseDao {
    /**
     * 添加对象
     * 
     * @Title addEntity
     * @author wanglc
     * @date 2013-12-5
     * @param entity 实体对象
     */
    public void addEntity(Object entity);
    
    /**
     * 修改对象
     * 
     * @Title updateEntity
     * @author wanglc
     * @date 2013-12-5
     * @param entity 实体对象
     */
    public void updateEntity(Object entity);
    
    /**
     * 删除对象，物理删除
     * 
     * @Title deleteEntity
     * @author wanglc
     * @date 2013-12-5
     * @param entity 实体对象
     */
    public void deleteEntity(Object entity);
    
    /**
     * 通过id删除对象，物理删除
     * 
     * @Title deleteEntityById
     * @author wanglc
     * @date 2013-12-5
     * @param clazz 实体类
     * @param id 实体对象主键ID
     */
    public void deleteEntityById(Class clazz, Serializable id);
    
    /**
     * 通过Id查询对象
     * 
     * @Title queryEntityById
     * @author wanglc
     * @date 2013-12-5
     * @param clazz 实体类
     * @param id 实体对象主键ID
     * @return 实体对象
     */
    public Object queryEntityById(Class clazz, Serializable id);
    
    /**
     * 通过条件查询对象集合
     * 
     * @Title queryEntitys
     * @author wanglc
     * @date 2013-12-5
     * @param queryString 查询条件
     * @param values 查询条件对应的值
     * @return List 实体集合
     */
    public List queryEntitys(String queryString, Object[] values);
    
    /**
     * 通过Example查询对象集合
     * 
     * @Title queryEntityByExample
     * @author wanglc
     * @date 2013-12-5
     * @param exampleEntity 模板对象
     * @return List
     */
    public List queryEntityByExample(Object exampleEntity);
    
    /**
     * 通过Criteria查询对象集合
     * 
     * @Title queryEntityByCriteria
     * @author wanglc
     * @date 2013-12-5
     * @param criteria criteria方式的查询对象
     * @return List
     */
    public List queryEntityByCriteria(Criteria criteria);
    
    /**
     * 获得HibernateTemplate
     * 
     * @Title getHibernateTemp
     * @author wanglc
     * @date 2013-12-5
     * @return HibernateTemplate
     */
    public HibernateTemplate getHibernateTemp();
    
    /**
     * 通过命名参数查找数据
     * 
     * @Title queryEntitysByMap
     * @author wanglc
     * @date 2013-12-5
     * @param hql hql查询语句
     * @param map 动态参数的值
     * @return List
     */
    public List queryEntitysByMap(final String hql,
        final Map<String, Object> map);
    
    /**
     * 根据HSQL，取得结果列表
     * 
     * @Title queryEntitys
     * @author wanglc
     * @date 2013-12-5
     * @param hql hql查询语句
     * @return List
     */
    public List queryEntitys(String hql);
    
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
    public int queryTotalCount(String hql, Map<String, Object> map);
    
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
    public List queryEntitysByPage(int start, int limit, String hql,
        Map<String, Object> map);
    
    /**
     * 执行hql
     * 
     * @Title executeHql
     * @author wanglc
     * @date 2013-12-5
     * @param hql hql查询语句
     * @return int 返回影响的行数
     */
    public int executeHql(String hql);
    
    /**
     * 执行hql
     * 
     * @Title executeHql
     * @author wanglc
     * @date 2013-12-5
     * @param hql hql查询语句
     * @param param 参数
     * @return int 返回影响的行数
     */
    public int executeHql(String hql,Map<String,Object> param) ;
    
    /**
     * 通过native sql 取得记录列表
     * 
     * @Title queryEntitysByNavtiveSql
     * @author wanglc
     * @date 2013-12-5
     * @param sql sql语句
     * @return List
     */
    public List queryEntitysByNavtiveSql(final String sql);
    
    /**
     * 持久化一个对象，该对象类型为T。
     * 
     * @Title save
     * @author wanglc
     * @date 2013-12-5
     * @param newInstance 需要持久化的对象，使用JPA标注。
     * @return Object 持久化的对象
     */
    Object save(Object newInstance);
    
    /**
     * 持久化一个对象，该对象类型为T。
     * 
     * @Title add
     * @author wanglc
     * @date 2013-12-5
     * @param newInstance 需要持久化的对象，使用JPA标注。
     */
    void add(Object newInstance);
    
    /**
     * 更新一个对象，主要用于更新一个在persistenceContext之外的一个对象。
     * 
     * @Title update
     * @author wanglc
     * @date 2013-12-5
     * @param transientObject transientObject 需要更新的对象，该对象不需要在persistenceContext中。
     */
    void update(Object transientObject);
    
    /**
     * 调用命名查询，可通过此方法调用xml中配置的存储过程，存储过程必须有返回值
     * 
     * @Title executeNamedQuery
     * @author wanglc
     * @date 2013-12-5
     * @param queryName xml中配置的存储过程名称
     * @param params 储过程参数值
     * @return List
     */
    List executeNamedQuery(String queryName, Object[] params);
    
    /**
     * 调用无返回值的存储过程，inParams是输入参数索引和值，outParamType是输出参数索引和类型
     * 
     * @Title callProc
     * @author wanglc
     * @date 2013-12-5
     * @param procSql 存储过程调用字符串
     * @param inParams 输入参数索引和值
     * @param outParamType 输出参数索引和类型
     * @return List<Map>
     * @throws SQLException
     */
    List<Map> callProc(String procSql, Map<Integer, Object> inParams,
        Map<Integer, Integer> outParamType)
        throws SQLException;
    
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
    List executeNamedQuery(final String queryName, final Object[] params,
        final int begin, final int max);
    
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
    List<Object> find(String query, Object[] params, int begin, int max);
    
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
    List query(String hql, Object[] params, int begin, int max);
    
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
    List query(final String hql, Object[] params);
    
    /**
     * 通过DAO接口查询任意对象
     * 
     * @Title query
     * @author wanglc
     * @date 2013-12-5
     * @param hql
     * @return 返回符合条件的所有记录集,如果没有查到数据,则返回null
     */
    List query(final String hql);
    
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
    int batchUpdate(final String hql, Object[] params);
    
    /**
     * 执行SQL语句查询
     * 
     * @Title executeNativeNamedQuery
     * @author wanglc
     * @date 2013-12-5
     * @param nnq SQL语句
     * @return List
     */
    List executeNativeNamedQuery(String nnq);
    
    /**
     * 执行原生sql
     * 
     * @Title executeNativeNamedQuery
     * @author wanglc
     * @date 2013-12-5
     * @param nnq SQL语句
     * @param params 参数列表
     * @return List
     */
    List executeNativeNamedQuery(final String nnq, final Object[] params);
    
    /**
     * 执行原生sql
     * 
     * @Title executeNativeQuery
     * @author wanglc
     * @date 2013-12-5
     * @param nnq SQL语句
     * @param params 参数列表
     * @param begin 开始位置
     * @param max 每页显示数量
     * @return List
     */
    List executeNativeQuery(final String nnq, final Object[] params,
        final int begin, final int max);
    
    /**
     * 执行原生sql
     * 
     * @Title executeNativeQuery
     * @author wanglc
     * @date 2013-12-5
     * @param nnq SQL语句
     * @param clazz 返回值的对象类型
     * @param params 参数列表
     * @param begin 开始位置
     * @param max 每页显示数量
     * @return List
     */
    List executeNativeQuery(final String nnq, Class clazz,
        final Object[] params, final int begin, final int max);
    
    /**
     * 原生SQL取得总记录数
     * 
     * @Title getTotalCountNativeQuery
     * @author wanglc
     * @date 2013-12-5
     * @param sql SQL语句
     * @param params 参数列表
     * @return int 查询到的条数
     */
    public int getTotalCountNativeQuery(String sql, Object[] params);
    
    /**
     * 执行完整原生sql获取对象集合
     * 
     * @Title executeNativeQuery
     * @author wanglc
     * @date 2013-12-5
     * @param sql SQL语句
     * @return List
     */
    List executeNativeQuery(String sql);
    
    /**
     * 执行SQL语句
     * 
     * @Title executeNativeSQL
     * @author wanglc
     * @date 2013-12-5
     * @param nnq SQL语句
     * @return int 返回影响行数
     */
    int executeNativeSQL(final String nnq);
    
    /**
     * 执行SQL语句
     * 
     * @Title executeNativeSQL
     * @author wanglc
     * @date 2013-12-5
     * @param nnq SQL语句
     * @param paramValues 参数列表
     * @return int
     */
    int executeNativeSQL(String nnq, Object[] paramValues);
    
    /**
     * 执行SQL语句查询，返回单个查询结果
     * 
     * @Title getSingleResult
     * @author wanglc
     * @date 2013-12-5
     * @param hql SQL语句
     * @param params 参数列表
     * @return Object 查询的对象
     */
    Object getSingleResult(final String hql, final Object[] params);
    
    /**
     * 组装自己的SQL进行查询返回相应的MAP
     * 
     * @Title querySQLForMap
     * @author wanglc
     * @date 2013-12-5
     * @param sql SQL语句
     * @param begin 开始位置
     * @param max 每页显示的数量
     * @return List<Map> 以map类型返回查询数据的集合
     */
    List<Map> querySQLForMap(String sql, int begin, int max);
    
    /**
     * 组装自己的HQL进行查询返回相应的MAP，通常用于多表关联查询，返回结果无法映射到一个entity中
     * 
     * @Title queryHQLForMap
     * @author wanglc
     * @date 2013-12-5
     * @param hql HQL语句
     * @param begin 开始位置
     * @param max 每页显示的条数
     * @return List<Map<String, ?>> 以map类型返回查询数据的集合
     */
    List<Map<String, ?>> queryHQLForMap(String hql, int begin, int max);
    
    /**
     * 查询符合条件的总条目
     * 
     * @Title totalCountHql
     * @author wanglc
     * @date 2013-12-5
     * @param hql HQL语句
     * @param params 参数列表
     * @return int 总条目
     */
    int totalCountHql(String hql, Object[] params);
    
    /**
     * 保存或者更新
     * 
     * @Title saveOrUpdate
     * @author wanglc
     * @date 2013-12-5
     * @param transientObject 保存或需要更新的对象
     */
    void saveOrUpdate(Object transientObject);
    
    /**
     * 执行SQL查询并返回Map集合
     * 
     * @Title querySQLForMap
     * @author wanglc
     * @date 2013-12-5
     * @param sql sql语句
     * @return List<Map> 以map类型返回查询数据的集合
     */
    List<Map> querySQLForMap(String sql);
    
    /**
     * 执行SQL查询并返回List集合
     * 
     * @Title querySQLForList
     * @author wanglc
     * @date 2013-12-5
     * @param sql sql语句
     * @return List
     */
    List querySQLForList(String sql);
    
    /**
     * querySQLPrcForMap
     * 
     * @Title querySQLPrcForMap
     * @author wanglc
     * @date 2013-12-5
     * @param sql sql语句
     * @param params 参数列表
     * @return List<Map>
     */
    List<Map> querySQLPrcForMap(String sql, Object[] params);
    
    /**
     * 获取与参数类型一致的数据集合
     * 
     * @Title getAllObjects
     * @author wanglc
     * @date 2013-12-5
     * @param c 与参数类型一致的类对象
     * @return List<?> 与参数类型一致的数据集合
     */
    List<?> getAllObjects(Class<?> c);
    
    /**
     * 删除数据或者启用
     * 
     * @Title delete
     * @author wanglc
     * @date 2013-12-5
     * @param ids 数据对象ID
     * @param className 对象的类型名称
     * @param pkName 主键名称
     * @param state 状态值
     */
    void delete(final String ids, String className, String pkName, String state);
    
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
    public void delete(final String conditionValues, String className,
        String targetColmName, String conditionName, String state);
    
    /**
     * 批量保存或更新
     * 
     * @Title saveOrUpdate
     * @author wanglc
     * @date 2013-12-5
     * @param entityList 需要批量保存的对象集合
     * @return boolean 是否成功
     */
    boolean saveOrUpdate(List<?> entityList);
    
    /**
     * 特殊业务保存
     * 
     * @Title saveOrUpdate
     * @author wanglc
     * @date 2013-12-5
     * @param entityList 需要保存的对象集合1
     * @param entityTowList 需要保存的对象集合2
     * @return boolean 是否成功
     */
    boolean saveOrUpdate(List<?> entityList, List<?> entityTowList);
    
    /**
     * SQL查询记录数
     * 
     * @Title queryCountSQL
     * @author wanglc
     * @date 2013-12-5
     * @param sql sql语句
     * @return int 记录数
     */
    int queryCountSQL(String sql);
    
    /**
     * 全部保存
     * 
     * @Title saveAll
     * @author wanglc
     * @date 2013-12-5
     * @param entityList 需要保存的对象集合
     * @return boolean 是否成功
     */
    boolean saveAll(List<?> entityList);
    
    /**
     * 根据HQL删除
     * 
     * @Title removeByHql
     * @author wanglc
     * @date 2013-12-5
     * @param hql hql语句
     */
    void removeByHql(String hql);
    
    /**
     * 自定义逻辑删除
     * 
     * @Title falseDelete
     * @author wanglc
     * @date 2013-12-5
     * @param classType 需要逻辑删除的类型名称
     * @param ids 需要删除的数据IDs
     * @param idField 条件字段名称
     * @param statusField 更新的字段名称
     */
    void falseDelete(Class<?> classType, final String ids, String idField,
        String statusField);
    
    /**
     * 分页查询
     * 
     * @Title findPageByQuery
     * @author wanglc
     * @date 2013-12-5
     * @param start 开始位置
     * @param pageSize 分页大小
     * @param hql hql语句
     * @param map 参数列表
     * @return List<?>
     */
    List<?> findPageByQuery(int start, int pageSize, String hql, Map<?, ?> map);
    
    /**
     * 取总条数
     * 
     * @Title getTotalCount
     * @author wanglc
     * @date 2013-12-5
     * @param hql hql语句
     * @param map 参数列表
     * @return int 总条数
     */
    int getTotalCount(String hql, Map<?, ?> map);
    
    /**
     * 分类取总条数
     * 
     * @Title getTotalCountByCategory
     * @author wanglc
     * @date 2013-12-5
     * @param hql hql语句
     * @return int总条数
     */
    int getTotalCountByCategory(String hql);
    
    /**
     * 从Sequnce取主键
     * 
     * @Title getIdFromSequence
     * @author wanglc
     * @date 2013-12-5
     * @param sequenceName 序列名称
     * @return int 序列的下一个值
     */
    int getIdFromSequence(String sequenceName);
    
    /**
     * 执行批量数据操作（包括插入，删除，和更新）native sql
     * 
     * @Title batchUpdate
     * @author wanglc
     * @date 2013-12-5
     * @param hql hql语句
     * @return int[]
     */
    int[] batchUpdate(final String[] hql);
    
    /**
     * 保存集合
     * 
     * @Title saveAllByList
     * @author wanglc
     * @date 2013-12-5
     * @param entities 需要保存的对象集合
     */
    void saveAllByList(List<?> entities);
    
    /**
     * 批量删除
     * 
     * @Title deleteEntities
     * @author wanglc
     * @date 2013-12-5
     * @param entities 需要删除的对象集合
     */
    public void deleteEntities(List<?> entities);
    
    /**
     * sql分页查询并转换为hibernate实体
     * 
     * @Title executeNativeSQLForBean
     * @author wanglc
     * @date 2014年9月19日
     * @param start 开始位置
     * @param pageSize 分页大小
     * @param sql sql语句
     * @param className 返回值的类型
     * @return List
     */
    public List executeNativeSQLForBean(int start, int pageSize, String sql,
        Class className);
    
    /**
     * 取总条数
     * 
     * @Title getCountByNativeSQL
     * @author wanglc
     * @date 2014-3-4
     * @param sql sql语句
     * @param className 对象名称
     * @return int 总条数
     */
    public int getCountByNativeSQL(String sql, Class className);
}
