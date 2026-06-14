package org.airtribe.AsyncApiApplicationC19.dto;

import java.util.List;


public class ProductResult {

  private List<Product> products;

  public ProductResult(List<Product> products) {
    this.products = products;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }
}
