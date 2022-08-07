package com.bridgelabz.springsecurityjwt.service.user;

import com.bridgelabz.springsecurityjwt.dto.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import com.bridgelabz.springsecurityjwt.exception.AddressBookException;
import com.bridgelabz.springsecurityjwt.repository.IAddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class AddressBookService implements IAddressBookService {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    IAddressBookRepository service;

    @Override
    public List<AddressBookData> getUsers() {
        return null;
    }

    @Override
    public List<AddressBookData> getAddressBookData() {
        return service.findAll();
    }

    @Override
    public List<AddressBookData> sortAddressBookDataByComparator() {
        List<AddressBookData> listOfData = service.findAll();
        listOfData.sort(Comparator.comparing(AddressBookData::getCity));
        return listOfData;
    }

    @Override
    public List<AddressBookData> sortContactsByCityOrderBy() {
        return service.findContactsByCityOrderBy();
    }

    @Override
    public List<AddressBookData> sortContactsByCity(String city) {
        return service.sortContactByCity(city);
    }

    @Override
    public List<AddressBookData> sortAddressBookDataStateByComparator() {
        List<AddressBookData> listOfData = service.findAll();
        listOfData.sort(Comparator.comparing(AddressBookData::getState));
        return listOfData;
    }

    @Override
    public List<AddressBookData> sortContactsByStateOrderBy() {
        return service.findContactsByStateOrderBy();
    }

    @Override
    public List<AddressBookData> sortContactsByState(String state) {
        return service.sortContactByState(state);
    }

    @Override
    public AddressBookData getAddressBookDataById(long personId) {
        return service.findById(personId).orElseThrow(() -> new AddressBookException("Person not found In the List"));
    }


    @Override
    public AddressBookData addAddressBookData(AddressBookDTO addressBookDTO) {
        AddressBookData user = modelMapper.map(addressBookDTO, AddressBookData.class);
        return service.save(user);
    }

    @Override
    public AddressBookData updateAddressBookData(long personId, AddressBookDTO addressBookDTO) {
        AddressBookData addressBookData = this.getAddressBookDataById(personId);
        modelMapper.map(addressBookDTO, addressBookData);
        service.save(addressBookData);
        return addressBookData;
    }

    @Override
    public void deleteAddressBookData(long personId) {
        AddressBookData addressBookData = this.getAddressBookDataById(personId);
        service.delete(addressBookData);
    }
}
