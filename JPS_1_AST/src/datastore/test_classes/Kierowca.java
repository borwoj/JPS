package datastore.test_classes;

public class Kierowca {
	public String imie;
	public String nazwisko;
	public int wiek;
	public boolean prawo_jazdy_D;
	public Samochod samochod;
	
	public Kierowca(String imie, String nazwisko, int wiek, boolean prawo_jazdy_D, Samochod samochod){
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.wiek = wiek;
		this.prawo_jazdy_D = prawo_jazdy_D;
		this.samochod = samochod;
	}

}
