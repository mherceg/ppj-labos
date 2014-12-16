import java.util.List;



public class inicijalizator extends Node{
	
	public inicijalizator(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * 71
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<izraz_pridruzivanja>")){
			childNula.provjeri();
			if(childNula.getValue()!=null){
				int length =childNula.getValue().length();
				this.characteristics.setBrElem(length+1); // zbog \0
				List<Tip> types=this.getTypes();
				for(int i=0;i<length;++i){
					types.add(new Tip(TipBasic.CHAR));
				}
				this.setType(childNula.getType());
				//this.characteristics.setBrElem(duljina niza znakova + 1);
				
			}
			else{
				this.characteristics.setType(childNula.getType());
			}
		}
		else if(childNula.getName().equals("L_VIT_ZAGRADA")){
			Node childJedan = child.get(1);
			childJedan.provjeri();
			this.characteristics.setBrElem(childJedan.getBrElem());
			this.characteristics.setType(childJedan.getType());
		}	
	}
	
	

}
