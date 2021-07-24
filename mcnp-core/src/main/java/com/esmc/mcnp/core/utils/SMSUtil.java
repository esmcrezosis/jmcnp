/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.core.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mawuli 
 * 
 * Classe qui permet d'écrire dans les tables SMS pour l'envoi
 *         des messages au client
 */
public class SMSUtil {

	public static Connection getConnection() throws SQLException {
		// Register the Java DB embedded JDBC driver
		Driver mysqlDriver = new org.mariadb.jdbc.Driver();
		//Driver mysqlDriver = new com.mysql.cj.jdbc.Driver();
		DriverManager.registerDriver(mysqlDriver);
		/*
		 * String url = "jdbc:mysql://localhost:3306/sms"; String userId =
		 * "gnokii"; String password = "esmc@gacsource";
		 */
		String userId = "esmc";
		String password = "esmc@gacsource";
		String url = "jdbc:mariadb://localhost:3306/kannel";

		// Get a connection
		Connection conn = DriverManager.getConnection(url, userId, password);
		// Set the auto-commit off
		conn.setAutoCommit(false);
		return conn;
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void closeStatement(Statement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void commit(Connection conn) {
		try {
			if (conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void rollback(Connection conn) {
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static boolean insertSms(String numero, String sms) {
		try (Connection con = getConnection()) {
			String sql = "insert into send_sms (momt, sender, receiver, msgdata, sms_type, smsc_id, service )values(?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "MT"); // first name
			pstmt.setString(2, "ESMC"); // last name
			pstmt.setString(3, numero);
			pstmt.setString(4, sms);
			pstmt.setInt(5, 2);
			pstmt.setString(6, "zte");
			pstmt.setString(7, "");
			pstmt.executeUpdate();
			commit(con);
			closeStatement(pstmt);
			return true;
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			Logger.getLogger(SMSUtil.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/*public static void main(String[] args) {

		if (SMSUtil.insertSms("+22890245932", "Bonjour manaani! de la part de ESMC")) {
			System.out.println("OK");
		} else {
			System.out.println("OKO");
		}
	}*/
}
