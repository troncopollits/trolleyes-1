/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import java.sql.Connection;
import java.sql.ResultSet;

import net.daw.dao.TipousuarioDao;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author jesus
 */
public class UsuarioBean {

    private int id,id_tipoUsuario;
    private String dni, nombre, ape1, ape2, login, pass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe1() {
        return ape1;
    }

    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return ape2;
    }

    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId_tipoUsuario() {
        return id_tipoUsuario;
    }

    public void setId_tipoUsuario(int id_tipoUsuario) {
        this.id_tipoUsuario = id_tipoUsuario;
    }
    
    public UsuarioBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
		this.setId(oResultSet.getInt("id"));
		this.setDni(oResultSet.getString("dni"));
		this.setNombre(oResultSet.getString("nombre"));
		this.setApe1(oResultSet.getString("ape1"));
		this.setApe2(oResultSet.getString("ape2"));
		this.setLogin(oResultSet.getString("login"));
		this.setPass(oResultSet.getString("pass"));
		if (expand > 0) {
			TipousuarioDao otipousuarioDao = new TipousuarioDao(oConnection, "tipousuario");
			this.setObj_tipoUsuario(otipousuarioDao.get(oResultSet.getInt("id_tipoUsuario"), expand - 1));
		} else {
			this.setId_tipoUsuario(oResultSet.getInt("id_tipoUsuario"));
		}
		return this;
	}

	
	private void setObj_tipoUsuario(TipousuarioBean tipousuarioBean) {
		// TODO Auto-generated method stub
		
	}

	public String getColumns() {
		String strColumns="";
		strColumns += "id,";
		strColumns += "dni,";
		strColumns += "nombre,";
		strColumns += "ape1,";
		strColumns += "ape2,";
		strColumns += "login,";
		strColumns += "pass,";		
		strColumns += "id_tipoUsuario";		
		return strColumns;				
	}
	
	public String getValues() {
		String strColumns="";
		strColumns += "null,";
		strColumns += EncodingHelper.quotate(dni) + ",";
		strColumns += EncodingHelper.quotate(nombre) + ",";
		strColumns += EncodingHelper.quotate(ape1) + ",";
		strColumns += EncodingHelper.quotate(ape2) + ",";
		strColumns += EncodingHelper.quotate(login) + ",";		
		strColumns += EncodingHelper.quotate("DA8AB09AB4889C6208116A675CAD0B13E335943BD7FC418782D054B32FDFBA04") + ",";			
		strColumns += id_tipoUsuario;		
		return strColumns;				
	}
	
	public String getPairs() {
		String strPairs="";
		strPairs += "id=" + id + ",";
		strPairs += "nombre=" + EncodingHelper.quotate(nombre) + ",";
		strPairs += "ape1=" + EncodingHelper.quotate(ape1) + ",";
		strPairs += "ape2=" + EncodingHelper.quotate(ape2) + ",";
		strPairs += "login=" + EncodingHelper.quotate(login) + ",";
		strPairs += "id_tipoUsuario=" + id_tipoUsuario;
		return strPairs;
		
	}
}
