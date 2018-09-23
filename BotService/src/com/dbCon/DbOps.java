package com.dbCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.failData.FailData;
import com.failData.Jobs_status;
import com.loadPojo.DMRpojo;
import com.loadPojo.LoadJobPojo;
import com.shipStatusPojo.TraingCenterPojo;

public class DbOps {

	Connection getCon() {

		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HP?autoReconnect=true&useSSL=false", "prem", "root");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;
	}

	public List<TraingCenterPojo> getCenters(String Pincode) {
		
		List<TraingCenterPojo> listTraingCenterPojo = new ArrayList<TraingCenterPojo>();
		
		try {
			Connection con = getCon();
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT TrainingCenterName FROM hp.`nsdc-training-center-list` where Pincode = '"+Pincode+"'");
			
			System.out.println("resultset");
			
			while (rs.next()) {
			TraingCenterPojo traingCenterPojoloop = new TraingCenterPojo();
			System.out.println("training center : "+rs.getString("TrainingCenterName"));
			traingCenterPojoloop.setCenters(rs.getString("TrainingCenterName"));
			listTraingCenterPojo.add(traingCenterPojoloop);
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return listTraingCenterPojo;
	}

	
}
