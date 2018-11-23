/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.daw.dao.TipoproductoDao;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author a044531896d
 */
public class ProductoBean {
    private int id;
    private String codigo;
    private String desc;
    private int existencias;
    private float precio;
    private String foto;
    private TipoproductoBean obj_TipoproductoBean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public TipoproductoBean getObj_TipoproductoBean() {
        return obj_TipoproductoBean;
    }

    public void setObj_TipoproductoBean(TipoproductoBean obj_TipoproductoBean) {
        this.obj_TipoproductoBean = obj_TipoproductoBean;
    }
    
    public ProductoBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws SQLException, Exception{
        this.setId(oResultSet.getInt("id"));
        this.setCodigo(oResultSet.getString("codigo"));
        this.setDesc(oResultSet.getString("desc"));
        this.setExistencias(oResultSet.getInt("existencias"));
        this.setPrecio(oResultSet.getFloat("precio"));
        this.setFoto(oResultSet.getString("foto"));
        if(expand > 0){
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, "tipoproducto");
            this.setObj_TipoproductoBean(oTipoproductoDao.get(oResultSet.getInt("id_tipoProducto")));
        }
        return this;
        
    }
    
    public String getColumns(){
        String strColumns="";
        strColumns += "id,";
        strColumns += "codigo,";
        strColumns += "producto.desc,";
        strColumns += "existencias,";
        strColumns += "precio,";
        strColumns += "foto,";
        strColumns += "id_tipoProducto";
        return strColumns;
    }
    
    public String getValues(){
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(codigo) + ",";
        strColumns += EncodingHelper.quotate(desc) + ",";
        strColumns += existencias + ",";
        strColumns += precio + ",";
        strColumns += EncodingHelper.quotate(foto) + ",";
        strColumns += obj_TipoproductoBean.getId();
        return strColumns;
    }
    
    public String getPairs(){
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "codigo=" + EncodingHelper.quotate(codigo) + ",";
        strPairs += "producto.desc=" + EncodingHelper.quotate(desc) + ",";
        strPairs += "existencias=" + existencias + ",";
        strPairs += "precio=" + precio + ",";
        strPairs += "foto=" + EncodingHelper.quotate(foto) + ",";
        strPairs += "id_tipoProducto=" + obj_TipoproductoBean.getId();
        strPairs += " WHERE id=" + id;
        return strPairs;
    }
}