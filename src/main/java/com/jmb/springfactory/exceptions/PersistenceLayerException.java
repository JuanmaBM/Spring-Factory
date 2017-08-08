package com.jmb.springfactory.exceptions;

public class PersistenceLayerException extends Exception {

  private static final long serialVersionUID = 7461167074842395995L;

  public PersistenceLayerException() {
    super();
  }
  
  public PersistenceLayerException(String message) {
    super(message);
  }
}
