package net.daw.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import net.daw.bean.ReplyBean;
import net.daw.bean.TipousuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.TipousuarioDao;
import net.daw.factory.ConnectionFactory;

public class TipousuarioService {

	HttpServletRequest oRequest;
	String ob = null;

	public TipousuarioService(HttpServletRequest oRequest) {
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
			TipousuarioDao oTipousuarioDao = new TipousuarioDao(oConnection, ob);
			TipousuarioBean oTipousuarioBean = oTipousuarioDao.get(id, 1);
			Gson oGson = new Gson();
			oReplyBean = new ReplyBean(200, oGson.toJson(oTipousuarioBean));
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
			TipousuarioDao oTipousuarioDao = new TipousuarioDao(oConnection, ob);
			int iRes = oTipousuarioDao.remove(id);
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
			TipousuarioDao oTipousuarioDao = new TipousuarioDao(oConnection, ob);
			int registros = oTipousuarioDao.getcount();
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
		ReplyBean oReplyBean;//Es un puto mensaje para averiguar si la operacion ha salido bien o mal en la base de datos.
		ConnectionInterface oConnectionPool = null;//Simple conexion con la bbdd.
		Connection oConnection;//Simple conexion con la bbdd.
		ArrayList<TipousuarioBean> listaTipousuarioBean = new ArrayList<TipousuarioBean>();
		try {
			listaTipousuarioBean = crearDatos();
			//String strJsonFromClient = oRequest.getParameter("json");//Esta es la puta operacion('op'), la recogemos.
			Gson oGson = new Gson();
			TipousuarioBean oTipousuarioBean = new TipousuarioBean();
			//oTipousuarioBean = oGson.fromJson(strJsonFromClient, TipousuarioBean.class);//NO LO ENTIENDO BIEN
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			TipousuarioDao oTipousuarioDao = new TipousuarioDao(oConnection, ob);//la conexion siempre viaja a traves de la petici�n para no saturar el programa, entonces siempre viaja entre clases
			
			for(TipousuarioBean tipousuarios : listaTipousuarioBean) {
				oTipousuarioBean = oTipousuarioDao.create(tipousuarios);
			}
			//oTipousuarioBean = oTipousuarioDao.create(oTipousuarioBean);//El objeto 'ob' es el nombre de la tabla donde se ejecutar� la consulta, que ser� en DAO.
			//oReplyBean = new ReplyBean(200, oGson.toJson(oTipousuarioBean));
			oReplyBean = new ReplyBean(200, oGson.toJson("Tipousuarios creados correctamente"));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}
		return oReplyBean;
	}

	private ArrayList<TipousuarioBean> crearDatos() {
		ArrayList<TipousuarioBean>listaRamdomTipoUsuario = new ArrayList<TipousuarioBean>();
		String[] desc = {"Cliente","Administrador","Becario"};
		Random RandomDesc = new Random();
		int numDeRegistrosCreados = 105;
		TipousuarioBean oTipousuarioBean;
		for(int i = 0; i < numDeRegistrosCreados; i++) {
			oTipousuarioBean = new TipousuarioBean();
			int randDesc = RandomDesc.nextInt(3);
			oTipousuarioBean.setDesc(desc[randDesc]);
			listaRamdomTipoUsuario.add(oTipousuarioBean);
		}
		return listaRamdomTipoUsuario;
	}

	public ReplyBean update() throws Exception {
		int iRes = 0;
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		try {
			String strJsonFromClient = oRequest.getParameter("json");
			Gson oGson = new Gson();
			TipousuarioBean oTipousuarioBean = new TipousuarioBean();
			oTipousuarioBean = oGson.fromJson(strJsonFromClient, TipousuarioBean.class);
			oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
			oConnection = oConnectionPool.newConnection();
			TipousuarioDao oTipousuarioDao = new TipousuarioDao(oConnection, ob);
			iRes = oTipousuarioDao.update(oTipousuarioBean);
			oReplyBean = new ReplyBean(200, Integer.toString(iRes));
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
			TipousuarioDao oTipousuarioDao = new TipousuarioDao(oConnection, ob);
			ArrayList<TipousuarioBean> alTipousuarioBean = oTipousuarioDao.getpage(iRpp, iPage, 1);
			Gson oGson = new Gson();
			oReplyBean = new ReplyBean(200, oGson.toJson(alTipousuarioBean));
		} catch (Exception ex) {
			throw new Exception("ERROR: Service level: getpage method: " + ob + " object", ex);
		} finally {
			oConnectionPool.disposeConnection();
		}

		return oReplyBean;

	}

}
