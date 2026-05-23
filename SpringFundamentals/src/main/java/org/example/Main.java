package org.example;

import javax.swing.text.DefaultEditorKit;
import org.example.config.BeanConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
  public static void main(String[] args) {
    // Application is creating the objecr
//    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
    Car car = applicationContext.getBean(Car.class);
    car.startCar();

    PetrolEngine engine  = applicationContext.getBean(PetrolEngine.class);
    PetrolEngine dieselEngine  = applicationContext.getBean(DieselEngine.class);
//    Car beanedCar1 = applicationContext.getBean("car1", Car.class);
//    Car beanedCar2 = applicationContext.getBean("car2", Car.class);
//    Car beanedCar3 = applicationContext.getBean("car2", Car.class);
//    beanedCar1.startCar();
//    beanedCar2.startCar();
//    beanedCar3.startCar();
//
//    System.out.println(beanedCar1.hashCode());
//    System.out.println(beanedCar2.hashCode());
//    System.out.println(beanedCar3.hashCode());
//
//    Car car1 = new Car();
//    car1.setCarType("Sedan");
//    car1.setCarModel("BMW");
//    car1.setEngine(new Engine("Diesel", "150 HP"));
//    car1.startCar();
//    Engine engine1 = new Engine("Petrol", "200 HP");
//    Car car2 = new Car("BMW", "Sedan", engine1);
//    car2.startCar();
//
//    System.out.println(car1.hashCode());
//    System.out.println(car2.hashCode());
  }
}