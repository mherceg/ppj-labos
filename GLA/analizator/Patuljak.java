
public class Patuljak{
	
	private String stanje;
	private Integer vraca;
	private String nasao;
	private boolean NoviRed;
	
	
	public Patuljak(String stanje, Integer vraca, String nasao, boolean noviRed) {
		super();
		this.stanje = stanje;
		this.vraca = vraca;
		this.nasao = nasao;
		NoviRed = noviRed;
	}
	
	public void setStanje(String stanje) {
		this.stanje = stanje;
	}

	public void setVraca(Integer vraca) {
		this.vraca = vraca;
	}

	public void setNasao(String nasao) {
		this.nasao = nasao;
	}

	public void setNoviRed(boolean noviRed) {
		NoviRed = noviRed;
	}

	public String getStanje() {
		return this.stanje;
	}

	public Integer getVraca() {
		return this.vraca;
	}

	public String getNasao() {
		return this.nasao;
	}

	public boolean getNoviRed() {
		return this.NoviRed;
	}

}
