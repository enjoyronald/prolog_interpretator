package copier_coller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;

//import windows.*;

public class Coller implements ActionListener{
	JEditorPane jep;
	public Coller(JEditorPane jep){
		this.jep=jep;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//String temp=jep.getText();
		//text.setText(temp+getAsText());
		jep.replaceSelection(Utilities.getAsText());

	}

}
