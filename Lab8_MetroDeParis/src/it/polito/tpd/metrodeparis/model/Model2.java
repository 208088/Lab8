package it.polito.tpd.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
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


public class Model2 {
	private List<Fermata> fermate;
	private List<Fermata2> stazioni;
	List<Connessione> connessioni;
	List<Linea> linee;
	GraphPath<Fermata2, DefaultWeightedEdge> path;
	WeightedGraph<Fermata2, DefaultWeightedEdge> grafo = new DefaultDirectedWeightedGraph<Fermata2, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);

	public List<Fermata> getFermate() {
		FermataDAO f = new FermataDAO();
		fermate = new ArrayList<Fermata>();
		fermate = f.getFermate();
		return fermate;
	}

	public void creaGrafo() {
		ConnessioneDAO co = new ConnessioneDAO();
		LineaDAO li = new LineaDAO();
		connessioni = co.getConnessione();
		linee = li.getLinee();
		fermate = getFermate();
		stazioni = new ArrayList<Fermata2>();
		for (Connessione c : connessioni) {
			Fermata f = getFermataById(c.getIdStazP());
			Fermata2 f1 = new Fermata2(f.getId(), f.getNome(), f.getCoordX(), f.getCoordY());
			System.out.println("Ecco la fermata appana creata: " + f1.getNome());
			f1.setIdLinea(c.getIdLinea());
			stazioni.add(f1);
			grafo.addVertex(f1);
		}
		for (Connessione c : connessioni) {
			Linea l = getLineaById(c.getIdLinea());
			Fermata2 f1 = getFermataById(c.getIdStazP(), c.getIdLinea());
			Fermata2 f2 = getFermataById(c.getIdStazA(), c.getIdLinea());
			if (f1 != null && f2 != null && l != null) {
				double distanza = LatLngTool.distance(new LatLng(f1.getCoordX(), f1.getCoordY()),
						new LatLng(f2.getCoordX(), f2.getCoordY()), LengthUnit.KILOMETER);
				double tempo = distanza / l.getVelocita();
				Graphs.addEdge(grafo, f1, f2, tempo);

			}
		}
		for (Fermata2 f : stazioni) {
			for (Fermata2 f1 : stazioni) {
				if (f.getNome().compareTo(f1.getNome()) == 0 && f.getIdLinea() != f1.getIdLinea()) {
					Linea l = getLineaById(f1.getIdLinea());
					Graphs.addEdge(grafo, f, f1, (l.getIntervallo()) / 60);
				}
			}
		}
	}

	private Fermata2 getFermataById(int idStazA, int idLinea) {
		for (Fermata2 f : stazioni) {
			if (f.getId() == idStazA && f.getIdLinea() == idLinea) {
				return f;
			}
		}
		return null;
	}

	public Fermata getFermataById(int id) {

		for (Fermata f : fermate) {
			if (f.getId() == id) {
				return f;
			}
		}
		return null;
	}

	public Linea getLineaById(int id) {

		for (Linea f : linee) {
			if (f.getId() == id) {
				return f;
			}
		}
		return null;
	}

	public Fermata getFermataByNome(String f1) {
		for (Fermata f : fermate) {
			if (f.getNome().compareTo(f1) == 0) {
				return f;
			}
		}
		return null;
	}

	public List<Fermata2> trovaCammino(Fermata p, Fermata a) {

		List<Fermata2> percorso= new ArrayList<Fermata2>();
		double peso=100000.0;
		//DijkstraShortestPath<Fermata2, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Fermata2, DefaultWeightedEdge>(grafo, p, a);
		List<Fermata2> fpartenza= getVerticiByNome(p.getNome());
		List<Fermata2> farrivi= getVerticiByNome(a.getNome());
		for (Fermata2 fp : fpartenza) {
			for (Fermata2 fa : farrivi) {
				DijkstraShortestPath<Fermata2, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Fermata2, DefaultWeightedEdge>(grafo, fp, fa);
				path = dijkstra.getPath();
				if(path!=null && peso> (dijkstra.getPathLength()+30*(percorso.size()-2)/3600))
				{
					peso=dijkstra.getPathLength()+30*(percorso.size()-2)/3600;
					percorso=Graphs.getPathVertexList(path);
				}
			}
		}
		return percorso;
	}

	private List<Fermata2> getVerticiByNome(String nome) {
		// TODO Auto-generated method stub
		List<Fermata2> lista= new ArrayList<Fermata2>();
		for (Fermata2 f : grafo.vertexSet()) {
			if(f.getNome().compareTo(nome)==0)
				lista.add(f);
		}
		return lista;
	}

	public double getWeight() {
		return path.getWeight();
	}

}
