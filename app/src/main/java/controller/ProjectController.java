/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;   
import util.ConnectionFactory;

/**
 *
 * @author dois0
 */
public class ProjectController {
    
    public void save(Project project) {
        
        // comando mysql
        String sql = "INSERT INTO projects(name, description,"
                + "createdAt, updatedAt) VALUES(?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            // estabele a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            
            // prepara a query
            statement = conn.prepareStatement(sql);
            
            // insere os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            
            // executa a query
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto", ex);
        } finally {
            // fecha a conexão com o banco de dados
            ConnectionFactory.closeConnection(conn, statement);
        }
    }
    
    public void update(Project project){
        
        String sql = "UPDATE projects SET name = ?, description = ?,"
                + "createdAt = ?, updateAt = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em atualizar o projeto", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }
    
    public List<Project> getAll(){
        String sql = "SELECT * FROM projects";
        
        // criação da lista de projetos
        List<Project> projects = new ArrayList<Project>();
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {     
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            
            // valor retornado pela execucao da query
            resultSet = statement.executeQuery();
            
            // percorre o resultset preenchendo a lista de projetos
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                // adiciona o projeto percorrido na lista de projetos
                projects.add(project);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar os projetos", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }
        
        // retorna a lista de projetos que foi criada e carregada do banco de dados
        return projects;
    }
    
    public void removeById(int idProject) {
        
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {     
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setInt(1, idProject);
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar o projeto", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }
    
}
