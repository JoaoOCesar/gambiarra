package dao;

import model.Pais;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class PaisDAO {
	private String url = "jdbc:mysql://localhost:3306/PrimeiroBanco";
	private String usuario = "root";
	private String senha = "aluno";
	private Connection connection;
	
	// Cria uma coleção listaDePaises em forma de List, para armazenar objetos do tipo Pais
    private List<Pais> listaDePaises;

    // Construtor
    public PaisDAO() {
    	// Cria a instância da listaDePaises atráves do comando new ArrayList
        listaDePaises = new ArrayList<>();
    }

    /*
     * Método que adiciona um país, recebido por parâmetro, a listaDePaises
     */
    public void adicionarPais(Pais pais) {
    	
    	if(abreConexao()) {
    		String sql = "INSERT INTO pais (nome, capital) VALUES(?, ?)"; 
    		try {
    			PreparedStatement ps = connection.prepareStatement(sql);
    			ps.setString(1, pais.getNome());
    			ps.setString(2, pais.getCapital());
        		ps.execute();
    		}catch(SQLException e){
    			e.printStackTrace();
    		}
    	}else {
    		System.out.println("paia dog");
    	}
    	
    	listaDePaises.add(pais);
    }

    /*
     * Método que retorna a lista de países
     */
    public List<Pais> obterListaDePaises() {
    	List<Pais> lista = new ArrayList<>();
    	
    	
    	try (Connection connection = DriverManager.getConnection(url,usuario,senha)) {
    		String sql = "SELECT * FROM pais";
    		PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		 
        	ResultSet resultset = preparedStatement.executeQuery(sql);
        	
        	while(resultset.next()) {
        		String nome = resultset.getString("nome");
        		String capital = resultset.getString("capital");
        		Pais pais = new Pais(nome,capital);
        		lista.add(pais);
        	}
        	
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
        return lista;
    }

    /*
     * Método que remove um pais, recebido por parâmetro, da listaDePaises
     */
    public void removerPais(Pais pais) {
    	// Remove um objeto país da lista
    	listaDePaises.remove(pais);
    }

    /*
     * Método que limpa a lista de países
     */
    public void limparListaDePaises() {
    	// Limpa a lista de países
        listaDePaises.clear();
    }

    /*
     * Método que obtem e retorna o primeiro país da lista
     */
    public Pais obterPrimeiroPais() {
    	
    	List<Pais> lista = new ArrayList<>();
    	
    	try(Connection connection = DriverManager.getConnection(url,usuario,senha)){
    		String sql = "SELECT * FROM pais LIMIT 1;";
        	PreparedStatement preparedStatement = connection.prepareStatement(sql);
        	ResultSet resultSet = preparedStatement.executeQuery(sql);
        	
        	while(resultSet.next()) {
        		String nome = resultSet.getString("nome");
        		String capital = resultSet.getString("capital");
        		
        		Pais pais = new Pais(nome,capital);
        		lista.add(pais);
        		
        	}
        	
        	 if (!lista.isEmpty()) {
             	
                 return lista.get(0);
             }
             return null;
        	
    	}catch(SQLException e) {
    		e.printStackTrace();
    		 return null;
    	}
    	
    }

    /*
     * Os métodos obterTerceiroPais e obterQuartoPais são semelhantes  ao  método
     * obterPrimeiroPais.
     */
    public Pais obterTerceiroPais() {
    	List <Pais> lista =  new ArrayList<>();
    	try (Connection connection = DriverManager.getConnection(url,usuario,senha)) {
    		String sql = "SELECT * FROM pais LIMIT 3;";
    		PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		 
        	ResultSet resultset = preparedStatement.executeQuery(sql);
        	
        	while(resultset.next()) {
        		String nome = resultset.getString("nome");
        		String capital = resultset.getString("capital");
        		Pais pais = new Pais(nome,capital);
        		lista.add(pais);
        	}
        	
        	if (lista.size() >= 3) {
                return lista.get(2);
            }
            return null;
        	
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    	
    	
    }

    public Pais obterQuartoPais() {
        if (listaDePaises.size() >= 4) {
            return listaDePaises.get(3);
        }
        return null;
    }
    
    public boolean abreConexao() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		
    		connection = DriverManager.getConnection(url, usuario, senha);
    		
    		if(connection != null) {
    			return true;
    		} else {
    			return false;
    		}
    		
    	}catch(ClassNotFoundException | SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public void fechaConexao(){
    	try {
    		if(connection!=null) {
    			connection.close();
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }    
    
}
