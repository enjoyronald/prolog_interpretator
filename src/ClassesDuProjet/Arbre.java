package ClassesDuProjet;

import java.util.ArrayList;

public class Arbre {
	protected Arbre pere;
	//protected ArrayList<Object> possPere=new ArrayList<Object>();
	protected ArrayList<Object> possMoi=new ArrayList<Object>();
	protected ClausesGoal nom=null;
	protected boolean isGoal=false;
	protected int prof;
	
	
	public Arbre(ClausesGoal n, Arbre p, ArrayList<Object> posm){
		this.setPere(p);
		nom=new ClausesGoal(n.toString());
		//possPere.addAll(pos);
		possMoi.addAll(posm);
		this.setProf(this.getPere().getProf()+1);
		
	}
	public Arbre(Arbre bis){
		this.setPere(bis.getPere());
		this.setNom(bis.getNom());
	//	possPere.addAll(bis.getPossPere());
		this.setProf(bis.getProf());
		this.setPossMoi(bis.getPossMoi());
	}

	public Arbre(ClausesGoal debut, ArrayList<Object> posm){
		this.setNom(debut);
		this.setPere(new Arbre());
		possMoi.addAll(posm);
		this.setProf(this.getPere().getProf()+1);
		
		//isGoal=true;
	}
	
	public Arbre(){
		this.setGoal(true);
		this.setProf(0);
	}

	
	
	public void setProf(int prof) {
		this.prof = prof;
	}

	public String toString() {
		return "Arbre [nom="+ nom + ", isGoal=" + isGoal + "]\n"+possMoi+"";
	}
	
	public boolean isGoal() {
		return isGoal;
	}
	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}
	

	public Arbre getPere() {
		return pere;
	}

	public void setPere(Arbre pere) {
		this.pere = pere;
	}





	public ClausesGoal getNom() {
		return nom;
	}

	public void setNom(ClausesGoal nom) {
		this.nom = nom;
	}
	public int getProf() {
		return prof;
	}
	public ArrayList<Object> getPossMoi() {
		return possMoi;
	}
	public void setPossMoi(ArrayList<Object> possMoi) {
		this.possMoi = possMoi;
	}
	

}
