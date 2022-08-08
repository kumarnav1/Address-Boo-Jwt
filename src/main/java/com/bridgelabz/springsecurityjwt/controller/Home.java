package com.bridgelabz.springsecurityjwt.controller;


import com.bridgelabz.springsecurityjwt.dto.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.dto.ResponseDTO;
import com.bridgelabz.springsecurityjwt.dto.UserNameOtpDTO;
import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import com.bridgelabz.springsecurityjwt.service.user.IAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class Home {

    final static String SUCCESS = "Entered Otp is valid, and Registration was successful.";
    final static String FAIL = "Entered OTP was not valid! , Registration failed!, please try again";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAddressBookService addressBookService;

    @RequestMapping("/welcome")
    public String welcome() {
        String text = "If you can see this..!!! ";
        text += "you are authorized !!!";
        return text;
    }

    @GetMapping(value = {"", "/", "/get"})
    public ResponseEntity<ResponseDTO> getAddressBookData() {
        List<AddressBookData> addressBookList = null;
        addressBookList = addressBookService.getAddressBookData();
        ResponseDTO responseDTO = new ResponseDTO("Get call Success !!!", addressBookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/get/{personId}"})
    public ResponseEntity<ResponseDTO> getAddressBookDataById(@PathVariable long personId) {
        AddressBookData addressBookData = addressBookService.getAddressBookDataById(personId);
        ResponseDTO responseDTO = new ResponseDTO("Success Call for Person Id!!!", addressBookData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/get/sortbycity/comparator"})
    public ResponseEntity<ResponseDTO> getSortedAddressBookDataByCityUsingComparator() {
        List<AddressBookData> addressBookList = addressBookService.sortAddressBookDataByComparator();
        ResponseDTO responseDTO = new ResponseDTO("Success Call for city!!!", addressBookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

        @GetMapping(value = {"/get/sortbycity"})
    public ResponseEntity<ResponseDTO> getContactsByCityUsingOrderBy() {
        List<AddressBookData> addressBookDataList = addressBookService.sortContactsByCityOrderBy();
        ResponseDTO responseDTO = new ResponseDTO("Success call for City!!!", addressBookDataList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/sortbycity/{city}")
    public ResponseEntity<ResponseDTO> getContactsByCity(@PathVariable("city") String city) {
        List<AddressBookData> addressBookList = null;
        addressBookList = addressBookService.sortContactsByCity(city);
        ResponseDTO responseDTO = new ResponseDTO("Get Call For ID Department Successful", addressBookList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/get/sortbystate/comparator"})
    public ResponseEntity<ResponseDTO> getSortedAddressBookDataByStateUsingComparator() {
        List<AddressBookData> addressBookList = addressBookService.sortAddressBookDataStateByComparator();
        ResponseDTO responseDTO = new ResponseDTO("Success Call for state!!!", addressBookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/get/sortbystate"})
    public ResponseEntity<ResponseDTO> getContactsByStateUsingOrderBy() {
        List<AddressBookData> addressBookDataList = addressBookService.sortContactsByStateOrderBy();
        ResponseDTO responseDTO = new ResponseDTO("Success call for State!!!", addressBookDataList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/sortbystate/{state}")
    public ResponseEntity<ResponseDTO> getContactsByState(@PathVariable("state") String state) {
        List<AddressBookData> addressBookList = null;
        addressBookList = addressBookService.sortContactsByState(state);
        ResponseDTO responseDTO = new ResponseDTO("Get Call For ID Department Successful", addressBookList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PostMapping({"/register", "/create"})
    public ResponseEntity<ResponseDTO> addUser(@Valid @RequestBody AddressBookDTO addressBookDTO) {
        addressBookDTO.setPassword(passwordEncoder.encode(addressBookDTO.getPassword()));
        AddressBookData user = addressBookService.addAddressBookData(addressBookDTO);

        ResponseDTO responseDTO = new ResponseDTO("Data ADDED Successfully!!!", user);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping(value = {"/update/{personId}"})
    public ResponseEntity<ResponseDTO> updateAddressBookData(@PathVariable int personId,
                                                             @Valid @RequestBody AddressBookDTO addressBookDTO) {
        addressBookDTO.setPassword(passwordEncoder.encode(addressBookDTO.getPassword()));
        AddressBookData addressBookData = addressBookService.updateAddressBookData(personId, addressBookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Data UPDATED Successfully!!!", addressBookData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{personId}"})
    public ResponseEntity<ResponseDTO> deleteAddressBookData(@PathVariable int personId) {
        addressBookService.deleteAddressBookData(personId);
        ResponseDTO responseDTO = new ResponseDTO("Data DELETED Successfully!!!",
                "ID number: " + personId + " DELETED!!!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping({"/verifyotp"})
    public String verifyOtp(@Valid @RequestBody UserNameOtpDTO userNameOtpDTO) {
        String username = userNameOtpDTO.getUsername();
        String otp = userNameOtpDTO.getOtp();
        Boolean isVerifyOtp = addressBookService.verifyOtp(username, otp);
        if (!isVerifyOtp)
            return FAIL;
        return SUCCESS;
    }
}