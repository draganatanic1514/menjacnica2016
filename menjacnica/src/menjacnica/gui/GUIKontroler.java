package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.interfejs.MenjacnicaInterface;

public class GUIKontroler {

	private static MenjacnicaGUI glavniProzor;
	private static MenjacnicaInterface sistem;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistem = new Menjacnica();
					glavniProzor = new MenjacnicaGUI();
					glavniProzor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(null);
		prozor.setVisible(true);
	}
	
	public static void prikaziObrisiKursGUI(Valuta valuta) {
			ObrisiKursGUI prozor = new ObrisiKursGUI(valuta);
			prozor.setLocationRelativeTo(null);
			prozor.setVisible(true);
	}
	
	public static void prikaziIzvrsiZamenu(Valuta valuta) {
		IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(valuta);
		prozor.setLocationRelativeTo(null);
		prozor.setVisible(true);
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(glavniProzor,
				"Autori: Bojan Tomic i Dragana Tanic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(glavniProzor,
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(glavniProzor);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(glavniProzor);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				glavniProzor.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, String kupovniKurs,
			String srednjiKurs, String prodajniKurs) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(Double.parseDouble(prodajniKurs));
			valuta.setKupovni(Double.parseDouble(kupovniKurs));
			valuta.setSrednji(Double.parseDouble(srednjiKurs));
			
			// Dodavanje valute u kursnu listu
			sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			glavniProzor.prikaziSveValute();
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			sistem.obrisiValutu(valuta);
			
			glavniProzor.prikaziSveValute();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static double izvrsiZamenu(Valuta valuta, boolean prodaja, String iznos) {
		return sistem.izvrsiTransakciju(valuta, prodaja, Double.parseDouble(iznos));
	}
	
	public static List<Valuta> vratiKursnuListu() {
		return sistem.vratiKursnuListu();
	}
}
