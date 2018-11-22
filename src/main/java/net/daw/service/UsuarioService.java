/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.daw.bean.ReplyBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.UsuarioDao;
import net.daw.factory.ConnectionFactory;

/**
 *
 * @author jesus
 */
public class UsuarioService {
	HttpServletRequest oRequest;
	String ob = null;

	public UsuarioService(HttpServletRequest oRequest) {
		super();
		this.oRequest = oRequest;
		ob = oRequest.getParameter("ob");
	}

	// Check para confirmar si estamos logueados
	protected Boolean checkPermission(String strMethodName) {
		UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
		if (oUsuarioBean != null) {
			return true;
		} else {
			return false;
		}
	}

	public ReplyBean get() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		// if (this.checkPermission("get")) {
		try {
			Integer id = Integer.parseInt(oRequest.getParameter("id"));
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
			UsuarioBean oUsuarioBean = oUsuarioDao.get(id);
			Gson oGson = new Gson();
			oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		// } else {
		// oReplyBean = new ReplyBean(401, "Unauthorized");

		// }
		return oReplyBean;

	}

	public ReplyBean remove() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		// if (this.checkPermission("get")) {
		try {
			Integer id = Integer.parseInt(oRequest.getParameter("id"));
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
			int iRes = oUsuarioDao.remove(id);
			oReplyBean = new ReplyBean(200, Integer.toString(iRes));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: remove method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		// } else {
		// oReplyBean = new ReplyBean(401, "Unauthorized");
		// }
		return oReplyBean;

	}

	public ReplyBean getcount() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		// if (this.checkPermission("get")) {
		try {
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
			int registros = oUsuarioDao.getcount();
			Gson oGson = new Gson();
			oReplyBean = new ReplyBean(200, oGson.toJson(registros));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		// } else {
		// oReplyBean = new ReplyBean(401, "Unauthorized");
		// }
		return oReplyBean;

	}

	public ReplyBean create() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		// if (this.checkPermission("get")) {
		try {
			String strJsonFromClient = oRequest.getParameter("json");
			Gson oGson = new Gson();
			UsuarioBean oUsuarioBean = new UsuarioBean();
			oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
			oUsuarioBean = oUsuarioDao.create(oUsuarioBean);
			oReplyBean = new ReplyBean(200, oGson.toJson("Usuario creado correctamente"));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		// } else {
		// oReplyBean = new ReplyBean(401, "Unauthorized");
		// }
		return oReplyBean;
	}

	public ReplyBean crearDatos() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		String[] dni = { "20934843S", "49085524M", "73846284E", "48468742Q", "23148745H", "19872987V", "12878918X",
				"98464987S", "98432112L", "89489732F", "46841354B" };// 11
		String[] nombre = { "Arturo", "Antonio", "Jose", "Benjamin", "Parrales", "Pepa", "Xavi", "David", "Berengario",
				"Andrés", "Maria" };// 11
		String[] ape1 = { "Sendra", "Garcia", "De la Reina", "Otras Hierbas", "Murillo", "Franklin", "De la Vega",
				"Lorca", "Pajares", "Abascal", "Gandhi" };// 11
		String[] ape2 = { "Bisquert", "Garcia", "Marquez", "Llamas", "Monzonis", "Perez", "Reverte", "Santacreu",
				"Ortega", "Ahuir", "Naranjo" };// 11
		String[] login = { "XXX", "SeñorX", "El Pipa", "Judas", "CJ", "Thompson", "Antoniet", "Bacalo", "Palleter",
				"Botella", "Julay" };// 11
		String[] pass = { "abc", "def", "ghi", "jkl", "mnn", "opq", "rst", "uvw", "xyz", "123", "456" };// 11
		Random randDni = new Random();
		Random randNombre = new Random();
		Random randApe1 = new Random();
		Random randApe2 = new Random();
		Random randLogin = new Random();
		Random randPass = new Random();
		Random randId_tipoUsuario = new Random();
		int[] id_tipoUsuario = { 618, 620, 633, 625, 640 };
		int numUsuarioCreados = 100;

		try {
			String strJsonFromClient = oRequest.getParameter("json");
			Gson oGson = new Gson();
			UsuarioBean oUsuarioBean = new UsuarioBean();
			oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);

			for (int i = 0; i <= numUsuarioCreados; i++) {
				oUsuarioBean = new UsuarioBean();
				int randomDni = randDni.nextInt(11);
				int randomNombre = randNombre.nextInt(11);
				int randomApe1 = randApe1.nextInt(11);
				int randomApe2 = randApe2.nextInt(11);
				int randomLogin = randLogin.nextInt(11);
				int randomPass = randPass.nextInt(11);
				int randomId_tipoUsuario = randId_tipoUsuario.nextInt(5);
				oUsuarioBean.setDni(dni[randomDni]);
				oUsuarioBean.setNombre(nombre[randomNombre]);
				oUsuarioBean.setApe1(ape1[randomApe1]);
				oUsuarioBean.setApe2(ape2[randomApe2]);
				oUsuarioBean.setLogin(login[randomLogin]);
				oUsuarioBean.setPass(pass[randomPass]);
				oUsuarioBean.setId_tipoUsuario(id_tipoUsuario[randomId_tipoUsuario]);
				oUsuarioBean = oUsuarioDao.create(oUsuarioBean);
			}
			oReplyBean = new ReplyBean(200, oGson.toJson("Usuarios creados correctamente"));
		} catch (Exception ex) {
			throw new Exception("ERROR: No entra", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		return oReplyBean;
	}

	public ReplyBean update() throws Exception {
		int iRes = 0;
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		// if (this.checkPermission("get")) {
		try {
			String strJsonFromClient = oRequest.getParameter("json");
			Gson oGson = new Gson();
			UsuarioBean oUsuarioBean = new UsuarioBean();
			oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
			iRes = oUsuarioDao.update(oUsuarioBean);
			oReplyBean = new ReplyBean(200, Integer.toString(iRes));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		// } else {
		// oReplyBean = new ReplyBean(401, "Unauthorized");
		// }
		return oReplyBean;
	}

	public ReplyBean getpage() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		// if (this.checkPermission("get")) {
		try {
			Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
			Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
			ArrayList<UsuarioBean> alUsuarioBean = oUsuarioDao.getpage(iRpp, iPage);
			Gson oGson = new Gson();
			oReplyBean = new ReplyBean(200, oGson.toJson(alUsuarioBean));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		// } else {
		// oReplyBean = new ReplyBean(401, "Unauthorized");
		// }
		return oReplyBean;
	}

	// ************LOGIN***********
	public ReplyBean login() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		String strLogin = oRequest.getParameter("user");
		String strPassword = oRequest.getParameter("pass");

		oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
		oConnection = oConnectionPool.newConnection();
		UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);

		UsuarioBean oUsuarioBean = oUsuarioDao.login(strLogin, strPassword);
		if (oUsuarioBean.getId() > 0) {
			oRequest.getSession().setAttribute("user", oUsuarioBean);
			Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
			oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
		} else {
			// throw new Exception("ERROR Bad Authentication: Service level: get page: " +
			// ob + " object");
			oReplyBean = new ReplyBean(401, "Bad Authentication");
		}
		return oReplyBean;
	}

	public ReplyBean logout() throws Exception {
		oRequest.getSession().invalidate();
		return new ReplyBean(200, "OK");
	}

	public ReplyBean check() throws Exception {
		ReplyBean oReplyBean;
		UsuarioBean oUsuarioBean;
		oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
		if (oUsuarioBean != null) {
			Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
			oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
		} else {
			oReplyBean = new ReplyBean(401, "No active session");
		}
		return oReplyBean;
	}
}
