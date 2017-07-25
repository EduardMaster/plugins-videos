package net.eduard.eduardapi.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.eduard.api.manager.DBManager;

public class SqlTest {
public static void main(String[] args) throws InterruptedException, SQLException {
	DBManager x = new DBManager("root","","localhost","test");
	x.openConnection();
//	Thread.sleep(1000*100);
	ResultSet s = x.getConnection().prepareStatement("select * from teste").executeQuery();
	x.closeConnection();
	s.close();
	s.close();
	s.close();
}
}
