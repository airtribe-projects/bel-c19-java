package org.example;

import javax.annotation.processing.SupportedSourceVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class Car {
  @Value("${car.carModel}")
  public String carModel;

  @Value("${car.carType}")
  private String carType;

  // has a relationship
  // Programming to an abstraction
  // rather than a concrete implementation
  // loose coupling
  @Autowired
  private Engine engine;


  public Car(@Value("${car.carModel}") String carModel, @Value("${car.carType}") String carType, Engine engine) {
    this.carModel = carModel;
    this.carType = carType;
    this.engine = engine;
  }

  public Car() {

  }

  public String getCarModel() {
    return carModel;
  }

  public void setCarModel(String carModel) {
    this.carModel = carModel;
  }

  public String getCarType() {
    return carType;
  }

  public void setCarType(String carType) {
    this.carType = carType;
  }

  public Engine getEngine() {
    return engine;
  }

  public void setEngine(Engine engine) {
    this.engine = engine;
  }
//
  public void startCar() {
    engine.startEngine();
    System.out.println("Car " + carModel + " of type " + carType + " is starting.");
  }
}
