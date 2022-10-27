package com.example.oenskeseddel.repository;

import com.example.oenskeseddel.model.Bruger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ØnskeRepository {

  @Value("${spring.datasource.url}")
  private String db_url;
  @Value("${spring.datasource.username}")
  private String uid;
  @Value("${spring.datasource.password}")
  private String pwd;

  public List<Bruger> getAll(){
    List<Bruger> brugerList = new LinkedList<>();
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();}
    return brugerList;
    }

  }

