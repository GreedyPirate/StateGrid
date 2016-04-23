package cn.sina.elec.service.impl;

import cn.sina.elec.dao.ElecDao;
import cn.sina.elec.domain.Elec;
import cn.sina.elec.service.ElecService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yj on 2015/11/14.
 */
@Service(ElecService.ELEC_SERVICE)
@Transactional(readOnly = true)
public class ElecServiceImp implements ElecService {

    @Resource(name=ElecDao.DAO_IMP)//我tm找了一下午的错,ElecDao
    ElecDao elecDao;

    @Override
    @Transactional(readOnly = false)
    public void saveElec(Elec elec) {
        elecDao.save(elec);
    }


    @Override
    public List<Elec> conditionalQuery(Elec elec) {
        //拼接sql
        StringBuilder condition = new StringBuilder();
        //封装参数
        List<Object> params = new ArrayList<Object>();
        if(StringUtils.isNotBlank(elec.getTextName())){
            condition.append(" and e.textName like ?");
            params.add("%"+elec.getTextName()+"%");
        }
        if(StringUtils.isNotBlank(elec.getTextRemark())){
            condition.append(" and e.textRemark like ?");
            params.add("%"+elec.getTextRemark()+"%");
        }
        //排序用map封装,而且必须是LinkedHashMap,因为排序分先后
        Map<String,String > orderBy = new LinkedHashMap<String,String>();
        orderBy.put("textName","ASC");
        orderBy.put("textRemark","DESC");

        return  elecDao.conditionalQuery(condition.toString(),params.toArray(),orderBy);
    }
}
