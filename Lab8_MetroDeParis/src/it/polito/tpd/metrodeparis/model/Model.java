package it.polito.tpd.metrodeparis.model;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tpd.metrodeparis.DAO.ConnessioneDAO;
import it.polito.tpd.metrodeparis.DAO.FermataDAO;
import it.polito.tpd.metrodeparis.DAO.LineaDAO;

public class Model{
private List<Fermata> fermate;
List<Connessione> connessioni;
List<Linea> linee;
GraphPath<Fermata, DefaultWeightedEdge> path;
SimpleWeightedGraph<Fermata, DefaultWeightedEdge> grafo= new SimpleWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);

public List<Fermata> getFermate() 
{
FermataDAO f= new FermataDAO();
fermate= new ArrayList<Fermata>();
fermate= f.getFermate();
	 return fermate;
}


public void creaGrafo() 
{		ConnessioneDAO co= new ConnessioneDAO();
		LineaDAO li= new LineaDAO();
		connessioni=co.getConnessione();
		linee= li.getLinee();
		Graphs.addAllVertices(grafo, fermate);
		for (Connessione c : connessioni) {
			Fermata f1= getFermataById(c.getIdStazA());
			Fermata f2= getFermataById(c.getIdStazP());
			if(f1!=null && f2!=null)
			{
				Linea l= getLineaById(c.getIdLinea());
				if(l==null)
					return;
				double distanza=LatLngTool.distance( new LatLng( f1.getCoordX(), f1.getCoordY()), new LatLng( f2.getCoordX(), f2.getCoordY()), LengthUnit. KILOMETER);
				double tempo=distanza/l.getVelocita();
				Graphs.addEdgeWithVertices(grafo, f1, f2, tempo);
				
			}
		}
}


public Fermata getFermataById(int id)
{	
	
	for (Fermata f : fermate)
	{
		if(f.getId()==id)
				{
					return f;
				}
	}
	return null;
}


public Linea getLineaById(int id)
{	
	
	for (Linea f : linee)
	{
		if(f.getId()==id)
				{
					return f;
				}
	}
	return null;
}


public Fermata getFermataByNome(String f1) {
	for (Fermata f : fermate)
	{
		if(f.getNome().compareTo(f1)==0)
				{
					return f;
				}
	}
	return null;
}

public List<Fermata> trovaCammino(Fermata p, Fermata a) {

	DijkstraShortestPath<Fermata, DefaultWeightedEdge> dijkstra=
			new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(grafo, p, a);
	
	path= dijkstra.getPath();
	if(path==null)
		return null;
	return Graphs.getPathVertexList(path);
}


public double getWeight() {
	return	path.getWeight();
}

}
