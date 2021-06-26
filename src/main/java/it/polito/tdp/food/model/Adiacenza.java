package it.polito.tdp.food.model;

public class Adiacenza {

	private String vertice1;
	private String vertice2;
	private Integer peso;
	public Adiacenza(String vertice1, String vertice2, Integer peso) {
		super();
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
		this.peso = peso;
	}
	public String getVertice1() {
		return vertice1;
	}
	public void setVertice1(String vertice1) {
		this.vertice1 = vertice1;
	}
	public String getVertice2() {
		return vertice2;
	}
	public void setVertice2(String vertice2) {
		this.vertice2 = vertice2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "Adiacenza [vertice1=" + vertice1 + ", vertice2=" + vertice2 + ", peso=" + peso + "]";
	}
	
}
