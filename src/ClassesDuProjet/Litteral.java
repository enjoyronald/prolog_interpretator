package ClassesDuProjet;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import action_listeners.RunListener;






public class Litteral {
	ArrayList<Variable> listeVar;
	String predicat; // nom du predicat
	boolean result =false;       // permet de savoir si le predicat il est validé ou pas 

	public Litteral(String str){
		predicat=str;
		listeVar=new ArrayList<Variable>();

	}


	// permet de verifier le resultat d'un predicat 
	public void majResultat(){


	}



	public ArrayList<Variable> getListeVar() {
		return listeVar;
	}
	public void setListeVar(ArrayList<Variable> listeVar) {
		this.listeVar = listeVar;
	}

	public String getPredicat() {
		return predicat;
	}
	public void setPredicat(String predicat) {
		this.predicat = predicat;
	}
	@Override
	public String toString() {
		//return  "  -"+ predicat + "-  listeVar ( " + listeVar + ")"   ;
		String str=predicat;
		if (!listeVar.isEmpty()){
			str+="(";
			for(int i=0; i<listeVar.size()-1; i++){
				str+=listeVar.get(i)+",";
			}
			str+=listeVar.get(listeVar.size()-1)+")";
		}
		return str;
	}


	public boolean getResult() {
		return result;
	}


	public void setResult(boolean result) {
		this.result = result;
	}



}
