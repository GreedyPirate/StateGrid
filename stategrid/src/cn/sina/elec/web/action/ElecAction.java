package cn.sina.elec.web.action;

import cn.sina.elec.domain.Elec;
import cn.sina.elec.service.ElecService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by yj on 2015/11/14.
 */
@SuppressWarnings("serial")
@Controller("elecAction")
@Scope(value="prototype")
public class ElecAction extends BaseAction<Elec>{

    @Resource(name=ElecService.ELEC_SERVICE)
    private ElecService elecService;
    private Elec elec = this.getModel();

    public String saveElec(){
        String date = request.getParameter("textDate");
        System.out.println(date);
        elecService.saveElec(elec);
        return SUCCESS;
    }
}
