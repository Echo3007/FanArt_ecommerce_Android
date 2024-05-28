/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

public class ReadWriteUserDetails {
    public String username, fullname, gender, address, city,  postcode,country, dob,
            acc_name, acc_num, acc_exp_date, acc_cvv_num, profilePicture;

    public ReadWriteUserDetails(String username,String fullname, String gender, String address, String city,
                                String postcode, String country, String dob, String acc_name, String acc_num,
                                String acc_exp_date, String acc_cvv_num, String profilePicture){
        this.username = username;
        this.fullname = fullname;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.dob = dob;
        this.acc_name = acc_name;
        this.acc_num = acc_num;
        this.acc_exp_date = acc_exp_date;
        this.acc_cvv_num = acc_cvv_num;
        this.profilePicture = profilePicture;


    }
    public ReadWriteUserDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(ReadWriteUserDetails.class)
    }

}
