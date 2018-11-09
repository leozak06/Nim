import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Nim extends Game{
	int WinningScore = 10;
	int LosingScore = -10;
	int NeutralScore = 0;
	
	public Nim(){
		currentState = new NimState();
	}
	public boolean isWinState(State state){
		NimState stateNim = (NimState) state;
		int curr_player = stateNim.coins;
		if(curr_player == 1){
			return true;
		}
		return false;
		
	}
	public boolean isStuckState(State state){
		return false;
	}
	public Set<State> getSuccessors(State state){
		if(isWinState(state)||isStuckState(state))
			return null;
		Set<State> successors = new HashSet<State>();
		NimState state_nim = (NimState) state;
		NimState succ_state = new NimState(state_nim);
		
		int coins=0;
		while(coins <= 3){
			succ_state.coins -= i;
			succ_state.player = (state.player == 0? 1: 0);
			
			successors.add(succ_state);
			coins++;
		}
		return successors;
	}
	public double eval(State state){
		if(isWinState(state)){
			//player who made the last move
			int prev_player = (state.player ==0?1:0);
			if(prev_player==0)//computer wins
				return WinningScore;
			else //human wins
				return LosingScore;
		}
		return NeutralScore;
	}
	public static void main(String[] args) throws Exception{
		Game game = new Nim();
		Search search = new Search(game);
		int depth = 13;
		
		//need to get human's move
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
			NimState nextState = null;
			switch(game.currentState.player){
				case 1: //Human
					//get human move
					System.out.println("Enter your *valid* move> ");
					int coinsOut = Integer.parseInt(in.readLine());
					
					nextState = new NimState((NimState)game.currentState);
					nextState.player = 1;
					nextState.coins -= coinsOut;
					System.out.println("Human: \n" + nextState);
					break;
				case 0: //computer
					nextState = (NimState)search.bestSuccessorState(depth);
					nextState.player = 0;
					System.out.println("Computer: \n" + nextState);
					break;
					
			}
			game.currentState = nextState;
			//change player
			game.currentState.player = (game.currentState.player==0 ? 1:0);
			
			//who wins?
			if(game.isWinState(game.currentState)){
				if(game.currentState.player == 1) //last move was by the computer
					System.out.println("Computer wins!");
				else	
					System.out.println("You Win!");
				break;
			}
			if(game.isStuckState(game.currentState)){
				System.out.println("Cat's game!");
				break;
			}
			
		}
	}
	
}