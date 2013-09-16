package action_listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ClassesDuProjet.ClausesFait;
import ClassesDuProjet.ClausesGoal;
import ClassesDuProjet.ClausesRegle;
import ClassesDuProjet.Fonctions;

import windows.Fenetre;
import windows.Resolution;

public class KeyBoardListener implements KeyListener {

	public static Fenetre win;
	ArrayList<String> start=new ArrayList<String>();
	protected ArrayList<String> fonctions =new ArrayList<String>();
	String str="";

	public KeyBoardListener(Fenetre bis){
		this.win=bis;
		initFonctions();
	}

	public void initFonctions(){
		String[] tab={"&run","&","&faits","&regles","&goals","&univer","&re-init"};
		for(int i=0; i<tab.length;i++){
			fonctions.add(tab[i]);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if((e.getKeyCode()==KeyEvent.VK_ENTER)){
			this.setStr(recupString(win.getText()));
			//=======================================On verifie s'il s'agit de fonction logiciel
			if(str.length()==0){

			}
			else if(fonctions.contains(str)){
				int i=fonctions.indexOf(str);
				if(i==0){ // &run   lancement de l'application 
					Resolution res=null;
					res=new Resolution(win);
				}
				if(i==1){
					//String[] tab={"&run","&","&faits","&regles","&goals","&univer"};
					affiche("\t pour lancer l'execution, tappez '&run'");
					affiche("\t pour Afficher les faits, tappez '&faits'");
					affiche("\t pour Afficher les regles, tappez '&regles'");
					affiche("\t pour Afficher les goals, tappez '&goals'");
					affiche("\t pour Afficher l'univer, tappez '&univer'");
					affiche("\t pour Supprimer tout l'univers, tappez '&re-init'");	
				}
				if(i==2){
					affiche("");
					affiche("\t------------------------------------------------------------");
					affiche("\t\tLISTE DES FAITS");
					affiche("\t------------------------------------------------------------");
					affiche("");
					for(int a=0;a<=win.getClausesFait().size()-1;a++){
						affiche(win.getClausesFait().get(a));
					}
					affiche("");
				}
				if(i==3){
					affiche("");
					affiche("\t------------------------------------------------------------");
					affiche("\t\tLISTE DES REGLES");
					affiche("\t------------------------------------------------------------");
					affiche("");
					for(int a=0;a<=win.getClausesRegle().size()-1;a++){
						affiche(win.getClausesRegle().get(a));
					}
					affiche("");
				}
				if(i==4){
					affiche("");
					affiche("\t------------------------------------------------------------");
					affiche("\t\tLISTE DES GOALS");
					affiche("\t------------------------------------------------------------");
					affiche("");
					for(int a=0;a<=win.getClausesGoal().size()-1;a++){
						affiche(win.getClausesGoal().get(a));
					}
					affiche("");
				}
				if(i==5){
					affiche("==========================================================");
					affiche("\t\tAFFICHAGE DE TOUT L'UNIVER");
					affiche("==========================================================");
					affiche("");
					affiche("\t------------------------------------------------------------");
					affiche("\t\tLISTE DES FAITS");
					affiche("\t------------------------------------------------------------");
					affiche("");
					for(int a=0;a<=win.getClausesFait().size()-1;a++){
						affiche(win.getClausesFait().get(a));
					}
					affiche("");
					affiche("\t------------------------------------------------------------");
					affiche("\t\tLISTE DES REGLES");
					affiche("\t------------------------------------------------------------");
					affiche("");
					for(int a=0;a<=win.getClausesRegle().size()-1;a++){
						affiche(win.getClausesRegle().get(a));
					}
					affiche("");
					affiche("\t------------------------------------------------------------");
					affiche("\t\tLISTE DES GOALS");
					affiche("\t------------------------------------------------------------");
					affiche("");
					for(int a=0;a<=win.getClausesGoal().size()-1;a++){
						affiche(win.getClausesGoal().get(a));
					}
					affiche("");
					affiche("---------------------------------------------------------------------------------------");
				}
				if(i==6){
					win.getClausesRegle().clear();
					win.getClausesFait().clear();
					win.getClausesGoal().clear();
					win.getListClauses().clear();
					affiche("");
					affiche("\t------------------------------------------------------------");
					affiche("\t\tTOUTES LES CLAUSES ONT ETE SUPPRIMEES");
					affiche("\t------------------------------------------------------------");
					affiche("");
				}
			}

			else if(!(str.charAt(str.length()-1)=='.')){
				System.out.println(str+"-");
				afficheSurLigne("\t ERREUR DE SYNTHASE, UNE CLAUSE DOIT FINIR PAR '.'");		
			}
			else{
				System.out.println(this.getStr());
				constructClauses();
			}
		}
		else if((e.getKeyCode()==KeyEvent.VK_CONTROL+KeyEvent.VK_N)){
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//Turn off metal's use of bold fonts
					UIManager.put("swing.boldMetal", Boolean.FALSE); 
					Fenetre win=new Fenetre();
				}
			});
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if((e.getKeyCode()==KeyEvent.VK_ENTER)){
			afficheSurLigne("prolog--$");	
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	//=======================================FONCTIONS UTILES A L'UTILISATION DU KEYLISTENER========================
	public void constructClauses(){
		ArrayList<String> test=new ArrayList<String>();
		test=Fonctions.decoupeClauses(this.getStr());
		int result;

		for(int i=0; i<test.size();i++){
			result=Fonctions.typeClauses(test.get(i));
			if(result==0){
				win.getClausesFait().add(new ClausesFait(test.get(i)));
				win.getListClauses().add(win.getClausesFait().get(win.getClausesFait().size()-1));
			}
			else if(result==1){
				win.getClausesGoal().add(new ClausesGoal(test.get(i)));
			}
			else {
				win.getClausesRegle().add(new ClausesRegle(test.get(i)));
				win.getListClauses().add(win.getClausesRegle().get(win.getClausesRegle().size()-1));
			}

		}


	}
	public static void affiche(Object str){
		String s;
		//win.getText().setText(doc);
		//win.getText().setText(win.getText().getText()+"\n"+str);
		s=win.getText().getText()+"\n"+str;
		s=s.substring(0, s.length());
		win.getText().setText(s);
	}
	public static void afficheSurLigne(Object str){
		String s;
		//win.getText().setText(doc);
		//win.getText().setText(win.getText().getText()+"\n"+str);
		s=win.getText().getText()+str;
		s=s.substring(0, s.length());
		win.getText().setText(s);
	}

	public String recupString(JEditorPane jep){
		String result="";
		String allText=jep.getText(); 


		int indiceDeb=allText.length()-1;
		int indiceFin=allText.length()-1;
		boolean go=true;

		for(int i=allText.length()-1; i>0;i--){
			char a=allText.charAt(i);

			if(a=='$'&&go){
				indiceDeb=i+1;
				go=false;
			}
		}
		result=allText.substring(indiceDeb);
		return result;
	}

	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}


}
