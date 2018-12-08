package productshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productshop.domain.entities.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//    @Query(value = "SELECT u FROM User u JOIN u.soldProducts p WHERE p.buyer IS NOT NULL")
//    Set<User> findUsersWithSoldProducts();


  //  @Query(value = "SELECT u FROM User as u JOIN u.soldProducts as p")
//  @Query("" +
//          "SELECT u FROM User AS u " +
//          "JOIN Product AS p ON p.seller.id = u.id " +
//          "WHERE p.buyer IS NOT NULL " +
//          "GROUP BY u.id " +
//          "ORDER BY u.lastName, u.firstName")
//  List<User> findUsersWithSoldProducts();
}
