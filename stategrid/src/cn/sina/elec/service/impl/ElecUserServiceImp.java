package cn.sina.elec.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.sina.elec.dao.ELecUserFileDao;
import cn.sina.elec.dao.ElecExportFieldsDao;
import cn.sina.elec.dao.ElecSystemDDLDao;
import cn.sina.elec.dao.ElecUserDao;
import cn.sina.elec.domain.ELecUserFile;
import cn.sina.elec.domain.ElecExportFields;
import cn.sina.elec.domain.ElecRole;
import cn.sina.elec.domain.ElecSystemDDL;
import cn.sina.elec.domain.ElecUser;
import cn.sina.elec.service.ElecUserService;
import cn.sina.elec.utils.FileUtils;
import cn.sina.elec.utils.ListUtils;
import cn.sina.elec.utils.MD5keyBean;
import cn.sina.elec.utils.page.PageInfo;

@Service(ElecUserService.ELEC_SERVICE)
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = true)
public class ElecUserServiceImp implements ElecUserService {

	@Resource(name = ElecUserDao.DAO_IMP)
	ElecUserDao elecUserDao;
	@Resource(name = ELecUserFileDao.DAO_IMP)
	ELecUserFileDao eLecUserFileDao;
	@Resource(name = ElecSystemDDLDao.DAO_IMP)
	ElecSystemDDLDao elecSystemDDLDao;
	@Resource(name = ElecExportFieldsDao.DAO_IMP)
	ElecExportFieldsDao exportFieldsDao;

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 跳转到用户管理主页.查询所属部门的数据字典,实现用户的按条件查询
	 * @Version: V1.0.0
	 * @Params: elecUser
	 * @Return: List<ElecUser>
	 */
	@Override
	public List<ElecUser> queryUser(ElecUser elecUser) {
		StringBuilder condition = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Map<String, String> orderBy = new LinkedHashMap<String, String>();

		String userName = elecUser.getUserName();
		String jctID = elecUser.getJctID();
		Date onDutyDateBegin = elecUser.getOnDutyDateBegin();
		Date onDutyDateEnd = elecUser.getOnDutyDateEnd();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username like ?");
			params.add("%" + userName + "%");
		}
		if (StringUtils.isNotBlank(jctID)) {
			condition.append(" and o.jctID = ?");
			params.add(jctID);
		}
		if (onDutyDateBegin != null) {
			condition.append(" and o.onDutyDate >= ?");
			params.add(onDutyDateBegin);
		}
		if (onDutyDateEnd != null) {
			condition.append(" and o.onDutyDate <= ?");
			params.add(onDutyDateEnd);
		}
		orderBy.put("o.onDutyDate", "asc");
		/*
		 * 方案一,查出来用户,再把ddlCode转成ddlName List<ElecUser> list =
		 * elecUserDao.conditionalQuery(condition.toString(), params.toArray(),
		 * orderBy);
		 * 
		 * 查出来又发现页面显示效果不好,因为我们把性别,职位都放在了数据字典中,user表存的只是ddlCode,我们已经进行转换
		 * 这种数据字典的转换肯定是经常的,比较重要
		 * 
		 * this.ddlCodeConvert(list);
		 */
		/*
		 * 方案二:直接用sql的左外连接查询ddlName List<ElecUser> list =
		 * elecUserDao.useSQLQuery(condition.toString(), params.toArray(),
		 * orderBy);
		 */

		/*
		 * 采用带分页的查询 2015年12月4日14:59:03
		 */
		HttpServletRequest request = ServletActionContext.getRequest();
		PageInfo pageInfo = new PageInfo(request);
		List<ElecUser> list = elecUserDao.sqlQueryWithPaging(
				condition.toString(), params.toArray(), orderBy, pageInfo);
		request.setAttribute("page", pageInfo.getPageBean());
		return list;
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 完成从性别的sexID=1转换到男的功能
	 * @Version: V1.0.0
	 * @Params: List<ElecUser> list
	 * @Return:
	 */
	@SuppressWarnings("unused")
	private void ddlCodeConvert(List<ElecUser> list) {
		if (list != null && list.size() > 0) {
			for (ElecUser elecUser : list) {
				// 分别转换性别和职位的ddlCode
				String sexID = elecSystemDDLDao.convertByKeywordAndDdlCode(
						"性别", elecUser.getSexID());
				elecUser.setSexID(sexID);
				String postID = elecSystemDDLDao.convertByKeywordAndDdlCode(
						"职位", elecUser.getPostID());
				elecUser.setPostID(postID);
			}
		}
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-21
	 * @Description: 登录名校验
	 * @Version: V1.0.0
	 * @Params: logonName
	 * @Return: String
	 */
	@Override
	public String checkUser(String logonName) {
		// 要返回的信息
		String message = "";
		/**
		 * 从数据库从查询出该登录名对应的用户
		 */
		if (StringUtils.isNotBlank(logonName)) {
			// 1.组织查询条件
			String condition = " and o.logonName = ?";
			Object[] params = { logonName };
			List<ElecUser> user = elecUserDao.conditionalQuery(condition,
					params, null);
			// 2.如果集合不为空,说明已经存在该登录名
			if (user != null && user.size() > 0) {
				message = "2";
			} else {
				message = "3";
			}
		} else {
			message = "1";
		}

		return message;
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 完成对页面注册用户数据的保存 分两步完成:保存用户和保存用户文件
	 *               因为两者是一对多关系,在文件中只需要有用户的userID就行
	 * @Version: V1.0.0
	 * @Params:
	 * @Return: String
	 * @Md5加密:对要保存的用户的密码加密,到时候在编辑页面上显示的也就加密后的密码,那么就有一个问题, 
	 *         如果用户没有修改密码,提交上来的还是加密后的,再经过加密,数据库中保存的就不能用了,
	 *         因为下次用户登录肯定输不对,解决方法是在ElecUser加属性,用了判断提交的密码和原来的密码是否一致,是否有必要修改
	 */
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void saveUser(ElecUser elecUser) {

		// 1：遍历多个附件，组织附件的PO对象，完成文件上传，保存用户的附件（多条数据），建立附件表和用户表的关联关系
		this.saveUserFile(elecUser);
		// 添加md5的密码加密
		this.encrypt(elecUser);
		// 获取页面传递的userID
		String userID = elecUser.getUserID();
		// 更新（update）
		if (StringUtils.isNotBlank(userID)) {
			// 组织PO对象，执行更新（1条）
			elecUserDao.update(elecUser);
		}
		// 新增（save）
		else {
			// 2：组织PO对象，保存用户（1条数据）
			elecUserDao.save(elecUser);
		}
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-23
	 * @Description: 加密用户密码
	 * @Version: V1.0.0
	 * @Params:
	 * @Return: void
	 */
	private void encrypt(ElecUser elecUser) {

		// 获取加密前的密码
		String logonPwd = elecUser.getLogonPwd();
		// 加密后的密码
		String md5password = "";
		// 如果没有填写密码，设置初始密码为123
		if (StringUtils.isBlank(logonPwd)) {
			logonPwd = "123";
		}
		// 获取修改用户之前的密码
		String password = elecUser.getMd5pwd();
		// 编辑的时候，没有修改密码的时候，是不需要加密的
		if (password != null && password.equals(logonPwd)) {
			md5password = logonPwd;
		}
		// 新增的时候，或者是编辑的时候修改密码的时候，需要加密的
		else {
			// 使用md5加密的时候
			MD5keyBean md5keyBean = new MD5keyBean();
			md5password = md5keyBean.getkeyBeanofStr(logonPwd);
		}
		// 放置到ElecUser对象中
		elecUser.setLogonPwd(md5password);
	}

	@SuppressWarnings("unused")
	private void saveUserFile(ElecUser elecUser) {
		File[] uploads = elecUser.getUploads();
		String[] uploadFileName = elecUser.getUploadsFileName();
		String[] uploadContentType = elecUser.getUploadsContentType();
		Date progressTime = new Date();
		// 遍历,一个一个保存
		if (uploads != null && uploads.length > 0) {
			for (int i = 0; i < uploads.length; i++) {
				// 组织ElecUserFile的PO对象
				ELecUserFile eLecUserFile = new ELecUserFile();
				eLecUserFile.setElecUser(elecUser);
				eLecUserFile.setFileName(uploadFileName[i]);
				eLecUserFile.setProgressTime(progressTime);
				String fileURL = FileUtils.uplaodAndReturnPath(uploads[i],
						uploadFileName[i], "用户管理");
				eLecUserFile.setFileURL(fileURL);
				eLecUserFileDao.save(eLecUserFile);
			}
		}
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-22
	 * @Description: 根据id查询用户
	 * @Version: V1.0.0
	 * @Params:
	 * @Return: String
	 */

	@Override
	public ElecUser searchUserByID(String userID) {
		return elecUserDao.findById(userID);
	}

	@Override
	public List<ElecSystemDDL> getDdlList(String keyword) {
		// 查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(keyword)) {
			condition += " and o.keyword=?";
			paramsList.add(keyword);
		}
		Object[] params = paramsList.toArray();
		// 排序
		Map<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.ddlCode", "asc");
		List<ElecSystemDDL> list = elecSystemDDLDao.conditionalQuery(condition,
				params, orderby);
		return list;
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-11-23
	 * @Description: 用户的批量删除
	 * @Version: V1.0.0
	 * @Params:
	 * @Return: String 删除分析: 批量删除,那么要传过来的就是一个String[]
	 *          userIDs,但是ElecUser模型中没有这个属性
	 *          好在struts给我们提供了一种方式:上传的数据用", "的形式组织成了字符串
	 *          删除用户就要把对应的文件也删除,文件的删除有细化为: 1.删除磁盘上的文件 2.删除数据库中的数据
	 *          这一步不需要我们自己去做,有一个级联删除的属性 <set name="elecUserFiles"
	 *          cascade="delete" />
	 */
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteUser(ElecUser elecUser) {
		// userID现在是一组ID,用", "分割
		String userID = elecUser.getUserID();
		String[] uids = userID.split(", ");
		/*
		 * 删除用户在磁盘上的文件 1.遍历该数组
		 */
		if (uids != null && uids.length > 0) {
			String realpath = ServletActionContext.getServletContext()
					.getRealPath("");
			for (String uid : uids) {
				// 2.查询并删除
				ElecUser user = elecUserDao.findById(uid);
				// 3.获取该用户的文件
				Set<ELecUserFile> userFiles = user.getElecUserFiles();
				if (userFiles != null && userFiles.size() > 0) {
					// 遍历删除磁盘文件
					for (ELecUserFile eLecUserFile : userFiles) {
						String userFilePath = realpath
								+ eLecUserFile.getFileURL();
						File userFile = new File(userFilePath);
						if (userFile.exists()) {
							// 4.删除用户文件
							userFile.delete();
						}
					}
				}
				// --- 删除用户的同时,还要把用户的角色信息删除了 -- begin --2015年11月26日11:11:29
				// 记住inverse=true,用户已经放弃了操作角色的权利,只能用角色删除用户
				Set<ElecRole> roles = user.getElecRoles();
				if (roles != null && roles.size() > 0) {
					// 遍历每一个角色,只要有当前用户,就删除
					for (ElecRole elecRole : roles) {
						// 不能用clear,会把别的用户也给删了
						elecRole.getElecUsers().remove(user);
					}
				}

				// ----------------------------------- end
				// ------------------------
			}
		}
		// <set name="elecUserFiles" cascade="delete" />,不用删除数据库中的文件数据了
		elecUserDao.deleteByIds(uids);
	}

	/**
	 * 根据登录名查询用户
	 */
	@Override
	public ElecUser getUserByLogonName(String name) {
		String condition = " and logonName = ?";
		Object[] params = { name };
		List<ElecUser> users = elecUserDao.conditionalQuery(condition, params,
				null);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-4
	 * @Description: 查询出用户管理的导出设置,belongTo为"5-1"
	 * @Version: V1.0.0
	 * @Params:
	 * @Return: ArrayList<String>
	 */
	@Override
	public ArrayList<String> getExcelFieldName() {
		ElecExportFields exportFields = exportFieldsDao.findById("5-1");
		String expName = exportFields.getExpNameList();
		return (ArrayList<String>) ListUtils.toList(expName, "#");
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-4
	 * @Description: 根据用户搜索的条件和导出设置中的字段来查询user select 导出设置中的字段 from elec where
	 *               用户搜索的条件 用户搜索的条件包括:用户姓名,所属单位和入职时间段
	 * @Version: V1.0.0
	 * @Params:
	 * @Return: ArrayList<ArrayList<String>>:excel的每一行数据(导出的数据)
	 */
	@Override
	public ArrayList<ArrayList<String>> getExcelData(ElecUser elecUser) {
		// 返回值
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();

		ElecExportFields exportFields = exportFieldsDao.findById("5-1");
		// 获取excel表头字段
		List<String> zList = ListUtils.toList(exportFields.getExpNameList(),"#");
		// 导出设置中的字段
		String expField = exportFields.getExpFieldName();
		// logonPwd#birthday#logonName转换为logonPwd,birthday,logonName,这可以作为select的查询字段
		expField = expField.replace("#", ",");
		// 组织查询条件
		StringBuilder condition = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Map<String, String> orderBy = new LinkedHashMap<String, String>();

		String userName = elecUser.getUserName();//页面传递过来的如果是中文会乱码
		try {
			//处理乱码
			userName = URLDecoder.decode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jctID = elecUser.getJctID();
		Date onDutyDateBegin = elecUser.getOnDutyDateBegin();
		Date onDutyDateEnd = elecUser.getOnDutyDateEnd();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username like ?");
			params.add("%" + userName + "%");
		}
		if (StringUtils.isNotBlank(jctID)) {
			condition.append(" and o.jctID = ?");
			params.add(jctID);
		}
		if (onDutyDateBegin != null) {
			condition.append(" and o.onDutyDate >= ?");
			params.add(onDutyDateBegin);
		}
		if (onDutyDateEnd != null) {
			condition.append(" and o.onDutyDate <= ?");
			params.add(onDutyDateEnd);
		}
		orderBy.put("o.onDutyDate", "asc");
		@SuppressWarnings("rawtypes")
		List userList = elecUserDao.excelQueryByField(condition.toString(),params.toArray(), orderBy, expField);
		// 接下来的思路是:把userList中报表需要的字段放入到一个list中,作为一条数据,再把这个list放入总的list中,作为整个excel
		// 组织poi报表需要的数据ArrayList<ArrayList<String>>
		if (userList != null && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				// 使用数组用来存放每一行的数据
				Object[] arrays = null;
				// 说明投影查询是多个字段，此时应该返回Object []
				if (expField.contains(",")) {
					arrays = (Object[]) userList.get(i);
				}
				// 说明投影查询是一个字段，此时应该返回Object对象
				else {
					arrays = new Object[1];
					arrays[0] = userList.get(i);
				}
				// 将Object[]转换成ArrayList<String>，用来存放每一行的数据
				ArrayList<String> data = new ArrayList<String>();
				if (arrays != null && arrays.length > 0) {
					for (int j = 0; j < arrays.length; j++) {
						// 存放每个字段的值
						Object o = arrays[j];
						if (zList != null && zList.get(j).equals("性别")
								|| zList.get(j).equals("所属单位")
								|| zList.get(j).equals("是否在职")
								|| zList.get(j).equals("职位")) {
							data.add(o != null ? elecSystemDDLDao.
									convertByKeywordAndDdlCode(zList.get(j), o.toString()) : "");
						} else {
							data.add(o != null ? o.toString() : "");
						}
					}
				}
				// 将每一行的数据，组织成ArrayList<ArrayList<String>>
				fieldData.add(data);
			}
		}
		return fieldData;
	}

	/**
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-6
	 * @Description: 导入excel数据到数据库,jxl报表
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: 
	 */
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void imporExcel(List<ElecUser> userList) {
		elecUserDao.saveList(userList);
	}

	/**
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-6
	 * @Description: 查询各单位人数
	 * @Version: V1.0.0
	 * @Params: 
	 * @Return: List<Object[]>
	 */
	@Override
	public List<Object[]> showUserview(String keyword, String ddlCode) {
		return elecUserDao.showUserview(keyword,ddlCode);
	}
}
