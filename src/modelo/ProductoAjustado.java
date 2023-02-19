package modelo;

public class ProductoAjustado implements Producto{
	
	private Producto productoBase;
	private double precio;
	private String agregados;
	private String eliminados;

	public ProductoAjustado(Producto base) {
		this.productoBase = base;
		this.precio = productoBase.getPrecio();
		this.agregados = "";
		this.eliminados = "";
	}
	
	public void agregarIngrediente(Ingrediente ingrediente) {
		precio += ingrediente.getCostoAdicional();
		agregados += ingrediente.getNombre() + ",";
	}
	
	public void eliminarIngrediente(Ingrediente ingrediente) {
		eliminados += ingrediente.getNombre() + ",";
	}
	
	@Override
	public double getPrecio() {
		return precio;
	}

	@Override
	public String getNombre() {
		return productoBase.getNombre();
	}

	@Override
	public String generarTextoFactura() {
		String mssg = "* " + productoBase.getNombre() + " -> $" + precio;
		if (agregados != "") {
			mssg += "  (Con adición de " + agregados + ")\n";
		}
		if (eliminados != "") {
			mssg += "  (Sin " + eliminados + ")\n";
		}
		return mssg;
	}

}