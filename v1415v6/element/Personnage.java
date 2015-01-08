/**
 * 
 */
package element;


import interaction.Actions;
import interaction.Deplacements;
import interfaceGraphique.VueElement;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import utilitaires.Calculs;

/**
 * Un personnage: un element possedant des caracteristiques et etant capable
 * de jouer une strategie. 
 */
public class Personnage extends Element implements IPersonnage {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Reference du leader de ce personnage, -1 si aucun.
	 */
	private int leader;
	
	/**
	 * Reference des personnages de l'equipe de ce personnage. 
	 * Vide si le leader n'est pas egal a -1.
	 */
	private ArrayList<Integer> equipe;

	/**
	 * Constructeur d'un personnage avec un nom et une quantite de force et de charisme.
	 * Au depart, le personnage n'a ni leader ni equipe.
	 * @param nom
	 * @param force
	 * @param charisme
	 * @param hp
	 * @param vitesse
	 * @param defense
	 */
	public Personnage(String nom, int force, int charisme, int hp, int vitesse, int defense) {
		super(nom);
		ajouterCaract("force", force);
		ajouterCaract("charisme", charisme);
		
		/* -------------------------------------------
		 * Nouvelles stats
		 * ---------------------------------------- */
		ajouterCaract("hp", hp);
		ajouterCaract("vitesse", vitesse);
		ajouterCaract("defense", defense);
				
		leader = -1;
		equipe = new ArrayList<Integer>();
	}
	
	/* ----------------------------------------------------------
	 * ACCESSEURS
	 * ---------------------------------------------------------- */
	
	/** ---------------------------------------------------------
	 * 		public int getForce
	 *  ---------------------------------------------------------
	 * Retourne la valeur de force.
	 * @return bonus de force
	 *  ------------------------------------------------------ */
	public int getForce() {
		return getCaract("force");
	}
	
	
	/** ---------------------------------------------------------
	 * 		public int getCharisme
	 *  ---------------------------------------------------------
	 * Retourne la valeur de charisme.
	 * @return bonus de charisme
	 *  ------------------------------------------------------ */
	public int getCharisme() {
		return getCaract("charisme");
	}
	
	/** ---------------------------------------------------------
	 * 		public int getHP
	 *  ---------------------------------------------------------
	 * Retourne la valeur de la vie.
	 * @return bonus de vie
	 *  ------------------------------------------------------ */
	public int getHP() {
		return getCaract("hp");
	}
	
	/** ---------------------------------------------------------
	 * 		public int getVitesse
	 *  ---------------------------------------------------------
	 * Retourne la valeur de vitesse.
	 * @return bonus de vitesse
	 *  ------------------------------------------------------ */
	public int getVitesse() {
		return getCaract("vitesse");
	}
	
	/** ---------------------------------------------------------
	 * 		public int getDefense
	 *  ---------------------------------------------------------
	 * Retourne la valeur de defense.
	 * @return bonus de defense
	 *  ------------------------------------------------------ */
	public int getDefense(){
		return getCaract("defense");
	}

	
	/** ----------------------------------------------------------
	 * 		public int getLeader()
	 *  ----------------------------------------------------------
	 * Retourne le leader.
	 * @return leader (-1 si aucun)
	 *  ---------------------------------------------------------- */
	public int getLeader() {
		return leader;
	}

	
	/** ----------------------------------------------------------
	 * 		public ArrayList<Integer> getEquipe
	 *  ----------------------------------------------------------
	 * Retourne la liste des personnages de l'equipe.
	 * @return equipe
	 * -------------------------------------------------------- */
	public ArrayList<Integer> getEquipe() {
		return equipe;
	}
	

	@Override
	/** ----------------------------------------------------------
	 * 		public String toString
	 *  ----------------------------------------------------------
	 *  Cette méthode détermine l'affichage des différentes stats
	 *  ----------------------------------------------------------
	 * @return string
	 * ------------------------------------------------------- */
	public String toString(){
		String lead = (leader != -1)? ", leader: " + leader: "";
		String eq = "";
		
		if(!equipe.isEmpty()) {
			eq += ", equipe: ";
			
			for(int i = 0; i < equipe.size(); i++) {
				eq += equipe.get(i);
				
				if(i < equipe.size() - 1) {
					eq += " ";
				}
			}
		} else {
			eq += " Aucune ";
		}
		
		return super.toString() + "[" + "HP : " + getHP() + ", For : " + getForce() + ", Vit : " + getVitesse() + ", Def : " + getDefense() + ", Cha :" + getCharisme() + "::> Equipe :" + lead + eq + "]";
	}
	
	
	
	
	@Override
	/** ----------------------------------------------------------
	 * 		public void setLeader()
	 *  ----------------------------------------------------------
	 * Cette méthode définie qui est le leader
	 * ------------------------------------------------------- */
	public void setLeader(int ref) throws RemoteException {
		leader = ref;
	}
	
	@Override
	/** ----------------------------------------------------------
	 * 		public void clearLeader()
	 *  ----------------------------------------------------------
	 * Cette méthode supprime le leader
	 * ------------------------------------------------------- */
	public void clearLeader() throws RemoteException {
		leader = -1;
	}
	
	@Override
	/** ----------------------------------------------------------
	 * 		public void ajouterEquipe(int ref)
	 *  ----------------------------------------------------------
	 * Cette méthode permet d'ajouter un personnage dans l'équipe
	 * -----------------------------------------------------------
	 * @param int ref
	 * ------------------------------------------------------- */
	public void ajouterEquipe(int ref) throws RemoteException {
		equipe.add((Integer) ref);
	}

	@Override
	/** ----------------------------------------------------------
	 * 		public void enleverEquipe(int ref)
	 *  ----------------------------------------------------------
	 * Cette méthode permet d'enlever un personnage dans l'équipe
	 * -----------------------------------------------------------
	 * @param int ref
	 * ------------------------------------------------------- */
	public void enleverEquipe(int ref) throws RemoteException {
		equipe.remove((Integer) ref);
	}

	@Override
	/** ----------------------------------------------------------
	 * 		public void enleverTouteEquipe()
	 *  ----------------------------------------------------------
	 * Cette méthode permet d'enlever toute l'équipe
	 * -------------------------------------------------------- */
	public void enleverTouteEquipe() throws RemoteException {
		equipe.clear();
	}
	
	
	/**
	 * Analyse la potion et autorise son ramassage selon les caracteristiques donnees
	 * @param ve vue de l'element
	 * @param converti personnage sous le controle d'un autre
	 * @throws RemoteException
	 */
	public boolean verifierPotion(VueElement cible, boolean converti) throws RemoteException {
		Element potion;
		int potForce, potCharisme, potVitesse, potHp, potDefense;
		
		try {
			potion = cible.getControleur().getElement();
			potForce = potion.getCaract("force");
			potCharisme = potion.getCaract("charisme");
			potVitesse = potion.getCaract("vitesse");
			potHp = potion.getCaract("hp");
			potDefense = potion.getCaract("defense");
			
			// Toutes les caracteristiques positives : potion positive
			if (potForce > 0 && potCharisme > 0 && potVitesse > 0 && potHp > 0 && potDefense > 0) return true;
			// Potion tue le personnage ou l'immobilise : potion negative
			if ((getHP() + potHp) <= 0 || (getVitesse() + potVitesse) <= 0) return false;
			// Le personnage converti ramasse des potions de charisme en vue d'un coup d'etat
			if (converti && potCharisme > 0 && getCharisme() < 100) return true;
			// Reduction d'une caracteristique principale : potion negative
			if (potCharisme < 0 || potVitesse < 0 || potForce < 0) return false;
		}
		catch (RemoteException e) {
			System.out.println("Erreur lors d'un duel :");
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * Calcule une case de fuite du personnage en fonction de la case ciblee
	 * @param per positition du personnage
	 * @param cible position de la cible
	 * @throws RemoteException
	 */
    public Point caseFuite (Point per, Point cible) {
    	Point dest = new Point();
    	Random r= new Random();
    	
    	if(cible.x > per.x) dest.x = r.nextInt(per.x);
    	else dest.x = per.x+(r.nextInt(99-per.x));
    	
    	if(cible.y > per.y) dest.y = r.nextInt(per.y);
    	else dest.y = per.y+r.nextInt(99-per.y);
    	
    	return dest;
    }
	
	/** --------------------------------------------------------------------
	 * 	public void strategie(VueElement ve, Hashtable<Integer,VueElement> voisins, Integer refRMI) throws RemoteException
	 *  --------------------------------------------------------------------
	 *  Classe à modifier pour la stratégie à adopter par votre personnage.
	 *  --------------------------------------------------------------------
	 * Met en place la strategie. On ne peut utiliser que les methodes de la 
	 * classe Actions.
	 * @param ve vue de l'element
	 * @param voisins element voisins de cet element
	 * @param refRMI reference attribuee a cet element par le serveur
	 * @throws RemoteException
	 */
	public void strategie(VueElement ve, Hashtable<Integer,VueElement> voisins, Integer refRMI) throws RemoteException {
        Actions actions = new Actions(ve, voisins); //je recupere les voisins (distance < 10)
        Deplacements deplacements = new Deplacements(ve,voisins);
        boolean converti, actionEffectuee = false;
        VueElement cible;
        
        do {
        	actionEffectuee = true;
        	
	        if (0 == voisins.size()) { // je n'ai pas de voisins, j'erre
	        	parler("J'erre...", ve);
	        	deplacements.seDirigerVers(0); //errer
	            
	        } else {
				converti = !getEquipe().isEmpty() && getLeader() != -1;
				
				// le leader de l'equipe est la cible prioritaire pour le gain
				cible = voisins.get(getLeader());
				if (cible == null) cible = Calculs.chercherElementProche(ve, voisins);
				
				int distPlusProche = Calculs.distanceChebyshev(ve.getPoint(), cible.getPoint());
				
				int refPlusProche = cible.getRef();
				Element elemPlusProche = cible.getControleur().getElement();
				
				// dans la meme equipe ?
				boolean memeEquipe = false;
				boolean favorable = false;
				
				if (elemPlusProche instanceof Personnage) {
					memeEquipe = (leader != -1 && leader == ((Personnage) elemPlusProche).getLeader()) || // meme leader
							leader == refPlusProche || // cible est le leader de this
							((Personnage) elemPlusProche).getLeader() == refRMI; // this est le leader de cible
					favorable=!(cible.getControleur().getElement().getCaract("charisme")>getForce() && 
								cible.getControleur().getElement().getCaract("force")>getCharisme());
				}
				
				if (distPlusProche <= 2) { // si suffisamment proches
					if (elemPlusProche instanceof Potion) { // potion
						if (verifierPotion(cible, converti)) {
							// je ramasse la potion positive
							parler("Je ramasse une potion", ve);
							actions.ramasser(refRMI, refPlusProche, ve.getControleur().getArene());
						} else {
							// j'ignore la position negative
							voisins.remove(refPlusProche);
							actionEffectuee = false;
						}
						
					} else { // personnage
						if (!memeEquipe) { // duel si pas dans la meme equipe
							// duel
							parler("Je fais un duel avec " + refPlusProche, ve);
							actions.interaction(refRMI, refPlusProche, ve.getControleur().getArene());
						} else if (refPlusProche == getLeader() && 
								getCharisme() > cible.getControleur().getElement().getCaract("charisme")) {
							// coup d'etat
							parler("Je fais un coup d'etat sur mon leader " + refPlusProche, ve);
							actions.interaction(refRMI, refPlusProche, ve.getControleur().getArene());
						} else {
				        	parler("J'erre...", ve);
				        	deplacements.seDirigerVers(0); // errer
						}
					}
				} else { // si voisins, mais plus eloignes
					if (elemPlusProche instanceof Potion) { // potion
						if (verifierPotion(cible, converti)) {
							// je me dirige vers la potion positive
							parler("Je vais vers la potion" + refPlusProche, ve);
							deplacements.seDirigerVers(refPlusProche);
						} else {
							// j'ignore la position negative
							voisins.remove(refPlusProche);
							actionEffectuee = false;
						}
					} else {
						if (!memeEquipe) { // ennemi
							if (favorable) {
								// je vais vers le plus proche
					        	parler("Je vais vers mon voisin " + refPlusProche, ve);
					        	deplacements.seDirigerVers(refPlusProche);
							} else {
								parler("Je m'enfuis " + refPlusProche, ve);
						    	deplacements.seDirigerVers(caseFuite(ve.getPoint(), cible.getPoint()));
							}
						} else if (refPlusProche == getLeader() && 
								getCharisme() > cible.getControleur().getElement().getCaract("charisme")) { 
							// je me dirige vers le leader pour tentative de coup d'etat
							parler("Je vais vers mon leader " + refPlusProche, ve);
				        	deplacements.seDirigerVers(refPlusProche);
						} else {
				        	parler("J'erre...", ve);
				        	deplacements.seDirigerVers(0); // errer
						}
					}
				}
	        }
        } while (!actionEffectuee);
	}
}