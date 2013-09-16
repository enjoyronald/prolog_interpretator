package copier_coller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;

//import windows.*;

public class Couper implements ActionListener{
	JEditorPane jep;
	public Couper(JEditorPane jep){
		this.jep=jep;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Utilities.setAsText(jep.getSelectedText());
		jep.replaceSelection("");

	}

}
