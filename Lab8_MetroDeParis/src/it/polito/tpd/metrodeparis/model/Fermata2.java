package it.polito.tpd.metrodeparis.model;

public class Fermata2 extends Fermata {

	private int idLinea;
	
	public Fermata2(int id, String nome, double coordX, double coordY) {
		super(id, nome, coordX, coordY);
	}

	

	@Override
	public int hashCode() {
		super.hashCode();
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + idLinea;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		super.equals(obj);
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fermata2 other = (Fermata2) obj;
		if (idLinea != other.idLinea)
			return false;
		return true;
	}



	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}
}
