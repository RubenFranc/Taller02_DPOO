package consola;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import modelo.Restaurante;
import modelo.Ingrediente;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoAjustado;

public class Aplicacion {
	
	public void ejecutarAplicacion() throws IOException {
		System.out.println("\nBienvenido al restaurante de hamburguesas DPOO");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		Restaurante restaurante = new Restaurante();
		restaurante.cargarInformacionRestaurante("data/ingredientes.txt", "data/menu.txt", "data/combos.txt");
		System.out.println("MENÚ:\n------------------------------");
		ArrayList<Producto> menuBase = restaurante.getMenuBase();
		
		for (Producto producto: menuBase) {
			System.out.println(producto.generarTextoFactura());
		}
		
		boolean continuar = true;
		boolean pedidoEnCurso = false;
		double nItems = 0;
		Pedido pedido = null;
		while (continuar){
			try{
				mostrarMenu(pedidoEnCurso);
				int opcion_seleccionada = Integer.parseInt(input("\nPor favor seleccione una opción"));
				if (pedidoEnCurso == false) {
					if (opcion_seleccionada == 1) {
						pedidoEnCurso = true;
						String nombreCliente = input("\nPor favor ingrese su nombre");
						String direccionCliente = input("\nPor favor ingrese su dirección");
						restaurante.iniciarPedido(nombreCliente, direccionCliente);
						pedido = restaurante.getPedidoEnCurso();
					}
					else {
						System.out.println("Saliendo de la aplicación ...");
						continuar = false;
					}
				}
				else {
					if (opcion_seleccionada == 1) {
						for (Producto producto: menuBase) {
							System.out.println(producto.generarTextoFactura());
						}
						String nombre = input("\nPor favor ingrese el nombre del producto que desea agregar al pedido");
						Producto producto = restaurante.getProducto(nombre);
						if (producto != null) {
							System.out.println("\n¿Desea agregar o quitar algún ingrediente?");
							System.out.println("1. Agregar ingrediente");
							System.out.println("2. Quitar ingrediente");
							System.out.println("3. No deseo hacer ningún cambio");
							
							String ajustar = input("\nSeleccione una opción");
							if (ajustar.equals("1") || ajustar.equals("2")){
								ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();
								if (ajustar.equals("1")) {
									for (Ingrediente ingrediente: ingredientes) {
									System.out.println("\n* " + ingrediente.getNombre() + " -> $"+ingrediente.getCostoAdicional());
									}
									String nombreIngrediente = input("\nPor favor ingrese el nombre del ingrediente del que desea la adición");
									Ingrediente ingrediente = restaurante.getIngrediente(nombreIngrediente);
									ProductoAjustado productoAjustado = new ProductoAjustado(producto);
									productoAjustado.agregarIngrediente(ingrediente);
									pedido.agregarProducto(productoAjustado);
								}
								else {
									for (Ingrediente ingrediente: ingredientes) {
										System.out.println("\n* " + ingrediente.getNombre());
										}
									String nombreIngrediente = input("\nPor favor ingrese el nombre del ingrediente que desea quitar");
									Ingrediente ingrediente = restaurante.getIngrediente(nombreIngrediente);
									ProductoAjustado productoAjustado = new ProductoAjustado(producto);
									productoAjustado.eliminarIngrediente(ingrediente);
									pedido.agregarProducto(productoAjustado);
								}
								
							}
							else {
								pedido.agregarProducto(producto);
							}
							nItems += 1;
						}
						else {
							System.out.println("Seleccione un producto válido.");
						}
					}
					else {
						if (nItems > 0 & opcion_seleccionada == 2) {
							restaurante.cerrarYGuardarPedido();
							pedidoEnCurso = false;
						}
						
					}
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}
	
	public static void mostrarMenu(boolean pedidoEnCurso) {
		System.out.println("\nOpciones:");
		if (pedidoEnCurso == false) {
			System.out.println("1. Hacer pedido");
			System.out.println("2. Cerrar aplicación");
		}
		else {
			System.out.println("1. Agregar producto");
			System.out.println("2. Terminar pedido");
		}
		
		
	}
	
	public String input(String mensaje){
		try{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e){
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		Aplicacion aplicacion = new Aplicacion();
		aplicacion.ejecutarAplicacion();
	}

}