/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import ConnectDB.OracleConnection;
import Model.Person;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 *
 * @author MyPC
 */
public class PersonController {
    public  ArrayList<Person> getPerson()
    {
        ArrayList<Person> ListPerson = new <Person>ArrayList();
        try{
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT * FROM Person";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Person person = new Person();
                
                person.setIdper(rs.getInt("IDPER"));
                person.setUsername(rs.getString("USERNAME"));
                person.setName(rs.getString("NAME"));
                person.setGender(rs.getInt("Gender"));
                 person.setPhone(rs.getString("PHONE"));
                person.setProvince(rs.getString("PROVINCE"));
                person.setDistrict(rs.getString("DISTRICT"));
                person.setTown(rs.getString("TOWN"));
                person.setAddress(rs.getString("ADDRESS"));
                person.setStatus(rs.getInt("STATUS"));

                ListPerson.add(person);  
            }
            rs.close();
            //st.close();
            con.close();
            
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return ListPerson;
    }
    
}