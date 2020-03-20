package golhar.cocomo.zinger.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class ShopModel implements Parcelable {
    private Integer id;
    private String name;
    private String photoUrl;
    private String mobile;
    private CollegeModel collegeModel;
    private Date openingTime;
    private Date closingTime;
    private Integer isDelete;

    public ShopModel() {
        this.collegeModel = new CollegeModel();
    }

    protected ShopModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        photoUrl = in.readString();
        mobile = in.readString();
        collegeModel = in.readParcelable(CollegeModel.class.getClassLoader());
        openingTime = (java.util.Date) in.readSerializable();
        closingTime = (java.util.Date) in.readSerializable();
        if (in.readByte() == 0) {
            isDelete = null;
        } else {
            isDelete = in.readInt();
        }
    }

    public static final Creator<ShopModel> CREATOR = new Creator<ShopModel>() {
        @Override
        public ShopModel createFromParcel(Parcel in) {
            return new ShopModel(in);
        }

        @Override
        public ShopModel[] newArray(int size) {
            return new ShopModel[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public CollegeModel getCollegeModel() {
        return collegeModel;
    }

    public void setCollegeModel(CollegeModel collegeModel) {
        this.collegeModel = collegeModel;
    }

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "ShopModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", mobile='" + mobile + '\'' +
                ", collegeModel=" + collegeModel +
                ", openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                ", isDelete=" + isDelete +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(photoUrl);
        dest.writeString(mobile);
        dest.writeParcelable(collegeModel, flags);
        dest.writeSerializable(openingTime);
        dest.writeSerializable(closingTime);
        if (isDelete == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isDelete);
        }
    }
}
