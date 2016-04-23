package cn.sina.elec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.sina.elec.dao.ElecFileUploadDao;
import cn.sina.elec.domain.ElecFileUpload;
import cn.sina.elec.service.ElecFileUploadService;
import cn.sina.elec.utils.DateUtils;
import cn.sina.elec.utils.FileUtils;
import cn.sina.elec.utils.lucene.LuceneUtils;

@Service(ElecFileUploadService.ELEC_SERVICE)
@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=true)
public class ElecFileUploadServiceImp implements ElecFileUploadService{

	@Resource(name=ElecFileUploadDao.DAO_IMP)
	ElecFileUploadDao elecFileUploadDao;

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-1
	 * @Description: 完成资料上传功能
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: void
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveFiles(ElecFileUpload elecFileUpload) {
		File[] uploads = elecFileUpload.getUploads();
		String[] fileNames = elecFileUpload.getUploadsFileName();
		String[] comments = elecFileUpload.getComments();
		String progressTime = DateUtils.formattoString(new Date());
		String belongTo = elecFileUpload.getBelongTo();
		String projID = elecFileUpload.getProjId();
		if(uploads!=null && uploads.length>0){
			for (int i = 0; i < uploads.length; i++) {
				//组织PO对象
				ElecFileUpload upload = new ElecFileUpload();
				upload.setBelongTo(belongTo);
				upload.setProjId(projID);
				upload.setProgressTime(progressTime);
				upload.setFileName(fileNames[i]);
				String URL = FileUtils.uplaodAndReturnPath(uploads[i], fileNames[i], "资料图纸管理");
				upload.setFileURL(URL);
				upload.setComment(comments[i]);
				elecFileUploadDao.save(upload);
				//同时保存到lucene的索引库中
				LuceneUtils.addIndex(upload);
			}
		}
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-1
	 * @Description: 通过所属单位和图纸类别来获取已经上传的文件
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return:  List<ElecFileUpload> 
	 */
	@Override
	public List<ElecFileUpload> getUploadedByID(ElecFileUpload elecFileUpload) {
		String projId = elecFileUpload.getProjId();
		String belongTo = elecFileUpload.getBelongTo();
		StringBuilder condition = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotBlank(belongTo) && !belongTo.equals("0")){
			condition.append(" and belongTo = ?");
			params.add(belongTo);
		}
		if(StringUtils.isNotBlank(projId) && !projId.equals("0")){
			condition.append(" and projId = ?");
			params.add(projId);
		}
		Map<String,String> orderBy = new LinkedHashMap<String, String>();
		return elecFileUploadDao.sqlQuery(condition.toString(), params.toArray(), orderBy);
	}

	@Override
	public ElecFileUpload getFielById(Integer seqID) {
		return elecFileUploadDao.findById(seqID);
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-2
	 * @Description:dataChartIndex.jsp的条件查询
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: List<ElecFileUpload> 
	 */
	@Override
	public List<ElecFileUpload> retrieval(ElecFileUpload elecFileUpload) {
		String projID = elecFileUpload.getProjId();
		String belongTo = elecFileUpload.getBelongTo();
		String queryString = elecFileUpload.getQueryString();
		//List<ElecFileUpload>是从lucene的索引库中查询出来的,再根据里面的主键ID查数据库
		List<ElecFileUpload> docList = LuceneUtils.searcherIndexByCondition(projID,belongTo,queryString);
		
		//定义返回结果的List,它是从数据库中查询出来的集合
		List<ElecFileUpload> returnList = new ArrayList<ElecFileUpload>();
		if(docList!=null && docList.size()>0){
			for (ElecFileUpload fileUpload : docList) {
				//通过主键查询
				Integer seqID = fileUpload.getSeqID();
				String condition = " and o.seqID = ?";
				Object[] params = {seqID};
				//采用sql查询,这样就不用转换projID和belongTo了
				List<ElecFileUpload> resultList = elecFileUploadDao.sqlQuery(condition, params, null);
				//主键查询只有惟一值
				ElecFileUpload upload = resultList.get(0);
				//这时候还要设置高亮字段,upload只是根据主键查出来的,fileUpload中有高亮的标签
				upload.setFileName(fileUpload.getFileName());
				upload.setComment(fileUpload.getComment());
				//也可以add(resultList.get(0))
				returnList.add(upload);
			}
		}
		return docList;
	}
}
