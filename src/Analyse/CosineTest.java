package Analyse;

import java.util.Iterator;
import java.util.LinkedList;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import Exception.BadLoginException;
import KeystrokeMeasuring.KeyStroke;
import Main.Account;

public class CosineTest {
	
	private static final double cosineSimilarityThreshold = 0.8; 
	private static final double hitrateThreshold = 0.9;

	public static boolean test(KeyStrokeSet testSet, Account account ) throws BadLoginException {
		try{
			LinkedList<KeyStrokeSet> sets = KeyStrokeSet.buildReferenceSet(account);
			//LinkedList<LinkedList<Double>> similarityMatrix = new LinkedList<LinkedList<Double>>();
			Iterator<KeyStrokeSet> setsIterator = sets.iterator();
			//LinkedList<Double> averageSimilarity = new LinkedList<Double>();
			
			int hitrate = 0;
			int nbChars = 0;
			
			//On definit la matrice de similarite avec les similarites cosinus de chaque touche de chaque entree, on calcule aussi les similarites moyennes pour chaque entree
			while(setsIterator.hasNext()){
				
				LinkedList<KeyStroke> temp = setsIterator.next().getSet();
				
				if(temp.size() == testSet.getSet().size()){
					
					Iterator<KeyStroke> tempIterator = temp.iterator();
					Iterator<KeyStroke> testIterator = testSet.getSet().iterator();
					//LinkedList<Double> tempSimilarities = new LinkedList<Double>();
					
					double somme = 0.0;
				
					while(tempIterator.hasNext()){
						
						KeyStroke test = testIterator.next();
						KeyStroke ref = tempIterator.next();
						somme += test.getCosineSimilarity(ref); 
						
					}
					
					//similarityMatrix.add(tempSimilarities);
					if(somme / temp.size() > cosineSimilarityThreshold)
						hitrate++;
					nbChars++;					
				
				} else return false; //si pas la bonne taille, le mot de passe est forcemment faux
			}
			
			if(hitrate/nbChars > hitrateThreshold)
				return true;
			else return false;
		
		}catch (EncryptionOperationNotPossibleException e){
			throw new BadLoginException();
		}
		
	}
				
}

