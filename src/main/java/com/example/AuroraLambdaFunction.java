/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * AuroraLambdaFunction Versión 1.0 7 nov 2023
 *
 * Copyright(c) 2007-2023, Boos IT.
 * admin@talento.cloud
 *
 * http://talento.cloud/license
 **/

package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La descripcion de la clase va aqui.
 * @version 	1.0 7 nov 2023
 * @author josorio
 */
public class AuroraLambdaFunction implements RequestHandler<Object, String> {

    private static final String AURORA_DB_URL = System.getenv("DB_HOST_FULL");//"jdbc:postgresql://your-aurora-hostname:5432/your-database";
    private static final String AURORA_DB_USERNAME = System.getenv("DB_USERNAME");//"your-username";
    private static final String AURORA_DB_PASSWORD = System.getenv("DB_PASSWORD");//"your-password";

    @Override
    public String handleRequest(Object input, Context context) {
        Connection connection = null;
        try {
            System.out.println("path: "+AURORA_DB_URL);
            // Establish the database connection
            connection = DriverManager.getConnection(AURORA_DB_URL, AURORA_DB_USERNAME, AURORA_DB_PASSWORD);

            // Execute SQL query
            String sql = "select codigo, descripcion from public.trv_propiedades limit 3";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Process the results if needed
            StringBuilder ultimo = new StringBuilder();
            while (resultSet.next()) {
                // Handle each row of the result set
                System.out.println("code: "+resultSet.getString(1));
                System.out.println("description: "+resultSet.getString(2));
                ultimo.append(resultSet.getString(2)).append("\n");
            }
            
            //connection.close();

            return "Query executed successfully! Encontrado:\n"+ultimo.toString();
        } catch (SQLException e) {
            // Handle database errors
            e.printStackTrace();
            return "Error executing query: " + e.getMessage();
        } finally {
            // Close the database connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}