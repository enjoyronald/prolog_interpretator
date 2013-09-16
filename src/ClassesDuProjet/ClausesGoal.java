package ClassesDuProjet;

public class ClausesGoal extends Clauses{

	public ClausesGoal(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}
	public ClausesGoal(ClausesGoal str) {
		super(str.getContenu());
		//this.setContenu(str.getContenu().substring(0, str.getContenu().length()-2));
		// TODO Auto-generated constructor stub
	}

}
