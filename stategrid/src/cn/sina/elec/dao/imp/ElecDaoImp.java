package cn.sina.elec.dao.imp;

import cn.sina.elec.dao.ElecDao;
import cn.sina.elec.domain.Elec;
import org.springframework.stereotype.Repository;

/**
 * @Repository(ElecDao.DAO_IMP)
 * 相当于在spring容器中定义：
 * <bean id=ElecDao.DAO_IMP class="com.itheima.elec.dao.impl.ElecTextDaoImpl">
 */
@Repository(ElecDao.DAO_IMP)
public class ElecDaoImp extends CommonDaoImp<Elec> implements ElecDao{
    
}
