public class NimState extends State{
	public int coins = 13;
	
	public NimState(){
		
	}
	
	public NimState(NimState state){
		this.coins = state.coins;
	}
	public String toString(){
		return "" + this.coins;
	}
}