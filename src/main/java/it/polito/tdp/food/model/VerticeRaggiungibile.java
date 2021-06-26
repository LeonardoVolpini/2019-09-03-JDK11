package it.polito.tdp.food.model;

public class VerticeRaggiungibile {

	private String vertice;
	private double peso;
	public VerticeRaggiungibile(String vertice, double peso) {
		super();
		this.vertice = vertice;
		this.peso = peso;
	}
	public String getVertice() {
		return vertice;
	}
	public void setVertice(String vertice) {
		this.vertice = vertice;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "VerticeRaggiungibile [vertice=" + vertice + ", peso=" + peso + "]";
	}
	
}
