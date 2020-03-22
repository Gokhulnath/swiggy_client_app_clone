package golhar.cocomo.zinger.model;


import android.os.Parcel;
import android.os.Parcelable;

import golhar.cocomo.zinger.enums.UserRole;

public class UserModel implements Parcelable {
    private String mobile;
    private String name;
    private String email;
    private String oauthId;
    private UserRole role;
    private Integer isDelete;

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        mobile = in.readString();
        name = in.readString();
        email = in.readString();
        oauthId = in.readString();
        role = role.valueOf(in.readString());
        if (in.readByte() == 0) {
            isDelete = null;
        } else {
            isDelete = in.readInt();
        }
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", oauthId='" + oauthId + '\'' +
                ", role=" + role +
                ", isDelete=" + isDelete +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobile);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(oauthId);
        dest.writeString(role.name());
        if (isDelete == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isDelete);
        }
    }
}
