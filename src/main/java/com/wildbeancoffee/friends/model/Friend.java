package com.wildbeancoffee.friends.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class Friend {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @NotBlank
  private String firstName;
  private String lastName;


  //This is a forward reference.. Now create a back reference in Friend Class
  @OneToMany(mappedBy = "friend")
  private Set<Address> addresses;

  public Set<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }

  public Friend() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
