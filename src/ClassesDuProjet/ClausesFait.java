package ClassesDuProjet;

import java.util.ArrayList;

public class ClausesFait extends Clauses{

	//-----------------Vue que ce sont des clauses Faits , elles ne comporte q'un seul litteral, et celui si est True
	public ClausesFait(String str) {
		super(str);
		for(int i=0; i<this.getLitteraux().size();i++){
			this.getLitteraux().get(i).setResult(true);
		}
	}
	
	public ClausesFait(ClausesFait str){
		super(str.getContenu());
		//this.setContenu(str.getContenu().substring(0, str.getContenu().length()-2));
	}
	

}
