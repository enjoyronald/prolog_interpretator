package action_listeners;

import windows.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;



import javax.swing.JFileChooser;


public class SaveAsListener implements ActionListener{
	Fenetre win;
	
	public SaveAsListener(Fenetre bis){
		win=bis;
	}
	public void actionPerformed(ActionEvent arg0) {
		String str="";
		for(int i=0; i<win.getListClauses().size();i++){
			str+=(win.getListClauses().get(i)+"\n");
		}
		for(int i=0; i<win.getClausesGoal().size();i++){
			str+=(win.getClausesGoal().get(i)+"\n");
		}
		JFileChooser fc= new JFileChooser();
		fc.setFileFilter(new FiltreFichier());
		int option = fc.showSaveDialog(win.getComponent(0));  
		if(option == JFileChooser.APPROVE_OPTION){  
			if(fc.getSelectedFile()!=null){  
				win.setProjetPath(fc.getSelectedFile().getAbsolutePath()); 
				try{
					win.setFile(fc.getSelectedFile());
					FileWriter fstream = new FileWriter(win.getProjetPath());
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(str);
					win.setProjet(win.nomFichier(win.getFile()));
					win.initTitle();
					out.close();
				}
				catch (Exception e){//Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
				Calendar cal = Calendar.getInstance();
				KeyBoardListener.affiche("Le projet est � �t� sauvegard� dans "+win.getProjetPath()+" � "+cal.get(Calendar.HOUR_OF_DAY)+"h "+cal.get(Calendar.MINUTE)+"m et "+cal.get(Calendar.SECOND)+"s");
				KeyBoardListener.affiche("prolog--$");	

			}

		}
	}
}
