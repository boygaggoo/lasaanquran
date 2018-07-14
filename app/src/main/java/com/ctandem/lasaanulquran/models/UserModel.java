package com.ctandem.lasaanulquran.models;

//@Entity(tableName = "users")
public class UserModel{}/* implements Parcelable {

    @PrimaryKey
    private long userId;
    private String email;
    private String phoneNumber;
    private String workPhoneNumber;
    private String firstName;
    private String lastName;
    private String birthday;
    private String gender;
    private String photoURL;
    private int countryId;
    private int cityId;
    private int districtId;
    private String countryName;
    private String cityName;
    private String districtName;
    private String zipCode;
    private String about;
    private int isVerified;
    private int isPhoneVerified;
    private int isEmailVerified;
    private String secretKey;

    public UserModel() {

    }

    protected UserModel(Parcel in) {
        userId = in.readLong();
        email = in.readString();
        phoneNumber = in.readString();
        workPhoneNumber = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        birthday = in.readString();
        gender = in.readString();
        photoURL = in.readString();
        countryId = in.readInt();
        cityId = in.readInt();
        districtId = in.readInt();
        countryName = in.readString();
        cityName = in.readString();
        districtName = in.readString();
        zipCode = in.readString();
        about = in.readString();
        isVerified = in.readInt();
        isPhoneVerified = in.readInt();
        isEmailVerified = in.readInt();
        secretKey = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public int getIsPhoneVerified() {
        return isPhoneVerified;
    }

    public void setIsPhoneVerified(int isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
    }

    public int getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(int isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(userId);
        parcel.writeString(email);
        parcel.writeString(phoneNumber);
        parcel.writeString(workPhoneNumber);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(birthday);
        parcel.writeString(gender);
        parcel.writeString(photoURL);
        parcel.writeInt(countryId);
        parcel.writeInt(cityId);
        parcel.writeInt(districtId);
        parcel.writeString(countryName);
        parcel.writeString(cityName);
        parcel.writeString(districtName);
        parcel.writeString(zipCode);
        parcel.writeString(about);
        parcel.writeInt(isVerified);
        parcel.writeInt(isPhoneVerified);
        parcel.writeInt(isEmailVerified);
        parcel.writeString(secretKey);
    }
}
*/