package it.polito.tdp.PremierLeague.model;

import java.time.LocalDateTime;
import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	DefaultDirectedWeightedGraph<Team,DefaultWeightedEdge> graph;
	Map<Integer,Team> teamMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
	}
	
	
	public DefaultDirectedWeightedGraph<Team,DefaultWeightedEdge> creaGrafo(){
		
		graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<Team> vertex = new ArrayList<>(dao.listAllTeams());
		
		Graphs.addAllVertices(graph, vertex);
		
		teamMap = new HashMap<>();
		
		for (Team t : vertex)
			teamMap.put(t.getTeamID(), t);
		
		dao.setPoints(teamMap);
		
		for (Team t1 : graph.vertexSet())
			for (Team t2 : graph.vertexSet())
				if (!t1.equals(t2) && (!graph.containsEdge(t2, t1) || !graph.containsEdge(t1, t2))) {
					int diffPunteggio = t1.getPunteggio() - t2.getPunteggio();
					if (diffPunteggio>0)
						Graphs.addEdge(graph, t1, t2, diffPunteggio);
					else if (diffPunteggio<0)
						Graphs.addEdge(graph, t2, t1, diffPunteggio);
				}
		
		return graph;		
		
	}
	
	public List<Team> getPeggiori(Team team){
		
		List<Team> peggiori = new ArrayList<>();
		
		for (Team peggiore : Graphs.successorListOf(graph, team))
			peggiori.add(peggiore);
		
		Collections.sort(peggiori);
		Collections.reverse(peggiori);
		
		return peggiori;
		
	}

	public List<Team> getMigliori(Team team){
		
		List<Team> migliori = new ArrayList<>();
		
		for (Team migliore : Graphs.predecessorListOf(graph, team))
			migliori.add(migliore);
		
		Collections.sort(migliori);
		
		return migliori;
		
	}
	
	double mediaRep;
	int critici;
	
	public void run(int N, int X) {
		
		PriorityQueue<Match> queue = new PriorityQueue<>();
		
		for (Team t : graph.vertexSet())
			t.setNumReporter(N);
		
		queue.addAll(dao.listAllMatches());
		
		int tot=0;
		int numMatch = queue.size();
		critici = 0;
		
		mediaRep = 0;
		
		while (!queue.isEmpty()) {
			
			Match match = queue.poll();
			LocalDateTime date = match.getDate();
			int punteggio = match.getResultOfTeamHome();
			int numReporterHome = teamMap.get(match.getTeamHomeID()).getNumReporter();
			int numReporterAway = teamMap.get(match.getTeamAwayID()).getNumReporter();
			
			int totReporterMatch = numReporterHome+numReporterAway;
			tot += totReporterMatch;
			
			if (totReporterMatch < X)
				critici++;
			
			if (punteggio != 0) {
			Team vincente = null;
			Team perdente = null;
			
			if (punteggio == 1) {
				vincente = teamMap.get(match.getTeamHomeID());
				perdente = teamMap.get(match.getTeamAwayID());
			}
			else if (punteggio == -1) {
				perdente = teamMap.get(match.getTeamHomeID());
				vincente = teamMap.get(match.getTeamAwayID());
			}
			else {
				break;
			}
			
			if (!this.getMigliori(vincente).isEmpty()) {
				if (Math.random()>0.5) {
					Team scelto = this.getMigliori(vincente).get((int) (Math.random()*this.getMigliori(vincente).size()));
					scelto.setNumReporter(scelto.getNumReporter()+1);
					vincente.setNumReporter(vincente.getNumReporter()-1);
				}
			}
			
			if (!this.getPeggiori(perdente).isEmpty()) {
				if (Math.random()>0.8) {
					Team scelto = this.getPeggiori(perdente).get((int) (Math.random()*this.getPeggiori(perdente).size()));
					int numRep = (int) (perdente.getNumReporter()*Math.random());
					scelto.setNumReporter(scelto.getNumReporter()+numRep);
					vincente.setNumReporter(vincente.getNumReporter()-numRep);
				}
			}
			
			
			
			
		}}
		
		mediaRep = tot/numMatch;
		
		
	}


	public double getMediaRep() {
		return mediaRep;
	}


	public void setMediaRep(double mediaRep) {
		this.mediaRep = mediaRep;
	}


	public int getCritici() {
		return critici;
	}


	public void setCritici(int critici) {
		this.critici = critici;
	}


	public PremierLeagueDAO getDao() {
		return dao;
	}


	public void setDao(PremierLeagueDAO dao) {
		this.dao = dao;
	}


	public DefaultDirectedWeightedGraph<Team, DefaultWeightedEdge> getGraph() {
		return graph;
	}


	public void setGraph(DefaultDirectedWeightedGraph<Team, DefaultWeightedEdge> graph) {
		this.graph = graph;
	}


	public Map<Integer, Team> getTeamMap() {
		return teamMap;
	}


	public void setTeamMap(Map<Integer, Team> teamMap) {
		this.teamMap = teamMap;
	}
	
}
