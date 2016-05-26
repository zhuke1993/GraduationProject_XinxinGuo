package com.gxx.nqh.dto;

/**
 * 贷款资质审核页面dto
 * Created by GXX on 2016/4/8.
 */
public class ProfileDto {
    private int isAvailable;
    private String userName;
    private String realName;
    private String idCardNo;
    private String schoolName;
    private String address;
    private String schoolCertification;
    private String idCertification;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchoolCertification() {
        return schoolCertification;
    }

    public void setSchoolCertification(String schoolCertification) {
        this.schoolCertification = schoolCertification;
    }

    public String getIdCertification() {
        return idCertification;
    }

    public void setIdCertification(String idCertification) {
        this.idCertification = idCertification;
    }

    public ProfileDto(int isAvailable, String userName, String realName, String idCardNo, String schoolName, String address, String schoolCertification, String idCertification) {
        this.isAvailable = isAvailable;
        this.userName = userName;
        this.realName = realName;
        this.idCardNo = idCardNo;
        this.schoolName = schoolName;
        this.address = address;
        this.schoolCertification = schoolCertification;
        this.idCertification = idCertification;
    }
}
