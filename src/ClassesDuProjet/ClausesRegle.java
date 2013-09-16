package ClassesDuProjet;

public class ClausesRegle extends Clauses{
	
	public ClausesRegle(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}
	public ClausesRegle(ClausesRegle str) {
		super(str.getContenu());
		//this.setContenu(str.getContenu().substring(0, str.getContenu().length()-2));
		// TODO Auto-generated constructor stub
	}

}
