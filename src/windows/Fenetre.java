package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;

import ClassesDuProjet.ClausesFait;
import ClassesDuProjet.ClausesGoal;
import ClassesDuProjet.ClausesRegle;
import action_listeners.*;
import copier_coller.*;

import javax.swing.*;


public class Fenetre extends JFrame {

	//=======================================



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//---------------------------------ATTRIBUTS CLES POUR L EXPLOITATION DE LA CLASSE----------------------------------
	protected String projet="";                      // nom du projet 
	protected File file;                             // fichier dans le quel est enregistrer le projet
	protected String projetPath;                     // chemin absolu du fichier d'enregistrement 

	//----------------------------------FIN DES AtTRIBUT CLEES DE LA CLASSE---------------------------------

	//-----------------------------------DECLARATION DES CLAUSES UTILES POUR LA RESOLUTION-----------------------------
	protected ArrayList<ClausesFait> clausesFait=new ArrayList<ClausesFait>();
	protected ArrayList<ClausesRegle> clausesRegle=new ArrayList<ClausesRegle>();
	protected ArrayList<ClausesGoal> clausesGoal =new ArrayList<ClausesGoal>();
	ArrayList<Object>     listClauses =new ArrayList<Object>();


	//________________________________________gros block d'affichage _________________________________________________________________________________

	//-----------------------------------------declaration du containner--------------------------------------------------------
	protected JPanel containner=new JPanel();
	protected JPanel center=new JPanel();
	protected JPanel down =new JPanel();


	//---------------------------------declaration des pop up menu --------------------------------------------------
	protected JPopupMenu jpmText = new JPopupMenu();

	//-----------------------------------------declaration des zones de text------------------------------------------------
	protected JEditorPane text=new JEditorPane();
	JScrollPane stext = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );	

	//------------------------------------------ajout de la barre pour les racourcis + container----------------------------------------------
	protected JPanel racourcis=new JPanel();
	//-------------------------------------------Fin declaration buttons pour racoursi 
	//--------------------------------------------- ajout de la barre de menu ------------------------------------------------------
	protected JMenuBar menuBar = new JMenuBar();

	//			-----------------------------------declaration des menus de la barre--------------------
	protected JMenu fichier = new JMenu("Fichier");
	protected JMenu edition = new JMenu("Edition");
	protected JMenu menuRun = new JMenu("Run");
	protected JMenu aide = new JMenu("Aide");

	//-------------------------------------------------declaration des sous menus pour fichier---------------------------------------------
	protected ArrayList<JMenuItem> listItemFile=new ArrayList<JMenuItem>();
	protected JMenuItem newFile = new JMenuItem("Nouveau",new ImageIcon("image/newfile_wiz.gif"));
	protected JMenuItem open = new JMenuItem("Ouvrir un fichier existant",new ImageIcon("image/prj_obj.gif"));
	protected JMenuItem save = new JMenuItem("enregistrer", new ImageIcon("image/save_edit.gif"));
	protected JMenuItem saveAs = new JMenuItem("Enregistrer sous", new ImageIcon("image/saveas_edit.gif"));
	protected JMenuItem close = new JMenuItem("fermer");

	protected JMenuItem tmp = new JMenuItem("fermer");  //utile pour les popup



	//------------------------------------------------declarations des sous menu pour edition
	protected ArrayList<JMenuItem> listItemEdit=new ArrayList<JMenuItem>();
	protected JMenuItem cut = new JMenuItem("couper", new ImageIcon("image/cut_edit.gif"));
	protected JMenuItem copy = new JMenuItem("copier",new ImageIcon("image/copy_edit.gif"));
	protected JMenuItem paste = new JMenuItem("coller",new ImageIcon("image/paste_edit.gif"));
	protected JMenuItem select = new JMenuItem("tout selectionner");
	protected JMenuItem delete = new JMenuItem("supprimer",new ImageIcon("image/delete_edit.gif"));


	//---------------------------------------------------declaration des sous menu pour run
	protected ArrayList<JMenuItem> listItemRun=new ArrayList<JMenuItem>();
	protected JMenuItem itemRun = new JMenuItem("Run",new ImageIcon("image/run_exc.gif"));


	//---------------------------------------------------declaration des sous menu aide 
	protected ArrayList<JMenuItem> listItemInfo=new ArrayList<JMenuItem>();
	protected JMenuItem infoPro = new JMenuItem("prolog",new ImageIcon("image/help.gif"));
	protected JMenuItem infoVer = new JMenuItem("info version",new ImageIcon("image/info_tsk.gif"));
	protected JMenuItem infoDev = new JMenuItem("info developpeurs",new ImageIcon("image/info_tsk.gif"));
	

	//--------------------------------------Fin declaration variables barre des menus



	//==================================================Fin Declariation des variables=====================================


	//===============================================Constructeur de la Fenetre============================================	
	public Fenetre( ){
		//partie utile pour la taille et les dimension de la fenÃªtre 
		this.setSize(700,400);
		this.setAlwaysOnTop(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("PROLOG VERSION 3.5 projet sans titre");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.getText().setText("####################################################################\n\t\t\tProlog version 3.5\n\tCe logicile prolog est en licence libre. Il vous est propose par 2 etudiants de l'universitÃ© de paris dauphne, Nabil Laraibi, et Ronald Choundong Chiatiah Ronald \n pour toute aide concernant l'utilisation du logiciel, tapez &aide\n pour nous contacter :\n####################################################################\nprolog--$");
		creationMenu();
		addListeners();
		creationRaccourci();
		creationPopUp();
		creationAff();

	}
	//---------------------------------------------------fin constructeur-------------------------------------------------------

	//-------------------------fonction creationZoneText(c'est cette fonction qui alloue les zone text-----------------------------
	public void addListeners(){

		//--------------------------les listeners des bouttons -----------------------------------------

		//----------------ajout des boutons 

		//---------------------les listener des items 
		OpenListener openl=new OpenListener(this);
		newFile.addActionListener(new NewListener());
		listItemFile.add(newFile);
		open.addActionListener(openl);
		listItemFile.add(open);
		save.addActionListener(new SaveListener(this));
		listItemFile.add(save);
		saveAs.addActionListener(new SaveAsListener(this));
		listItemFile.add(saveAs);
		close.addActionListener(new CloseListener());
		listItemFile.add(close);

		cut.addActionListener(new Couper(this.getText()));listItemEdit.add(cut);
		copy.addActionListener(new Copier(this.getText()));listItemEdit.add(copy);
		paste.addActionListener(new Coller(this.getText()));listItemEdit.add(paste);
		delete.addActionListener(new DeleteListener(this.getText()));listItemEdit.add(delete);
		select.addActionListener(new SelectListener(this.getText()));listItemEdit.add(delete);

		//itemRun.addActionListener(new RunListener(this));listItemRun.add(itemRun);

		infoPro.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop = new JOptionPane();

				String mess = "";
				mess += " Logic programming is a programming paradigm based \non mathematical logic. In this paradigm the programmer \nspeciï¬�es relationships among data values (this constitutes a logic program) \nand then poses queries to the execution \n environment (usually an interactive interpreter) in order to see whether certain relationships hold. Putting this in another way, a logic program,\n through explicit facts and rules, deï¬�nes a base of knowledge from which implicit knowledge can be extracted. This style of programming is popular for data base \n interfaces, expert systems, and mathematical theorem provers.\n In this tutorial you will be introduced to\n Prolog, the primary logic programming language,\n through the interactive SWI-Prolog system (interpreter).\nYou will notice that Prolog has some similarities to a functional programming language such as Hugs. A\nfunctional program consists of a sequence \nof function deï¬�nitions â€” a logic program consists of a sequence\nof relation deï¬�nitions. Both rely heavily on recursive deï¬�nitions. The big diï¬€erence is in the underlying\nexecution â€œengineâ€� â€” i.e., the imperative parts of the languages. The execution engine of a functional\nlanguage evaluates an expression \nby converting it to an acyclic graph and then reducing the graph to a\nnormal form which represents the computed value. The Prolog execution environment, on the other hand,\ndoesnâ€™t so much â€œcomputeâ€� an answer, it â€œdeducesâ€� an answer from the relation deï¬�nitions at hand. Rather\nthan being given an expression to evaluate, the Prolog environment is given an expression which it interprets as a question:\nFor what parameter values does the expression evaluate to true?\n You will see that Prolog is quite diï¬€erent from other programming languages you have studied. First, \n Prologhas no types. In fact, the basic logic programming environment has no literal values as such. Identiï¬�ers\nstarting with lower-case letters denote data values (almost like values in an enumerated type) while all other\nidentiï¬�ers denote variables. Though the basic elements of Prolog are typeless, most implementations have\nbeen enhanced to include character and integer values and operations. Also, Prolog has mechanisms built\nin for describing tuples and lists. You will ï¬�nd some similarity between these structures and those provided \n in Hugs.";

				JOptionPane.showMessageDialog(null, mess, "Ã€ propos de prolog", JOptionPane.INFORMATION_MESSAGE);
				jop.setSize(new Dimension(350,500));
			}
		});listItemInfo.add(infoPro);

		//information sur la version
		infoVer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop = new JOptionPane();

				String mess = "Version Beta \n release date : 23-02-2013 ";

				JOptionPane.showMessageDialog(null, mess, "Version", JOptionPane.INFORMATION_MESSAGE);
				jop.setSize(new Dimension(350,500));
			}
		});listItemInfo.add(infoVer);
		//information sur les programmeurs
		infoDev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop = new JOptionPane();

				String mess = "Pragrammeurs; \n Choundong Chiatiah Ronald \n Nabil LaraÃ¯bi  ";

				JOptionPane.showMessageDialog(null, mess, "Version", JOptionPane.INFORMATION_MESSAGE);
				jop.setSize(new Dimension(350,500));
			}
		});listItemInfo.add(infoDev); 

	}


	//-------------------------------------barre de menu-------------------------------------------------------
	public void creationMenu(){

		//on remplie le menu fichier 
		this.fichier.add(newFile);
		this.fichier.addSeparator();
		this.fichier.add(open);
		this.fichier.add(save);
		this.fichier.add(saveAs);
		this.fichier.addSeparator();
		this.fichier.add(close);

		// on remplie edition
		this.edition.add(copy);
		this.edition.add(cut);
		this.edition.add(paste);
		this.edition.add(select);
		this.edition.addSeparator();
		this.edition.add(delete);

		// on remplie Run
		//this.menuRun.add(itemRun);

		//on remplie aide
		this.aide.add(infoPro);
		this.aide.add(infoVer);
		this.aide.add(infoDev);

		//on rajoute les menu Ã  la menuBar
		this.menuBar.add(fichier);
		this.menuBar.add(edition);
		this.menuBar.add(menuRun);
		this.menuBar.add(aide);
		this.setJMenuBar(menuBar);


	}

	//---------------------            creation barre raccourcis
	public void creationRaccourci(){
	}
	public void creationPopUp(){
		//jpmText = null;


		jpmText.add(copyOf(cut), 0);  //couper 
		jpmText.add(copyOf(copy));   //copier
		jpmText.add(copyOf(paste));    // coller
		jpmText.addSeparator();
		jpmText.add(copyOf(save));
		jpmText.add(copyOf(saveAs));

		stext.setPreferredSize(new Dimension(800,400));
		text.setComponentPopupMenu(jpmText);
		text.setCaretColor(Color.RED);
		text.setDisabledTextColor(Color.BLUE);
		text.setEditable(true);
		text.setSelectedTextColor(Color.RED);

		text.addKeyListener(new KeyBoardListener(this));



	}
	public void creationAff(){

		text.setBackground(Color.BLACK);
		//text.setForeground(Color.DARK_GRAY);
		text.setForeground(Color.WHITE);
		//text.setBounds(5, 10,3, 15);
	

		//this.setContentPane(console);
		this.setContentPane(containner);
		containner.setLayout(new BorderLayout());
		//stext.setSize(new Dimension(this.getWidth()-10, sconsole.getHeight()*4));
		containner.add(stext, BorderLayout.CENTER);
	}







	public JMenuItem copyOf(JMenuItem original){
		JMenuItem copy=new JMenuItem();
		copy.setName(original.getName());
		copy.setText(original.getText());
		for(int i=0; i<original.getActionListeners().length;i++){
			copy.addActionListener(original.getActionListeners()[i]);
		}
		copy.setIcon(original.getIcon());

		return copy;
	}

	public void initTitle(){
		this.setTitle("le projet ouvert est : "+projet);
	}
	// Petite fonction pour RETOURNER LE NOM DES FICHIER POUR LE TITRE HIHIHI
	public String nomFichier(File f){
		String ext=null;
		if(f==null){
			return ext;
		}
		else{
			String tenpon =f.getAbsolutePath();
			int a=tenpon.lastIndexOf(92);
			int b=tenpon.lastIndexOf(".");

			ext = tenpon.substring(a+1,b);
			ext.toLowerCase();
			return ext;
		}	

	}
	//===========================================+Fonction gerant l'affichage======================================

	//===========================================les getteurs et les setteurs========================================
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public JEditorPane getText() {
		return text;
	}

	public void setText(JEditorPane text) {
		this.text = text;
	}


	public String getProjetPath() {
		return projetPath;
	}

	public void setProjetPath(String projetPath) {
		this.projetPath = projetPath;
	}

	public String getProjet() {
		return projet;
	}
	public void setProjet(String projet) {
		this.projet = projet;
	}
	public ArrayList<ClausesFait> getClausesFait() {
		return clausesFait;
	}
	public void setClausesFait(ArrayList<ClausesFait> clausesFait) {
		this.clausesFait = clausesFait;
	}
	public ArrayList<ClausesRegle> getClausesRegle() {
		return clausesRegle;
	}
	public void setClausesRegle(ArrayList<ClausesRegle> clausesRegle) {
		this.clausesRegle = clausesRegle;
	}
	public ArrayList<ClausesGoal> getClausesGoal() {
		return clausesGoal;
	}
	public void setClausesGoal(ArrayList<ClausesGoal> clausesGoal) {
		this.clausesGoal = clausesGoal;
	}
	public ArrayList<Object> getListClauses() {
		return listClauses;
	}
	public void setListClauses(ArrayList<Object> listClauses) {
		this.listClauses = listClauses;
	}


}

