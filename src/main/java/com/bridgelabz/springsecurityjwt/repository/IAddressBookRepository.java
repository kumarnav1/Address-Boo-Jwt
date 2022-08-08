package com.bridgelabz.springsecurityjwt.repository;

import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAddressBookRepository extends JpaRepository<AddressBookData, Long> {
    public AddressBookData findByUsername(String username);

    @Query(value = "select * from address_book_data order by city", nativeQuery = true)
    List<AddressBookData> findContactsByCityOrderBy();

    @Query(value = "select * from address_book_data where city=:city", nativeQuery = true)
    List<AddressBookData> sortContactByCity(String city);

    @Query(value = "select *from address_book_data order by state", nativeQuery = true)
    List<AddressBookData> findContactsByStateOrderBy();

    @Query(value = "select * from address_book_data where state=:state", nativeQuery = true)
    List<AddressBookData> sortContactByState(String state);

    @Modifying
    @Transactional
    @Query(value = "update address_book_data set verified=true where username = :username ", nativeQuery = true)
    void changeVerified(String username);

    @Query(value = "select verified from address_book_data where username = :username", nativeQuery = true)
    Boolean isVerified(String username);

    @Modifying
    @Transactional
    @Query(value = "update address_book_data set password = :newPassword where username = :username AND email = :email", nativeQuery = true)
    void updateNewPassword(String username, String email, String newPassword);
}