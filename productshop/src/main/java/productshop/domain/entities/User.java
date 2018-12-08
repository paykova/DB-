package productshop.domain.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
public class User extends BaseEntity{

//    private String firstName;
//    private String lastName;
//    private Integer age;
//    private List<User> friends;
//    private Set<Product> soldProducts;
//
//    public User() {
//    }
//
//    @Column(name = "first_name")
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//    @Column(name = "last_name", nullable = false)
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    @Column(name = "age")
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    @ManyToMany(targetEntity = User.class)
//    @JoinTable(name = "users_friends",
//                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//                inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
//    public List<User> getFriends() {
//        return friends;
//    }
//
//    public void setFriends(List<User> friends) {
//        this.friends = friends;
//    }
//
//    @OneToMany(mappedBy = "seller")
//    public Set<Product> getSoldProducts() {
//        return soldProducts;
//    }
//
//    public void setSoldProducts(Set<Product> soldProducts) {
//        this.soldProducts = soldProducts;
//    }

    private String firstName;
    private String lastName;
    private Integer age;
    private Set<User> friends;
    private Set<Product> boughtProducts;
    private Set<Product> soldProducts;

    public User() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToMany
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToMany(mappedBy = "buyer")
    public Set<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(Set<Product> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    @OneToMany(mappedBy = "seller")
    public Set<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
