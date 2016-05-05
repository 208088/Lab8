package it.polito.tpd.metrodeparis.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import it.polito.tpd.metrodeparis.model.Fermata;



public class FermataDAO {

	private List<Fermata> fermate= new ArrayList<Fermata>();
	
	public List<Fermata> getFermate(){
		Connection conn= DBConnect.getConnection();
		String sql="select id_fermata, nome, coordX, coordY from fermata;";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet res= st.executeQuery();
			while(res.next())
			{
				Fermata fermata=new Fermata(Integer.parseInt(res.getString("id_fermata")), res.getString("nome"), Double.parseDouble(res.getString("coordX")), Double.parseDouble(res.getString("coordY")));
				fermate.add(fermata);
			}
		//	DBConnect.releaseConnection();
			res.close();
			return fermate;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore nella query!");
		}
		
	}
}
