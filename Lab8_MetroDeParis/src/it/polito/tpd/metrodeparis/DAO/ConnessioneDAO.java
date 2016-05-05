package it.polito.tpd.metrodeparis.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tpd.metrodeparis.model.Connessione;

public class ConnessioneDAO {
	public List<Connessione> getConnessione()
	{
		List<Connessione> connessioni= new ArrayList<Connessione>();
		Connection conn= DBConnect.getConnection();
		String sql= "select id_connessione, c.id_linea, id_stazP, id_stazA, velocita from connessione c, linea l where c.id_linea=l.id_linea;";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet res= st.executeQuery();
			while(res.next())
			{
				Connessione e= new Connessione(Integer.parseInt(res.getString("id_connessione")), Integer.parseInt(res.getString("id_linea")), Integer.parseInt(res.getString("id_stazP")), Integer.parseInt(res.getString("id_stazA")), Double.parseDouble(res.getString("velocita")));
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
