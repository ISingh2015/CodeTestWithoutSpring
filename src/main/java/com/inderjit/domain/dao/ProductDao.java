package com.inderjit.domain.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import com.inderjit.codetest.model.Product;
import com.inderjit.codetest.model.Products;

public class ProductDao {

	private static final Logger log = Logger.getLogger("ProductDao.class");
	/**
	 * Singleton instance.
	 */
	private static ProductDao instance;

	public static ProductDao getInstance() {
		getProductService();
		return instance;
	}

	private static ProductDao getProductService() {
		if (instance == null) {
			log.info("Creating Product Service");
			instance = new ProductDao();
			try {
				instance.populateData();
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}
		return instance;
	}

	private static Products list = new Products();
	private static final String PRODUCTS_CSV_FILE_PATH = "products.csv";

	private ProductDao() {
	}

	public Products getAllProducts() {
		return list;
	}

	/**
	 * Adding to existing product List.
	 * 
	 * @param product
	 */

	public void addProduct(Product product) {
		list.getProductList().add(product);
	}

	/**
	 * Method to work with CSV file. creates PRODUCT object from data in CSV file.
	 * Included key and 7 others fields for PRODUCT.CSV for now. Should work with
	 * 10000 records in CSV file with expected FORMAT. Please see CSV file for
	 * Format. Needs Optimization in case of very large files.
	 * 
	 * @throws Exception
	 */
	private void populateData() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();

		File file = new File(classLoader.getResource(PRODUCTS_CSV_FILE_PATH).getFile());
		FileInputStream input = new FileInputStream(file);

		InputStreamReader isReader = new InputStreamReader(input);
		BufferedReader reader = new BufferedReader(isReader);
		String str;
		int keyForItem = 0;
		while ((str = reader.readLine()) != null) {
			String data[] = str.split(",");
			try {
				Product product = new Product(keyForItem, data[0], data[1], data[2], data[3], data[4],
						new Double(data[5]).doubleValue(), new Double(data[6]).doubleValue(),
						new Integer(data[7]).intValue());
				addProduct(product);
				keyForItem++;
				log.info(product.toString());
			} catch (Exception e) {
				// ignoring errors (e.g: case of header present)
			}
		}
		isReader.close();
		reader.close();
	}
}
