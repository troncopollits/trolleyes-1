/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import net.daw.bean.LineaBean;
import net.daw.bean.ReplyBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.LineaDao;
import net.daw.factory.ConnectionFactory;

public class LineaService {
	HttpServletRequest oRequest;
	String ob = null;

	public LineaService(HttpServletRequest oRequest) {
		super();
		this.oRequest = oRequest;
		ob = oRequest.getParameter("ob");
	}

	public ReplyBean get() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		try {
			Integer id = Integer.parseInt(oRequest.getParameter("id"));
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			LineaDao oLineaDao = new LineaDao(oConnection, ob);
			LineaBean oLineaBean = oLineaDao.get(id);
			Gson oGson = new Gson();
			oReplyBean = new ReplyBean(200, oGson.toJson(oLineaDao));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}

		return oReplyBean;

	}

	public ReplyBean remove() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		try {
			Integer id = Integer.parseInt(oRequest.getParameter("id"));
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			LineaDao oLineaDao = new LineaDao(oConnection, ob);
			int iRes = oLineaDao.remove(id);
			oReplyBean = new ReplyBean(200, Integer.toString(iRes));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: remove method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		return oReplyBean;

	}

	public ReplyBean getcount() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		try {
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			LineaDao oLineaDao = new LineaDao(oConnection, ob);
			int registros = oLineaDao.getcount();
			Gson oGson = new Gson();
			oReplyBean = new ReplyBean(200, oGson.toJson(registros));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}

		return oReplyBean;

	}

	public ReplyBean create() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		try {
			String strJsonFromClient = oRequest.getParameter("json");
			Gson oGson = new Gson();
			LineaBean oLineaBean = new LineaBean();
			oLineaBean = oGson.fromJson(strJsonFromClient, LineaBean.class);
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			LineaDao oLineaDao = new LineaDao(oConnection, ob);
			oLineaBean = oLineaDao.create(oLineaBean);
			oReplyBean = new ReplyBean(200, oGson.toJson("Linea creada correctamente"));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		return oReplyBean;
	}

	public ReplyBean crearDatos() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		int[] id_producto = { 120, 134, 144, 150, 163 };
		int[] id_factura = { 1, 2, 3, 4, 5 };
		Random randomId_producto = new Random();
		Random randomId_factura = new Random();
		int nuevosRegistros = 100;

		try {
			String strJsonFromClient = oRequest.getParameter("json");
			Gson oGson = new Gson();
			LineaBean oLineaBean = new LineaBean();
			oLineaBean = oGson.fromJson(strJsonFromClient, LineaBean.class);
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			LineaDao oLineaDao = new LineaDao(oConnection, ob);

			for (int i = 0; i < nuevosRegistros; i++) {
				oLineaBean = new LineaBean();
				int randId_producto = randomId_producto.nextInt(5);
				int randId_factura = randomId_factura.nextInt(5);
				int cantidad = ThreadLocalRandom.current().nextInt(0, 3000 + 1);

				oLineaBean.setCantidad(cantidad);
				oLineaBean.setId_factura(id_factura[randId_factura]);
				oLineaBean.setId_producto(id_producto[randId_producto]);
				oLineaBean = oLineaDao.create(oLineaBean);
			}
			oReplyBean = new ReplyBean(200, oGson.toJson("Lineas creadas correctamente"));
		} catch (Exception ex) {
			throw new Exception("ERROR: No entra", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		return oReplyBean;

	}

	public ReplyBean update() throws Exception {
		int iRes = 0;
		ReplyBean oReplyBean = null;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		try {
			String strJsonFromClient = oRequest.getParameter("json");
			Gson oGson = new Gson();
			LineaBean oLineaBean = new LineaBean();
			oLineaBean = oGson.fromJson(strJsonFromClient, LineaBean.class);
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			LineaDao oLineaDao = new LineaDao(oConnection, ob);
			iRes = oLineaDao.update(oLineaBean);
			// oReplyBean.setStatus(200);
			// oReplyBean.setJson(Integer.toString(iRes));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		return oReplyBean;
	}

	public ReplyBean getpage() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		try {
			Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
			Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			LineaDao oLineaDao = new LineaDao(oConnection, ob);
			ArrayList<LineaBean> alLineaBean = oLineaDao.getpage(iRpp, iPage);
			Gson oGson = new Gson();
			oReplyBean = new ReplyBean(200, oGson.toJson(alLineaBean));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: getpage method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}

		return oReplyBean;

	}
}
