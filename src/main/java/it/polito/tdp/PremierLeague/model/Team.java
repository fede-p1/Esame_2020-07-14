package it.polito.tdp.PremierLeague.model;

public class Team implements Comparable<Team>{
	Integer teamID;
	String name;

	int punteggio;
	
	int numReporter;
	
	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
		
		this.punteggio = 0;
		this.numReporter = 0;
	}
	
	public int getNumReporter() {
		return numReporter;
	}

	public void setNumReporter(int numReporter) {
		this.numReporter = numReporter;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addVictory() {
		punteggio+=3;
	}
	
	public void addDraw() {
		punteggio+=1;
	}
	

	public int getPunteggio() {
		return punteggio;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
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
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}

	@Override
	public int compareTo(Team o) {
		// TODO Auto-generated method stub
		return this.punteggio - o.getPunteggio();
	}
	
}
