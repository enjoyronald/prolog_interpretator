package action_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;

import windows.*;

public class OpenListener implements ActionListener{

	Fenetre win;
	public OpenListener(Fenetre bis){
		win=bis;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//System.out.println(arg0.getSource().getClass());
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FiltreFichier());
		//	fc.addActionListener(new FcListener());
		int returnVal = fc.showOpenDialog(win.getComponent(0));
		if(returnVal==JFileChooser.APPROVE_OPTION){
			win.setProjetPath(fc.getSelectedFile().getAbsolutePath()); 
			try{
				win.setFile(fc.getSelectedFile());
				win.setProjetPath(fc.getSelectedFile().getAbsolutePath());
				String chaine="";
				InputStream ips=new FileInputStream(win.getProjetPath()); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				while ((ligne=br.readLine())!=null){
					System.out.println(ligne);
					chaine+=ligne+"\n";
				}
				win.setProjet(win.nomFichier(win.getFile()));
				win.initTitle();
				chaine=chaine.substring(0, chaine.length()-1);
				KeyBoardListener.affiche(chaine);
				br.close(); 
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
			
		}
		if(returnVal==JFileChooser.CANCEL_OPTION){
			KeyBoardListener.affiche(" aucun fichier n'a ete ouvert, operation annule par l'utilisateur");
		}


	}

}
