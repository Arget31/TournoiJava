

import java.rmi.RemoteException;
import java.util.Random;

import controle.Console;
import element.Element;
import element.Potion;

public class TestPotionAlea {

	public static void main(String[] args) {

		try {
			int port = 5099; // par defaut, 5099
			if (args.length > 0) {
				port = Integer.parseInt(args[0]);
			}
			
			String ipArene = "localhost"; // par défaut, localhost
			if (args.length > 1) { 
				ipArene = args[1];
			}
			
			//TODO GOTO CHECK
			Random r = new Random();
//			Element pot1 = new Potion("Anduril", r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(1), r.nextInt(20) - 10);
//			Element pot2 = new Potion("Anduril", r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(1), r.nextInt(20) - 10);
//			Element pot3 = new Potion("Anduril", r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(1), r.nextInt(20) - 10);
//			Element pot4 = new Potion("Anduril", r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(1), r.nextInt(20) - 10);
//			Element pot5 = new Potion("Anduril", r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(20) - 10, r.nextInt(1), r.nextInt(20) - 10);
			Element pot6 = new Potion("Anduril", 1000, 1000 ,1000, 10000, 10000);
//			Element pot7 = new Potion("Verbal", 0, r.nextInt(10), 0, 0, 0);
//			Element pot8 = new Potion("Strongzor", /*r.nextInt(10)*/0,0, 0, 1, 0);
			
//			new Console(pot1, r.nextInt(100), r.nextInt(100), port, ipArene);
//			new Console(pot2, r.nextInt(100), r.nextInt(100), port, ipArene);
//			new Console(pot3, r.nextInt(100), r.nextInt(100), port, ipArene);
//			new Console(pot4, r.nextInt(100), r.nextInt(100), port, ipArene);
//			new Console(pot5, r.nextInt(100), r.nextInt(100), port, ipArene);
//			new Console(pot6, r.nextInt(100), r.nextInt(100), port, ipArene);
//			new Console(pot7, r.nextInt(100), r.nextInt(100), port, ipArene);
			new Console(pot6, r.nextInt(100), r.nextInt(100), port, ipArene);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}