package cn.sina.junit;

import org.junit.Test;

import cn.sina.elec.domain.xsd.ElecSystemDDL;
import cn.sina.elec.webservice.GetSystemDDL;
import cn.sina.elec.webservice.GetSystemDDLResponse;
import cn.sina.elec.webservice.IWebSystemDDLServiceSkeleton;

public class WebServiceTest {

	@Test
	public void testWebService(){
		IWebSystemDDLServiceSkeleton skeleton = new IWebSystemDDLServiceSkeleton();
		//参数类
		GetSystemDDL getSystemDDL = new GetSystemDDL();
		getSystemDDL.setArgs0("部门");
		//调用并获取结果
		GetSystemDDLResponse response = skeleton.getSystemDDL(getSystemDDL);
		ElecSystemDDL[] elecSystemDDLs = response.get_return();
		if(elecSystemDDLs!=null && elecSystemDDLs.length>0){
			for (int i = 0; i < elecSystemDDLs.length; i++) {
				ElecSystemDDL elecSystemDDL = elecSystemDDLs[i];
				System.out.println(elecSystemDDL.getDdlName());
			}
		}
	}
}
