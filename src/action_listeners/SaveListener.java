package action_listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;

import javax.swing.JFileChooser;

import windows.*;

public class SaveListener implements ActionListener{
Fenetre win;

	public SaveListener(Fenetre bis){
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
		
		if(win.getProjet()==""){
			JFileChooser fc= new JFileChooser();
			fc.setFileFilter(new FiltreFichier());
			int option = fc.showSaveDialog(win.getComponent(0));  
			if(option == JFileChooser.APPROVE_OPTION){  
				if(fc.getSelectedFile()!=null){  
					win.setProjetPath(fc.getSelectedFile().getAbsolutePath()); 
					Calendar cal = Calendar.getInstance();
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
					KeyBoardListener.affiche("Le projet est � �t� sauvegard� dans "+win.getProjetPath()+" � "+cal.get(Calendar.HOUR_OF_DAY)+"h "+cal.get(Calendar.MINUTE)+"m et "+cal.get(Calendar.SECOND)+"s");
					KeyBoardListener.affiche("prolog--$");	

				}

			}	
		}
		else {
			try{
				Calendar cal = Calendar.getInstance();
				FileWriter fstream = new FileWriter(win.getProjetPath());
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(str);	
				KeyBoardListener.affiche("Sauvegarde bien effectu�e "+" � "+cal.get(Calendar.HOUR_OF_DAY)+"h "+cal.get(Calendar.MINUTE)+"m et "+cal.get(Calendar.SECOND)+"s");
				KeyBoardListener.affiche("prolog--$");	
				//	win.getText().setText(str+"\n"+"Sauvegarde bien effectu�e "+" � "+cal.get(Calendar.HOUR_OF_DAY)+"h "+cal.get(Calendar.MINUTE)+"m et "+cal.get(Calendar.SECOND)+"s");
				//Close the output stream
				out.close();
			}catch (Exception e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}	
		}
	}

}
