package com.hanshin.yongbin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class practice {
	static Connection conn = null;
	static String table = "databasetest.addressbooks";
	
	public static void main(String[] args) {
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost:3306/databasetest?serverTimezone=UTC";
		try { 
				Class.forName(jdbc_driver).newInstance();
				Connection con = DriverManager.getConnection(jdbc_url, "root", "fumika003");
				conn = con;
				Statement st = con.createStatement();
			
				System.out.printf("���̺� ������");
				
				String sql = "create table if not exists " + table
						+ "(id INT, name VARCHAR(45), "
						+ "tel VARCHAR(45), email VARCHAR(60), "
						+ "address VARCHAR(60))";
				st.executeUpdate(sql);
				
				System.out.printf("\n\n");
				System.out.printf("--------5���� ������ �Է�--------\n");
				
				insert(1,"���","010-6473-5441","abc970704@hs.ac.kr","��⵵ �����");
				insert(2,"����","010-125-5454","ds@hs.ac.kr","��⵵ �����");
				insert(3,"�ͱ�","010-456-1421","mg@hs.ac.kr","��û�� õ�Ƚ�");
				insert(4,"��ĥ","010-425-4221","yc@hs.ac.kr","��� ���׽�");
				insert(5,"����","010-569-1816","dd@hs.ac.kr","������ ��õ��");

				list("id");
				
				System.out.printf("\n\n");
				System.out.printf("-------- ������ ������Ʈ  --------\n");
				
				update(1,"abc970704@naver.com");
				update(2,"ds@naver.com");
				update(3,"mg@naver.com");
				update(4,"yc@naver.com");
				update(5,"dd@naver.com");
				
				list("id");
				
				System.out.printf("\n\n");				
				System.out.printf("--------   ���� �� ����   --------\n");
				delete(lowCounter());
				delete(lowCounter() - 1);
				
				list("id");
				
				st.close(); 
				con.close();
		} catch (Exception e) {
			e.printStackTrace(); }
	}
	
	public static void insert(int id, String name, String tel, String email, String address){
		try {
				String sql = "INSERT INTO " + table + " VALUES (?, ?, ?, ?, ?)";	
				PreparedStatement pst = conn.prepareStatement(sql);
		
				pst.setInt(1, id);
				pst.setString(2, name);
				pst.setString(3, tel);
				pst.setString(4, email);				
				pst.setString(5, address);
		
				pst.executeUpdate();
		
				pst.close();
			} catch (Exception e) {
				e.printStackTrace(); }

	}
	
	public static void list(String culum){
		try {
				Statement st = conn.createStatement();
			
				String sql = "SELECT * FROM " + table + " ORDER BY " + culum + " ASC";
				ResultSet rs = st.executeQuery(sql);
			
				while(rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String tel = rs.getString("tel");
					String email = rs.getString("email");
					String address = rs.getString("address");
					System.out.printf("id:  %d, name: %s, tell: %s, email: %s, address: %s"
							+ "\n", id, name, tel, email, address);
				}			
			
				rs.close();
				st.close(); 
			}catch (Exception e) {
				e.printStackTrace(); }
		
	}
	
	public static void update(int id, String email) {
		try {
			Statement st = conn.createStatement();
			String sql = "UPDATE " + table + " set email = '" + email + "' WHERE id = " + id;
			st.executeUpdate(sql);
			
			st.close(); 
		}catch (Exception e) {
			e.printStackTrace(); }
	}
	
	public static int lowCounter(){
		int rc = 0;
		try {
			Statement st = conn.createStatement();
		
			String sql = "SELECT * FROM " + table;
			ResultSet rs = st.executeQuery(sql);
		
			ResultSetMetaData rsmd=null;

			rsmd=rs.getMetaData();

			int rowCnt = rsmd.getColumnCount();
			rc = rowCnt;
			rs.close();
			st.close();

		}catch (Exception e) {
			e.printStackTrace(); }
		return rc;

	}
	
	public static void delete(int id)	{
		try {
			Statement st = conn.createStatement();
			String sql = "DELETE FROM " + table + " where id = " + id;
			st.executeUpdate(sql);
			
			st.close(); 
		}catch (Exception e) {
			e.printStackTrace(); }
	}

}
