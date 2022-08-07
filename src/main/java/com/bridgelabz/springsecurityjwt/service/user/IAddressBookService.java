package com.bridgelabz.springsecurityjwt.service.user;

import com.bridgelabz.springsecurityjwt.dto.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.entity.AddressBookData;

import java.util.List;

public interface IAddressBookService {
    public AddressBookData addAddressBookData(AddressBookDTO user);

    public List<AddressBookData> getUsers();

    List<AddressBookData> getAddressBookData();

    List<AddressBookData> sortAddressBookDataByComparator();

    List<AddressBookData> sortContactsByCityOrderBy();

    List<AddressBookData> sortContactsByCity(String city);

    List<AddressBookData> sortAddressBookDataStateByComparator();

    List<AddressBookData> sortContactsByStateOrderBy();

    List<AddressBookData> sortContactsByState(String state);

    AddressBookData getAddressBookDataById(long personId);

    AddressBookData updateAddressBookData(long personId, AddressBookDTO addressBookDTO);

    void deleteAddressBookData(long personId);
}
