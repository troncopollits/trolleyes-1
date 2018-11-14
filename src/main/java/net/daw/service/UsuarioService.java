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
		if (this.checkPermission("get")) {
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
		} else {
			oReplyBean = new ReplyBean(401, "Unauthorized");

		}
		return oReplyBean;

	}

	public ReplyBean remove() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		if (this.checkPermission("get")) {
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
		} else {
			oReplyBean = new ReplyBean(401, "Unauthorized");
		}
		return oReplyBean;

	}

	public ReplyBean getcount() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		if (this.checkPermission("get")) {
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
		} else {
			oReplyBean = new ReplyBean(401, "Unauthorized");
		}
		return oReplyBean;

	}

	public ReplyBean create() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		if (this.checkPermission("get")) {
			try {
				String strJsonFromClient = oRequest.getParameter("json");
				Gson oGson = new Gson();
				UsuarioBean oUsuarioBean = new UsuarioBean();
				oUsuarioBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
				oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
				oConnection = oConnectionPool.newConnection();
				UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
				oUsuarioBean = oUsuarioDao.create(oUsuarioBean);
				oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
			} catch (Exception ex) {
				throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
			} finally {
				oConnectionPool.disposeConnection();
			}
		} else {
			oReplyBean = new ReplyBean(401, "Unauthorized");
		}
		return oReplyBean;
	}

	public ReplyBean update() throws Exception {
		int iRes = 0;
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		if (this.checkPermission("get")) {
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
		} else {
			oReplyBean = new ReplyBean(401, "Unauthorized");
		}
		return oReplyBean;
	}

	public ReplyBean getpage() throws Exception {
		ReplyBean oReplyBean;
		ConnectionInterface oConnectionPool = null;
		Connection oConnection;
		if (this.checkPermission("get")) {
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
		} else {
			oReplyBean = new ReplyBean(401, "Unauthorized");
		}
		return oReplyBean;
	}
	
	//************LOGIN***********
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
	            //throw new Exception("ERROR Bad Authentication: Service level: get page: " + ob + " object");
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
