package com.bridgelabz.springsecurityjwt.service.user;

import com.bridgelabz.springsecurityjwt.dto.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import com.bridgelabz.springsecurityjwt.entity.UserNameOtpData;
import com.bridgelabz.springsecurityjwt.exception.AddressBookException;
import com.bridgelabz.springsecurityjwt.repository.IAddressBookRepository;
import com.bridgelabz.springsecurityjwt.repository.IUserNameOtpRepository;
import com.bridgelabz.springsecurityjwt.service.EmailSenderService;
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
    EmailSenderService senderService;

    @Autowired
    IAddressBookRepository iAddressBookRepository;

    @Autowired
    IUserNameOtpRepository iUserNameOtpRepository;

    @Override
    public List<AddressBookData> getAddressBookData() {
        return iAddressBookRepository.findAll();
    }

    @Override
    public List<AddressBookData> sortAddressBookDataByComparator() {
        List<AddressBookData> listOfData = iAddressBookRepository.findAll();
        listOfData.sort(Comparator.comparing(AddressBookData::getCity));
        return listOfData;
    }

    @Override
    public List<AddressBookData> sortContactsByCityOrderBy() {
        return iAddressBookRepository.findContactsByCityOrderBy();
    }

    @Override
    public List<AddressBookData> sortContactsByCity(String city) {
        return iAddressBookRepository.sortContactByCity(city);
    }

    @Override
    public List<AddressBookData> sortAddressBookDataStateByComparator() {
        List<AddressBookData> listOfData = iAddressBookRepository.findAll();
        listOfData.sort(Comparator.comparing(AddressBookData::getState));
        return listOfData;
    }

    @Override
    public List<AddressBookData> sortContactsByStateOrderBy() {
        return iAddressBookRepository.findContactsByStateOrderBy();
    }

    @Override
    public List<AddressBookData> sortContactsByState(String state) {
        return iAddressBookRepository.sortContactByState(state);
    }

    @Override
    public AddressBookData getAddressBookDataById(long personId) {
        return iAddressBookRepository.findById(personId).orElseThrow(() -> new AddressBookException("Person not found In the List"));
    }

    @Override
    public AddressBookData addAddressBookData(AddressBookDTO addressBookDTO) {
        AddressBookData user = modelMapper.map(addressBookDTO, AddressBookData.class);
        int otps = (int) Math.floor(Math.random() * 1000000);
        String otp = String.valueOf(otps);
        UserNameOtpData userNameOtp = new UserNameOtpData(addressBookDTO.username, otp);
        iUserNameOtpRepository.save(userNameOtp);
        senderService.sendEmail(user.getEmail(), "OTP for Registration", otp);
        return iAddressBookRepository.save(user);
    }


    @Override
    public AddressBookData updateAddressBookData(long personId, AddressBookDTO addressBookDTO) {
        AddressBookData addressBookData = this.getAddressBookDataById(personId);
        modelMapper.map(addressBookDTO, addressBookData);
        int otps = (int) Math.floor(Math.random() * 1000000);
        String otp = String.valueOf(otps);
        UserNameOtpData userNameOtp = new UserNameOtpData(addressBookDTO.username, otp);
        iUserNameOtpRepository.save(userNameOtp);
        senderService.sendEmail(addressBookData.getEmail(), "OTP for Updating Details.", otp);
        iAddressBookRepository.save(addressBookData);
        return addressBookData;
    }

    @Override
    public void deleteAddressBookData(long personId) {
        AddressBookData addressBookData = this.getAddressBookDataById(personId);
        iAddressBookRepository.delete(addressBookData);
    }

    @Override
    public Boolean verifyOtp(String username, String otp) {
        UserNameOtpData serverOtp = iUserNameOtpRepository.findByUsername(username);

        if (otp == null)
            return false;
        if (!(otp.equals(serverOtp.getOtp())))
            return false;
        iAddressBookRepository.changeVerified(username);
        iUserNameOtpRepository.deleteEntry(username);
        return true;
    }

    @Override
    public Boolean isVerified(String username) {
        return iAddressBookRepository.isVerified(username);
    }

    @Override
    public Boolean verifyEmail(String username, String email) {
        AddressBookData addressBookData = iAddressBookRepository.findByUsername(username);
        return addressBookData.getEmail().equals(email);
    }

    @Override
    public void sendOtpForCreatingPassword(String username, String email) {
        int otps = (int) Math.floor(Math.random() * 1000000);
        String otp = String.valueOf(otps);
        senderService.sendEmail(email, "OTP for Updating Details.", otp);
        UserNameOtpData userNameOtp = new UserNameOtpData(username, otp);
        iUserNameOtpRepository.save(userNameOtp);
    }
}
