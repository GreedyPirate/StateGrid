package cn.sina.elec.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.sina.elec.dao.ElecCommonMsgContentDao;
import cn.sina.elec.dao.ElecCommonMsgDao;
import cn.sina.elec.domain.ElecCommonMsg;
import cn.sina.elec.domain.ElecCommonMsgContent;
import cn.sina.elec.service.ElecCommonMsgService;
import cn.sina.elec.utils.StringUtils;

@Service(ElecCommonMsgService.ELEC_SERVICE)
@Transactional(readOnly = true)
public class ElecCommonMsgServiceImp implements ElecCommonMsgService {
	
	@Resource(name=ElecCommonMsgDao.DAO_IMP)
	private ElecCommonMsgDao elecCommonMsgDao;
	
	/**运行监控数据表Dao*/
	@Resource(name=ElecCommonMsgContentDao.DAO_IMP)
	ElecCommonMsgContentDao elecCommonMsgContentDao;

	@Override
	public ElecCommonMsg getCommonMsg() {
		List<ElecCommonMsg> list = elecCommonMsgDao.conditionalQuery("", null, null);
		ElecCommonMsg commonMsg = null;
		if(list!=null && list.size()>0){
			commonMsg = list.get(0);
			/**********************************************begin**********************************************************/
			//获取数据内容
			//以类型作为条件，按照显示顺序升序排列，查询站点运行情况的数据
			String stationCondition = " and o.type=?";
			Object [] stationParams = {"1"};
			Map<String, String> stationOrderby = new LinkedHashMap<String, String>();
			stationOrderby.put("o.orderby", "asc");
			List<ElecCommonMsgContent> stationList = elecCommonMsgContentDao.conditionalQuery(stationCondition, stationParams, stationOrderby);
			//获取返回的数据（拼装之后）
			String stationContent = "";
			if(stationList!=null && stationList.size()>0){
				for(ElecCommonMsgContent elecCommonMsgContent:stationList){
					String content = elecCommonMsgContent.getContent();
					stationContent += content;
				}
			}
			//将数据赋值给页面的属性（站点运行情况）
			commonMsg.setStationRun(stationContent);
			/**********************************************************************************/
			//以类型作为条件，按照显示顺序升序排列，查询站点运行情况的数据
			String devCondition = " and o.type=?";
			Object [] devParams = {"2"};
			Map<String, String> devOrderby = new LinkedHashMap<String, String>();
			devOrderby.put("o.orderby", "asc");
			List<ElecCommonMsgContent> devList = elecCommonMsgContentDao.conditionalQuery(devCondition, devParams, devOrderby);
			//获取返回的数据（拼装之后）
			String devContent = "";
			if(devList!=null && devList.size()>0){
				for(ElecCommonMsgContent elecCommonMsgContent:devList){
					String content = elecCommonMsgContent.getContent();
					devContent += content;
				}
			}
			//将数据赋值给页面的属性（设备运行情况）
			commonMsg.setDevRun(devContent);
			/**********************************************end**********************************************************/
		}
		return commonMsg;
	}

	@Transactional(rollbackFor={Exception.class, RuntimeException.class},isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveCommonMsg(ElecCommonMsg elecCommonMsg) {
		//查询运行监控表，获取运行监控表的数据，返回List<ElecCommonMsg> list，使用list作为判断数据库中是否存在数据
		List<ElecCommonMsg> list = elecCommonMsgDao.conditionalQuery("", null, null);
		//如果list!=null:数据表表中存在数据，获取页面传递的2个参数，组织PO对象，执行更新（update）
		if(list!=null && list.size()>0){
			//方案二：组织PO对象，执行update
			ElecCommonMsg commonMsg = list.get(0);
			commonMsg.setStationRun("1");//1表示站点运行情况
			commonMsg.setDevRun("2");//2表示设备运行情况
			commonMsg.setCreateDate(new Date());
		}
		//如果list==null:数据表表中不存在数据，获取页面传递的2个参数，组织PO对象，执行新增（save）
		else{
			ElecCommonMsg commonMsg = new ElecCommonMsg();
			commonMsg.setCreateDate(new Date());
			commonMsg.setStationRun("1");//1表示站点运行情况
			commonMsg.setDevRun("2");//2表示设备运行情况
			elecCommonMsgDao.save(commonMsg);
		}
		/**********************************************begin**********************************************************/
		//保存到数据表中
		//删除之前的数据
		List<ElecCommonMsgContent> contentList = elecCommonMsgContentDao.conditionalQuery("", null, null);
		elecCommonMsgContentDao.deleteByCollection(contentList);
		//从页面获取站点运行情况和设备运行情况，根据站点运行情况，和设备运行情况保存数据
		String stationRun = elecCommonMsg.getStationRun();
		String devRun = elecCommonMsg.getDevRun();
		//调用StirngUtil的方法，分割字符串
		List<String> stationList = StringUtils.getContentByList(stationRun, 50);
		if(stationList!=null && stationList.size()>0){
			for(int i=0;i<stationList.size();i++){
				ElecCommonMsgContent elecCommonMsgContent = new ElecCommonMsgContent();
				elecCommonMsgContent.setType("1");//1表示站点运行情况
				elecCommonMsgContent.setContent(stationList.get(i));
				elecCommonMsgContent.setOrderby(i+1);
				elecCommonMsgContentDao.save(elecCommonMsgContent);
			}
		}
		List<String> devList = StringUtils.getContentByList(devRun, 50);
		if(devList!=null && devList.size()>0){
			for(int i=0;i<devList.size();i++){
				ElecCommonMsgContent elecCommonMsgContent = new ElecCommonMsgContent();
				elecCommonMsgContent.setType("2");//2表示设备运行情况
				elecCommonMsgContent.setContent(devList.get(i));
				elecCommonMsgContent.setOrderby(i+1);
				elecCommonMsgContentDao.save(elecCommonMsgContent);
			}
		}
		/**********************************************end**********************************************************/
	}
}

	
	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-18
	 * @Description: 查询监控信息
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: 
	 *//*
	@Override
	public ElecCommonMsg getCommonMsg() {
		List<ElecCommonMsg> list = commonMsgDao.conditionalQuery("",null,null);
		ElecCommonMsg commonMsg = null;
		if(list!=null && list.size()>0){
			commonMsg = list.get(0);
		}
		return commonMsg;
	}

	*//**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-18
	 * @Description: 保存或更新提交的监控信息.如果数据库中没有信息则提交,有则更新
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: 
	 * @Error: 事务无法提交,在CommonDao中使用spring回调函数就ok了
	 *//*
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveCommonMsg(ElecCommonMsg elecCommonMsg) {
		List<ElecCommonMsg> list = commonMsgDao.conditionalQuery("",null,null);
		//判断数据库中是否有数据,也就是说第一次提交是直接保存的,不需要更新
		if(list!=null && list.size()>0){
			*//**
			 * 这里容易犯错的点:我们应该如何更新
			 * 	 1.获取list的第一个元素,用set方法更新(通过缓存对象更新)
			 * 	 2.直接用dao保存当前对象(直接将对象更新到数据库)
			 * 经过分析:方案2是错误的,因为从数据库中查询出来的ElecCommonMsg显示到页面,页面再提交到数据库的ElecCommonMsg
			 * 	 他们的主键id都是一样的,而这两个对象都是持久化对象,在hibernate的缓存是不允许这种情况的
			 *//*
			ElecCommonMsg commonMsg = list.get(0);
			commonMsg.setCreateDate(new Date());
			commonMsg.setDevRun(elecCommonMsg.getDevRun());
			commonMsg.setStationRun(elecCommonMsg.getStationRun());
		}else{
			elecCommonMsg.setCreateDate(new Date());
			commonMsgDao.save(elecCommonMsg);
		}
	}*/

