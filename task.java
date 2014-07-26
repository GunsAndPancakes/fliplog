

public class Task {
	
	private int amt;
	private String name;
	private int bPrice;
	private int sPrice;
	
	public Task(final int amt, final String name, final double bPrice, final double sPrice){
		this.amt = amt;
		this.name = name;
		this.bPrice = (int) bPrice;
		this.sPrice = (int) sPrice;
	};
	
	public int amt(){
		return amt;
	}
	
	public String name(){
		return name;
	}

	public int bPrice(){
		return (int) bPrice;
	}
	
	public int sPrice(){
		return (int) sPrice;
	}
	
	public String toString(){
		final int Profit = (int) (sPrice - bPrice);
		return String.format("I bought %dx %s for %d gp and sold at %d gp for a profit of %d gp.", amt, name, bPrice, sPrice, Profit);
	}

}
