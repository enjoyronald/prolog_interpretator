package ClassesDuProjet;




import java.util.ArrayList;




public abstract class Clauses {
	protected String contenu;
	protected ArrayList<Litteral> litteraux=new ArrayList<Litteral>();
	protected int numero;                    // c'est ce numéro qui nous permet de savoir son ordre dans le programme 
	protected static int id=0;               // 
	
	public Clauses (String str){
		//str=str.replaceAll(" ","");
		contenu=str;
		analyse(this);
		numero=id++;
		
	}
	
	
	public static void analyse(Clauses clause){
		String str="";
		Litteral l=null;
		boolean attente=false;
		//	RunListener.affiche("//-------------------------------LANCEMENT DE L ANALYSE-------------------------------------------//");

		for(int i=0; i<clause.getContenu().length();i++){
			char c= clause.getContenu().charAt(i);



			if(c==' '){//si c'est un espace on ne l'ajoute pas a str
				if(attente){  // si c'est vrai, on est sur qu'il attent une variable 
					str+=" ";
				} 	
			}
			
			if(c==10);
			else if(c=='('){
				l=new Litteral(str);
				clause.litteraux.add(l);
				str="";   // on vide str      on commence lance l'attente de variable pour notre litteral 	
				attente=true;
			}
			else if(c==':'){  // on ne fait rien
			
			}

			else if(c=='-'){
				str="";
			}
			else if(c==' '){
				str="";
			}

			else if(c==','){  // il y a de possibilit�, soit il separe 2 variable, soit 2 litteraux
				if(attente){  // si c'est vrai, on est sur qu'il attent une variable 
					l.getListeVar().add(new Variable(str));
	
					str="";
				}
				else if(clause.getContenu().charAt(i-1)==')'){  // si c'est la fin d'un literal, on fait rien 
					
				}
				else{    // dans ce cas c'est un literal sans argument 
					l=new Litteral(str);
					clause.litteraux.add(l);
					str="";
				}

			}

			else if(c==')'){
				l.getListeVar().add(new Variable(str));
			//	l.getListeVar().get(l.getListeVar().size()-1).setClause(clause);
			//	clause.getListeVar().add(l.getListeVar().get(l.getListeVar().size()-1));
				//l.updateResult();
				str="";
				attente=false;
			}
			else if(i==clause.getContenu().length()-1){  // fin de la clasue
				str+=c;
				l=new Litteral(str);
				clause.litteraux.add(l);
				str="";
			}
		
			else str+=c;
		}
	
	}
	
	
	@Override
	public String toString() {
		//return contenu + " les Litteraux sont : " + litteraux + " "+numero+"  fin de la clause";
		String str="";
		for(int i=0;i<litteraux.size()-1;i++){
			str+=litteraux.get(i)+",";
		}
		if(!litteraux.isEmpty())
		str+=litteraux.get(litteraux.size()-1)+".";
		return str;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public ArrayList<Litteral> getLitteraux() {
		return litteraux;
	}

	public void setLitteraux(ArrayList<Litteral> litteraux) {
		this.litteraux = litteraux;
	}

	public int getNumero() {
		return numero;
	}
	
	
	
	
}

