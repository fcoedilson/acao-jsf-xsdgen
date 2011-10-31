package br.org.acao.util.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Classe utilizada para realizar a conexão com o banco de dados do
 * patrimônio, em Oracle
 * 
 * @author lafitte
 * @since 23/04/2010
 *
 */
public class Connections {

	/**
	 * Conexão com o banco de dados Oracle do Sistema de Patrimônio
	 * Retorna a conexão do pool de conexões do container. As configurações de conexão estão no 
	 * arquivo de contexto
	 * @return
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static Connection oracleConnection() throws NamingException, SQLException {

		Connection conection = null;
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource)   initContext.lookup("java:comp/env/jdbc/patrimonio");
			conection = ds.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conection;
	}
	
	public static Connection pgConnection() throws NamingException, SQLException {

		Connection conection = null;
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource)   initContext.lookup("java:comp/env/jdbc/sigafrota");
			conection = ds.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conection;
	}
}
