package models;

public class User {
  private String username;
  private String ip;
  private String port;

  public User(String username, String ip, String port) {
    this.username = username;
    this.ip = ip;
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public String getIp() {
    return ip;
  }

  public String getPort() {
    return port;
  }
}
