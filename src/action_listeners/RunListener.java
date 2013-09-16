package action_listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.jws.soap.SOAPBinding.Style;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ClassesDuProjet.Fonctions;

import windows.Fenetre;
import windows.Resolution;

public class RunListener implements ActionListener{

	protected static Fenetre win;
	
	public RunListener(Fenetre bis){
		win=bis;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Resolution res=new Resolution(win);
	}
	
	
	/*
	public static void affiche(Object str){
	
		//win.getText().setText(doc);
		win.getText().setText(win.getText().getText()+"\n"+str.toString());
	}
*/
	
}
