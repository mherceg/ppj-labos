
public interface Patuljak {
	/**
	 * Stanje u koje se prelazi
	 * @return
	 */
	String getStanje();
	/**
	 * Kolko se mora vratit na input stringu
	 * @return
	 */
	Integer getVraca();
	/**
	 * Uniformni znak koji odgovara stringu kojega je primio.
	 * Ono kaj je našao 
	 * @return
	 */
	String getNasao();
	/**
	 * Jesam li prešao u novi red
	 * @return
	 */
	boolean getNoviRed();
}
