package ClassesDuProjet;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import action_listeners.RunListener;



public class Fonctions {





	//-----------------------Fonctions de decoupage en differents String --------------------------------------------


	public static ArrayList<String> decoupeClauses(String f){
		ArrayList<String> result=new ArrayList<String>();
		if(f==null){
			return result;
		}
		else{
			String delims = ".";
			StringTokenizer tokens = new StringTokenizer(f, delims);
			while(tokens.hasMoreTokens()) result.add(tokens.nextToken());
		}
		return result;
	}


	//-----------------------------Fontion qui retroune de quel type est la clause---------------------
	//-----------retourne 0 si un fait
	//-----------retourne 1 si un goal 
	//-----------retourne 2 si une regle 

	public static int typeClauses(String str){
		int result = 0;
		for(int i=0; i<str.length(); i++){
			if(str.charAt(i)=='g'){		
				if(str.charAt(i+1)=='o'){
					return 1;
				}
			}
			else if (str.charAt(i)==':'){
				if(str.charAt(i+1)=='-'){
					return 2;
				}
			}		
		}
		return 0;
	}


	public static ArrayList<String> decoupeString(String str){
		ArrayList<String> result=new ArrayList<String>();

		String delims = ".";
		StringTokenizer tokens = new StringTokenizer(str, delims);
		while(tokens.hasMoreTokens()) result.add(tokens.nextToken());

		return result;
	}

	//-----------------------------------Fonctions pour le renomage des variables-------------------------------------------

	/*
	 *            Renome la variable var , par la variable b, dans toute la clause   
	 */
	public static void renomVar(Variable var, Variable b, ClausesRegle clause){

		String stmp=var.getName();
		var.setName(b.getName()); 
		for(int i=0; i<clause.getLitteraux().size();i++){
			for(int a=0; a<clause.getLitteraux().get(i).getListeVar().size(); a++){
				if(clause.getLitteraux().get(i).getListeVar().get(a).getName().equals(b.getName())){
					clause.getLitteraux().get(i).getListeVar().get(a).setName(stmp);
					clause.getLitteraux().get(i).getListeVar().get(a).setCons(b.isCons());
					clause.getLitteraux().get(i).getListeVar().get(a).setFree(b.isFree());
					clause.getLitteraux().get(i).getListeVar().get(a).setNum(b.isNum());
					clause.getLitteraux().get(i).getListeVar().get(a).setLink(b.isLink());
				}
			}
		}
	}
	public static void renomVar(Variable var, Variable b, ClausesGoal clause){

		String stmp=var.getName();
		var.setName(b.getName());
		for(int i=0; i<clause.getLitteraux().size();i++){
			for(int a=0; a<clause.getLitteraux().get(i).getListeVar().size(); a++){
				if(clause.getLitteraux().get(i).getListeVar().get(a).getName().equals(stmp)){

					clause.getLitteraux().get(i).getListeVar().get(a).setName(b.getName());
					clause.getLitteraux().get(i).getListeVar().get(a).setCons(b.isCons());
					clause.getLitteraux().get(i).getListeVar().get(a).setFree(b.isFree());
					clause.getLitteraux().get(i).getListeVar().get(a).setNum(b.isNum());
					clause.getLitteraux().get(i).getListeVar().get(a).setLink(b.isLink());
				}
			}
		}
	}

	//----------------------------------Fonction pour tester si l'unification est possible----------------
	//                           a (appartient au goal) est   celui qui recoit   et b celui qui envoi   (TESTE ET CA MARCHE)              
	public static boolean testUnif(ClausesGoal goal, Object test){
		if(test instanceof ClausesFait) {
			boolean result=true;
			boolean dur=false;
			int i=0;
			String stra,strb;
			ClausesFait fait=new ClausesFait((ClausesFait)test);


			if(!(fait.getLitteraux().get(0).getPredicat().equals(goal.getLitteraux().get(0).getPredicat()))||!(fait.getLitteraux().get(0).getListeVar().size()==goal.getLitteraux().get(0).getListeVar().size())){

				return false;	
			}
			else {
				boolean goOn=true;
				while(goOn&&i<fait.getLitteraux().get(0).getListeVar().size()){

					if(goal.getLitteraux().get(0).getListeVar().get(i).isFree()){ goOn=true; result=true;}
					else if(fait.getLitteraux().get(0).getListeVar().get(i).isFree()){goOn=true; result=true;}
					else{
						result =(goal.getLitteraux().get(0).getListeVar().get(i).getName().equals(fait.getLitteraux().get(0).getListeVar().get(i).getName()));
						goOn=result;
					}

					i++;
				}
				return result;
			}
		}
		else{
			boolean result=true;
			boolean dur=false;
			int i=0;
			String stra,strb;
			ClausesRegle fait=new ClausesRegle((ClausesRegle)test);

			if(!(fait.getLitteraux().get(0).getPredicat().equals(goal.getLitteraux().get(0).getPredicat()))||!(fait.getLitteraux().get(0).getListeVar().size()==goal.getLitteraux().get(0).getListeVar().size())){

				return false;	
			}
			else {
				boolean goOn=true;
				while(goOn&&i<fait.getLitteraux().get(0).getListeVar().size()){

					if(fait.getLitteraux().get(0).getListeVar().get(i).isFree()){ goOn=true; result=true;}
					else if(goal.getLitteraux().get(0).getListeVar().get(i).isFree()){goOn=false; result=false;}
					else {
						result =(goal.getLitteraux().get(0).getListeVar().get(i).getName().equals(fait.getLitteraux().get(0).getListeVar().get(i).getName()));
						goOn=result;
					}
					i++;
				}
				return result;
			}


		}

	} 

	//-------------------------------------------Fonction Unif-----------------------------------------------------------------------------
	/* 1-) on enregistre notre clause goal ( c'est un noeud de la resolution      // ca doit etre fait dans la fonction resolve
	 * 2-) on renome toutes variable dans le goal (bien évidement juste les variables affectés) 
	 * 3-1) Si c'est une clause Fait , alors on retrire le premier litteral du goal et on relance l'algo de resolution 
	 * 3-2) et s'il s'agit d'une clause règle, on retire aussi le premier litteral du goal, par contre on rajoute en debut les litteraux du fait (à partir du rang 1)
	 * Dans la fonction unif, premièrement , les variables de la subgoal qui seront renomé, le seront dans toute la clause goal 

	 */
	//------------------------------------------A la sortie de la fonction unif, le goal sera modifié-------------------

	public static void unif(ClausesGoal goal, Object aUnif){
		if(aUnif instanceof ClausesFait){
			ClausesFait clause=new ClausesFait((ClausesFait)aUnif);
			Litteral litGoal= goal.getLitteraux().get(0);
			Litteral litaUnif=clause.getLitteraux().get(0);

			for(int i=0; i<litGoal.getListeVar().size(); i++){
				renomVar(litGoal.getListeVar().get(i),litaUnif.getListeVar().get(i), goal);
			}

			goal.getLitteraux().remove(0);  // on retire le premier litteral du goal 

		}
		else if(aUnif instanceof ClausesRegle){
			ClausesRegle clause=new ClausesRegle((ClausesRegle)aUnif);
			Litteral litGoal= goal.getLitteraux().get(0);
			Litteral litaUnif=clause.getLitteraux().get(0);

			/* clause va representer une clause tampon 
			 * 
			 */
			for(int i=0; i<litaUnif.getListeVar().size(); i++){
				renomVar(litaUnif.getListeVar().get(i),litGoal.getListeVar().get(i), clause);
			}

			goal.getLitteraux().remove(0);  // on retire le premier litteral du goal 

			//-------------------fin partie commune au deux cas 
			clause.getLitteraux().remove(0);
			for(int i=clause.getLitteraux().size()-1; i>=0; i--){
				goal.getLitteraux().add(0, clause.getLitteraux().get(i));
			}

			for(int i=0; i<litGoal.getListeVar().size(); i++){
				renomVar(litGoal.getListeVar().get(i),litaUnif.getListeVar().get(i), clause);
			}
		}


	}




	public static ArrayList<Object> findPossibilities(ClausesGoal goal, ArrayList<Object> clauses){
		ArrayList<Object> possibilite = new ArrayList<Object>();
		for(int i=0; i<clauses.size();i++){
			if(testUnif(goal, clauses.get(i))){
				possibilite.add(clauses.get(i));
			}
		}
		return possibilite;
	}

	public static ArrayList<Arbre> grandsPeres(Arbre ar){
		ArrayList<Arbre> result=new ArrayList<Arbre>();
		if(ar.getProf()==0) return result;

		Arbre tmp= new Arbre(ar);
		if(tmp.isGoal()){
			result.add(tmp);
			return result;
		}
		if(tmp.getPere().isGoal()){
			result.add(tmp);
			result.add(tmp.getPere());
		}

		for(int i=ar.getProf();i>=1;i--){
			if(tmp.getPere().isGoal()){}
		//	else{
			tmp=new Arbre(tmp.getPere());
			result.add(0,tmp);
		//	}
		} 

		return result;
	}


}
