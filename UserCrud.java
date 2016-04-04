public class UserCrud {
	@Test
	public void testAdd(){
		SessionFactory factory = SessionUtils.factory;
		//建立连接
		Session session = factory.openSession();
		//关闭连接资源
		session.close();
	}

}































