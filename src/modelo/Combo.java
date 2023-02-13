package modelo;

import java.util.ArrayList;

public class Combo extends Producto {

	private double descuento;
	private String nombreCombo;
	private ArrayList<Producto> items;
	
	public Combo(String nombre, double descuento) {
		this.descuento = descuento;
		this.nombreCombo = nombre;
		this.items = new ArrayList<Producto>();
	}
	
	public void agregarItemACombo(Producto itemCombo) {
		items.add(itemCombo);
	}
	
	public double getPrecio() {
		double p = 0;
		for (Producto producto: items) {
			p += producto.getPrecio()*(1 - descuento);
		}
		return p;
	}
	
	public String generarTextoFactura() {
		String mssg = "* " + nombreCombo + " -> $" + getPrecio() + "\n";
		for (Producto producto: items) {
			mssg += "  -" + producto.getNombre() + "\n";
		}
		return mssg;
	}

	@Override
	public String getNombre() {
		return this.nombreCombo;
	}
	
}