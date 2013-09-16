package ClassesDuProjet;

public class Variable {
	protected String name;
	
	protected boolean isNum; 	// variable numerique , on peut donc effectuer des calcul (pas libre )
	protected boolean isFree;  // ça veut dire que c'est une variable libre , on peut la changer par une autre 
	protected boolean isLink;  // peut pas modifier
	protected boolean isCons;   // peu pas modifier
	
	
	
	
	
	public Variable (String str) {
		this.name = str;
		determinerType();
		
	}
	
	/*public void renome(Variable var){
		this.setLink(var.isLink);
		this.setCons(var.isCons);
		this.setFree(var.isFree);
		this.setNum(var.isNum);
	} */
	
	
	public void determinerType(){
		int i;
		try{
			i=Integer.parseInt(name);
			this.setNum(true);
			this.setLink(true);		
		}
		catch (NumberFormatException e){
			if(name.length()==0){
				
			}
		else if (!(name.charAt(0) >= 'a' && name.charAt(0)<='z' )){ // est une constante, donc non modifiable
				this.setFree(true);
				this.setLink(false);
			}
			else{
				this.setCons(true);
				this.setLink(true);
			}
		}
		
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public boolean isNum() {
		return isNum;
	}


	public void setNum(boolean isNum) {
		this.isNum = isNum;
	}


	public boolean isFree() {
		return isFree;
	}


	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}


	public boolean isLink() {
		return isLink;
	}


	public void setLink(boolean isLink) {
		this.isLink = isLink;
	}


	public boolean isCons() {
		return isCons;
	}


	public void setCons(boolean isCons) {
		this.isCons = isCons;
	}


	@Override
	public String toString() {
		return name ;
	}
	
	
	
}