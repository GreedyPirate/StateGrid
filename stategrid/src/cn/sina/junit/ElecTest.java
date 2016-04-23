package cn.sina.junit;

import java.io.Serializable;
import java.util.*;

import cn.sina.elec.dao.ElecDao;
import cn.sina.elec.service.ElecService;
import cn.sina.elec.utils.MD5keyBean;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Test;

import cn.sina.elec.domain.Elec;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ElecTest {
    @Test
    public void testElec() {
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Elec elec = new Elec();
        elec.setTextDate(new Date());
        elec.setTextName("state");
        elec.setTextRemark("test");

        session.save(elec);

        transaction.commit();
        session.close();
    }

    /**
     * 因为没有开启事务,这要在业务层完成,所以要加自动自交设置
     */
    @Test
    public void testDao() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ElecDao elecDao = (ElecDao) context.getBean(ElecDao.DAO_IMP);
        Elec elec = new Elec();
        elec.setTextDate(new Date());
        elec.setTextName("common");
        elec.setTextRemark("werqxaq");
        elecDao.save(elec);
    }

    /**
     * 测试service层
     */
    @Test
    public void testService(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ElecService elecService = (ElecService) context.getBean(ElecService.ELEC_SERVICE);
        Elec elec = new Elec();
        elec.setTextDate(new Date());
        elec.setTextName("service");
        elec.setTextRemark("test service");
        elecService.saveElec(elec);
    }

    /**
     * 测试更新方法
     */
    @Test
    public void testUpdate(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ElecDao elecDao = (ElecDao) context.getBean(ElecDao.DAO_IMP);
        Elec elec = new Elec();
        elec.setTextID("402881e75106160f015106165ef60001");
        elec.setTextName("update");
        elec.setTextDate(new Date());
        elec.setTextRemark("test update");
        elecDao.update(elec);
    }
    /**
     * 测试 通过id查找方法
     */
    @Test
    public void testFindById(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ElecDao elecDao = (ElecDao) context.getBean(ElecDao.DAO_IMP);
        String id = "402881e75104f047015104f049680001";
        Elec elec = elecDao.findById(id);
        System.out.println(elec);
    }

    /**
     * 通过id删除
     */
    @Test
    public void testDeleteById(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ElecDao elecDao = (ElecDao) context.getBean(ElecDao.DAO_IMP);
        String id = "402881e75106160f015106165ef60001";
        elecDao.deleteById(id);
    }
    /**
     * 通过ids删除一组数据
     */
    @Test
    public void testDeleteByIds(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ElecDao elecDao = (ElecDao) context.getBean(ElecDao.DAO_IMP);
        Serializable[] ids = {"402881e75104381c015104381f780001","402881e7510441ba01510441bbe50001","402881e7510569a101510569a4140001"};
        elecDao.deleteByIds(ids);
    }

    /**
     * 通过集合删除
     */
    @Test
    public void testDeleteByCollection(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ElecDao elecDao = (ElecDao) context.getBean(ElecDao.DAO_IMP);

        Elec elec1 = new Elec();
        elec1.setTextID("402881e75104f047015104f049680001");

        Elec elec2 = new Elec();
        elec2.setTextID("402881e751059cac0151059cdb880001");

        List<Elec> list = new ArrayList<Elec>();
        list.add(elec1);
        list.add(elec2);

        elecDao.deleteByCollection(list);
    }
    /**
     * 按条件查询
     *  拼接sql,并且排序
     */
    @Test
    public void testConditionalQuery(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ElecService elecService = (ElecService) context.getBean(ElecService.ELEC_SERVICE);

        Elec elec = new Elec();
        elec.setTextName("mm");
        elec.setTextRemark("qx");

        List<Elec> list = elecService.conditionalQuery(elec);
        if(list!=null && list.size()>0){
            System.out.println(list);
        }
    }
    @Test
    public void testFor(){
        Map<String,String> map = new HashMap<String ,String >();
        map.put("001","doudou");
        map.put("002","xixi");
        for(Map.Entry<String,String> entry: map.entrySet()){
            System.out.println(entry.getKey());
        }
    }
    @Test
    public void testMd5(){
    	MD5keyBean bean = new MD5keyBean();
    	System.out.println(bean.getkeyBeanofStr("123"));
    }
}
