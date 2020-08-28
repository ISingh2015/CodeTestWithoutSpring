package com.inderjit.domain.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.inderjit.codetest.model.Porder;


/**
 * Orders CRUD done using H2 in memory database and JDBC. Authentication implementation 
 * and Optimization would be required.
 * 
 * @author Inderjit SS
 *
 */
//@Slf4j
public class OrderDao {
	
	private static final Logger log = Logger.getLogger("OrderDao.class");
	
	/**
	 * Singleton instance.
	 */
	private static OrderDao instance;
	public static OrderDao getInstance() {
		getOrderService();
		return instance;
	}

	private static OrderDao getOrderService() {
		if (instance == null) {
			log.info("Creating Order Service");
			instance = new OrderDao();
		}
		return instance;
	}
	
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:mem:myDb";
	static final String INSERTORDER = "insert into Porder values (?,?,?,?,?)";
	static final String FINDALLORDERS = "select * from porder where productId= %d";
	static final String FINDORDERBYID = "select * from porder where orderId= %d";	
	static final String DELETEORDER = "delete from porder where order_id= %d";
	static final String DELETEALLORDERS = "delete from porder where order_id <> null";	
	static final String CREATEORDERTABLE = 
	"CREATE TABLE   PORDER " + 
    "(order_id INTEGER not NULL, " + 
    " productId VARCHAR(255), " +  
    " qtyOrdered DECIMAL(12,2), " +  
    " description VARCHAR(255), " +
    " createdDate DATE, " +    
    " PRIMARY KEY ( order_id ))";
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStat() {
		return stat;
	}

	public void setStat(Statement stat) {
		this.stat = stat;
	}

	private OrderDao () {
		getConnection();
		createDatabaseTables();
	}
	
	public PreparedStatement getPstat() {
		return pstat;
	}

	public void setPstat(PreparedStatement pstat) {
		this.pstat = pstat;
	}

	/**
	 * Get Orders related to Product Id for Stock Calculation.
	 * 
	 * @param productId
	 * @return
	 */
	public List<Porder> findAll(Integer productId) {
		List<Porder> porderList = new ArrayList<>();
		try {
			getConnection();
			String getOrders = String.format (FINDALLORDERS,productId);
			ResultSet rs = getStat().executeQuery(getOrders);
			if (rs.next()) {
				porderList.add(createNewOrder(rs));
			}
			conn.commit();
			log.log(Level.INFO,"findAll Orders -> Found " + porderList.size());			
		} catch (Exception e) {
			log.log(Level.INFO,e.getMessage());
		}
		return porderList;
	};

	private Porder createNewOrder (ResultSet rs) throws SQLException{
		Porder porder = new Porder();
		porder.setId(rs.getInt("id"));
		porder.setProductId(rs.getInt("productId"));
		porder.setDescription(rs.getString("description"));
		porder.setQtyOrdered(rs.getDouble("qtyOrdered"));
		porder.setCreatedDate(rs.getDate("createdDate"));
		return porder;
	}
	
	public Porder save(Porder order) {
		try {
			getConnection();
			pstat = conn.prepareStatement(INSERTORDER);			
			getPstat().setInt(1, order.getId());
			getPstat().setInt(2,order.getProductId());
			getPstat().setString(3, order.getDescription());
			getPstat().setDate(4, order.getCreatedDate());
			getPstat().executeUpdate();
			conn.commit();
			log.log(Level.INFO,"Order Saved " + order.getId());			
		} catch (Exception e) {
			log.log(Level.SEVERE,e.getMessage());
		}
		return order;

	};

	public Porder findById(Integer orderId) {
		Porder porder=null;
		try {
			getConnection();
			String getOrders = String.format (FINDORDERBYID,orderId);
			ResultSet rs = getStat().executeQuery(getOrders);
			if (rs.next()) {
				porder = createNewOrder(rs);
			}
			conn.commit();
			log.log(Level.INFO,"Order Found " + orderId);			
		} catch (Exception e) {
			log.log(Level.SEVERE,e.getMessage());
		}
		return porder;

	};

	public Porder delete(Porder order, boolean deleteAll) {
		boolean deleted=false;
		String getOrders;		
		try {
			getConnection();
			if (deleteAll) {
				getOrders = String.format (DELETEALLORDERS);
			} else {
				getOrders = String.format (DELETEORDER,order.getId());				
			}

			getStat().executeUpdate(getOrders);
			conn.commit();
			deleted=true;
			log.log(Level.INFO,"Order(s) Deleted");
		} catch (Exception e) {
			log.log(Level.SEVERE,e.getMessage());
		}
		return deleted ? order:null ;

	};

	private Connection getConnection() {
		if (conn!=null && stat != null) return conn;
		try {
			Class.forName(JDBC_DRIVER);
			log.info("Connecting to database "+ DB_URL);
			conn = DriverManager.getConnection(DB_URL);
			stat = conn.createStatement();
// 			handle transaction from individual get or save statements
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			log.log(Level.SEVERE,e.getMessage());
		}
		return conn;
	}
	
	private void createDatabaseTables() {
		log.info("Creating Order Table");
		try {
			getConnection();
			String createOrderTable= String.format (CREATEORDERTABLE);
			getStat().executeUpdate(createOrderTable);
			conn.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE,e.getMessage());
		}
	}
}
