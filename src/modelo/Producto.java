package modelo;

public abstract class Producto {
	
	public abstract double getPrecio();
	
	public abstract String getNombre();
	
	public abstract String generarTextoFactura();
}