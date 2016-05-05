package it.polito.tpd.metrodeparis.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tpd.metrodeparis.model.Linea;



public class LineaDAO {

	
	public List<Linea> getLinee()
	{
		List<Linea> connessioni= new ArrayList<Linea>();
		Connection conn= DBConnect.getConnection();
		String sql= "select id_linea, nome, velocita, intervallo,colore from linea;";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet res= st.executeQuery();
			while(res.next())
			{
				Linea e= new Linea(Integer.parseInt(res.getString("id_linea")), res.getString("nome"), Double.parseDouble(res.getString("velocita")), Double.parseDouble(res.getString("intervallo")), res.getString("colore"));
				connessioni.add(e);
			}
			//DBConnect.releaseConnection();
			res.close();
			return connessioni;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore nella query alla tabella connessioni");
		}
	}
	
}

