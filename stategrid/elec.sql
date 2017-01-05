/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.5.36 : Database - stategrid
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`stategrid` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `stategrid`;

/*Table structure for table `elec_commonmsg` */

DROP TABLE IF EXISTS `elec_commonmsg`;

CREATE TABLE `elec_commonmsg` (
  `comID` varchar(50) NOT NULL,
  `stationRun` varchar(5000) DEFAULT NULL,
  `devRun` varchar(5000) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`comID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_commonmsg` */

insert  into `elec_commonmsg`(`comID`,`stationRun`,`devRun`,`createDate`) values ('4028818759645d5901596493672e0004','1','2','2017-01-03 21:50:33');

/*Table structure for table `elec_commonmsg_content` */

DROP TABLE IF EXISTS `elec_commonmsg_content`;

CREATE TABLE `elec_commonmsg_content` (
  `comID` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL,
  `content` varchar(32) NOT NULL,
  `orderby` int(32) NOT NULL,
  PRIMARY KEY (`comID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_commonmsg_content` */

insert  into `elec_commonmsg_content`(`comID`,`type`,`content`,`orderby`) values ('40288187596497c601596498837e0001','1','<p>\r\n	《长城》</p>\r\n',1),('40288187596497c60159649883820002','2','<p>\r\n	《新中国建立》</p>\r\n',1);

/*Table structure for table `elec_exportfields` */

DROP TABLE IF EXISTS `elec_exportfields`;

CREATE TABLE `elec_exportfields` (
  `belongTo` varchar(10) NOT NULL,
  `expNameList` varchar(5000) DEFAULT NULL,
  `expFieldName` varchar(5000) DEFAULT NULL,
  `noExpNameList` varchar(5000) DEFAULT NULL,
  `noExpFieldName` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`belongTo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_exportfields` */

insert  into `elec_exportfields`(`belongTo`,`expNameList`,`expFieldName`,`noExpNameList`,`noExpFieldName`) values ('5-1','用户姓名#所属单位#性别#联系地址#联系电话#电子邮箱#职位','userName#jctID#sexID#address#contactTel#email#postID','登录名#密码#出生日期#手机#是否在职#入职时间#离职时间#备注','logonName#logonPwd#birthday#mobile#isDuty#onDutyDate#offDutyDate#remark'),('5-3','站点运行情况#设备运行情况#创建日期','stationRun#devRun#createDate',NULL,NULL);

/*Table structure for table `elec_fileupload` */

DROP TABLE IF EXISTS `elec_fileupload`;

CREATE TABLE `elec_fileupload` (
  `SeqID` int(11) NOT NULL,
  `ProjID` varchar(50) DEFAULT NULL,
  `BelongTo` varchar(50) DEFAULT NULL,
  `FileName` varchar(50) DEFAULT NULL,
  `FileURL` varchar(1000) DEFAULT NULL,
  `ProgressTime` varchar(20) DEFAULT NULL,
  `COMMENT` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`SeqID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_fileupload` */

insert  into `elec_fileupload`(`SeqID`,`ProjID`,`BelongTo`,`FileName`,`FileURL`,`ProgressTime`,`COMMENT`) values (1,'1','1','2-160323113221.doc','/upload/2016/04/19/资料图纸管理/GA3H2L6Q8181WOZ7W3MCQNTIPSHHJBOS.doc','2016-04-19 22:20:08','么么哒');

/*Table structure for table `elec_popedom` */

DROP TABLE IF EXISTS `elec_popedom`;

CREATE TABLE `elec_popedom` (
  `MID` varchar(32) NOT NULL,
  `pid` varchar(32) NOT NULL,
  `NAME` varchar(500) DEFAULT NULL,
  `url` varchar(5000) DEFAULT NULL,
  `icon` varchar(5000) DEFAULT NULL,
  `target` varchar(500) DEFAULT NULL,
  `isParent` tinyint(1) DEFAULT NULL,
  `isMenu` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`MID`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_popedom` */

insert  into `elec_popedom`(`MID`,`pid`,`NAME`,`url`,`icon`,`target`,`isParent`,`isMenu`) values ('aa','0','技术设施维护管理','','../images/MenuIcon/jishusheshiweihuguanli.gif','',1,1),('ab','aa','仪器设备管理','','../images/MenuIcon/yiqishebeiguanli.gif','mainFrame',0,1),('ac','aa','设备校准检修','','../images/MenuIcon/shebeijiaozhunjianxiu.gif','mainFrame',0,1),('ad','aa','设备购置计划','','../images/MenuIcon/shebeigouzhijihua.gif','mainFrame',0,1),('ae','0','技术资料图纸管理','','../images/MenuIcon/jishuziliaotuzhiguanli.gif','',1,1),('af','ae','资料图纸管理','../datachart/elecFileUploadAction_home.do','../images/MenuIcon/ziliaotuzhiguanli.gif','mainFrame',0,1),('ag','0','站点设备运行管理','','../images/MenuIcon/zhuandianshebeiyunxingguanli.gif','',1,1),('ah','ag','站点基本信息','','../images/MenuIcon/zhandianjibenxinxi.gif','mainFrame',0,1),('ai','ag','运行情况','','../images/MenuIcon/yunxingqingkuang.gif','mainFrame',0,1),('aj','ag','维护情况','','../images/MenuIcon/weihuqingkuang.gif','mainFrame',0,1),('ak','0','监测台建筑管理','','../images/MenuIcon/jiancetaijianzhuguanli.gif','',1,1),('al','ak','监测台建筑管理','','../images/MenuIcon/jiancetaijianzhu.gif','mainFrame',0,1),('am','0','系统管理','','../images/MenuIcon/xitongguanli.gif','',1,1),('an','am','用户管理','../system/elecUserAction_home.do','../images/MenuIcon/yonghuguanli.gif','mainFrame',0,1),('ao','am','角色管理','../system/elecRoleAction_home.do','../images/MenuIcon/jueseguanli.gif','mainFrame',0,1),('ap','am','运行监控','../system/elecCommonMsgAction_home.do','../images/MenuIcon/daibanshiyi.gif','mainFrame',0,1),('aq','am','数据字典维护','../system/elecSystemDDLAction_home.do','../images/MenuIcon/shujuzidianguanli.gif','mainFrame',0,1),('ar','0','审批流转','','../images/MenuIcon/shenpiliuzhuanguanli.gif','',1,1),('as','ar','审批流程管理','../workflow/elecProcessDefinitionAction_home.do','../images/MenuIcon/shenpiliuchengguanli.gif','mainFrame',0,1),('at','ar','申请模板管理','../workflow/elecApplicationTemplateAction_home.do','../images/MenuIcon/shenqingmobanguanli.gif','mainFrame',0,1),('au','ar','起草申请','../workflow/elecApplicationFlowAction_home.do','../images/MenuIcon/qicaoshenqing.gif','mainFrame',0,1),('av','ar','待我审批','../workflow/elecApplicationFlowAction_myTaskHome.do','../images/MenuIcon/daiwoshenpi.gif','mainFrame',0,1),('aw','ar','我的申请查询','../workflow/elecApplicationFlowAction_myApplicationHome.do','../images/MenuIcon/wodeshenqingchaxun.gif','mainFrame',0,1),('ba','0','系统登录','','','',1,0),('bb','ba','首页显示','/system/elecMenuAction_menuHome.do','','',0,0),('bc','ba','标题','/system/elecMenuAction_title.do','','',0,0),('bd','ba','菜单','/system/elecMenuAction_left.do','','',0,0),('be','ba','加载左侧树型结构','/system/elecMenuAction_showMenu.do','','',0,0),('bf','ba','改变框架','../system/elecMenuAction_change.do','','',0,0),('bg','ba','加载主显示页面','../system/elecMenuAction_loading.do','','',0,0),('bh','ba','站点运行','../system/elecMenuAction_alermStation.do','','',0,0),('bi','ba','设备运行','../system/elecMenuAction_alermDevice.do','','',0,0),('bj','ba','重新登录','../system/elecMenuAction_logout.do','','',0,0),('ca','0','运行监控','','','',1,0),('cb','ca','保存','/system/elecCommonMsgAction_save.do','','',0,0),('cc','ca','ajax进度条','/system/elecCommonMsgAction_progressBar.do','','',0,0),('da','0','导出设置','','','',1,0),('db','da','导出设置设置','/system/elecExportFieldsAction_setExportExcel.do','','',0,0),('dc','da','导出设置保存','/system/elecExportFieldsAction_saveSetExportExcel.do','','',0,0),('ea','0','数据字典','','','',1,0),('eb','ea','编辑','/system/elecSystemDDLAction_edit.do','','',0,0),('ec','ea','保存','/system/elecSystemDDLAction_save.do','','',0,0),('fa','0','用户管理','','','',1,0),('fb','fa','新增','/system/elecUserAction_add.do','','',0,0),('fc','fa','保存','/system/elecUserAction_save.do','','',0,0),('fd','fa','编辑','/system/elecUserAction_edit.do','','',0,0),('fe','fa','删除','/system/elecUserAction_delete.do','','',0,0),('ff','fa','验证登录名','/system/elecUserAction_checkUser.do','','',0,0),('fg','fa','导出excel','/system/elecUserAction_exportExcel.do','','',0,0),('fh','fa','excel导入页面','/system/elecUserAction_importPage.do','','',0,0),('fi','fa','excel导入','/system/elecUserAction_importData.do','','',0,0),('fj','fa','人员统计(单位)','/system/elecUserAction_chartUser.do','','',0,0),('fk','fa','人员统计(性别)','/system/elecUserAction_chartUserFCF.do','','',0,0),('fl','fa','联动查询单位名称','/system/elecUserAction_findJctUnit.do','','',0,0),('ga','0','角色管理','','','',1,0),('gb','ga','编辑','/system/elecRoleAction_edit.do','','',0,0),('gc','ga','保存','/system/elecRoleAction_save.do','','',0,0),('ha','0','申请流程管理','','','',1,0),('hb','ha','部署流程定义','/workflow/elecProcessDefinitionAction_add.do','','',0,0),('hc','ha','保存','/workflow/elecProcessDefinitionAction_save.do','','',0,0),('hd','ha','查看流程图','/workflow/elecProcessDefinitionAction_downloadProcessImage.do','','',0,0),('he','ha','删除流程定义','/workflow/elecProcessDefinitionAction_delete.do','','',0,0),('ia','0','申请模板管理','','','',1,0),('ib','ia','新增','/workflow/elecApplicationTemplateAction_add.do','','',0,0),('ic','ia','新增保存','/workflow/elecApplicationTemplateAction_save.do','','',0,0),('id','ia','编辑','/workflow/elecApplicationTemplateAction_edit.do','','',0,0),('ie','ia','编辑保存','/workflow/elecApplicationTemplateAction_update.do','','',0,0),('if','ia','删除','/workflow/elecApplicationTemplateAction_delete.do','','',0,0),('ig','ia','下载','/workflow/elecApplicationTemplateAction_download.do','','',0,0),('ja','0','申请流程管理','','','',1,0),('jb','ja','提交申请页面','/workflow/elecApplicationFlowAction_submitApplication.do','','',0,0),('jc','ja','提交申请','/workflow/elecApplicationFlowAction_saveApplication.do','','',0,0),('jd','ja','我的申请查询首页','/workflow/elecApplicationFlowAction_myApplicationHome.do','','',0,0),('je','ja','待我审批首页','/workflow/elecApplicationFlowAction_myTaskHome.do','','',0,0),('jf','ja','跳转审批处理页面','/workflow/elecApplicationFlowAction_flowApprove.do','','',0,0),('jg','ja','下载','/workflow/elecApplicationFlowAction_download.do','','',0,0),('jh','ja','审核','/workflow/elecApplicationFlowAction_approve.do','','',0,0),('ji','ja','审核历史','/workflow/elecApplicationFlowAction_approvedHistory.do','','',0,0),('jj','ja','查看流程图','/workflow/elecApplicationFlowAction_approvedHistoryPic.do','','',0,0),('jk','ja','查看流程执行位置图片','/workflow/elecApplicationFlowAction_processImage.do','','',0,0);

/*Table structure for table `elec_role` */

DROP TABLE IF EXISTS `elec_role`;

CREATE TABLE `elec_role` (
  `roleID` varchar(32) NOT NULL,
  `roleName` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_role` */

insert  into `elec_role`(`roleID`,`roleName`) values ('1','系统管理员'),('2','高级管理员'),('3','中级管理员'),('4','业务用户'),('5','一般用户'),('6','普通用户');

/*Table structure for table `elec_role_popedom` */

DROP TABLE IF EXISTS `elec_role_popedom`;

CREATE TABLE `elec_role_popedom` (
  `roleID` varchar(32) NOT NULL DEFAULT '~',
  `mid` varchar(32) NOT NULL,
  `pid` varchar(32) NOT NULL,
  PRIMARY KEY (`roleID`,`mid`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_role_popedom` */

insert  into `elec_role_popedom`(`roleID`,`mid`,`pid`) values ('1','aa','0'),('1','ab','aa'),('1','ac','aa'),('1','ad','aa'),('1','ae','0'),('1','af','ae'),('1','ag','a0'),('1','ah','ag'),('1','ai','ag'),('1','aj','ag'),('1','ak','0'),('1','al','ak'),('1','am','a0'),('1','an','am'),('1','ao','am'),('1','ap','am'),('1','aq','am'),('1','ar','a0'),('1','as','ar'),('1','at','ar'),('1','au','ar'),('1','av','ar'),('1','aw','ar'),('1','ba','0'),('1','bb','ba'),('1','be','ba'),('1','bf','ba'),('1','bg','ba'),('1','bh','ba'),('1','bi','ba'),('1','bj','ba'),('1','ca','0'),('1','cb','ca'),('1','cc','ca'),('1','da','0'),('1','db','da'),('1','dc','da'),('1','ea','0'),('1','eb','ea'),('1','ec','ea'),('1','fa','0'),('1','fb','fa'),('1','fc','fa'),('1','fd','fa'),('1','fe','fa'),('1','ff','fa'),('1','fg','fa'),('1','fh','fa'),('1','fi','fa'),('1','fj','fa'),('1','fk','fa'),('1','fl','fa'),('1','ga','0'),('1','gb','ga'),('1','gc','ga'),('1','ha','0'),('1','hb','ha'),('1','hc','ha'),('1','hd','ha'),('1','he','ha'),('1','ia','0'),('1','ib','ia'),('1','ic','ia'),('1','id','ia'),('1','ie','ia'),('1','if','ia'),('1','ig','ia'),('1','ja','0'),('1','jb','ja'),('1','jc','ja'),('1','jd','ja'),('1','je','ja'),('1','jf','ja'),('1','jg','ja'),('1','jh','ja'),('1','ji','ja'),('1','jj','ja'),('1','jk','ja'),('2','aa','0'),('2','ab','aa'),('2','ac','aa'),('2','ad','aa'),('2','ae','0'),('2','af','ae'),('2','ag','a0'),('2','ah','ag'),('2','ai','ag'),('2','aj','ag'),('2','ak','0'),('2','al','ak'),('2','am','a0'),('2','an','am'),('2','ao','am'),('2','ap','am'),('2','aq','am'),('2','ar','a0'),('2','as','ar'),('2','at','ar'),('2','au','ar'),('2','av','ar'),('2','aw','ar'),('2','ba','0'),('2','bb','ba'),('2','be','ba'),('2','bf','ba'),('2','bg','ba'),('2','bh','ba'),('2','bi','ba'),('3','aa','0'),('3','ab','aa'),('3','ac','aa'),('3','ad','aa'),('3','ae','0'),('3','af','ae'),('3','ag','a0'),('3','ah','ag'),('3','ai','ag'),('3','aj','ag'),('3','ak','0'),('3','al','ak'),('3','bj','ba'),('3','ca','0'),('3','cb','ca'),('3','cc','ca'),('3','da','0'),('3','db','da'),('3','dc','da'),('3','ea','0'),('3','eb','ea'),('3','ec','ea'),('3','fa','0'),('3','fb','fa'),('3','fc','fa'),('3','fd','fa'),('3','fe','fa'),('3','ff','fa'),('3','fg','fa'),('3','fh','fa'),('3','fi','fa'),('3','fj','fa'),('3','fk','fa'),('3','fl','fa'),('3','ga','0'),('3','gb','ga'),('3','gc','ga'),('3','ha','0'),('3','hb','ha'),('3','hc','ha'),('3','hd','ha'),('3','he','ha'),('3','ia','0'),('3','ib','ia'),('3','ic','ia'),('3','id','ia'),('3','ie','ia'),('3','if','ia'),('3','ig','ia'),('3','ja','0'),('3','jb','ja'),('3','jc','ja'),('3','jd','ja'),('3','je','ja'),('3','jf','ja'),('3','jg','ja'),('3','jh','ja'),('3','ji','ja'),('3','jj','ja'),('3','jk','ja');

/*Table structure for table `elec_systemddl` */

DROP TABLE IF EXISTS `elec_systemddl`;

CREATE TABLE `elec_systemddl` (
  `seqID` int(11) NOT NULL,
  `keyword` varchar(20) DEFAULT NULL,
  `ddlCode` int(11) DEFAULT NULL,
  `ddlName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`seqID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_systemddl` */

insert  into `elec_systemddl`(`seqID`,`keyword`,`ddlCode`,`ddlName`) values (1,'性别',1,'男'),(2,'性别',2,'女'),(3,'是否在职',1,'是'),(4,'是否在职',2,'否'),(5,'所属单位',1,'北京'),(6,'所属单位',2,'上海'),(7,'所属单位',3,'深圳'),(14,'审核状态',1,'审核中'),(15,'审核状态',2,'审核不通过'),(16,'审核状态',3,'审核通过'),(17,'图纸类别',1,'国内图书'),(18,'图纸类别',2,'国外图书'),(19,'职位',1,'总经理'),(20,'职位',2,'部门经理'),(21,'职位',3,'员工'),(51,'北京',1,'北京昌平电力公司'),(52,'北京',2,'北京海淀电力公司'),(53,'北京',3,'北京西城电力公司'),(54,'上海',1,'上海浦东电力公司'),(55,'上海',2,'上海闸北电力公司'),(56,'上海',3,'上海徐汇电力公司'),(57,'深圳',1,'深圳福田电力公司'),(58,'深圳',2,'深圳龙岗电力公司'),(59,'深圳',3,'深圳南山电力公司');

/*Table structure for table `elec_text` */

DROP TABLE IF EXISTS `elec_text`;

CREATE TABLE `elec_text` (
  `textID` varchar(255) NOT NULL,
  `textName` varchar(255) DEFAULT NULL,
  `textDate` date DEFAULT NULL,
  `textRemark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`textID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_text` */

/*Table structure for table `elec_user` */

DROP TABLE IF EXISTS `elec_user`;

CREATE TABLE `elec_user` (
  `userID` varchar(50) NOT NULL,
  `jctID` varchar(50) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `logonName` varchar(50) DEFAULT NULL,
  `logonPwd` varchar(50) DEFAULT NULL,
  `sexID` varchar(50) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `contactTel` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `isDuty` varchar(50) DEFAULT NULL,
  `postID` varchar(50) DEFAULT NULL,
  `onDutyDate` datetime DEFAULT NULL,
  `offDutyDate` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `jctUnitID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_user` */

insert  into `elec_user`(`userID`,`jctID`,`userName`,`logonName`,`logonPwd`,`sexID`,`birthday`,`address`,`contactTel`,`email`,`mobile`,`isDuty`,`postID`,`onDutyDate`,`offDutyDate`,`remark`,`jctUnitID`) values ('402881e43d8b1e28013d8b8a88a10154','3','杰杰','jay','1234','1','2017-01-05 21:41:06','龙锦苑','13999778487','1qawq1@163.com','48657897','1','1','2017-01-05 00:00:00',NULL,NULL,'1'),('402881e43d8b1e28013d8b8ae5a10001','1','超级管理员','admin','1234','1','2017-01-03 00:00:00','龙锦苑','13999778487','1qawq1@163.com','48657897','1','1','2017-01-05 00:00:00',NULL,'','1'),('402881e43d8b1e28013d8b8ae5a10154','2','豆豆','doudou','1234','2',NULL,'龙锦苑','13999778487','1qawq1@163.com','48657897','1','1',NULL,NULL,NULL,'1');

/*Table structure for table `elec_user_file` */

DROP TABLE IF EXISTS `elec_user_file`;

CREATE TABLE `elec_user_file` (
  `fileID` varchar(32) NOT NULL,
  `fileName` varchar(32) DEFAULT NULL,
  `fileURL` varchar(32) DEFAULT NULL,
  `progressTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fileID`),
  KEY `FK8C29A13660490B39` (`userID`),
  CONSTRAINT `FK8C29A13660490B39` FOREIGN KEY (`userID`) REFERENCES `elec_user` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_user_file` */

/*Table structure for table `elec_user_role` */

DROP TABLE IF EXISTS `elec_user_role`;

CREATE TABLE `elec_user_role` (
  `userID` varchar(255) NOT NULL,
  `roleID` varchar(255) NOT NULL,
  PRIMARY KEY (`roleID`,`userID`),
  KEY `FK14CB98305AF3B5CF` (`roleID`),
  KEY `FK14CB983060490B39` (`userID`),
  CONSTRAINT `FK14CB98305AF3B5CF` FOREIGN KEY (`roleID`) REFERENCES `elec_role` (`roleID`),
  CONSTRAINT `FK14CB983060490B39` FOREIGN KEY (`userID`) REFERENCES `elec_user` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `elec_user_role` */

insert  into `elec_user_role`(`userID`,`roleID`) values ('402881e43d8b1e28013d8b8ae5a10001','1'),('402881e43d8b1e28013d8b8ae5a10154','1'),('402881e43d8b1e28013d8b8a88a10154','2'),('402881e43d8b1e28013d8b8a88a10154','3');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
