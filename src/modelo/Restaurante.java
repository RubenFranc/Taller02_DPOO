package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restaurante {
	
	private int numeroPedidos;
	private Pedido pedidoEnCurso;
	private ArrayList<Producto> menuBase;
	private ArrayList<Ingrediente> ingredientes;
	private Map<String, Ingrediente> mapIngredientes;
	private Map<String, Producto> mapMenuBase;
	private Map<Integer, String> mapPedidos;
	
	public Restaurante(int numeroPedidos) {
		this.numeroPedidos = numeroPedidos;
		this.menuBase = new ArrayList<>();
		this.ingredientes = new ArrayList<>();
		this.mapIngredientes = new HashMap<>();
		this.mapMenuBase = new HashMap<>();
		this.mapPedidos = new HashMap<>();
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		Pedido pedido = new Pedido(nombreCliente, direccionCliente, numeroPedidos);
		this.pedidoEnCurso = pedido;
		this.numeroPedidos +=1;
	}
	
	public Pedido getPedidoEnCurso() {
		return pedidoEnCurso;
	}
	
	public void cerrarYGuardarPedido() throws IOException {
		mapPedidos.put(pedidoEnCurso.getIdPedido(), pedidoEnCurso.generarTextoFactura());
		String ruta = "pedidos/"+pedidoEnCurso.getIdPedido()+".txt";
		File file = new File(ruta);
		pedidoEnCurso.guardarFactura(file);
	}
	
	public ArrayList<Producto> getMenuBase(){
		return this.menuBase;
	}
	
	public ArrayList<Ingrediente> getIngredientes() {
		return this.ingredientes;
	}
	
	public Map<Integer, String> getPedidos(){
		return this.mapPedidos;
	}
	
	public Producto getProducto(String nombre) {
		return mapMenuBase.get(nombre);
	}
	
	public Ingrediente getIngrediente(String nombre) {
		return mapIngredientes.get(nombre);
	}
	
	private void cargarIngredientes(String archivoIngredientes) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
		String linea = br.readLine();
		while (linea != null) {
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String cA = partes[1].replace("\n", "");
			int costoAdicional = Integer.parseInt(cA);
			Ingrediente ingrediente = new Ingrediente(nombre, costoAdicional);
			mapIngredientes.put(nombre, ingrediente);
			ingredientes.add(ingrediente);
			linea = br.readLine();
		}
		br.close();
	}
	
	private void cargarMenu(String archivoMenu) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
		String linea = br.readLine();
		while (linea != null) {
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String p = partes[1].replace("\n", "");
			int precio = Integer.parseInt(p);
			ProductoMenu producto = new ProductoMenu(nombre, precio);
			mapMenuBase.put(nombre, producto);
			menuBase.add(producto);
			linea = br.readLine();
		}
		br.close();
	}
	
	private void cargarCombos(String archivoCombos) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
		String linea = br.readLine();
		while (linea != null) {
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String dcto = partes[1].replace("%", "");
			double descuento = Integer.parseInt(dcto)*0.01;
			Producto p1 = new ProductoMenu(partes[2], mapMenuBase.get(partes[2]).getPrecio());
			Producto p2 = new ProductoMenu(partes[3], mapMenuBase.get(partes[3]).getPrecio());
			Producto p3 = new ProductoMenu(partes[4], mapMenuBase.get(partes[4]).getPrecio());
			Combo combo = new Combo(nombre, descuento);
			combo.agregarItemACombo(p1);
			combo.agregarItemACombo(p2);
			combo.agregarItemACombo(p3);
			mapMenuBase.put(nombre, combo);
			menuBase.add(combo);
			linea = br.readLine();
		}
		br.close();
	}
	
	public void cargarInformacionRestaurante(String archivoIngredientes, String archivoMenu, String archivoCombos) throws IOException {
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
		cargarIngredientes(archivoIngredientes);
	}
	
}