package it.polito.tpd.metrodeparis.model;

public class Connessione {
	private int id;
	private int idLinea;
	private int idStazP;
	private int idStazA;
	private double velocita;
	
	public Connessione(int id, int idLinea, int idStazP, int idStazA, double velocita) {
		super();
		this.id = id;
		this.idLinea = idLinea;
		this.idStazP = idStazP;
		this.idStazA = idStazA;
		this.velocita = velocita;
	}
	
	public int getId() {
		return id;
	}

	public int getIdLinea() {
		return idLinea;
	}

	public int getIdStazP() {
		return idStazP;
	}

	public int getIdStazA() {
		return idStazA;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connessione other = (Connessione) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
