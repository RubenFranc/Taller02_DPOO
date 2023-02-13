package modelo;

public class ProductoMenu extends Producto{
	
	private String nombre;
	private double precio;

	public ProductoMenu(String nombre, double precioBase) {
		this.nombre = nombre;
		this.precio = precioBase;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public double getPrecio() {
		return precio;
	}

	@Override
	public String generarTextoFactura() {
		return "* " + nombre + " -> $" + precio + "\n";
	} 
	
}