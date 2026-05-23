package org.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("petrol")
public class PetrolEngine extends Engine {

  public PetrolEngine(@Value("${engine.engineType}") String engineType, @Value("${engine.enginePower}") String enginePower) {
    super(engineType, enginePower);
  }
}
