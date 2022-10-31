package com.example.oenskeseddel.repository;

import com.example.oenskeseddel.model.Bruger;
import com.example.oenskeseddel.model.Ønske;
import com.example.oenskeseddel.model.Ønskeseddel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
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


  public Bruger findBrugerViaID(int bruger_id){
    Bruger bruger = new Bruger();
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      PreparedStatement psts =conn.prepareStatement("SELECT * FROM brugere WHERE bruger_id=?");
      psts.setInt(1,bruger_id);
      ResultSet rs = psts.executeQuery();
      rs.next();
      String bruger_navn = rs.getString(2);
      String bruger_password = rs.getString(3);
      bruger.setBruger_id(bruger_id);
      bruger.setBruger_navn(bruger_navn);
      bruger.setBruger_password(bruger_password);
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();}
    return bruger;
  }
  public Bruger findBrugerViaNavn(String bruger_navn){
    Bruger bruger = new Bruger();
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      PreparedStatement psts =conn.prepareStatement("SELECT * FROM brugere WHERE bruger_navn=?");
      psts.setString(1,bruger_navn);
      ResultSet rs = psts.executeQuery();
      if(rs.next()){
      int bruger_id = rs.getInt(1);
      String bruger_password = rs.getString(3);
      bruger.setBruger_id(bruger_id);
      bruger.setBruger_navn(bruger_navn);
      bruger.setBruger_password(bruger_password);
        return bruger;
      }
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();}
    return null;
  }



  public int loginBruger(String navn, String password){
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      PreparedStatement psts =conn.prepareStatement("SELECT * FROM brugere");
      ResultSet resultSet = psts.executeQuery();
      while(resultSet.next()){
        int bruger_id = resultSet.getInt(1);
        String bruger_name = resultSet.getString(2);
        String bruger_password = resultSet.getString(3);
        if(bruger_name.equals(navn) && bruger_password.equals(password)){
          return bruger_id;
        }
      }

    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();}
    return -1;
    }

  public List<Ønskeseddel> getEgneØnskesedler(int bruger_id){
    List<Ønskeseddel> seddelList = new LinkedList<>();
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
      }
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();}
    return seddelList;
  }

  public List<Ønskeseddel> getDelteØnskesedler(int bruger_id){
    List<Ønskeseddel> seddelList = new LinkedList<>();
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      String findDelteSedler = "SELECT ønskesedler.* FROM  delte_brugere " +
          "INNER JOIN ønskesedler ON delte_brugere.seddel_id = ønskesedler.seddel_id " +
          "where delte_brugere.bruger_id=?;";
      PreparedStatement psFindDelteSedler = conn.prepareStatement(findDelteSedler);
      psFindDelteSedler.setInt(1,bruger_id);

      ResultSet resultSetBruger = psFindDelteSedler.executeQuery();
      while(resultSetBruger.next()){
        int seddel_id = resultSetBruger.getInt(1);
        String seddel_navn = resultSetBruger.getString(2);
        int brugerID = resultSetBruger.getInt(3);
        seddelList.add(new Ønskeseddel(seddel_id,seddel_navn,brugerID));
      }
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();
    }
    return seddelList;
  }

  public List<Ønske> getØnsker(int seddel_id){
    List<Ønske> ønskeListe = new LinkedList<>();
    try{
      Connection conn = DriverManager.getConnection(db_url,uid,pwd);
      String findØnsker = "SELECT * FROM ønsker where seddel_id=?";
      PreparedStatement psFindØnsker = conn.prepareStatement(findØnsker);
      psFindØnsker.setInt(1,seddel_id);

      ResultSet resultSetØnske = psFindØnsker.executeQuery();
      while(resultSetØnske.next()){
        int ønske_id = resultSetØnske.getInt(1);
        String ønske_navn = resultSetØnske.getString(3);
        double ønske_pris = resultSetØnske.getDouble(4);
        ønskeListe.add(new Ønske(ønske_id, seddel_id, ønske_navn,ønske_pris));
      }
    }
    catch (SQLException e){
        System.out.println("Cannot connect to database");
        e.printStackTrace();
    }
    return ønskeListe;
  }

  public boolean isBrugerNavnAvailable(String bruger_navn){
    try {
      Connection conn = DriverManager.getConnection(db_url, uid, pwd);
      String queryCheck = "SELECT * FROM brugere WHERE bruger_navn=?";
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
      updatePS.executeUpdate();
      }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();
    }
  }

  public void createØnskeseddel(int bruger_id, String seddel_navn){
    try{
      System.out.println(bruger_id);
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

  public void createØnske(int seddel_id, String ønske_navn, double ønske_pris){
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

  public boolean createDelte_brugere(int seddel_id, String delbruger){
    try{
      Bruger fundetBruger = findBrugerViaNavn(delbruger);
      if(!(fundetBruger==null)) {
        Connection conn = DriverManager.getConnection(db_url, uid, pwd);
        String queryAdd = "INSERT INTO delte_brugere(seddel_id, bruger_id)" +
            "VALUES(?,?)";
        PreparedStatement updatePS = conn.prepareStatement(queryAdd);
        updatePS.setInt(1,seddel_id);
        updatePS.setInt(2,fundetBruger.getBruger_id());
        updatePS.executeUpdate();
        return true;
      }
    }
    catch (SQLException e){
      System.out.println("Cannot connect to database");
      e.printStackTrace();
    }
    return false;
  }


}
