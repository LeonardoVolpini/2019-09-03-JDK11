package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private FoodDao dao;
	private SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	private List<String> vertici;
	private boolean grafoCreato;
	
	private List<String> best;
	private int max;
	
	public Model() {
		this.dao= new FoodDao();
		this.grafoCreato=false;
		this.best= new ArrayList<>();
	}
	
	public void creaGrafo(int C) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici=dao.getVertici(C);
		Graphs.addAllVertices(grafo, this.vertici);
		for (Adiacenza a : dao.getAdiacenze(C)) {
			if (grafo.vertexSet().contains(a.getVertice1()) && grafo.vertexSet().contains(a.getVertice2())) {
				if (a.getPeso()!=0)
					Graphs.addEdgeWithVertices(grafo, a.getVertice1(), a.getVertice2(), (double)a.getPeso());
			}
		}
		this.grafoCreato=true;
	}
	
	public boolean isGrafoCreato() {
		return grafoCreato;
	}

	public int getNumVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return grafo.edgeSet().size();
	}
	
	public Set<String> getVertici(){
		return grafo.vertexSet();
	}
	
	public List<VerticeRaggiungibile> adiacenti(String vertice){ 
		List<VerticeRaggiungibile> result = new ArrayList<>();
		for (String v : Graphs.neighborListOf(grafo, vertice)) {
			result.add(new VerticeRaggiungibile(v, grafo.getEdgeWeight(grafo.getEdge(vertice, v))) ) ;
		}
		//result.remove(0); //rimuobo la componete connesse che indica se stesso
		return result;
	}
	
	public List<String> percorsoMax(String partenza, int N){ 
		this.best=null; 
		this.max=0; 
		List<String> parziale= new ArrayList<>();
		int livello=1;
		parziale.add(partenza);
		ricorsione(parziale ,N, livello);
		return this.best;
	}
	private void ricorsione(List<String> parziale, int N, int livello){
		if(livello==N) { //caso terminale //forse N+1
			int massimo= this.pesoParziale(parziale);
			if(this.best==null || massimo>this.max){ //prima iterazione o ho trovato una soluzione migliore
				this.max=massimo;
				this.best= new ArrayList<>(parziale);
				return;
			}
			else //ho trovato una soluzione ma non è la migliore
				return;
		}

		//da qui faccio la ricorsione:
		String ultimo = parziale.get(livello-1);
		for(String v : Graphs.neighborListOf(grafo, ultimo)){ 
				if (!parziale.contains(v)){ //per evitare i cicli 
					parziale.add(v); 
					ricorsione(parziale,N,livello+1); 
					parziale.remove(parziale.size()-1); //backtracking
				}
		}
	}
	
	private Integer pesoParziale(List<String> parziale) {
		int peso=0;
		int i=0; //indice che mi serve per prendere il match successivo in parziale
		for (String s : parziale) {
			if (i==(parziale.size()-1)) 
				break;
			DefaultWeightedEdge e = grafo.getEdge(s, parziale.get(i+1));
			i++;
			peso += grafo.getEdgeWeight(e);
		}
		return peso;
	}

	public int getPesoMax() {
		return max;
	}
	
	
	
}
