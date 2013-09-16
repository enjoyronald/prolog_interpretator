package windows;
import java.util.*;

import javax.swing.JOptionPane;

import action_listeners.KeyBoardListener;
import action_listeners.KeyBoardListener;

import ClassesDuProjet.*;

public class Resolution {
	Fenetre win;
	protected boolean solution; // cette derniere renvoi true si il exite une solution possible et false sinon, un false n'empêche pas l'affichage des write 
	ArrayList<String> start=new ArrayList<String>();
	ArrayList<ClausesFait> clausesFait=new ArrayList<ClausesFait>();
	ArrayList<ClausesRegle> clausesRegle=new ArrayList<ClausesRegle>();
	ArrayList<ClausesGoal> clausesGoal =new ArrayList<ClausesGoal>();


	static ArrayList<String> predicatsConnus=new ArrayList<String>();
	ArrayList<Object> arbre=new ArrayList<Object>();    // contient sommet +arraylist possibilite 
	static ArrayList<Object>     listClauses =new ArrayList<Object>();



	public Resolution(Fenetre bis){
		win=bis;

		initialisationPredicats();
		constructClauses();
		initialisationListClauses();
		resolve();
	}

	public void initialisationPredicats(){
		String[] tab={"egal","inf","sup","plus","moins","fois","div","mod","write","read","!","fail","dif","nl","readln"};
		for(int i=0; i<tab.length;i++){
			predicatsConnus.add(tab[i]);
		}
	}


	public void constructClauses(){
		clausesFait.addAll(win.getClausesFait());
		clausesGoal.addAll(win.getClausesGoal());
		clausesRegle.addAll(win.getClausesRegle());
		listClauses.addAll(win.getListClauses());

	}
	public void initialisationListClauses(){

	}

	public void resolve(){


		boolean bo=true;
		int a=0;

		while(bo&&a<clausesGoal.size()){
			ClausesGoal tmp=new ClausesGoal(clausesGoal.get(a));
			Arbre debut=new Arbre(tmp,Fonctions.findPossibilities(tmp, listClauses));
			bo=resolveRec(debut);
			a++;
		}
		KeyBoardListener.affiche("==================\n       "+bo+"\n================ ");

	}


	public static boolean resolveRec (Arbre go){
		ArrayList<Object> possMoi=new ArrayList<Object>();
		possMoi.addAll(go.getPossMoi());
		Arbre copy=new Arbre(go.getNom(), go.getPere(), possMoi);                    // on recupere le père intact 
		boolean unified=false;                      // pour savoir si l'unification a eu lieu
		System.out.println("\n voici l'arbre "+go.getNom()+"\n et  "+go.getPossMoi());
		if(!go.getPossMoi().isEmpty()){
			//System.out.println("\n je cherche a unifier "+go.getNom()+"\n et  "+go.getPossMoi());
			Fonctions.unif(go.getNom(),go.getPossMoi().get(0));
			unified=true;

		}
		if(go.getNom().getLitteraux().isEmpty()){
			return true;
		}

		else if(predicatsConnus.contains(go.getNom().getLitteraux().get(0).getPredicat())){
			if(go.getNom().getLitteraux().get(0).getPredicat().equals("!")||go.getNom().getLitteraux().get(0).getPredicat().equals("")){
				return false;
			}
			else{
				if(unified){
					//	go.getPossMoi().remove(0);
					copy.getPossMoi().remove(0);  //====================A RETIRER===========================
				}
				Arbre g=new Arbre(go);
				majResult(go.getNom().getLitteraux().get(0), go.getNom());
				boolean bo=go.getNom().getLitteraux().get(0).getResult();

				if(bo){

					go.getNom().getLitteraux().remove(0);
					if(go.getNom().getLitteraux().isEmpty()||((go.getNom().getLitteraux().get(0).getPredicat().charAt(0)=='.'))){
						return true;
					}
					return resolveRec(new Arbre(go.getNom(),copy,Fonctions.findPossibilities(go.getNom(), listClauses)));
				}
				else{
					if(copy.getPossMoi().isEmpty()){
						if(cherchePere(copy).isGoal()){
							return false;
						}
						return resolveRec(cherchePere(copy));
					}
					else{
						return resolveRec(copy);
					}

				}
			}

		}

		else{

			if(!unified){
				if(cherchePere(go).isGoal()){
					return false;
				}

				return resolveRec(cherchePere(copy));
			}
			else{

				copy.getPossMoi().remove(0);

				if(go.getNom().getLitteraux().isEmpty()||((go.getNom().getLitteraux().get(0).getPredicat().charAt(0)=='.'))){
					return true;
				}

				else{
					ArrayList<Object> t=Fonctions.findPossibilities(go.getNom(), listClauses);
					go.setPossMoi(Fonctions.findPossibilities(go.getNom(), listClauses));
					if(!t.isEmpty()){
						ClausesGoal g=new ClausesGoal(go.getNom());
						return resolveRec(new Arbre(go.getNom(),copy,Fonctions.findPossibilities(go.getNom(), listClauses)));
					}	
					else{
						if(!copy.getPossMoi().isEmpty()){
							System.out.println(go+"je suis la");
							return resolveRec(copy);
						}
						else{
							if(cherchePere(copy).isGoal()){
								return false;
							}
							return resolveRec(cherchePere(copy));
						}
					}
				}

			}

		}
	}



	public static Arbre cherchePere(Arbre copy){
		ArrayList<Arbre> peres=Fonctions.grandsPeres(copy);
		Arbre result=new Arbre();
		if(peres.size()<=0){
			return new Arbre();          // dans ce cas on est deja au sommet
		}
		else {
			int a=peres.size()-1;
			boolean bo=true;
			boolean continu=true;

			while(a>0&&continu){
				System.out.println(peres.get(a).getNom()==null);
				if(peres.get(a).getNom()==null){
					continu=false;
				}
				else if(!predicatsConnus.contains(peres.get(a).getNom().getLitteraux().get(0).getPredicat())){

					if(bo){
						result=new Arbre(peres.get(a));
						bo=false;
					}
				}
				a--;
			}
			return result;
		}
	}




	public static void majResult(Litteral lit, ClausesGoal clause){

		if(lit.getPredicat().equals("egal")){
			if(!(lit.getListeVar().size()==2)){
				lit.setResult(false);
			}
			else{
				if((lit.getListeVar().get(0).isFree())&&lit.getListeVar().get(1).isLink()){  // si le premier est une variable libre et le deuxieme li� , alors libre recoit li� et devient li�

					Fonctions.renomVar(lit.getListeVar().get(0), lit.getListeVar().get(1), clause); // on renome la variable dans toute la clause 
					lit.setResult(true);
				}
				lit.setResult(lit.getListeVar().get(0).getName().equals(lit.getListeVar().get(1).getName()));
			}
		}

		else if(lit.getPredicat().equals("inf")){
			if(lit.getListeVar().size()>2){
				lit.setResult(false);
			}
			else if (lit.getListeVar().get(0).isNum()&&lit.getListeVar().get(1).isNum()){ // on verifie que les 2 variable sont bien num�riques avant de faire les calculs 
				lit.setResult( Integer.parseInt(lit.getListeVar().get(0).getName())< Integer.parseInt(lit.getListeVar().get(1).getName()));
			}
			else{
				lit.setResult(false);
			}
		}

		else if(lit.getPredicat().equals("sup")){
			if(lit.getListeVar().size()>2){
				lit.setResult(false);
			}
			else if (lit.getListeVar().get(0).isNum()&&lit.getListeVar().get(1).isNum()){ // on verifie que les 2 variable sont bien num�riques avant de faire les calculs 
				lit.setResult( Integer.parseInt(lit.getListeVar().get(0).getName())> Integer.parseInt(lit.getListeVar().get(1).getName()));
			}
			else{
				lit.setResult(false);
			}
		}

		else if(lit.getPredicat().equals("plus")){
			if(!(lit.getListeVar().size()==3)){ // il faut qu'il y ai trois variable 
				lit.setResult(false);
			}
			else if (lit.getListeVar().get(0).isFree()&&lit.getListeVar().get(1).isNum()&&lit.getListeVar().get(2).isNum()){ // premiere varaible libre et les 2 autre li� 

				Fonctions.renomVar(lit.getListeVar().get(0),new Variable(String.valueOf(Integer.parseInt(lit.getListeVar().get(1).getName())+Integer.parseInt(lit.getListeVar().get(2).getName()))) , clause);
				lit.setResult(true);
			}
			else if(lit.getListeVar().get(0).isNum()){
				lit.setResult(Integer.parseInt(lit.getListeVar().get(0).getName())==Integer.parseInt(lit.getListeVar().get(1).getName())+Integer.parseInt(lit.getListeVar().get(2).getName()));
			}

			else{
				lit.setResult(false);
			}
		}


		else if(lit.getPredicat().equals("moins")){
			if(!(lit.getListeVar().size()==3)){ // il faut qu'il y ai trois variable 
				lit.setResult(false);
			}
			else if (lit.getListeVar().get(0).isFree()&&lit.getListeVar().get(1).isNum()&&lit.getListeVar().get(2).isNum()){ // premiere varaible libre et les 2 autre li� 

				Fonctions.renomVar(lit.getListeVar().get(0),new Variable(String.valueOf(Integer.parseInt(lit.getListeVar().get(1).getName())-Integer.parseInt(lit.getListeVar().get(2).getName()))), clause);
				lit.setResult(true);
			}
			else if(lit.getListeVar().get(0).isNum()){
				lit.setResult(Integer.parseInt(lit.getListeVar().get(0).getName())==Integer.parseInt(lit.getListeVar().get(1).getName())-Integer.parseInt(lit.getListeVar().get(2).getName()));
			}
			else{
				lit.setResult(false);
			}
		}

		else if(lit.getPredicat().equals("fois")){
			if(!(lit.getListeVar().size()==3)){ // il faut qu'il y ai trois variable 
				lit.setResult(false);
			}
			else if (lit.getListeVar().get(0).isFree()&&lit.getListeVar().get(1).isNum()&&lit.getListeVar().get(2).isNum()){ // premiere varaible libre et les 2 autre li� 

				Fonctions.renomVar(lit.getListeVar().get(0),new Variable(String.valueOf(Integer.parseInt(lit.getListeVar().get(1).getName())*Integer.parseInt(lit.getListeVar().get(2).getName()))), clause);
				lit.setResult(true);
			}
			else if(lit.getListeVar().get(0).isNum()){
				lit.setResult(Integer.parseInt(lit.getListeVar().get(0).getName())==Integer.parseInt(lit.getListeVar().get(1).getName())*Integer.parseInt(lit.getListeVar().get(2).getName()));
			}
			else{
				lit.setResult(false);
			}
		}

		else if(lit.getPredicat().equals("div")){
			if(!(lit.getListeVar().size()==3)){ // il faut qu'il y ai trois variable 
				lit.setResult(false);
			}
			else if (lit.getListeVar().get(0).isFree()&&lit.getListeVar().get(1).isNum()&&lit.getListeVar().get(2).isNum()){ // premiere varaible libre et les 2 autre li� 

				Fonctions.renomVar(lit.getListeVar().get(0),new Variable(String.valueOf(Integer.parseInt(lit.getListeVar().get(1).getName())/Integer.parseInt(lit.getListeVar().get(2).getName()))), clause);
				lit.setResult(true);
			}
			else if(lit.getListeVar().get(0).isNum()){
				lit.setResult(Integer.parseInt(lit.getListeVar().get(0).getName())==Integer.parseInt(lit.getListeVar().get(1).getName())/Integer.parseInt(lit.getListeVar().get(2).getName()));
			}
			else{
				lit.setResult(false);
			}
		}

		else if(lit.getPredicat().equals("mod")){
			if(!(lit.getListeVar().size()==3)){ // il faut qu'il y ai trois variable 
				lit.setResult(false);
			}
			else if (lit.getListeVar().get(0).isFree()&&lit.getListeVar().get(1).isNum()&&lit.getListeVar().get(2).isNum()){ // premiere varaible libre et les 2 autre li� 

				Fonctions.renomVar(lit.getListeVar().get(0),new Variable(String.valueOf(Integer.parseInt(lit.getListeVar().get(1).getName())%Integer.parseInt(lit.getListeVar().get(2).getName()))), clause);
				lit.setResult(true);
			}
			else if(lit.getListeVar().get(0).isNum()){
				lit.setResult(Integer.parseInt(lit.getListeVar().get(0).getName())==Integer.parseInt(lit.getListeVar().get(1).getName())%Integer.parseInt(lit.getListeVar().get(2).getName()));
			}
			else{
				lit.setResult(false);
			}
		}

		else if(lit.getPredicat().equals("write")||lit.getPredicat().equals("writeln")){
			KeyBoardListener.affiche("\n");
			for(int i=0; i<lit.getListeVar().size();i++){
				KeyBoardListener.affiche(lit.getListeVar().get(i)+" ");
			}
			lit.setResult(true);
		}

		else if(lit.getPredicat().equals("read")||lit.getPredicat().equals("readln")){
			if(!(lit.getListeVar().size()==1)){ // il faut qu'il y ai 1 variable 
				lit.setResult(false);
			}

			else if(lit.getListeVar().get(0).isFree()){
				JOptionPane jop = new JOptionPane();
				String nom = (String)jop.showInputDialog(null,"Veuillez entrer une valeur pour "+lit.getListeVar().get(0).getName(),"Read",JOptionPane.QUESTION_MESSAGE,null, null, null);
				Fonctions.renomVar(lit.getListeVar().get(0),new Variable(nom), clause);
				lit.setResult(true);
			}
			else{
				lit.setResult(false);
			}

		}

		else if (lit.getPredicat().equals("!"))
		{
			lit.setResult(true);
		}
		else if (lit.getPredicat().equals(""))
		{
			lit.setResult(true);
		}
		else if (lit.getPredicat().equals("nl"))
		{
			KeyBoardListener.affiche("\n");
		}

		else if(lit.getPredicat().equals("fail"))
		{
			lit.setResult(false); 
		}

		else if(lit.getPredicat().equals("dif")){
			if(!(lit.getListeVar().size()>2)){
				lit.setResult(false);
			}
			else if (lit.getListeVar().get(0).isNum()&&lit.getListeVar().get(1).isNum()){ // on verifie que les 2 variable sont bien num�riques avant de faire les calculs 
				lit.setResult( Integer.parseInt(lit.getListeVar().get(0).getName())!= Integer.parseInt(lit.getListeVar().get(1).getName()));
			}
			else{
				lit.setResult(false);
			}
		}
	}



}
