package com.example.oenskeseddel.repository;

import com.example.oenskeseddel.model.Bruger;
import com.example.oenskeseddel.model.Ønskeseddel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Repository
public class ØnskeRepository {

  @Value("${spring.datasource.url}")
  private String db_url;
  @Value("${spring.datasource.username}")
  private String uid;
  @Value("${spring.datasource.password}")
  private String pwd;

  public int loginBruger(String navn, String password){
    List<Bruger> brugerList = new LinkedList<>();
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      PreparedStatement psts =conn.prepareStatement("SELECT * FROM brugere");
      ResultSet resultSet = psts.executeQuery();
      while(resultSet.next()){
        String bruger_name = resultSet.getString(2);
        String bruger_password = resultSet.getString(3);
        if(bruger_name.equals(navn) && bruger_password.equals(password)){
          return resultSet.getInt(1);
        }
      }

    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();}
    return -1;
    }


  public List<Ønskeseddel> getØnskesedler(int bruger_id){
    List<Ønskeseddel> seddelList = new LinkedList<>();
    ListIterator<Ønskeseddel> it = seddelList.listIterator();
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      String findBrugerSedler = "SELECT * FROM ønskesedler where bruger_id=?";
      PreparedStatement psFindBrugerSedler = conn.prepareStatement(findBrugerSedler);
      psFindBrugerSedler.setInt(1,bruger_id);

      ResultSet resultSetBruger = psFindBrugerSedler.executeQuery();
      while(resultSetBruger.next()){
        int seddel_id = resultSetBruger.getInt(1);
        String seddel_navn = resultSetBruger.getString(2);
        seddelList.add(new Ønskeseddel(seddel_id,seddel_navn,bruger_id));

        String findØnsker = "SELECT * FROM ønske where seddel_id=?";
        PreparedStatement psFindØnsker = conn.prepareStatement(findØnsker);
        psFindØnsker.setInt(1,seddel_id);

        ResultSet resultSetØnske = psFindØnsker.executeQuery();
        Ønskeseddel seddel = it.next();
        while(resultSetØnske.next()){
          int ønske_id = resultSetØnske.getInt(1);
          String ønske_navn = resultSetØnske.getString(3);
          double ønske_pris = resultSetØnske.getDouble(4);
          seddel.addØnske(ønske_id, seddel_id, ønske_navn, ønske_pris);}
      }
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();}
    return seddelList;
  }

  public boolean isBrugerNavnAvailable(String bruger_navn){
    try {
      Connection conn = DriverManager.getConnection(db_url, uid, pwd);
      String queryCheck = "SELECT brugere WHERE navn=?";
      PreparedStatement checkNavn = conn.prepareStatement(queryCheck);
      checkNavn.setString(1, bruger_navn);
      ResultSet resultSet = checkNavn.executeQuery();
      if (!resultSet.next()) {
        return true;
      }
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();
    }
    return false;
  }

  public void opretBruger(String bruger_navn, String bruger_password){
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      String queryAdd ="INSERT INTO brugere(bruger_navn,bruger_password)" +
          "VALUES(?,?)";
      PreparedStatement updatePS = conn.prepareStatement(queryAdd);
      updatePS.setString(1,bruger_navn);
      updatePS.setString(2,bruger_password);
      updatePS.executeQuery();
      }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();
    }
  }


  public void addØnskeseddel(int bruger_id, String seddel_navn){
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      String queryAdd ="INSERT INTO ønskesedler(seddel_navn,bruger_id)" +
          "VALUES(?,?)";
      PreparedStatement updatePS = conn.prepareStatement(queryAdd);
      updatePS.setString(1,seddel_navn);
      updatePS.setInt(2,bruger_id);
      updatePS.executeUpdate();
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();}

  }

  public void addØnske(int seddel_id, String ønske_navn, double ønske_pris){
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      String queryAdd ="INSERT INTO ønsker(ønske_navn,ønske_pris,seddel_id)" +
          "VALUES(?,?,?)";
      PreparedStatement updatePS = conn.prepareStatement(queryAdd);
      updatePS.setString(1,ønske_navn);
      updatePS.setDouble(2,ønske_pris);
      updatePS.setInt(3,seddel_id);
      updatePS.executeUpdate();
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();}

  }

}
