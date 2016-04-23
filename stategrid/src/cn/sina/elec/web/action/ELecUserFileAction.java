package cn.sina.elec.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sina.elec.domain.ELecUserFile;
import cn.sina.elec.service.ELecUserFileService;

@SuppressWarnings("serial")
@Controller("eLecUserFileAction")
@Scope("prototype")
public class ELecUserFileAction extends BaseAction<ELecUserFile>{
	@Resource(name=ELecUserFileService.ELEC_SERVICE)
	ELecUserFileService fileService;
	
	ELecUserFile eLecUserFile = this.getModel();
}
