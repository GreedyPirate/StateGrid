package cn.sina.elec.web.action;

import cn.sina.elec.utils.GenericUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yj on 2015/11/14.
 * 以后肯定有很多个action类,抽取出一个BaseAction出来
 * 还有可能要用到request和response
 */
@SuppressWarnings({"serial","rawtypes","unchecked"})
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,ServletRequestAware,ServletResponseAware {
    T entity;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

	public BaseAction() {
        /**
         * Class klass = (Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            entity = (T)klass.newInstance();
         为了提高复用性,写在一个工具类中
         */
        Class klass = GenericUtils.getActualType(this.getClass());
        try {
            entity = (T) klass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T getModel() {
        return entity;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        //还记得老王怎么说的吗？这两个方法给你了request对象,你得存起来
        this.request = httpServletRequest;
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.response = httpServletResponse;
    }
}
