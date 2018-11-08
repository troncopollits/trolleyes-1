package net.daw.service;

import java.util.ArrayList;

import net.daw.bean.ProductoBean;

public class RellenarService {
	public ArrayList<ProductoBean> RellenarProducto(int numero) {
		String[] codigo = { "56TT", "GTE4", "K8J6", "JKK1", "TTP9" };
		String[] desc = { "Hacha", "Machete", "Cuchillo", "Navaja", "Destornillador", "Martillo" };
		String[] desc2 = { "de plástico", "de oro", "de plata", "de madera", "de aluminio", "de hierro" };
		String foto = "Foto";
		int[] id_tipoProducto = { 1, 2, 3, 4, 5, 6 };
		int[] existencias = { 1, 2, 3, 4, 5, 6 };

		// Para tener mayor control de el maximo de objetos que tengo en los arrays
		int maxDatos = 6;

		ArrayList<ProductoBean> resultado = new ArrayList<>();
		ProductoBean resultadoProducto;
		for (int i = 0; i < numero; i++) {
			resultadoProducto = new ProductoBean();
			resultadoProducto.setCodigo(codigo[randomMath(maxDatos)]);
			resultadoProducto.setDesc(desc[randomMath(maxDatos)] + " " + desc2[randomMath(maxDatos)]);
			resultadoProducto.setPrecio((float) (((int) (Math.random() * 10000)) * 0.01));
			resultadoProducto.setFoto(foto);
			resultadoProducto.setId_tipoProducto(id_tipoProducto[randomMath(maxDatos)]);
			resultadoProducto.setExistencias(existencias[randomMath(maxDatos)]);
			resultado.add(resultadoProducto);
		}
		return resultado;
	}

	private int randomMath(int number) {
		return (int) (Math.random() * number);
	}
}
