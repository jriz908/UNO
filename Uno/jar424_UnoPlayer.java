import java.util.ArrayList;
import java.util.List;

public class jar424_UnoPlayer implements UnoPlayer {

    /**
     * play - This method is called when it's your turn and you need to
     * choose what card to play.
     *
     * The hand parameter tells you what's in your hand. You can call
     * getColor(), getRank(), and getNumber() on each of the cards it
     * contains to see what it is. The color will be the color of the card,
     * or "Color.NONE" if the card is a wild card. The rank will be
     * "Rank.NUMBER" for all numbered cards, and another value (e.g.,
     * "Rank.SKIP," "Rank.REVERSE," etc.) for special cards. The value of
     * a card's "number" only has meaning if it is a number card. 
     * (Otherwise, it will be -1.)
     *
     * The upCard parameter works the same way, and tells you what the 
     * up card (in the middle of the table) is.
     *
     * The calledColor parameter only has meaning if the up card is a wild,
     * and tells you what color the player who played that wild card called.
     *
     * Finally, the state parameter is a GameState object on which you can 
     * invoke methods if you choose to access certain detailed information
     * about the game (like who is currently ahead, what colors each player
     * has recently called, etc.)
     *
     * You must return a value from this method indicating which card you
     * wish to play. If you return a number 0 or greater, that means you
     * want to play the card at that index. If you return -1, that means
     * that you cannot play any of your cards (none of them are legal plays)
     * in which case you will be forced to draw a card (this will happen
     * automatically for you.)
     */
    


    /**
     * callColor - This method will be called when you have just played a
     * wild card, and is your way of specifying which color you want to 
     * change it to.
     *
     * You must return a valid Color value from this method. You must not
     * return the value Color.NONE under any circumstances.
     */
   
 


  public int play(List<Card> hand, Card upCard, Color calledColor, GameState state){
	
	Rank upRank = upCard.getRank();
	Color upColor = upCard.getColor();
	int upNumber = upCard.getNumber();
	
	Card inHand = null;
	Rank handRank = null;
	Color handColor = null;
	int handNumber = -1;
	
	int numBlue = 0;            
	int numRed = 0;
	int numGreen = 0;
	int numYellow = 0;
	
	ArrayList<Card> legal = new ArrayList<Card>();      //declare a new array list to store all of the legal cards in my hand

	for (int i=0; i<hand.size(); i++){					//determine which cards are legal
		 inHand = hand.get(i);
		 handRank = inHand.getRank();
		 handColor = inHand.getColor();
		 handNumber = inHand.getNumber();

		if (handRank.equals(Rank.WILD) || handRank.equals(Rank.WILD_D4))
			legal.add(inHand);

		else if (upRank.equals(Rank.NUMBER)){
			if(handNumber==upNumber || handColor.equals(upColor))
			legal.add(inHand);
			}

		else if (upRank.equals(Rank.WILD) || upRank.equals(Rank.WILD_D4)){
			if(calledColor.equals(handColor))
				legal.add(inHand);
			}

		else {
			if(handRank.equals(upRank) || handColor.equals(upColor))
				legal.add(inHand);
			}											//determined whether card is legal or not
			
		if(handColor.equals(Color.GREEN))				//determine the color and add it to the count for the color
				numGreen++;
			
			if(handColor.equals(Color.YELLOW))
				numYellow++;			
				
			if(handColor.equals(Color.BLUE))
				numBlue++;
				
			if(handColor.equals(Color.RED))
				numRed++;	
		}
		
		int maxColor = Math.max(Math.max(Math.max(numGreen, numYellow), numBlue), numRed); //determine which color is the most prominent in my hand (bestColor)
		Color bestColor = Color.GREEN;
		
			if(numGreen==maxColor)
				bestColor.equals(Color.GREEN);
				
			else if(numYellow==maxColor)
				bestColor.equals(Color.YELLOW);

			else if(numBlue==maxColor)
				bestColor.equals(Color.BLUE);
				
			else if(numRed==maxColor)
				bestColor.equals(Color.RED);	
		
		
	
		Card playCard = null; //this is the card in the array list of legal cards that will be played
		
		
	if(legal.size()==0)	 //if there are no legal cards, draw a card.
		return -1;
		
	if(legal.size()==1){ 		//if there is only one legal card, play it.
		playCard = legal.get(0);
		return hand.indexOf(playCard);    //find the play card in the original list of cards and return that index
		}
		
	if(state.getNumCardsInHandsOfUpcomingPlayers()[0]==1){		//If the next player has 1 card, look for a card that prevents them from winning
		for(int i=0; i<legal.size(); i++){
			playCard = legal.get(i);							//start by looking for wild draw fours
			
			if(playCard.getRank() == Rank.WILD_D4)
				return hand.indexOf(playCard);
		}														//no wild draw fours available
		
		for(int i=0; i<legal.size(); i++){						//next, look for draw twos
			playCard = legal.get(i);
			
			if(playCard.getRank() == Rank.DRAW_TWO)
				return hand.indexOf(playCard);
		}														//no draw twos available
		
		for(int i=0; i<legal.size(); i++){						//next, look for skips
			playCard = legal.get(i);
			
			if(playCard.getRank() == Rank.SKIP)
				return hand.indexOf(playCard);
		}														//no skips available
		
		for(int i=0; i<legal.size(); i++){						//lastly, look for reverses
			playCard = legal.get(i);
			
			if(playCard.getRank() == Rank.REVERSE)
				return hand.indexOf(playCard);
		}														//no special cards found (excluding regular wild)
		
		//now, pick a number card whose color is least likely to match the color of the next player's card
		
		List<Card> pile = state.getPlayedCards(); //find the color that has been played the most already
		
		int pileGreen = 0;
		int pileYellow = 0;
		int pileBlue = 0;
		int pileRed = 0;
		
		for(int i=0; i<pile.size(); i++){
			Card pileCard = pile.get(i);
			Color pileCardColor = pileCard.getColor();
			
			if(pileCardColor.equals(Color.GREEN))
				pileGreen++;
			
			if(pileCardColor.equals(Color.YELLOW))
				pileYellow++;			
				
			if(pileCardColor.equals(Color.BLUE))
				pileBlue++;
				
			if(pileCardColor.equals(Color.RED))
				pileRed++;	
		}	

			int maxPileColor = Math.max(Math.max(Math.max(pileGreen, pileYellow), pileBlue), pileRed);    //find highest color count
			ArrayList<Color> pileColor = new ArrayList<Color>();    //this is an array list of colors that will hold the most prominent colors in the pile.
				
				if (maxPileColor==pileGreen)
					pileColor.add(Color.GREEN);
				if (maxPileColor==pileYellow)
					pileColor.add(Color.YELLOW);
				if (maxPileColor==pileBlue)
					pileColor.add(Color.BLUE);
				if (maxPileColor-pileGreen==pileRed)
					pileColor.add(Color.RED);           
					
			if(pileColor.size()>1){             //if there are multiple colors that are the most prominent in the pile, the color that the next player called last will be removed from the array list of prominent colors. This is because there is a better chance that this color will be the color in the next player's hand.
				if(pileColor.indexOf(state.getMostRecentColorCalledByUpcomingPlayers()[0])!=-1)
					pileColor.remove(state.getMostRecentColorCalledByUpcomingPlayers()[0]);
					}
					
					for(int i=0;i<legal.size();i++){
						playCard = legal.get(i);
						
						if(pileColor.indexOf(playCard.getColor())!=-1);      //this plays the first card in my hand that is the same color as the most prominent color in the pile
							return hand.indexOf(playCard);
								
					}
					
					for(int i=0;i<legal.size();i++){               //if nothing else works, I will just play the first card in my hand that matches my bestColor
						playCard = legal.get(i);
						
						if(playCard.getColor()==bestColor)
							return hand.indexOf(playCard);
					
					}
		
		}          //this is the end of the if statement whose conditional is if the next player has 1 card.
		
		//now that I do not have to worry about the next player winning the round, I can focus on winning my self.
		
		if(state.getNumCardsInHandsOfUpcomingPlayers()[0]<state.getNumCardsInHandsOfUpcomingPlayers()[2]){  //If the player after me has less cards than the player before me, I will try to play a reverse     
			for(int i=0;i<legal.size();i++){
						playCard = legal.get(i);
						
						if(playCard.getRank().equals(Rank.REVERSE))
							return hand.indexOf(playCard);
					
			}
		}

		if(state.getNumCardsInHandsOfUpcomingPlayers()[0]==state.getNumCardsInHandsOfUpcomingPlayers()[2] && state.getTotalScoreOfUpcomingPlayers()[0]>state.getTotalScoreOfUpcomingPlayers()[2]){  //If the player after me and the player before me have the same number of cards, but the player after me has a higher total score, I will look to play a reverse.
			for(int i=0;i<legal.size();i++){
						playCard = legal.get(i);
						
						if(playCard.getRank().equals(Rank.REVERSE))
							return hand.indexOf(playCard);
					
			}
		}	

		if(state.getNumCardsInHandsOfUpcomingPlayers()[0]<state.getNumCardsInHandsOfUpcomingPlayers()[1]){  //if the player after me has less cards than the player after him, I will try to play a draw two. If no draw two is available, I will try to play a skip.
			for(int i=0;i<legal.size();i++){
						playCard = legal.get(i);
						
						if((playCard.getRank()).equals(Rank.DRAW_TWO))
							return hand.indexOf(playCard);
					
			}
			
			for(int i=0;i<legal.size();i++){
						playCard = legal.get(i);
						
						if(playCard.getRank().equals(Rank.SKIP))
							return hand.indexOf(playCard);
			}
		}
			
		if(state.getNumCardsInHandsOfUpcomingPlayers()[0]==state.getNumCardsInHandsOfUpcomingPlayers()[1] && state.getTotalScoreOfUpcomingPlayers()[0]>state.getTotalScoreOfUpcomingPlayers()[1]){     //if the player after me and the player after him have the same number of cards, but the player after me has a higher total score than the player after him, I will try to play a draw two. If no draw two is a available, I will try to play a skip.
			for(int i=0;i<legal.size();i++){
						playCard = legal.get(i);
						
						if((playCard.getRank()).equals(Rank.DRAW_TWO))
							return hand.indexOf(playCard);
					
			}	
			
			for(int i=0;i<legal.size();i++){
						playCard = legal.get(i);
						
						if(playCard.getRank().equals(Rank.SKIP))
							return hand.indexOf(playCard);
					
			}
			
		}	
		
			for(int i=0;i<legal.size();i++){                         //if no reverses, draw two's, or skips are necessary or available, I will simply play a number card whose color matches my best color.
						playCard = legal.get(i);
						
						if(playCard.getColor().equals(bestColor) && playCard.getNumber()!=-1)
							return hand.indexOf(playCard);
							}
						
			for(int i=0;i<legal.size();i++){						//if there are no numbered cards whose color matches my best color, then I will play a special card whose color matches my best color.
						playCard = legal.get(i);
						
						if(playCard.getColor().equals(bestColor))
							return hand.indexOf(playCard);
							}
							
					playCard=legal.get(0);							//Lastly, if no special cards are necessary or available, and there are no legal cards in my hand that match my best color, I will simply play the first legal card in my hand.
						return hand.indexOf(playCard);
				
				
		}

   public Color callColor(List<Card> hand){          //for the callColor method, I will just call the most prominent color in my hand.
		int numBlue = 0;
		int numRed = 0;
		int numGreen = 0;
		int numYellow = 0;
		Card inHand = null;
		Color handColor = null;
   
			for (int i=0; i<hand.size(); i++){
			inHand = hand.get(i);
			handColor = inHand.getColor();
			
			if(handColor.equals(Color.GREEN))
				numGreen++;
			
			if(handColor.equals(Color.YELLOW))
				numYellow++;			
				
			if(handColor.equals(Color.BLUE))
				numBlue++;
				
			if(handColor.equals(Color.RED))
				numRed++;
			
			}
			
			int max = Math.max(Math.max(Math.max(numGreen, numYellow), numBlue), numRed);
			
				if(max==numGreen)
					return Color.GREEN;
					
				else if(max==numBlue)
					return Color.BLUE;
					
				else if(max==numRed)
					return Color.RED;

				else 
					return Color.YELLOW;

		}

			

}		