package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}

	public List<String> getVertici(int C) {
		String sql="SELECT DISTINCT(portion_display_name) AS nome "
				+ "FROM `portion` "
				+ "WHERE calories < ? "
				+ "ORDER BY portion_display_name";
		Connection conn = DBConnect.getConnection() ;
		List<String> list = new ArrayList<>() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, C);
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				list.add(res.getString("nome"));
			}
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Adiacenza> getAdiacenze(int C){
		String sql="SELECT P1.portion_display_name AS n1, P2.portion_display_name AS n2, COUNT(DISTINCT P1.food_code) AS peso "
				+ "FROM `portion` P1, `portion` P2 "
				+ "WHERE P1.food_code=P2.food_code "
				+ "		AND P1.portion_id<>P2.portion_id "
				+ "		AND P1.calories < ? AND P2.calories < ? "
				+ "GROUP BY P1.portion_display_name, P2.portion_display_name";
		Connection conn = DBConnect.getConnection() ;
		List<Adiacenza> list = new ArrayList<>() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, C);
			st.setInt(2, C);
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				Adiacenza a = new Adiacenza(res.getString("n1"),res.getString("n2"),res.getInt("peso"));
				list.add(a);
			}
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	

}
